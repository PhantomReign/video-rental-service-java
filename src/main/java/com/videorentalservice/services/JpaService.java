package com.videorentalservice.services;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Rave on 18.02.2017.
 */
public interface JpaService<T> {
    List<?> listAll();

    Page<T> findAll(Predicate predicate, Pageable pageable);

    T getById(Integer id);

    T saveOrUpdate(T domainObject);

    void delete(Integer id);
}