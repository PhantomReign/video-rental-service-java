package com.videorentalservice.services;

import com.videorentalservice.models.Disc;
import com.videorentalservice.repositories.DiscRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rave on 18.02.2017.
 */
@Service
public class DiscServiceImplementation implements DiscService {
    private DiscRepository discRepository;

    @Autowired
    public void setProductRepository(DiscRepository discRepository) {
        this.discRepository = discRepository;
    }

    @Override
    public List<?> listAll() {
        List<Disc> discs = new ArrayList<>();
        discRepository.findAll().forEach(discs::add);
        return discs;
    }

    @Override
    public Page<Disc> listAllByPage(Pageable pageable) {
        return discRepository.findAll(pageable);
    }

    @Override
    public Disc getById(Integer id) {
        return discRepository.findOne(id);
    }

    @Override
    public Disc saveOrUpdate(Disc discObject) {
        return discRepository.save(discObject);
    }

    @Override
    public void delete(Integer id) {
        discRepository.delete(id);
    }

}