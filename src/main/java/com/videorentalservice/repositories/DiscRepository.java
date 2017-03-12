package com.videorentalservice.repositories;

import com.videorentalservice.models.Disc;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Rave on 18.02.2017.
 */
public interface DiscRepository extends JpaRepository<Disc, Integer> {
}
