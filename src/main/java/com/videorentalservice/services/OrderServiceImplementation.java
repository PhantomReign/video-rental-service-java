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
        List<Disc> persistentDiscs = new ArrayList<>();;
        for (Disc d : discs) {
            persistentDiscs.add(discRepository.getOne(d.getId()));
        }

        orderObject.setDiscs(persistentDiscs);

        return orderRepository.save(orderObject);
    }

    @Override
    public Order update(Order orderObject) {
        return orderRepository.save(orderObject);
    }

    @Override
    public void delete(Integer id) {
        orderRepository.delete(id);
    }

}
