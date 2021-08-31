package com.iguan.demo.usermanager.config.security.jwt;

import java.io.Serializable;

/**
 * Created by Tigran Melkonyan
 * Date: 29/08/2021
 * Time: 19:44
 */
public class JwtRequest implements Serializable {

    private String username;
    private String password;

    public JwtRequest() {

    }

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
