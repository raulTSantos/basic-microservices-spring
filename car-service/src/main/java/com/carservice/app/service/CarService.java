package com.carservice.app.service;

import java.util.List;

import com.carservice.app.model.Car;

public interface CarService {
	public Car save(Car user);
	public List<Car> findAll();
	public Car findById(Long id); 
	public List<Car> findByUserId(Long id); 
}
