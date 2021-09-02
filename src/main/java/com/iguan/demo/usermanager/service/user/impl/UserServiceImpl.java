package com.iguan.demo.usermanager.service.user.impl;

import com.iguan.demo.usermanager.domain.entity.user.User;
import com.iguan.demo.usermanager.exceptions.RecordConflictException;
import com.iguan.demo.usermanager.exceptions.error.ErrorCode;
import com.iguan.demo.usermanager.model.common.PageModel;
import com.iguan.demo.usermanager.model.rest.request.UserCreateRequest;
import com.iguan.demo.usermanager.model.rest.response.admin.UserResponse;
import com.iguan.demo.usermanager.model.user.UserSearchProperties;
import com.iguan.demo.usermanager.model.rest.request.UserUpdateRequest;
import com.iguan.demo.usermanager.repository.user.UserRepository;
import com.iguan.demo.usermanager.service.role.RoleService;
import com.iguan.demo.usermanager.service.user.UserService;
import com.iguan.demo.usermanager.service.validator.ModelValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Tigran Melkonyan
 * Date: 30/08/2021
 * Time: 20:35
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelValidator validator;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelValidator validator, RoleService roleService) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public UserResponse createUser(UserCreateRequest model) {
        LOGGER.trace("Create user for model - {}", model);
        assertNotExistsByName(model.getUserName());
        User user = model.toEntity();
        user.setRoles(Collections.singletonList(roleService.findByName("USER")));
        userRepository.save(user);
        LOGGER.info("Successfully created user - {}", user);
        return UserResponse.from(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUserById(UUID id) {
        LOGGER.info("Retrieving User with id - {}", id);
        Assert.notNull(id, "id cannot be null");
        User user = userRepository.findById(id).orElseThrow(() ->
                new RecordConflictException("User not exists", ErrorCode.NOT_EXISTS_EXCEPTION));
        LOGGER.info("Successfully retrieved User for id - {}, result - {}", id, user);
        return UserResponse.from(user);
    }

    private User getUserEntityById(UUID id) {
        LOGGER.info("Retrieving User with id - {}", id);
        Assert.notNull(id, "id cannot be null");
        User user = userRepository.findById(id).orElseThrow(() ->
                new RecordConflictException("User not exists", ErrorCode.NOT_EXISTS_EXCEPTION));
        LOGGER.info("Successfully retrieved User for id - {}, result - {}", id, user);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public PageModel<UserResponse> search(UserSearchProperties searchProperties) {
        PageModel<User> usersPage = userRepository.search(searchProperties);
        return new PageModel<>(
                usersPage.getItems()
                        .stream()
                        .map(UserResponse::from)
                        .collect(Collectors.toList()),
                usersPage.getTotalCount());
    }

    @Transactional
    @Override
    public UserResponse updateUser(UUID id, UserUpdateRequest model) {
        LOGGER.info("Creating User for model - {}", model);
        validator.validate(model);
        User user = getUserEntityById(id);
        user = userRepository.save(model.toEntity(user));
        LOGGER.info("Successfully created user - {}", user);
        return UserResponse.from(user);
    }

    @Transactional
    @Override
    public void close(UUID id) {
        LOGGER.info("Closing user account with id - {} ", id);
        Assert.notNull(id, "id cannot be null");
        User user = getUserEntityById(id);
        user.setActive(false);
        userRepository.save(user);
        LOGGER.info("Successfully closed user account with id - {} ", id);
    }

    @Transactional
    @Override
    public void delete(final UUID id) {
        LOGGER.info("Closing user account with id - {} ", id);
        Assert.notNull(id, "id cannot be null");
        userRepository.deleteById(id);
        LOGGER.info("Successfully deleted user with id - {} ", id);
    }

    @Override
    public UserResponse getByUserName(String name) {
        LOGGER.info("Retrieving User with name - {}", name);
        Assert.hasText(name, "name is required");
        User user = userRepository.findByUsername(name).orElseThrow(() ->
                new RecordConflictException("User not exists", ErrorCode.NOT_EXISTS_EXCEPTION));
        LOGGER.info("Successfully retrieved User for id - {}, result - {}", name, user);
        return UserResponse.from(user);
    }

    @Transactional
    @Override
    public UserResponse setActive(final UUID id) {
        LOGGER.info(" user account as active with id - {} ", id);
        Assert.notNull(id, "id cannot be null");
        User user = getUserEntityById(id);
        user.setActive(true);
        userRepository.save(user);
        LOGGER.info("Successfully set user account as active with id - {} ", id);
        return UserResponse.from(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getActiveUserById(final UUID id) {
        LOGGER.info("Retrieving user with id - {}", id);
        Assert.notNull(id, "id cannot be null");
        User user = getUserEntityById(id);
        if (!user.isActive()) {
            throw new RecordConflictException("User not active", ErrorCode.USER_NOT_ACTIVE);
        }
        LOGGER.info("Successfully retrieved user for id - {}, result - {}", id, user);
        return UserResponse.from(user);
    }

    private void assertNotExistsByName(final String name) {
        if (userRepository.existsByUsername(name)) {
            throw new RecordConflictException
                    (String.format("User with name - %s already exists", name),
                            ErrorCode.USER_ALREADY_EXIST_EXCEPTION);
        }
    }
}
