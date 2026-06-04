package com.flight_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flight_service.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {}
