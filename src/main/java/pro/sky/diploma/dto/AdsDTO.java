package pro.sky.diploma.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pro.sky.diploma.entities.Ads;

/**
 * Класс-DTO для описания объявлений на платформе
 */
@Getter
@Setter
@Data
public class AdsDTO {
    private Long author;
    private String image;
    private Long pk;
    private Integer price;
    private String title;
    private UserDTO user;

    /**
     * Создаёт {@link AdsDTO} из {@link Ads}
     *
     * @param ads на входе {@link Ads}
     * @return возвращает {@link AdsDTO}
     */
    public static AdsDTO fromAds(Ads ads) {
        AdsDTO adsDTO = new AdsDTO();
        adsDTO.setAuthor(UserDTO.fromUser(ads.getUser()).getId());
        adsDTO.setImage(adsDTO.getImage());
        adsDTO.setPk(ads.getId());
        adsDTO.setPrice(ads.getPrice());
        adsDTO.setTitle(ads.getTitle());
        adsDTO.setUser(UserDTO.fromUser(ads.getUser()));
        return adsDTO;
    }

    /**
     * Создаёт объявление из DTO объявления
     *
     * @return {@link Ads}
     */
    public Ads toAds() {
        Ads ads = new Ads();
        ads.setId(this.getPk());
        ads.setImage(this.toAds().getImage());
        ads.setPrice(this.getPrice());
        ads.setTitle(this.getTitle());
        ads.setUser(this.getUser().toUser());
        return ads;
    }
}
