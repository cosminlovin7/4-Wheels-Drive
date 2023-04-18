package com.upb.fourwheelsdrive.repository;

import com.upb.fourwheelsdrive.model.car_advertisement.CarAdvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CarAdvertRepository extends JpaRepository<CarAdvert, Long> {
    @Query("SELECT ca FROM CarAdvert ca WHERE ca.applicationUser.id = :userId")
    List<CarAdvert> getCarAdvertsByUserId(@Param("userId") Long userId);

    @Query("SELECT ca FROM CarAdvert ca " +
            "WHERE (:brandId IS NULL OR ca.carBrand.id = :brandId)" +
            "AND (:modelId IS NULL OR ca.carModel.id = :modelId) " +
            "AND (:generationId IS NULL OR ca.carModelGeneration.id = :generationId)" +
            "AND (:year IS NULL OR ca.year = :year) " +
            "AND (:minYear IS NULL OR ca.year >= :minYear) " +
            "AND (:maxYear IS NULL OR ca.year <= :maxYear) " +
            "AND (:price IS NULL OR ca.price = :price) " +
            "AND (:minPrice IS NULL OR ca.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR ca.price <= :maxPrice) " +
            "AND (:fuelType IS NULL OR ca.fuelType = :fuelType) " +
            "AND (:country IS NULL OR ca.country = :country) ")
    List<CarAdvert> getFilteredCarAdverts(
                                          @Param("brandId") Long brandId,
                                          @Param("modelId") Long modelId,
                                          @Param("generationId") Long generationId,
                                          @Param("year") Integer year,
                                          @Param("minYear") Integer minYear,
                                          @Param("maxYear") Integer maxYear,
                                          @Param("price") Double price,
                                          @Param("minPrice") Double minPrice,
                                          @Param("maxPrice") Double maxPrice,
                                          @Param("fuelType") String fuelType,
                                          @Param("country") String country
                                          );
}
