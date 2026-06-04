package com.hotel_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel_service.entity.Hotel;
import com.hotel_service.repository.HotelRepository;

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
                new Hotel(null, "Grand Hotel", "New York"),
                new Hotel(null, "Royal Inn", "London")));
    }

    @GetMapping
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }
    
    // NEW ENDPOINT: Matched with your WebFlux tool path: /hotels/search?city=...&budget=...
    @GetMapping("/search")
    public List<Hotel> findHotels(@RequestParam String city, @RequestParam(required = false) Integer budget) {
        return hotelRepository.findAll().stream()
                .filter(h -> h.getCity().equalsIgnoreCase(city))
                .filter(h -> budget == null || h.getPricePerNight() <= budget)
                .toList();
    }
}
