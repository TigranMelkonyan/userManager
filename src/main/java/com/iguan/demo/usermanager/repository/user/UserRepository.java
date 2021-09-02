package com.iguan.demo.usermanager.repository.user;

import com.iguan.demo.usermanager.domain.entity.user.User;
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
public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryCustom {

    Optional<User> findByUsername(final String name);

    boolean existsByUsername(final String name);

    void deleteById(final UUID uuid);

    Optional<User> findById(final UUID uuid);
}
