package com.videorentalservice.repositories;

import com.videorentalservice.domain.Disc;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Rave on 18.02.2017.
 */
public interface DiscRepository extends CrudRepository<Disc, Integer> {
}
