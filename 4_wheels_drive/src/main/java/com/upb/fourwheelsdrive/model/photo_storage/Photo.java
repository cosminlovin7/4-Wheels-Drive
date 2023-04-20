package com.upb.fourwheelsdrive.model.photo_storage;

import com.upb.fourwheelsdrive.model.car_advertisement.CarAdvert;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Photo {
    @Id
    @SequenceGenerator(
            name = "photo_sequence",
            sequenceName = "photo_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "photo_sequence"
    )
    private Long id;
    private String data;
    @ManyToOne
    @JoinColumn(
            name = "car_advert_id"
    )
    private CarAdvert carAdvert;

    public Photo(final String data, final CarAdvert carAdvert) {
        this.data = data;
        this.carAdvert = carAdvert;
    }
}
