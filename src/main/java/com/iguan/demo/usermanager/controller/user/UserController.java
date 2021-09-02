package com.iguan.demo.usermanager.controller.user;

import com.iguan.demo.usermanager.controller.AbstractController;
import com.iguan.demo.usermanager.exceptions.NoPermissionException;
import com.iguan.demo.usermanager.exceptions.error.ErrorCode;
import com.iguan.demo.usermanager.model.common.PageModel;
import com.iguan.demo.usermanager.model.rest.request.UserUpdateRequest;
import com.iguan.demo.usermanager.model.rest.request.search.UserSearchRequest;
import com.iguan.demo.usermanager.model.rest.response.admin.UserResponse;
import com.iguan.demo.usermanager.model.rest.response.common.PageResponse;
import com.iguan.demo.usermanager.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validator;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 19:44
 */
@RestController
@RequestMapping("api/users")
@Api("UserManager-Controller")
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
public class UserController extends AbstractController {

    private final UserService userService;

    public UserController(UserService userService, Validator validator) {
        super(validator);
        this.userService = userService;
    }

    @PutMapping("close")
    @ApiOperation(value = "close user")
    public void closeUser(@RequestHeader("Authorization") final String token) {
        String userName = getCurrentAuditor().orElseThrow(() -> new NoPermissionException("No Permission", ErrorCode.NO_PERMISSION));
        UserResponse user = userService.getByUserName(userName);
        userService.close(user.getId());
    }

    @PutMapping
    @ApiOperation(value = "update user", response = UserResponse.class)
    public ResponseEntity<UserResponse> updateUser(
            @RequestHeader("Authorization") final String token,
            final UserUpdateRequest model) {
        validator(model);
        String userName = getCurrentAuditor().orElseThrow(() -> new NoPermissionException("No Permission", ErrorCode.NO_PERMISSION));
        UserResponse user = userService.getByUserName(userName);
        return respondOK(userService.updateUser(user.getId(), model));
    }

    @GetMapping("search")
    @ApiOperation(value = "Search users", response = PageResponse.class)
    public ResponseEntity<PageResponse> search(
            @RequestHeader("Authorization") final String token,
            final UserSearchRequest searchRequest
    ) {
        final PageModel<UserResponse> userPage = userService.search(searchRequest.toUserSearchProperties());
        return respondOK(new PageResponse(userPage
                .getItems(), userPage.getTotalCount()));
    }
}
