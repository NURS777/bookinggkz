package kz.diplomaproject.springboot.springDIploma.controllers;

import kz.diplomaproject.springboot.springDIploma.beans.DatabaseBean;
import kz.diplomaproject.springboot.springDIploma.entities.*;
import kz.diplomaproject.springboot.springDIploma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController2 {

    @Autowired
    @Qualifier("dbEventBean")
    private DatabaseBean databaseBean;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = (Users) authentication.getPrincipal();
            return user;
        }return null;
    }

    @PostMapping("/bookuser")
    public String BookUser(@RequestParam(name = "id") Long id){
        Booking booking = databaseBean.getBook(id);
        if(booking!=null){
            booking.setStatus("booked");
            databaseBean.updateBook(booking);
            return "redirect:/homepage?successbook";
        }
        return "redirect:/homepage?errorbook";
    }

    @PostMapping("/cancelbookuser")
    public String CancelBookUser(@RequestParam(name = "id") Long id){
        Booking booking = databaseBean.getBook(id);
        if(booking!=null){
            booking.setStatus("unbooked");
            databaseBean.updateBook(booking);
            return "redirect:/homepage?successcancel";
        }
        return "redirect:/homepage?errorcances";
    }
}
