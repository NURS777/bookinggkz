package kz.diplomaproject.springboot.springDIploma.services;

import kz.diplomaproject.springboot.springDIploma.entities.Users;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    Users getUser(String email);
    Users addUser(Users user);

    Users addUserModer(Users user);

    Users updateUser(Users user);

    List<Users> getAllUsers();
}
