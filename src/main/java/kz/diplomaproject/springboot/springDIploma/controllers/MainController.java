package kz.diplomaproject.springboot.springDIploma.controllers;

import kz.diplomaproject.springboot.springDIploma.beans.DatabaseBean;
import kz.diplomaproject.springboot.springDIploma.entities.*;
import kz.diplomaproject.springboot.springDIploma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.PasswordAuthentication;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    @Qualifier("dbEventBean")
    private DatabaseBean databaseBean;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping( value = "/mainpage")
    public String mainpage(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        List<Cities> cities = databaseBean.getAllCities();
        model.addAttribute("cities",cities);
        return "mainpage";
    }

    @GetMapping( value = "/")
    public String index(Model model){
        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        List<Cities> cities = databaseBean.getAllCities();
        model.addAttribute("cities",cities);
        model.addAttribute("CURRENT_USER",getUser());
        return "mainpage";
    }

    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = (Users) authentication.getPrincipal();
            return user;
        }return null;
    }

    @PostMapping("/signup")
    public String toRegister(Model model,
            @RequestParam(name = "user_name") String name,
                             @RequestParam(name = "user_birthday") Date date,
                             @RequestParam(name = "user_email") String email,
                             @RequestParam(name = "user_phone") String phone,
                             @RequestParam(name = "user_password") String password,
                             @RequestParam(name = "user_city") String city,
                             @RequestParam(name = "user_category") Long topic_id){
        Topics topic = databaseBean.getTopic(topic_id);
        Users user = new Users();
        if(topic!=null){
            user.setFullname(name);user.setBirthdate(date);user.setEmail(email);
            user.setPhonenumber(phone);user.setCity(city);
            user.setTopics(topic);
            user.setPassword(passwordEncoder.encode(password));
        }

        if(userService.addUser(user)!=null){

            return "redirect:/mainpage?emailsuccess";
        }
        return "redirect:/mainpage?emailerror";

    }


    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        return "profile";
    }

    @PostMapping("toupdatepassword")
    @PreAuthorize("isAuthenticated()")
    public String toUpdatePass(@RequestParam(name = "user_oldpass") String oldpass,
                               @RequestParam(name = "user_newpass") String newpass,
                               @RequestParam(name = "user_renewpass") String renewpass){

        Users currentUser = getUser();
        if(newpass.equals(renewpass)){
            if(passwordEncoder.matches(oldpass,currentUser.getPassword())){

                currentUser.setPassword(passwordEncoder.encode(newpass));

                userService.updateUser(currentUser);
                return "redirect:/profile?passwordsuccess";
            }
            return "redirect:/profile?oldpasserror";
        }
        return "redirect:/profile?passworderror";
    }
    @GetMapping("/accessdeniedpage")
    public String accessDeniedPage(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        return "403";
    }

    @GetMapping("/homepage")
    @PreAuthorize("isAuthenticated()")
    public String getHomePAge(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        List<Cities> cities = databaseBean.getAllCities();
        model.addAttribute("cities",cities);
        //System.out.println(topics);
        return "homepage";
    }

    @GetMapping("/booking")
    @PreAuthorize("isAuthenticated()")
    public String getBookPage(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        List<ManageEvents> events = databaseBean.getAllEvents();
        model.addAttribute("events",events);
        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        List<Cities> cities = databaseBean.getAllCities();
        model.addAttribute("cities",cities);
        return "booking";
    }
    @GetMapping("/bookbycity")
    @PreAuthorize("isAuthenticated()")
    public String getBookCityPage(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        List<ManageEvents> events = databaseBean.getAllEvents();
        model.addAttribute("events",events);
        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        return "bookbycity";
    }

    @GetMapping( "details/{eventId}-page.html")
    @PreAuthorize("isAuthenticated()")
    public String details(@PathVariable(name = "eventId") Long id,Model model){
        model.addAttribute("CURRENT_USER",getUser());
        ManageEvents event = databaseBean.getEvent(id);
        model.addAttribute("event",event);
        return "details";
    }


    @GetMapping( "booking/{topicId}-page.html")
    @PreAuthorize("isAuthenticated()")
    public String booking(@PathVariable(name = "topicId") Long id,Model model){
        model.addAttribute("CURRENT_USER",getUser());
        Topics topic = databaseBean.getTopic(id);
        List<ManageEvents> events = databaseBean.getAllEvents();
        model.addAttribute("events",events);
        model.addAttribute("topic",topic);
        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        return "booking";
    }
}
