package kz.diplomaproject.springboot.springDIploma.beans;
import kz.diplomaproject.springboot.springDIploma.entities.*;
import kz.diplomaproject.springboot.springDIploma.repositories.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class DatabaseBean {

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

    public List<Cities> getAllCities(){return citiesRepository.findAll();}
    public Cities getCity(Long id){
        Optional<Cities> optional = citiesRepository.findById(id);
        return optional.orElse(null);
    }

    public List<Topics> getAllTopics(){return topicsRepository.findAll();}

    public Topics getTopic(Long id){
        Optional<Topics> optional = topicsRepository.findById(id);
        return optional.orElse(null);
    }

    public List<ManageEvents> getAllEvents(){
        return manageEventRepository.findAll();
    }
    public void addTopic(Topics topic){
        topicsRepository.save(topic);
    }
    public ManageEvents getEvent(Long id){
        Optional<ManageEvents> optional = manageEventRepository.findById(id);
        return optional.orElse(null);
        //return manageEventRepository.getById(id);
    }

    public void addEvent(ManageEvents event){
        manageEventRepository.save(event);
    }

    public void addOrgan(Organizations organization){
        organizationRepository.save(organization);
    }


    public List<Organizations> getAllOrganizations(){
        return organizationRepository.findAll();
    }

    public Organizations getOrganization(Long id){
        return organizationRepository.findById(id).orElse(null);
    }

    public void addBooking(Booking booking){
        bookingRepository.save(booking);
    }

    public List<Booking> getAllBooks(){return bookingRepository.findAll();}

    public Booking getBook(Long id){
        Optional<Booking> optional = bookingRepository.findById(id);
        return optional.orElse(null);
    }

    public void deleteBook(Long id){
        bookingRepository.deleteById(id);
    }

}
