package com.dulal.memegram_security.Repos;



import com.dulal.memegram_security.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    Long countByEmail(String email);
    long countByUsername(String username);
    Long findRoleById(long id);

}
