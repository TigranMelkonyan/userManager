package com.iguan.demo.usermanager.model.rest.response.admin;

import com.iguan.demo.usermanager.domain.entity.user.User;
import com.iguan.demo.usermanager.model.rest.response.role.RoleResponse;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Tigran Melkonyan
 * Date: 01/09/2021
 * Time: 19:42
 */
public class UserResponse {

    private UUID id;
    private String userName;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean active;
    private List<RoleResponse> roles;


    public UserResponse(UUID id, String userName, String firstName, String lastName, String phone, boolean active, List<RoleResponse> roles) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.active = active;
        this.roles = roles;
    }

    public static UserResponse from(final User entity) {
        List<RoleResponse> roles = entity.getRoles()
                .stream().map(RoleResponse::from)
                .collect(Collectors.toList());
        return new UserResponse(
                entity.getId(), entity.getUsername(),entity.getFirstName(),
                entity.getLastName(), entity.getPhone(),
                entity.isActive(), roles
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<RoleResponse> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResponse> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserResponse that = (UserResponse) o;

        return new EqualsBuilder()
                .append(active, that.active)
                .append(id, that.id)
                .append(userName, that.userName)
                .append(roles, that.roles)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(userName)
                .append(active)
                .append(roles)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userName", userName)
                .append("active", active)
                .append("roles", roles)
                .toString();
    }
}
