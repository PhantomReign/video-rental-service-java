package com.videorentalservice.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.videorentalservice.models.QRole;
import com.videorentalservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

/**
 * Created by Rave on 18.02.2017.
 */
public interface RoleRepository extends JpaRepository<Role, Integer>,
        QueryDslPredicateExecutor<Role>,
        QuerydslBinderCustomizer<QRole> {

    Role getByName(String name);

    @Override
    default public void customize(QuerydslBindings bindings, QRole root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
