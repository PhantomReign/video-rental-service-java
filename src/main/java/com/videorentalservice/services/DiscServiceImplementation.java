package com.videorentalservice.services;

import com.videorentalservice.domain.Disc;
import com.videorentalservice.repositories.DiscRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    /*public Iterable<Disc> listAllDiscs() {
        return discRepository.findAll();
    }*/
    public List<?> listAll() {
        List<Disc> discs = new ArrayList<>();
        discRepository.findAll().forEach(discs::add);
        return discs;
    }

    @Override
    /*public Disc getDiscById(Integer id) {
        return discRepository.findOne(id);
    }*/
    public Disc getById(Integer id) {
        return discRepository.findOne(id);
    }

    @Override
    /*public Disc saveDisc(Disc disc) {
        return discRepository.save(disc);
    }*/
    public Disc saveOrUpdate(Disc discObject) {
        return discRepository.save(discObject);
    }

    @Override
    /*public void deleteDisc(Integer id) {
        discRepository.delete(id);
    }*/
    public void delete(Integer id) {
        discRepository.delete(id);
    }

}