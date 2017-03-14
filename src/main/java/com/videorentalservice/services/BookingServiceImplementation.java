package com.videorentalservice.services;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.models.Booking;
import com.videorentalservice.repositories.BookingRepository;
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
public class BookingServiceImplementation implements BookingService {

    private BookingRepository bookingRepository;

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<?> listAll() {
        List<Booking> bookings = new ArrayList<>();
        bookingRepository.findAll().forEach(bookings::add);
        return bookings;
    }

    @Override
    public Page<Booking> findAll(Predicate predicate, Pageable pageable) {
        return bookingRepository.findAll(predicate, pageable);
    }

    @Override
    public Booking getById(Integer id) {
        return bookingRepository.findOne(id);
    }

    @Override
    public Booking saveOrUpdate(Booking bookingObject) {
        return bookingRepository.save(bookingObject);
    }

    @Override
    public void delete(Integer id) {
        bookingRepository.delete(id);
    }

}
