package com.iguan.demo.usermanager.repository.user.impl;

import com.iguan.demo.usermanager.domain.entity.user.User;
import com.iguan.demo.usermanager.model.common.PageModel;
import com.iguan.demo.usermanager.model.user.UserSearchProperties;
import com.iguan.demo.usermanager.repository.user.UserRepositoryCustom;
import com.iguan.demo.usermanager.service.user.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 19:45
 */
@Component
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public PageModel<User> search(UserSearchProperties searchProperties) {
        LOGGER.trace("Retrieve users for search parameters - {}", searchProperties);
        TypedQuery<User> userListQuery = buildQuery(searchProperties, "user", User.class);
        TypedQuery<Long> userTotalCountQuery = buildQuery(searchProperties, "count(user)", Long.class);
        userListQuery.setFirstResult(searchProperties.getFrom());
        userListQuery.setMaxResults(searchProperties.getSize());
        PageModel<User> userPage = new PageModel<>(userListQuery.getResultList(), userTotalCountQuery.getSingleResult());
        LOGGER.debug("Successfully retrieved users for search parameters - {}", searchProperties);
        return userPage;
    }

    private <T> TypedQuery<T> buildQuery(UserSearchProperties searchProperties, String selectCondition, Class<T> type) {
        Map<String, Object> properties = new HashMap<>();
        String conditionPrefix = " where ";
        String queryStr = " select " + selectCondition + " from User user join user.roles roles ";
        if (StringUtils.hasText(searchProperties.getUserName())) {
            queryStr = queryStr + conditionPrefix + " user.username like :userName ";
            conditionPrefix = " and ";
            properties.put("userName", "%" + searchProperties.getUserName() + "%");
        }
        if (StringUtils.hasText(searchProperties.getLastName())) {
            queryStr = queryStr + conditionPrefix + " user.lastName like :lastName ";
            conditionPrefix = " and ";
            properties.put("lastName", "%" + searchProperties.getLastName() + "%");
        }
        if (StringUtils.hasText(searchProperties.getFirstName())) {
            queryStr = queryStr + conditionPrefix + " user.firstName like :firstName ";
            conditionPrefix = " and ";
            properties.put("firstName", "%" + searchProperties.getFirstName() + "%");
        }
        if (!Boolean.TRUE.equals(searchProperties.getIncludeHasAdminRole())) {
            queryStr = queryStr + conditionPrefix + " roles.name != 'ADMIN' ";
            conditionPrefix = " and ";
        }
        if (!Boolean.TRUE.equals(searchProperties.getIncludeInActive())) {
            queryStr = queryStr + conditionPrefix + " user.active = true ";
            conditionPrefix = " and ";
        }
        TypedQuery<T> query = entityManager.createQuery(queryStr, type);
        properties.forEach(query::setParameter);
        return query;
    }

}
