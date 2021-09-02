package com.iguan.demo.usermanager.model.rest.request;

import com.iguan.demo.usermanager.config.security.password.ValidPassword;
import com.iguan.demo.usermanager.domain.entity.user.User;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 23:29
 */
public class UserCreateRequest {

    @NotEmpty(message = "required")
    @Size(min = 1, max = 100)
    private String userName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String phone;

    @NotEmpty(message = "required")
    @ValidPassword
    @Size(min = 8, max = 20)
    private String password;

    @NotEmpty(message = "required")
    @ValidPassword
    @Size(min = 8, max = 20)
    private String matchingPassword;

    public UserCreateRequest() {
    }

    public User toEntity() {
        return new User(userName, password, firstName, lastName, phone);
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

        UserCreateRequest that = (UserCreateRequest) o;

        return new EqualsBuilder()
                .append(userName, that.userName)
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(phone, that.phone)
                .append(password, that.password)
                .append(matchingPassword, that.matchingPassword)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userName)
                .append(firstName)
                .append(lastName)
                .append(phone)
                .append(password)
                .append(matchingPassword)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userName", userName)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("phone", phone)
                .append("password", password)
                .append("matchingPassword", matchingPassword)
                .toString();
    }
}
