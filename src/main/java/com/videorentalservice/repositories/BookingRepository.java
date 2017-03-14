package com.videorentalservice.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.videorentalservice.models.Booking;
import com.videorentalservice.models.QBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

/**
 * Created by Rave on 26.02.2017.
 */
public interface BookingRepository extends JpaRepository<Booking, Integer>,
        QueryDslPredicateExecutor<Booking>,
        QuerydslBinderCustomizer<QBooking> {

    @Override
    default public void customize(QuerydslBindings bindings, QBooking root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
