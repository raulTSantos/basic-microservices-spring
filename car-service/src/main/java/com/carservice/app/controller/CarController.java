package com.carservice.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carservice.app.model.Car;
import com.carservice.app.service.CarService;

@RestController
@RequestMapping("api/cars")
public class CarController {
	@Autowired
	private CarService service;
	
	@PostMapping
    public ResponseEntity<Car> create(@RequestBody Car car){
		Car savedCar = service.save(car);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }
	@GetMapping("{id}")
    public ResponseEntity<Car> retrieve(@PathVariable("id") Long id){
		Car responseDto = service.findById(id);
		if (responseDto == null){
			return ResponseEntity.noContent().build();
		}
        return ResponseEntity.ok(responseDto);
    }
	@GetMapping
	public ResponseEntity<List<Car>> retrieveAll(){
		List<Car> usuarios = service.findAll();
		if(usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}
	@GetMapping("/user/{id}")
	public ResponseEntity<List<Car>> retrieveByUser(@PathVariable("id") Long userId){
		List<Car> carsList = service.findByUserId(userId);
		if(carsList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(carsList);
	}
}
