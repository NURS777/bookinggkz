package kz.diplomaproject.springboot.springDIploma.controllers;

import kz.diplomaproject.springboot.springDIploma.beans.DatabaseBean;
import kz.diplomaproject.springboot.springDIploma.entities.*;
import kz.diplomaproject.springboot.springDIploma.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.jni.File;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    @Qualifier("dbEventBean")
    private DatabaseBean databaseBean;

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
    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = (Users) authentication.getPrincipal();
            return user;
        }return null;
    }
    @GetMapping( "/adminpage")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String getAdminPage(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        List<Organizations> organizations = databaseBean.getAllOrganizations();
        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        Long id = getUser().getOrganization().getId();
        List<Cities> cities = databaseBean.getAllCities();
        model.addAttribute("cities",cities);
        if(id!=5){
            model.addAttribute("org",databaseBean.getOrganization(getUser().getOrganization().getId()));
        }else {
            model.addAttribute("orgs",organizations);
        }
        return "adminpage";
    }


    @PostMapping("/addevent")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String toAddEvent(Model model,
                             @RequestParam(name = "event_name") String name,
                             @RequestParam(name = "event_descr") String descr,
                             @RequestParam(name = "event_price") double price,
                             @RequestParam(name = "event_date") Date date,
                             @RequestParam(name = "orgs_id")Long orgs_id,
                             @RequestParam(name = "top_id")List<Topics> topics,
                             @RequestParam(name = "event_ava")MultipartFile file,
                             @RequestParam(name = "city_id")Long city_id,
                             @RequestParam(name = "event_title")String title,
                             @RequestParam(name = "event_address")String address,
                             @RequestParam(name = "event_partnernum")String partnernum){

        Organizations organization = databaseBean.getOrganization(orgs_id);
        Cities city = databaseBean.getCity(city_id);
        List<ManageEvents> eventsList = databaseBean.getAllEvents();


        if(organization!=null&&city!=null){
            ManageEvents events = new ManageEvents();
            events.setName(name);events.setDescription(descr);events.setPrice(price);
            events.setDate(date);events.setOrganization(organization);events.setTitle(title);
            events.setAddress(address);events.setPartnumber(partnernum);events.setCities(city);


            if(file.getContentType().equals("image/jpeg")||file.getContentType().equals("image/png")||file.getContentType().equals("image/jpg")) {

                try {

                    String picName = DigestUtils.sha1Hex("IMAGE_"+(eventsList.size()+1)+"_!Picture");

                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(uploadPath + picName+".jpg");
                    Files.write(path.toAbsolutePath(), bytes);

                    events.setEventImg(picName);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            events.setTopics(topics);
            databaseBean.addEvent(events);
            return "redirect:/adminpage?success";
        }
        model.addAttribute("CURRENT_USER",getUser());
        return "redirect:/adminpage?erroradd";
    }

    @GetMapping(value = "/viewphoto/{url}",produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody byte[] viewEventPhoto(@PathVariable(name = "url") String url) throws IOException {
        String pictureUrl = viewPath+defaultPicture;
        if(url!=null&&!url.equals("null")){
           pictureUrl = viewPath+url+".jpg";
        }

        InputStream in;
        try{
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();
        }catch (Exception e){
            ClassPathResource resource = new ClassPathResource(viewPath+defaultPicture);
            in = resource.getInputStream();
            e.printStackTrace();
        }

        return IOUtils.toByteArray(in);
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



    @GetMapping("/eventslist")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String evenstList(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        List<ManageEvents> events = databaseBean.getAllEvents();

        Long id = getUser().getOrganization().getId();
        Organizations organization = getUser().getOrganization();
        List<ManageEvents> manageEvents = new ArrayList<>();
        if(id!=5&&organization!=null){

            for(int i = 0;i<events.size();i++){
                if(events.get(i).getOrganization().getName().equals(getUser().getFullname())){
                    manageEvents.add(events.get(i));
                }
            }
            model.addAttribute("evs",manageEvents);
        }else {
            model.addAttribute("eventsList",events);
        }

        return "eventslist";
    }

    @GetMapping("/addtopicpage")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addTopicpage(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        return "addtopicpage";
    }

    @PostMapping("/addtopic")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String toAddEvent(Model model,
                             @RequestParam(name = "topic_name") String name,
                             @RequestParam(name = "topic_descr") String descr,
                             @RequestParam(name = "topic_ava")MultipartFile file){

        List<Topics> topics = databaseBean.getAllTopics();
        model.addAttribute("topics",topics);
        Topics topic = new Topics();
        topic.setName(name);topic.setDescription(descr);
            if(file.getContentType().equals("image/jpeg")||file.getContentType().equals("image/png")||file.getContentType().equals("image/jpg")) {

                try {

                    String picName = DigestUtils.sha1Hex("Top_"+(topics.size()+2)+"_!Picture");

                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(uploadPathT + picName+".jpg");
                    Files.write(path.toAbsolutePath(), bytes);

                    topic.setTopicImg(picName);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                databaseBean.addTopic(topic);
                return "redirect:/addtopicpage?success";
            }
        model.addAttribute("CURRENT_USER",getUser());
        return "redirect:/addtopicpage?erroradd";
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String accounts(Model model){
        model.addAttribute("CURRENT_USER",getUser());
        List<Users> users = userService.getAllUsers();
        model.addAttribute("users",users);
        return "accounts";
    }

}
