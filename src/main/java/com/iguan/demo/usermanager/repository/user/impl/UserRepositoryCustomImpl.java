package com.iguan.demo.usermanager.repository.user.impl;

import com.iguan.demo.usermanager.domain.entity.user.User;
import com.iguan.demo.usermanager.model.common.PageModel;
import com.iguan.demo.usermanager.model.user.UserSearchProperties;
import com.iguan.demo.usermanager.repository.user.UserRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
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

    private String conditionPrefix = " where ";

    @PersistenceContext
    private EntityManager entityManager;

    public PageModel<User> search(UserSearchProperties searchProperties) {
       TypedQuery<User> userListQuery = buildQuery(searchProperties, "user", User.class);
       TypedQuery<Long> usersCount = buildQuery(searchProperties, "count(user)", Long.class);
        userListQuery.setFirstResult(searchProperties.getFrom());
        userListQuery.setMaxResults(searchProperties.getSize());
        Long totalCount = usersCount.getSingleResult();
        return new PageModel<>(userListQuery.getResultList(), totalCount);
    }

    private <T>TypedQuery<T> buildQuery(UserSearchProperties searchProperties, String selectCondition, Class<T> type) {
        Map<String, Object> properties = new HashMap<>();
        String queryStr = " select " + selectCondition+ " from User user ";
        if(StringUtils.hasText(searchProperties.getUserName())) {
            queryStr = queryStr + conditionPrefix + " user.username like :userNameLike ";
            conditionPrefix = " and ";
            properties.put("userNameLike", "%"+ searchProperties.getUserName()+"%");
        }
        if(!Boolean.TRUE.equals(searchProperties.getIncludeInActive())) {
            queryStr = queryStr + conditionPrefix + " user.active = true ";
            conditionPrefix = " and ";
        }
        TypedQuery query =  entityManager.createQuery(queryStr, type);
        properties.keySet().forEach(entry -> query.setParameter(entry, properties.get(entry)));
        return query;
    }
}
