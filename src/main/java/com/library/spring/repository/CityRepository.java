package com.library.spring.repository;

import com.library.spring.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findCityByCityName(String cityName);
}
