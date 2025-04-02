package com.devsuperior.demo.repository;

import com.devsuperior.demo.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findAllByOrderByNameAsc();
}
