package com.iguan.demo.usermanager.domain.entity.role;

import com.iguan.demo.usermanager.domain.common.BaseEntity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 17:40
 */
@Entity
@Table
public class Role extends BaseEntity {

    @Column(unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof Role)) return false;

        final Role role = (Role) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(name, role.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("name", name)
                .toString();
    }
}
