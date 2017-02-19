package com.videorentalservice.services;

import java.util.List;

/**
 * Created by Rave on 18.02.2017.
 */
public interface CRUDService<T> {
    List<?> listAll();

    T getById(Integer id);

    T saveOrUpdate(T domainObject);

    void delete(Integer id);
}