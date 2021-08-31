package com.iguan.demo.usermanager.controller.user;

import com.iguan.demo.usermanager.controller.AbstractController;
import com.iguan.demo.usermanager.domain.entity.user.User;
import com.iguan.demo.usermanager.model.user.UserCreateModel;
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
@RequestMapping("api/users")
@Api("UserAdmin-Controller")
public class UserAdminController extends AbstractController {

    private final UserService userService;

    public UserAdminController(UserService userService, Validator validator) {
        super(validator);
        this.userService = userService;
    }

    @PostMapping("create")
    @ApiOperation(value = "create user", response = User.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> createUser(
            @RequestHeader("Authorization") final String token,
            final UserCreateModel model) {
        validator(model);
        return ResponseEntity.ok(userService.createUser(model));
    }

    @PostMapping("{id}/activate")
    @ApiOperation(value = "activate account", response = User.class)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void activateAccount(
            @RequestHeader("Authorization") final String token,
            @PathVariable("id") final UUID userId
    ) {
        userService.setActive(userId);
    }

    @PutMapping("{id}/close")
    @ApiOperation(value = "close account", response = User.class)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void closeAccount(
            @RequestHeader("Authorization") final String token,
            @PathVariable("id") final UUID userId
    ) {

        userService.close(userId);
    }

    @DeleteMapping("{id}/delete")
    @ApiOperation(value = "delete user", response = User.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(
            @RequestHeader("Authorization") final String token,
            @PathVariable("id") final UUID userId
    ) {
        userService.delete(userId);
    }

    @GetMapping("{id}/get")
    @ApiOperation(value = "get user by id", response = User.class)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<User> getByUserId(
            @RequestHeader("Authorization") final String token,
            @PathVariable("id") final UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    @ApiOperation(value = "get user by name", response = User.class)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<User> getByUserName(
            @RequestHeader("Authorization") final String token,
            final String userName) {
        return ResponseEntity.ok(userService.getByUserName(userName));
    }
}
