package com.videorentalservice.services;

import com.videorentalservice.models.Role;

/**
 * Created by Rave on 18.02.2017.
 */
public interface RoleService extends JpaService<Role> {
    Role getByName(String name);
}
