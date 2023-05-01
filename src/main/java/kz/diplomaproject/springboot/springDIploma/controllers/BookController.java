package kz.diplomaproject.springboot.springDIploma.controllers;

import kz.diplomaproject.springboot.springDIploma.beans.DatabaseBean;
import kz.diplomaproject.springboot.springDIploma.entities.Booking;
import kz.diplomaproject.springboot.springDIploma.entities.Feedbacks;
import kz.diplomaproject.springboot.springDIploma.entities.ManageEvents;
import kz.diplomaproject.springboot.springDIploma.entities.Users;
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

import java.util.ArrayList;
import java.util.List;


//controller for booking system from default user side (client booking system)
@Controller
public class BookController {

    //configuration database by name
    @Autowired
    @Qualifier("dbEventBean")
    private DatabaseBean databaseBean;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    //get current session(user)
    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = (Users) authentication.getPrincipal();
            return user;
        }return null;
    }

    //method for book event
    @PostMapping("/bookevent")
    public String bookEvent(Model model,
                            @RequestParam(name = "id") Long id,
                            @RequestParam(name = "bookphonenum") String phone,
                            @RequestParam(name = "numberoftickets") String tickets){
        ManageEvents event = databaseBean.getEvent(id);
        if(event!=null){
            Booking booking = new Booking();
            booking.setEvents(event);
            booking.setNumberoftickets(tickets);
            booking.setPhoneforbook(phone);
            booking.setStatus("unbooked");
            Users user = getUser();
            booking.setUsers(user);
            databaseBean.addBooking(booking);
            return "redirect:/details/"+id+"-page.html?booksuccess";
        }
        return "redirect:/details/"+id+"-page.html??bookerror";
    }

    //method for get user books
    @GetMapping("/myorders")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String myOrders(Model model){
        List<Booking> books = databaseBean.getAllBooks();
        List<Booking> booksOfUser = new ArrayList<>();
        for(int i = 0;i<books.size();i++){
            if(books.get(i).getUsers().getEmail().equals(getUser().getEmail())){
                booksOfUser.add(books.get(i));
            }
        }
        model.addAttribute("books",booksOfUser);
        System.out.println(booksOfUser);
        return "myorders";
    }

    //method for drop/delete/cancel user book
    @PostMapping("/deletebook")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String deleteBook(Model model,
                             @RequestParam(name = "id") Long id){
        Booking booking = databaseBean.getBook(id);
        if(booking!=null){
            databaseBean.deleteBook(id);
        }
        return "redirect:/myorders";
    }


    //method to leave a rating/feedback for the site,first emoji
    @PostMapping("/feedbackTwo")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String feedbackOne(Model model,
                              @RequestParam(name = "rating") int rating){
            Feedbacks feedbacks = new Feedbacks();
            feedbacks.setFeedback("two");
            databaseBean.addFeedback(feedbacks);
            return "redirect:/myorders";
    }

    //method to leave a rating/feedback for the site,second emoji
    @PostMapping("/feedbackThree")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String feedbackThree(Model model,
                              @RequestParam(name = "rating") int rating){
        Feedbacks feedbacks = new Feedbacks();
        feedbacks.setFeedback("three");
        databaseBean.addFeedback(feedbacks);
        return "redirect:/myorders";
    }

    //method to leave a rating/feedback for the site,third emoji
    @PostMapping("/feedbackFour")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String feedbackFour(Model model,
                              @RequestParam(name = "rating") int rating){
        Feedbacks feedbacks = new Feedbacks();
        feedbacks.setFeedback("four");
        databaseBean.addFeedback(feedbacks);
        return "redirect:/myorders";
    }

    //method to leave a rating/feedback for the site,forth emoji
    @PostMapping("/feedbackFive")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String feedbackFive(Model model,
                              @RequestParam(name = "rating") int rating){
        Feedbacks feedbacks = new Feedbacks();
        feedbacks.setFeedback("five");
        databaseBean.addFeedback(feedbacks);
        return "redirect:/myorders";
    }
}
