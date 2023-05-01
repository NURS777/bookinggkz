package kz.diplomaproject.springboot.springDIploma.controllers;

import kz.diplomaproject.springboot.springDIploma.beans.DatabaseBean;
import kz.diplomaproject.springboot.springDIploma.entities.*;
import kz.diplomaproject.springboot.springDIploma.repositories.RoleRepository;
import kz.diplomaproject.springboot.springDIploma.services.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class MainController {
    @Autowired
    @Qualifier("dbEventBean")
    private DatabaseBean databaseBean;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Value("${file.eimages.viewPath}")
    private String viewPath;

    @Value("${file.eimages.uploadPath}")
    private String uploadPath;

    @Value("${file.eimages.defaultPicture}")
    private String defaultPicture;

    @Value("${file.timages.viewPath}")
    private String viewPathT;

    @Value("${file.timages.uploadPath}")
    private String uploadPathT;

    @Value("${file.timages.defaultPicture}")
    private String defaultPictureT;

    @GetMapping( value = "/mainpage")
    public String mainpage(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        List<Cities> cities = databaseBean.getAllCities();
        model.addAttribute("cities",cities);
        Roles roles = roleRepository.findByRole("ROLE_ADMIN");
        Roles roles2 = roleRepository.findByRole("ROLE_MODERATOR");
        Roles roles3 = roleRepository.findByRole("ROLE_USER");
        if(getUser()!=null){
            if(getUser().getRoles().contains(roles)||getUser().getRoles().contains(roles2)||getUser().getRoles().contains(roles3)){
                return "redirect:/homepage";
            }else { return  "mainpage";}
        }
        return "mainpage";
    }

    @GetMapping( value = "/")
    public String index(Model model){
        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        List<Cities> cities = databaseBean.getAllCities();
        model.addAttribute("cities",cities);
        model.addAttribute("CURRENT_USER",getUser());
        Roles roles = roleRepository.findByRole("ROLE_ADMIN");
        Roles roles2 = roleRepository.findByRole("ROLE_MODERATOR");
        Roles roles3 = roleRepository.findByRole("ROLE_USER");
        if(getUser()!=null){
            if(getUser().getRoles().contains(roles)||getUser().getRoles().contains(roles2)||getUser().getRoles().contains(roles3)){
                return "redirect:/homepage";
            }else { return  "mainpage";}
        }
        return "mainpage";
    }

    @GetMapping("/404")
    public String notFound() throws ConfigDataResourceNotFoundException {
        return "404";
    }

    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = (Users) authentication.getPrincipal();
            return user;
        }return null;
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        Cities cities = getUser().getCity();
        return "profile";
    }

    @GetMapping("/homepage")
    @PreAuthorize("isAuthenticated()")
    public String getHomePAge(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        List<Cities> cities = databaseBean.getAllCities();
        model.addAttribute("cities",cities);
        List<Users> users = userService.getAllUsers();
        model.addAttribute("users",users);
        List<Booking> bookings = databaseBean.getAllBooks();
        model.addAttribute("books",bookings);
        List<Organizations> organizations = databaseBean.getAllOrganizations();
        model.addAttribute("orgs",organizations);
        model.addAttribute("CURRENT_USER",getUser());
        if(getUser().getOrganization()!=null){
            List<Booking> books = databaseBean.getAllBooks();
            List<Booking> booksOfOrg = new ArrayList<>();
            if(getUser().getOrganization().getId()!=5&&getUser().getOrganization()!=null){

                for(int i = 0;i<books.size();i++){
                    if(books.get(i).getEvents().getOrganization().getName().equals(getUser().getFullname())){
                        booksOfOrg.add(books.get(i));
                    }
                }
                model.addAttribute("books",booksOfOrg);
            }else {
                model.addAttribute("bookList",books);
            }
        }
        List<Feedbacks> feedbacks = databaseBean.getAllFeedbacks();
        if(feedbacks!=null){
            List<Feedbacks> feedbacks1 = new ArrayList<>();
            List<Feedbacks> feedbacks2 = new ArrayList<>();
            List<Feedbacks> feedbacks3 = new ArrayList<>();
            List<Feedbacks> feedbacks4 = new ArrayList<>();
            for(int i = 0;i<feedbacks.size();i++){
                if(feedbacks.get(i).getFeedback().equals("two")){
                    feedbacks1.add(feedbacks.get(i));
                }else if(feedbacks.get(i).getFeedback().equals("three")){
                    feedbacks2.add(feedbacks.get(i));
                }else if(feedbacks.get(i).getFeedback().equals("four")){
                    feedbacks3.add(feedbacks.get(i));
                }else if(feedbacks.get(i).getFeedback().equals("five")){
                    feedbacks4.add(feedbacks.get(i));
                }
            }
            model.addAttribute("fTwo",feedbacks1);
            model.addAttribute("fThree",feedbacks2);
            model.addAttribute("fFour",feedbacks3);
            model.addAttribute("fFive",feedbacks4);
        }
        //System.out.println(topics);
        return "homepage";
    }

    @GetMapping("/bookingByTopic/{userId}-page.html")
    @PreAuthorize("isAuthenticated()")
    public String getBookPage(Model model,@PathVariable(name = "userId") Long id){
        if(Objects.equals(getUser().getId(), id)){
            model.addAttribute("CURRENT_USER",getUser());
        }else {
            return "redirect:/bookingByTopic/"+getUser().getId() +"-page.html?booksuccess";
        }
        List<ManageEvents> events = databaseBean.getAllEvents();
        model.addAttribute("events",events);
        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        List<Cities> cities = databaseBean.getAllCities();
        model.addAttribute("cities",cities);
        Roles roles = roleRepository.findByRole("ROLE_ADMIN");
        Roles roles2 = roleRepository.findByRole("ROLE_MODERATOR");
        if(getUser()!=null){
            if(getUser().getRoles().contains(roles)||getUser().getRoles().contains(roles2)){
                return "redirect:/homepage";
            }
        }
        return "bookingByTopic";
    }

    @GetMapping(value = "/viewTphoto/{url}",produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody byte[] viewTopicPhoto(@PathVariable(name = "url") String url) throws IOException {
        String pictureUrl = viewPathT+defaultPictureT;
        if(url!=null&&!url.equals("null")){
            pictureUrl = viewPathT+url+".jpg";
        }

        InputStream in;
        try{
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();
        }catch (Exception e){
            ClassPathResource resource = new ClassPathResource(viewPathT+defaultPictureT);
            in = resource.getInputStream();
            e.printStackTrace();
        }

        return IOUtils.toByteArray(in);
    }

    @GetMapping( "details/{eventId}-page.html")
    @PreAuthorize("isAuthenticated()")
    public String details(@PathVariable(name = "eventId") Long id,Model model){
        model.addAttribute("CURRENT_USER",getUser());
        ManageEvents event = databaseBean.getEvent(id);
        model.addAttribute("event",event);
        Roles roles = roleRepository.findByRole("ROLE_ADMIN");
        Roles roles2 = roleRepository.findByRole("ROLE_MODERATOR");
        if(getUser()!=null){
            if(getUser().getRoles().contains(roles)||getUser().getRoles().contains(roles2)){
                return "redirect:/homepage";
            }
        }
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
        List<Cities> cities = databaseBean.getAllCities();
        model.addAttribute("cities",cities);
        Roles roles = roleRepository.findByRole("ROLE_ADMIN");
        Roles roles2 = roleRepository.findByRole("ROLE_MODERATOR");
        if(getUser()!=null){
            if(getUser().getRoles().contains(roles)||getUser().getRoles().contains(roles2)){
                return "redirect:/homepage";
            }
        }
        return "booking";
    }

    @GetMapping("bookbycity/{cityId}-page.html")
    @PreAuthorize("isAuthenticated()")
    public String getBookCityPage(Model model,@PathVariable(name = "cityId") Long id){
        model.addAttribute("CURRENT_USER",getUser());
        List<ManageEvents> events = databaseBean.getAllEvents();
        model.addAttribute("events",events);
        List<Cities> cities = databaseBean.getAllCities();
        model.addAttribute("cities",cities);
        Cities city = databaseBean.getCity(id);
        model.addAttribute("city",city);
        Roles roles = roleRepository.findByRole("ROLE_ADMIN");
        Roles roles2 = roleRepository.findByRole("ROLE_MODERATOR");
        if(getUser()!=null){
            if(getUser().getRoles().contains(roles)||getUser().getRoles().contains(roles2)){
                return "redirect:/homepage";
            }
        }
        return "bookByCity";
    }
}
