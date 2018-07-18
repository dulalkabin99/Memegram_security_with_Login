package com.dulal.memegram_security.Repos;


import com.dulal.memegram_security.Models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Long> {

    Role findByRole(String role);
    long findIdByRole(String role);
}
