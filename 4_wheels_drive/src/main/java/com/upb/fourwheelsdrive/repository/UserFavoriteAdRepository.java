package com.upb.fourwheelsdrive.repository;

import com.upb.fourwheelsdrive.model.car_advertisement.UserFavoriteAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavoriteAdRepository extends JpaRepository<UserFavoriteAd, Long> {
}
