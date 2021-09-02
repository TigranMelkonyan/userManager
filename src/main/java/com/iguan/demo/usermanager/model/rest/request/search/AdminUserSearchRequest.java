package com.iguan.demo.usermanager.model.rest.request.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iguan.demo.usermanager.model.user.UserSearchProperties;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.validation.constraints.Size;

/**
 * Created by Tigran Melkonyan
 * Date: 31/08/2021
 * Time: 23:53
 */
public class AdminUserSearchRequest {

    private String userName;
    private String firstName;
    private String lastName;
    private Boolean includeInActive;

    @Size(min = 0)
    private Integer from = 0;

    @Size(min = 1)
    private Integer size = 50;

    @JsonIgnore
    public UserSearchProperties toUserSearchProperties() {
        UserSearchProperties userSearchProperties = new UserSearchProperties();
        userSearchProperties.setUserName(userName);
        userSearchProperties.setFirstName(firstName);
        userSearchProperties.setLastName(lastName);
        userSearchProperties.setIncludeInActive(includeInActive);
        userSearchProperties.setFrom(from);
        userSearchProperties.setSize(size);
        userSearchProperties.setIncludeHasAdminRole(true);
        return userSearchProperties;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIncludeInActive() {
        return includeInActive;
    }

    public void setIncludeInActive(Boolean includeInActive) {
        this.includeInActive = includeInActive;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AdminUserSearchRequest that = (AdminUserSearchRequest) o;

        return new EqualsBuilder()
                .append(userName, that.userName)
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(includeInActive, that.includeInActive)
                .append(from, that.from)
                .append(size, that.size)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userName)
                .append(firstName)
                .append(lastName)
                .append(includeInActive)
                .append(from)
                .append(size)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userName", userName)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("includeInActive", includeInActive)
                .append("from", from)
                .append("size", size)
                .toString();
    }
}
