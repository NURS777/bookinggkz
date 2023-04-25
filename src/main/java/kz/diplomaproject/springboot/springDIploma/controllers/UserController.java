package kz.diplomaproject.springboot.springDIploma.controllers;

import kz.diplomaproject.springboot.springDIploma.entities.Users;
import kz.diplomaproject.springboot.springDIploma.services.OTPService;
import kz.diplomaproject.springboot.springDIploma.services.UserService;
import org.aspectj.weaver.tools.AbstractTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    public OTPService otpService;

    @Autowired
    public UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = (Users) authentication.getPrincipal();
            return user;
        }return null;
    }

    @PostMapping("/changeOldUserPass")
    public String changePassOldUser(@RequestParam(name = "npass")String newPass,
                                    @RequestParam(name = "nrpass")String repeatPass){
        Users user = userService.getUser(OtpController.usermail);
        if(newPass.equals(repeatPass)){
                user.setPassword(passwordEncoder.encode(newPass));

                userService.updateUser(user);
                return "redirect:/mainpage?passwordchangesuccess";
        }else {
            return "redirect:/mainpage?passwordchangeerror";
        }
    }
}
