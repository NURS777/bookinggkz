package kz.diplomaproject.springboot.springDIploma.beans;
import kz.diplomaproject.springboot.springDIploma.entities.*;
import kz.diplomaproject.springboot.springDIploma.repositories.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;


//сonfiguration for all repositories
@Configuration
public class DatabaseBean {
    //annotations of repositories for work with therir database
    @Autowired
    private ManageEventRepository manageEventRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    //method to get all cities from table
    public List<Cities> getAllCities(){return citiesRepository.findAll();}

    //method to get city by id from cities table
    public Cities getCity(Long id){
        Optional<Cities> optional = citiesRepository.findById(id);
        return optional.orElse(null);
    }
    //method to get all topics from table
    public List<Topics> getAllTopics(){return topicsRepository.findAll();}

    //method to get topic by id from topics table
    public Topics getTopic(Long id){
        Optional<Topics> optional = topicsRepository.findById(id);
        return optional.orElse(null);
    }

    //method to get all events from table
    public List<ManageEvents> getAllEvents(){
        return manageEventRepository.findAll();
    }

    //method to add topic to table
    public void addTopic(Topics topic){
        topicsRepository.save(topic);
    }

    //method to get event by id
    public ManageEvents getEvent(Long id){
        Optional<ManageEvents> optional = manageEventRepository.findById(id);
        return optional.orElse(null);
        //return manageEventRepository.getById(id);
    }

    //method to add event to table
    public void addEvent(ManageEvents event){
        manageEventRepository.save(event);
    }

    //method to add organ to organizations table
    public void addOrgan(Organizations organization){
        organizationRepository.save(organization);
    }

    //method for get all organization from table
    public List<Organizations> getAllOrganizations(){
        return organizationRepository.findAll();
    }

    //table for get organization by id form table
    public Organizations getOrganization(Long id){
        return organizationRepository.findById(id).orElse(null);
    }

    //method for add booking to table
    public void addBooking(Booking booking){
        bookingRepository.save(booking);
    }

    //method for get all books from table
    public List<Booking> getAllBooks(){return bookingRepository.findAll();}

    //method for get book by id from table
    public Booking getBook(Long id){
        Optional<Booking> optional = bookingRepository.findById(id);
        return optional.orElse(null);
    }

    //method to drop/delete book from table
    public void deleteBook(Long id){
        bookingRepository.deleteById(id);
    }

    //method to set/update book from booking table
    public void updateBook(Booking booking){
        bookingRepository.save(booking);
    }

    //method to add feedback
    public void addFeedback(Feedbacks feedbacks){feedbackRepository.save(feedbacks);}

    //method to get all feedbacks
    public List<Feedbacks> getAllFeedbacks(){return feedbackRepository.findAll();}

}
