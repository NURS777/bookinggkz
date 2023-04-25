package kz.diplomaproject.springboot.springDIploma.services;

import kz.diplomaproject.springboot.springDIploma.entities.ManageEvents;
import kz.diplomaproject.springboot.springDIploma.repositories.ManageEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    private ManageEventRepository manageEventRepository;
    @Override
    public List<ManageEvents> getAllEvents() {
        return manageEventRepository.findAll();
    }
}
