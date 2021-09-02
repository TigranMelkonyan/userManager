package com.iguan.demo.usermanager.repository.role;

import com.iguan.demo.usermanager.domain.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 01/09/2021
 * Time: 02:25
 */
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(final String name);
}
