package com.iguan.demo.usermanager.repository.user;

import com.iguan.demo.usermanager.domain.entity.user.User;
import com.iguan.demo.usermanager.model.common.PageModel;
import com.iguan.demo.usermanager.model.user.UserSearchProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 19:45
 */
@Repository
public interface UserRepositoryCustom {

    PageModel<User> search(UserSearchProperties searchProperties);
}
