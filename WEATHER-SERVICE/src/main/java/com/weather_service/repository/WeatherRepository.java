package com.weather_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weather_service.entity.Weather;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {}
