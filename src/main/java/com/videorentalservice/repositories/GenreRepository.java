package com.videorentalservice.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.videorentalservice.models.Genre;
import com.videorentalservice.models.QGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

/**
 * Created by Rave on 19.02.2017.
 */
public interface GenreRepository extends JpaRepository<Genre, Integer>,
        QueryDslPredicateExecutor<Genre>,
        QuerydslBinderCustomizer<QGenre> {

    @Override
    default public void customize(QuerydslBindings bindings, QGenre root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
