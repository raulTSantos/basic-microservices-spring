package com.carservice.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carservice.app.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	public List<Car> findByUserId(Long userId);
}
