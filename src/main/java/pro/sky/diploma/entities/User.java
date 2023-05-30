package pro.sky.diploma.entities;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pro.sky.diploma.dto.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс-сущность для всех зарегистрированных пользователей на платформе
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password", length = 1000)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Image image;
    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Comment> comments;
    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Ads> ads;
}
