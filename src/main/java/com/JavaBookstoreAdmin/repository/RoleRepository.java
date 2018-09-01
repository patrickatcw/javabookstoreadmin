package com.JavaBookstoreAdmin.repository;

import com.JavaBookstoreAdmin.domain.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByname(String name);

}