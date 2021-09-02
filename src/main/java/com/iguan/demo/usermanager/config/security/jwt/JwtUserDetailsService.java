package com.iguan.demo.usermanager.config.security.jwt;

import com.iguan.demo.usermanager.domain.entity.user.User;
import com.iguan.demo.usermanager.exceptions.RecordConflictException;
import com.iguan.demo.usermanager.exceptions.error.ErrorCode;
import com.iguan.demo.usermanager.model.rest.request.UserCreateRequest;
import com.iguan.demo.usermanager.repository.user.UserRepository;
import com.iguan.demo.usermanager.service.role.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 19:45
 */
@Service(value = "jwtUserDetailsService")
@Transactional
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;
    private final RoleService roleService;

    public JwtUserDetailsService(UserRepository userRepository, PasswordEncoder bcryptEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    public User register(UserCreateRequest signUpRequest) {
        if (!userRepository.existsByUsername(signUpRequest.getUserName())) {
            User user = new User();
            user.setUsername(signUpRequest.getUserName());
            user.setFirstName(signUpRequest.getFirstName());
            user.setLastName(signUpRequest.getLastName());
            user.setPhone(signUpRequest.getPhone());
            user.setPassword(bcryptEncoder.encode(signUpRequest.getPassword()));
            user.setRoles(Collections.singletonList(roleService.findByName("USER")));
            return userRepository.save(user);
        } else {
            throw new RecordConflictException(String.format("Error: Username - %s is already taken!", signUpRequest.getUserName()),
                    ErrorCode.USER_ALREADY_EXIST_EXCEPTION);
        }
    }

    private List<GrantedAuthority> getAuthority(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
