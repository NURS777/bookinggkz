package kz.diplomaproject.springboot.springDIploma.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",length = 200)
    private String name;

    @Column(name = "title",length = 200)
    private String title;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private double price;
    @Column(name = "address")
    private String address;

    @Column(name = "partnumber")
    private String partnumber;

    @Column(name = "date")
    private Date date;

    @Column(name = "event_img")
    private String eventImg;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organizations organization;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cities cities;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Topics> topics;
}
