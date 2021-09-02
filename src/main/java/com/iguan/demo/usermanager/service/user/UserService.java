package com.iguan.demo.usermanager.service.user;

import com.iguan.demo.usermanager.domain.entity.user.User;
import com.iguan.demo.usermanager.model.common.PageModel;
import com.iguan.demo.usermanager.model.rest.request.UserCreateRequest;
import com.iguan.demo.usermanager.model.rest.response.admin.UserResponse;
import com.iguan.demo.usermanager.model.user.UserSearchProperties;
import com.iguan.demo.usermanager.model.rest.request.UserUpdateRequest;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 23:12
 */
public interface UserService {

    UserResponse createUser(final UserCreateRequest model);

    UserResponse getUserById(final UUID id);

    UserResponse getByUserName(final String name);

    PageModel<UserResponse> search(final UserSearchProperties searchProperties);

    UserResponse updateUser(final UUID id, final UserUpdateRequest model);

    void close(final UUID id);

    UserResponse setActive(final UUID id);

    UserResponse getActiveUserById(final UUID id);

    void delete(final UUID id);

}
