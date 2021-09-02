package com.iguan.demo.usermanager.model.rest.response.role;

import com.iguan.demo.usermanager.domain.entity.role.Role;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Tigran Melkonyan
 * Date: 01/09/2021
 * Time: 19:57
 */
public class RoleResponse {

    private String name;

    public RoleResponse() {
    }

    public RoleResponse(final String name) {
        this.name = name;
    }

    public static RoleResponse from(final Role role) {
        return new RoleResponse(role.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RoleResponse that = (RoleResponse) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}
