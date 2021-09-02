package com.iguan.demo.usermanager.repository.user;

import com.iguan.demo.usermanager.domain.entity.user.User;
import com.iguan.demo.usermanager.model.common.PageModel;
import com.iguan.demo.usermanager.model.user.UserSearchProperties;
import org.springframework.stereotype.Repository;


/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 19:45
 */
@Repository
public interface UserRepositoryCustom {

    PageModel<User> search(final UserSearchProperties searchProperties);
}
