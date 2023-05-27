package pro.sky.diploma.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс-сущность для всех объявлений, опубликованных на платформе
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Integer price;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @OneToMany(mappedBy = "ads", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(mappedBy = "ads", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Image image;
}