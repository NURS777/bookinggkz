package kz.diplomaproject.springboot.springDIploma.controllers;

import kz.diplomaproject.springboot.springDIploma.entities.Users;
import kz.diplomaproject.springboot.springDIploma.services.EmailService;
import kz.diplomaproject.springboot.springDIploma.services.OTPService;
import kz.diplomaproject.springboot.springDIploma.services.UserService;
import kz.diplomaproject.springboot.springDIploma.ustilities.EmailTemplate;
import ognl.IntHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


// controller for send one time password to email
@Controller
public class OtpController {

    @Autowired
    public OTPService otpService;

    @Autowired
    public EmailService emailService;
    @Autowired
    private UserService userService;


    //logger for showing opt in terminal
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //get current session
    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = (Users) authentication.getPrincipal();
            return user;
        }return null;
    }

    //method for generate otp
    @GetMapping("/generateOtp")
    public String generateOTP(Model model,
                              @RequestParam(name = "email") String email) throws MessagingException {
        Users user = userService.getUser(email);
        if(user!=null){
            int otp = otpService.generateOTP(email);
            logger.info("OTP : "+otp);
            //Generate The Template to send OTP
            EmailTemplate template = new EmailTemplate("sendOtp.html");
            Map<String,String> replacements = new HashMap<String,String>();
            replacements.put("user", email);
            replacements.put("otpnum", String.valueOf(otp));
            String message = template.getTemplate(replacements);
            emailService.sendOtpMessage(email, "LOGIN VIA CODE", message + " " + otp);
            return "redirect:/mainpage?successsendotp";
        }
        return  "redirect:/mainpage?errorsendotp";
    }

    //static usermail for future use as temporary cache
    protected static String usermail = "";

    //checking otp
    @RequestMapping(value ="/validateOtp", method = RequestMethod.POST)
    public String validateOtp(@RequestParam("otpnum") int otpnum){
        //Validate the Otp
        if(otpnum >= 0){
            String username = otpService.getOtpKey(otpnum);
            int serverOtp = otpService.getOtp(username);
            if(serverOtp > 0){
                if(otpnum == serverOtp){
                    usermail = username;
                    System.out.println(username);
                    return "redirect:/mainpage?otpsuccess";
                }
                else {
                    System.out.println(username);
                    return "redirect:/mainpage?otperror";
                }
            }else {
                System.out.println(username);
                return "redirect:/mainpage?otperror";
            }
        }else {
            return "redirect:/mainpage?otperror";
        }
    }
}
