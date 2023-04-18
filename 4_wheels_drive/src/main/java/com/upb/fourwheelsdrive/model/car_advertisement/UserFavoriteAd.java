package com.upb.fourwheelsdrive.model.car_advertisement;

import com.upb.fourwheelsdrive.model.user.ApplicationUser;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserFavoriteAd {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_favorite_ad_sequence"
    )
    @SequenceGenerator(
            name = "user_favorite_ad_sequence",
            sequenceName = "user_favorite_ad_sequence",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private ApplicationUser applicationUser;
    @ManyToOne
    @JoinColumn(
            name = "car_advert_id"
    )
    private CarAdvert carAdvert;
}
