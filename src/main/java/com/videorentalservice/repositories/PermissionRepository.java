package com.videorentalservice.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.videorentalservice.models.Permission;
import com.videorentalservice.models.QPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

/**
 * Created by Rave on 21.03.2017.
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer>,
        QueryDslPredicateExecutor<Permission>,
        QuerydslBinderCustomizer<QPermission> {

    @Override
    default public void customize(QuerydslBindings bindings, QPermission root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
