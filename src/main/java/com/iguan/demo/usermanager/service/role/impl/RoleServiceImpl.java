package com.iguan.demo.usermanager.service.role.impl;

import com.iguan.demo.usermanager.domain.entity.role.Role;
import com.iguan.demo.usermanager.exceptions.RecordConflictException;
import com.iguan.demo.usermanager.exceptions.error.ErrorCode;
import com.iguan.demo.usermanager.repository.role.RoleRepository;
import com.iguan.demo.usermanager.service.role.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by Tigran Melkonyan
 * Date: 01/09/2021
 * Time: 02:23
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Role findByName(final String name) {
        LOGGER.trace("Retrieve role by name - {} ", name);
        Assert.hasText(name, "name cannot be null");
        final Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new RecordConflictException(
                        String.format("Role with name - %s not exists", name),
                        ErrorCode.NOT_EXISTS_EXCEPTION
                ));
        LOGGER.info("Successfully retrieved Role by name - {}, result - {} ", name, role);
        return role;
    }
}
