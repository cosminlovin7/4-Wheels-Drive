package com.upb.fourwheelsdrive.repository;

import com.upb.fourwheelsdrive.model.photo_storage.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Query("SELECT p FROM Photo p WHERE p.carAdvert.id = :car_advert_id")
    List<Photo> getAdPhotos(@Param("car_advert_id") Long car_advert_id);
}
