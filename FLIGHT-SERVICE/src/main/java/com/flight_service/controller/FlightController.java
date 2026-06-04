package com.flight_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.flight_service.entity.Flight;
import com.flight_service.repository.FlightRepository;

import jakarta.annotation.PostConstruct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightRepository repo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {

        repo.deleteAll();

        jdbcTemplate.execute("ALTER TABLE flight AUTO_INCREMENT = 1");

        repo.saveAll(List.of(
                new Flight(null, "AA123", "New York", "Delhi"),
                new Flight(null, "BA456", "London", "Chennai")));
    }

    @GetMapping
    public List<Flight> getAll() {
        return repo.findAll();
    }
    
    // NEW ENDPOINT: Matched with your WebFlux tool path: /flights/search?from=...&to=...
    @GetMapping("/search")
    public List<Flight> searchFlights(@RequestParam("from") String from, @RequestParam("to") String to) {
        return repo.findAll().stream()
                .filter(f -> f.getDestination().equalsIgnoreCase(to) && f.getOrigin().equalsIgnoreCase(from))
                .toList();
    }
}
