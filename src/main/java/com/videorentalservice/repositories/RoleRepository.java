package com.videorentalservice.repositories;

import com.videorentalservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Rave on 18.02.2017.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
