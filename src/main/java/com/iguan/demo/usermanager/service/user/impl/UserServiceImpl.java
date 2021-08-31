package com.iguan.demo.usermanager.service.user.impl;

import com.iguan.demo.usermanager.domain.entity.user.User;
import com.iguan.demo.usermanager.exceptions.RecordConflictException;
import com.iguan.demo.usermanager.exceptions.error.ErrorCode;
import com.iguan.demo.usermanager.model.common.PageModel;
import com.iguan.demo.usermanager.model.user.UserCreateModel;
import com.iguan.demo.usermanager.model.user.UserSearchProperties;
import com.iguan.demo.usermanager.model.user.UserUpdateModel;
import com.iguan.demo.usermanager.repository.user.UserRepository;
import com.iguan.demo.usermanager.service.user.UserService;
import com.iguan.demo.usermanager.service.user.validator.ModelValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

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

    public UserServiceImpl(UserRepository userRepository, ModelValidator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Transactional
    @Override
    public User createUser(UserCreateModel model) {
        LOGGER.trace("Create user for model - {}", model);
        assertNotExistsByName(model.getUserName());
        User user = userRepository.save(model.toEntity());
        LOGGER.info("Successfully created user - {}", user);
        return user;
    }

    @Transactional
    @Override
    public User getUserById(UUID id) {
        LOGGER.info("Retrieving User with id - {}", id);
        Assert.notNull(id, "id cannot be null");
        User user = userRepository.findById(id).orElseThrow(() ->
                new RecordConflictException("User not exists", ErrorCode.NOT_EXISTS_EXCEPTION));
        LOGGER.info("Successfully retrieved User for id - {}, result - {}", id, user);
        return user;
    }

    @Transactional
    @Override
    public PageModel<User> search(UserSearchProperties searchProperties) {
        return userRepository.search(searchProperties);
    }

    @Transactional
    @Override
    public User updateUser(UUID id, UserUpdateModel model) {
        LOGGER.info("Creating User for model - {}", model);
        validator.validate(model);
        assertNotExistsByName(model.getUserName());
        User user = userRepository.save(model.toEntity());
        LOGGER.info("Successfully created user - {}", user);
        return user;
    }

    @Transactional
    @Override
    public void close(UUID id) {
        LOGGER.info("Closing user account with id - {} ", id);
        Assert.notNull(id, "id cannot be null");
        User user = getActiveUserById(id);
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
    @Transactional
    public void checkIfExistsById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RecordConflictException("User not exists", ErrorCode.NOT_EXISTS_EXCEPTION);
        }
    }

    @Override
    public User getByUserName(String name) {
        LOGGER.info("Retrieving User with name - {}", name);
        Assert.hasText(name, "name is required");
        User user = userRepository.findByUsername(name).orElseThrow(() ->
                new RecordConflictException("User not exists", ErrorCode.NOT_EXISTS_EXCEPTION));
        LOGGER.info("Successfully retrieved User for id - {}, result - {}", name, user);
        return user;
    }

    @Transactional
    @Override
    public User setActive(final UUID id) {
        LOGGER.info(" user account as active with id - {} ", id);
        Assert.notNull(id, "id cannot be null");
        User user = getUserById(id);
        user.setActive(true);
        userRepository.save(user);
        LOGGER.info("Successfully set user account as active with id - {} ", id);
        return user;
    }

    @Transactional
    @Override
    public User getActiveUserById(final UUID id) {
        LOGGER.info("Retrieving user with id - {}", id);
        Assert.notNull(id, "id cannot be null");
        User user = getUserById(id);
        if (!user.isActive()) {
            new RecordConflictException("User not active", ErrorCode.USER_NOT_ACTIVE);
        }
        LOGGER.info("Successfully retrieved user for id - {}, result - {}", id, user);
        return user;
    }

    private void assertNotExistsByName(final String name) {
        if (userRepository.existsByUsername(name)) {
            throw new RecordConflictException
                    (String.format("User with name - %s already exists", name),
                            ErrorCode.USER_ALREADY_EXIST_EXCEPTION);
        }
    }
}
