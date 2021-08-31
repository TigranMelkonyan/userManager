package com.iguan.demo.usermanager.model.user;

import com.iguan.demo.usermanager.config.security.password.ValidPassword;
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
public class UserUpdateModel {

    @NotEmpty(message = "required")
    @Size(min = 1, max = 100)
    private String userName;

    @NotEmpty(message = "required")
    @ValidPassword
    @Size(min = 8, max = 20)
    private String password;

    public UserUpdateModel() {
    }

    public User toEntity() {
        return new User(userName, password);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserUpdateModel that = (UserUpdateModel) o;

        return new EqualsBuilder()
                .append(userName, that.userName)
                .append(password, that.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userName)
                .append(password)
                .toHashCode();
    }

    @Override
    public String
    toString() {
        return new ToStringBuilder(this)
                .append("userName", userName)
                .append("password", password)
                .toString();
    }
}
