package pro.sky.diploma.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс-сущность для всех изображений, опубликованных на платформе
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "file_size")
    private Long fileSize;
    @Column(name = "media_type")
    private String mediaType;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] data;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @OneToOne
    @JoinColumn(name = "ads_id")
    @JsonIgnore
    private Ads ads;
}