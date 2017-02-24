package com.videorentalservice.repositories;

import com.videorentalservice.models.Genre;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Rave on 19.02.2017.
 */
public interface GenreRepository extends CrudRepository<Genre, Integer> {
}
