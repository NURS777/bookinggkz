package kz.diplomaproject.springboot.springDIploma.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phoneforbook")
    private String phoneforbook;

    @Column(name = "numberoftickets")
    private String numberoftickets;

    @ManyToOne(fetch = FetchType.LAZY)
    private ManageEvents events;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
}
