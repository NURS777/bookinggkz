package kz.diplomaproject.springboot.springDIploma.controllers;

import kz.diplomaproject.springboot.springDIploma.beans.DatabaseBean;
import kz.diplomaproject.springboot.springDIploma.entities.Cities;
import kz.diplomaproject.springboot.springDIploma.entities.Organizations;
import kz.diplomaproject.springboot.springDIploma.entities.Topics;
import kz.diplomaproject.springboot.springDIploma.entities.Users;
import kz.diplomaproject.springboot.springDIploma.services.OTPService;
import kz.diplomaproject.springboot.springDIploma.services.UserService;
import org.aspectj.weaver.tools.AbstractTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;


//controller for manage user register.
@Controller
public class UserController {

    @Autowired
    public OTPService otpService;

    @Autowired
    public UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DatabaseBean databaseBean;

    //get current session
    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = (Users) authentication.getPrincipal();
            return user;
        }return null;
    }

    //method for sign up
    @PostMapping("/signup")
    public String toRegister(Model model,
                             @RequestParam(name = "user_name") String name,
                             @RequestParam(name = "user_birthday") Date date,
                             @RequestParam(name = "user_email") String email,
                             @RequestParam(name = "user_phone") String phone,
                             @RequestParam(name = "user_password") String password,
                             @RequestParam(name = "city_id") Long city_id,
                             @RequestParam(name = "user_category") Long topic_id){
        Topics topic = databaseBean.getTopic(topic_id);
        Users user = new Users();
        if(topic!=null){
            user.setFullname(name);user.setBirthdate(date);user.setEmail(email);
            Cities city = databaseBean.getCity(city_id);
            user.setPhonenumber(phone);user.setCity(city);
            user.setTopics(topic);
            user.setPassword(passwordEncoder.encode(password));
        }

        if(userService.addUser(user)!=null){

            return "redirect:/mainpage?emailsuccess";
        }
        return "redirect:/mainpage?emailerror";

    }


    //method to update from profile page
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

    //error handling 403
    @GetMapping("/accessdeniedpage")
    public String accessDeniedPage(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        return "403";
    }

    //change password getting by one time password(FORGOT PASSWORD)
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


    //map for add organization as a moderator
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
}
