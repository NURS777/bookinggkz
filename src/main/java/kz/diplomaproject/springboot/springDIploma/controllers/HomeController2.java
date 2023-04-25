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


    @GetMapping("/organizationspage")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String getOrgsPage(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        List<Organizations> organizations = databaseBean.getAllOrganizations();
        List<Users> users = userService.getAllUsers();
        model.addAttribute("users",users);
        model.addAttribute("orgs",organizations);
        return "organizationspage";
    }

    @PostMapping("/addorgan")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String toRegister(Model model,
                             @RequestParam(name = "org_name") String name,
                             @RequestParam(name = "org_password") String pass,
                             @RequestParam(name = "org_email") String email,
                             @RequestParam(name = "org_phone") String phone){
        Users user = new Users();
        Organizations organization = new Organizations();
        organization.setName(name);organization.setPhonenumber(phone);organization.setCity(null);
        databaseBean.addOrgan(organization);
        user.setCity(null);user.setFullname(name);user.setTopics(null);
        user.setEmail(email);user.setPhonenumber(phone);user.setBirthdate(null);
        user.setPassword(passwordEncoder.encode(pass));user.setOrganization(organization);


        if(userService.addUserModer(user)!=null){

            return "redirect:/organizationspage?orgsuccess";
        }
        return "redirect:/organizationspage?orgerror";
    }

    @GetMapping("/orderspage")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public String getOrderPage(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        Long id = getUser().getOrganization().getId();
        Organizations organization = getUser().getOrganization();
        List<Booking> books = databaseBean.getAllBooks();
        List<Booking> booksOfOrg = new ArrayList<>();
        if(id!=5&&organization!=null){

            for(int i = 0;i<books.size();i++){
                if(books.get(i).getEvents().getOrganization().getName().equals(getUser().getFullname())){
                    booksOfOrg.add(books.get(i));
                }
            }
            model.addAttribute("books",booksOfOrg);
        }else {
            model.addAttribute("bookList",books);
        }
        return "orderspage";
    }
}
