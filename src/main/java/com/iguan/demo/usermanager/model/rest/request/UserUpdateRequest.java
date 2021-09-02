package com.iguan.demo.usermanager.model.rest.request;

import com.iguan.demo.usermanager.domain.entity.user.User;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 23:29
 */
public class UserUpdateRequest {

    @NotEmpty(message = "required")
    @Size(min = 1, max = 100)
    private String firstName;

    @NotEmpty(message = "required")
    @Size(min = 1, max = 100)
    private String lastName;

    private String phone;

    public UserUpdateRequest() {
    }

    public User toEntity(User entity) {
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setPhone(phone);
        return entity;
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

        UserUpdateRequest that = (UserUpdateRequest) o;

        return new EqualsBuilder()
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(phone, that.phone)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(firstName)
                .append(lastName)
                .append(phone)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("phone", phone)
                .toString();
    }
}
