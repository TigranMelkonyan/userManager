package com.iguan.demo.usermanager.service.role;

import com.iguan.demo.usermanager.domain.entity.role.Role;

/**
 * Created by Tigran Melkonyan
 * Date: 01/09/2021
 * Time: 02:22
 */
public interface RoleService {
    Role findByName(final String name);
}
