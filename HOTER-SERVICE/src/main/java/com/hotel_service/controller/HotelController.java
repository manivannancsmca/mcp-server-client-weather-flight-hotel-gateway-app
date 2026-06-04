package com.hotel_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel_service.entity.Hotel;
import com.hotel_service.repository.HotelRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {

        hotelRepository.deleteAll();

        jdbcTemplate.execute("ALTER TABLE hotel AUTO_INCREMENT = 1");

        hotelRepository.saveAll(List.of(
                new Hotel(null, "Grand Hotel", "New York", 200),
                new Hotel(null, "Royal Inn", "London", 150),
                new Hotel(null, "Luxury Palace", "New York", 500),
                new Hotel(null, "Budget Stay", "London", 100),
                new Hotel(null, "Comfort Suites", "New York", 300)));
    }

    @GetMapping
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @GetMapping("/search")
    public List<Hotel> findHotels(
            @RequestParam String city,
            @RequestParam(required = false) Integer budget) {

        if (budget == null) {
            return hotelRepository.findByCityIgnoreCase(city);
        }

        return hotelRepository
                .findByCityIgnoreCaseAndPricePerNightLessThanEqual(city, budget);
    }
}
