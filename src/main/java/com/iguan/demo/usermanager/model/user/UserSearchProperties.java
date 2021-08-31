package com.iguan.demo.usermanager.model.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.validation.constraints.Size;

/**
 * Created by Tigran Melkonyan
 * Date: 31/08/2021
 * Time: 23:53
 */
public class UserSearchProperties {

    private String userName;
    private Boolean includeInActive;

    @Size(min = 0)
    private Integer from = 0;

    @Size(min = 1)
    private Integer size = 50;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userName", userName)
                .append("includeActive", includeInActive)
                .append("from", from)
                .append("size", size)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserSearchProperties that = (UserSearchProperties) o;

        return new EqualsBuilder()
                .append(userName, that.userName)
                .append(includeInActive, that.includeInActive)
                .append(from, that.from)
                .append(size, that.size)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userName)
                .append(includeInActive)
                .append(from)
                .append(size)
                .toHashCode();
    }
}
