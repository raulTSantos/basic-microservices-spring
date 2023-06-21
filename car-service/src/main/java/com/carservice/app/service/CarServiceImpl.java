package com.carservice.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carservice.app.model.Car;
import com.carservice.app.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService{

	@Autowired
	private CarRepository repository;

	@Override
	public Car save(Car user) {
		return repository.save(user);
	}

	@Override
	public List<Car> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Car findById(Long id) {

		return repository.findById(id).orElse(null);
	}

	@Override
	public List<Car> findByUserId(Long id) {
		// TODO Auto-generated method stub
		return repository.findByUserId(id);
	}
}
