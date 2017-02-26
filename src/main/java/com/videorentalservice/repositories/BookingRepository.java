package com.videorentalservice.repositories;

import com.videorentalservice.models.Booking;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Rave on 26.02.2017.
 */
public interface BookingRepository extends CrudRepository<Booking, Integer> {
}
