package kz.diplomaproject.springboot.springDIploma.services;

import kz.diplomaproject.springboot.springDIploma.entities.Roles;
import kz.diplomaproject.springboot.springDIploma.entities.Users;
import kz.diplomaproject.springboot.springDIploma.repositories.RoleRepository;
import kz.diplomaproject.springboot.springDIploma.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByEmail(username);

        if(user!=null){
            return user;
        }else {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }

    @Override
    public Users getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users addUserModer(Users user) {
        Users checkUser = userRepository.findByEmail(user.getEmail());
        if(checkUser==null){
            Roles userRole = roleRepository.findByRole("ROLE_MODERATOR");
            ArrayList<Roles> roles = new ArrayList<>();
            roles.add(userRole);
            user.setRoles(roles);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public Users addUser(Users user) {
        Users checkUser = userRepository.findByEmail(user.getEmail());
        if(checkUser==null){
            Roles userRole = roleRepository.findByRole("ROLE_USER");
            ArrayList<Roles> roles = new ArrayList<>();
            roles.add(userRole);
            user.setRoles(roles);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public Users updateUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }


}
