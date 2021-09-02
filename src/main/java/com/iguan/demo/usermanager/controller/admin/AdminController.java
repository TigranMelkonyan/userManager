package com.iguan.demo.usermanager.controller.admin;

import com.iguan.demo.usermanager.controller.AbstractController;
import com.iguan.demo.usermanager.model.common.PageModel;
import com.iguan.demo.usermanager.model.rest.request.UserCreateRequest;
import com.iguan.demo.usermanager.model.rest.request.UserUpdateRequest;
import com.iguan.demo.usermanager.model.rest.request.search.AdminUserSearchRequest;
import com.iguan.demo.usermanager.model.rest.response.admin.UserResponse;
import com.iguan.demo.usermanager.model.rest.response.common.PageResponse;
import com.iguan.demo.usermanager.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validator;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 19:44
 */
@RestController
@RequestMapping("api/admin/users")
@Api("AdminManager-Controller")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController extends AbstractController {

    private final UserService userService;

    public AdminController(UserService userService, Validator validator) {
        super(validator);
        this.userService = userService;
    }

    @PostMapping("create")
    @ApiOperation(value = "create user", response = UserResponse.class)
    public ResponseEntity<UserResponse> createUser(
            @RequestHeader("Authorization") final String token,
            final UserCreateRequest model) {
        validator(model);
        return respondOK(userService.createUser(model));
    }

    @PostMapping("{id}/activate")
    @ApiOperation(value = "activate user")
    public void activateUser(
            @RequestHeader("Authorization") final String token,
            @PathVariable("id") final UUID userId
    ) {
        userService.setActive(userId);
    }

    @PutMapping("{id}/close")
    @ApiOperation(value = "close user")
    public void closeUser(
            @RequestHeader("Authorization") final String token,
            @PathVariable("id") final UUID userId
    ) {

        userService.close(userId);
    }

    @DeleteMapping("{id}/delete")
    @ApiOperation(value = "delete user")
    public void deleteUser(
            @RequestHeader("Authorization") final String token,
            @PathVariable("id") final UUID userId
    ) {
        userService.delete(userId);
    }

    @GetMapping("{id}/get")
    @ApiOperation(value = "get user by id", response = UserResponse.class)
    public ResponseEntity<UserResponse> getByUserId(
            @RequestHeader("Authorization") final String token,
            @PathVariable("id") final UUID userId) {
        return respondOK(userService.getUserById(userId));
    }

    @GetMapping
    @ApiOperation(value = "get user by name", response = UserResponse.class)
    public ResponseEntity<UserResponse> getByUserName(
            @RequestHeader("Authorization") final String token,
            final String userName) {
        return respondOK(userService.getByUserName(userName));
    }

    @PutMapping("{id}/update")
    @ApiOperation(value = "update user", response = UserResponse.class)
    public ResponseEntity<UserResponse> updateUser(
            @RequestHeader("Authorization") final String token,
            @PathVariable("id") final UUID userId,
            final UserUpdateRequest model
    ) {
        validator(model);
        return respondOK(userService.updateUser(userId, model));
    }

    @GetMapping("search")
    @ApiOperation(value = "Search users", response = PageResponse.class)
    public ResponseEntity<PageResponse> searchUsers(
            @RequestHeader("Authorization") final String token,
            final AdminUserSearchRequest searchRequest
    ) {
        final PageModel<UserResponse> userPage = userService.search(searchRequest.toUserSearchProperties());
        return respondOK(new PageResponse(userPage
                .getItems(), userPage.getTotalCount()));
    }
}
