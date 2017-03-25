package com.videorentalservice.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.videorentalservice.models.QUser;
import com.videorentalservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;


/**
 * Created by Rave on 18.02.2017.
 */
public interface UserRepository
        extends JpaRepository<User, Integer>,
        QueryDslPredicateExecutor<User>,
        QuerydslBinderCustomizer<QUser> {

    User getByEmail(String email);
    User getByUserName(String userName);

    @Override
    default public void customize(QuerydslBindings bindings, QUser root) {

        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
