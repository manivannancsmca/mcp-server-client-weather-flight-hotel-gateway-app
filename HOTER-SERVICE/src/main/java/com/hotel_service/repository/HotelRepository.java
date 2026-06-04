package com.hotel_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel_service.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByCityIgnoreCase(String city);

    List<Hotel> findByCityIgnoreCaseAndPricePerNightLessThanEqual(
            String city,
            Integer pricePerNight);
            
}
