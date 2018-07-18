package com.dulal.memegram_security.services;



import com.dulal.memegram_security.Models.User;
import com.dulal.memegram_security.Repos.UserRepo;
import com.dulal.memegram_security.Repos.RoleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;




@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo=userRepo;
    }

    public User findByEmail (String email){
        return userRepo.findByEmail(email);
    }

    public Long countByEmail(String email){
        return userRepo.countByEmail(email);
    }
    public User findByUserName(String username){
        return userRepo.findByUsername(username);
    }


        public  void saveUser(User user){
            user.setRoles(Arrays.asList(roleRepo.findByRole("USER")));
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        }
        public  void saveAdmin(User user){
            user.setRoles(Arrays.asList(roleRepo.findByRole("ADMIN")));
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);

    }
}
