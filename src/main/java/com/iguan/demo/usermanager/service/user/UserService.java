package com.iguan.demo.usermanager.service.user;

import com.iguan.demo.usermanager.domain.entity.user.User;
import com.iguan.demo.usermanager.model.common.PageModel;
import com.iguan.demo.usermanager.model.user.UserCreateModel;
import com.iguan.demo.usermanager.model.user.UserSearchProperties;
import com.iguan.demo.usermanager.model.user.UserUpdateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 23:12
 */
public interface UserService {

    User createUser(final UserCreateModel model);

    User getUserById(final UUID id);

    User getByUserName(final String name);

    PageModel<User> search(final UserSearchProperties searchProperties);

    User updateUser(final UUID id, final UserUpdateModel model);

    void close(final UUID id);

    void checkIfExistsById(final UUID id);

    User setActive(final UUID id);

    User getActiveUserById(final UUID id);

    void delete(final UUID id);

}
