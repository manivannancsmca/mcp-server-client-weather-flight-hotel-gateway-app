package com.weather_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weather_service.entity.Weather;
import com.weather_service.repository.WeatherRepository;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherRepository repo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {

        repo.deleteAll();

        jdbcTemplate.execute("ALTER TABLE weather AUTO_INCREMENT = 1");

        repo.saveAll(List.of(
                new Weather(null, "New York", "Sunny"),
                new Weather(null, "London", "Cloudy")));
    }

    @GetMapping
    public List<Weather> getAll() {
        return repo.findAll();
    }
    
    // NEW ENDPOINT: Matched with your WebFlux tool path: /weather/api/v1/current?city=...
    @GetMapping("/current")
    public ResponseEntity<Weather> getWeatherByCity(@RequestParam String city) {
        return repo.findAll().stream()
                .filter(w -> w.getCity().equalsIgnoreCase(city))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); 
                // Tip: In production, you'd add a custom finder in your WeatherRepository
    }
}
