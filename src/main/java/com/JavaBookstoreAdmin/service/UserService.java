package com.JavaBookstoreAdmin.service;

import com.JavaBookstoreAdmin.domain.User;
import com.JavaBookstoreAdmin.domain.security.UserRole;

import java.util.Set;

public interface UserService {
    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);
}

