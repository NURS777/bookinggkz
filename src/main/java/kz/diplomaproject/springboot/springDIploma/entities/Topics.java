package kz.diplomaproject.springboot.springDIploma.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "topics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description",columnDefinition = "TEXT")
    private  String description;

    @Column(name = "topic_img")
    private String topicImg;

}
