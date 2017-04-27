package com.videorentalservice.services;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.models.Disc;
import com.videorentalservice.models.Order;
import com.videorentalservice.repositories.DiscRepository;
import com.videorentalservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rave on 26.02.2017.
 */
@Service
public class OrderServiceImplementation implements OrderService {

    private OrderRepository orderRepository;
    private DiscRepository discRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setDiscRepository(DiscRepository discRepository) {
        this.discRepository = discRepository;
    }

    @Override
    public List<?> listAll() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }

    @Override
    public Page<Order> findAll(Predicate predicate, Pageable pageable) {
        return orderRepository.findAll(predicate, pageable);
    }

    @Override
    public Order getById(Integer id) {
        return orderRepository.findOne(id);
    }

    @Override
    public Order save(Order orderObject) {

        List<Disc> discs = orderObject.getDiscs();
        List<Disc> persistedDiscs = new ArrayList<>();
        Disc updatedDisc = null;
        if(discs != null){
            for (Disc disc : discs) {
                if(disc.getId() != null)
                {
                    updatedDisc = discRepository.findOne(disc.getId());
                    if (updatedDisc.getAvailable()){
                        updatedDisc.setItemCount(updatedDisc.getItemCount().subtract(BigInteger.ONE));
                        persistedDiscs.add(updatedDisc);
                    }
                }
            }
        }

        orderObject.setDiscs(persistedDiscs);

        return orderRepository.save(orderObject);
    }

    @Override
    public Order update(Order orderObject) {
        Order persistedOrder = getById(orderObject.getId());

        List<Disc> persistedDiscs = persistedOrder.getDiscs();
        List<Disc> newPersistedDiscs = new ArrayList<>();
        Disc updatedDisc = null;

        if(persistedDiscs != null && orderObject.getStatus().equals("Vrátená")){
            for (Disc disc : persistedDiscs) {
                if(disc.getId() != null)
                {
                    updatedDisc = discRepository.findOne(disc.getId());
                    updatedDisc.setItemCount(updatedDisc.getItemCount().add(BigInteger.ONE));
                    newPersistedDiscs.add(updatedDisc);
                }
            }
            persistedOrder.setDiscs(newPersistedDiscs);
        }


        persistedOrder.setStatus(orderObject.getStatus());
        return orderRepository.save(persistedOrder);
    }

    @Override
    public void delete(Integer id) {
        orderRepository.delete(id);
    }

}
