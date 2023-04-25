package kz.diplomaproject.springboot.springDIploma.repositories;

import kz.diplomaproject.springboot.springDIploma.entities.ManageEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ManageEventRepository extends JpaRepository<ManageEvents,Long> {
}
