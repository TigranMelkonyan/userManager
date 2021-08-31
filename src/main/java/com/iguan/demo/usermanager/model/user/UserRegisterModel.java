package com.iguan.demo.usermanager.model.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 19:46
 */
public class UserRegisterModel {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String matchingPassword;

    public UserRegisterModel() {
    }

    public UserRegisterModel(
            String firstName, String lastName,
            String username, String matchingPassword, String password) {
        this.username = username;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserRegisterModel that = (UserRegisterModel) o;

        return new EqualsBuilder()
                .append(username, that.username)
                .append(password, that.password)
                .append(matchingPassword, that.matchingPassword)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(username)
                .append(password)
                .append(matchingPassword)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("username", username)
                .append("password", password)
                .append("matchingPassword", matchingPassword)
                .toString();
    }
}
