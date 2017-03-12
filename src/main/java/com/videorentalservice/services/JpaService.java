package com.videorentalservice.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Rave on 18.02.2017.
 */
public interface JpaService<T> {
    List<?> listAll();

    Page<T> listAllByPage(Pageable pageable);

    T getById(Integer id);

    T saveOrUpdate(T domainObject);

    void delete(Integer id);
}