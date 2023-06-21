package com.userservice.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.app.dto.CarDTO;
import com.userservice.app.model.User;
import com.userservice.app.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User savedUser = userService.saveUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	@GetMapping("{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") Long userId) {
		User responseDto = userService.getUserById(userId);
		if (responseDto == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping
	public ResponseEntity<List<User>> listarUsuarios() {
		List<User> usuarios = userService.getAll();
		if (usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}

	@CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackGetCars")
	@GetMapping("/car/{userId}")
	public ResponseEntity<List<CarDTO>> listarCarros(@PathVariable("userId") long id) {
		User usuario = userService.getUserById(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}

		List<CarDTO> carros = userService.getCars(id);
		return ResponseEntity.ok(carros);
	}

	@CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackSaveCars")
	@PostMapping("/car/{userId}")
	public ResponseEntity<CarDTO> guardarCarro(@PathVariable("userId") long userId, @RequestBody CarDTO carro) {
		CarDTO nuevoCarro = userService.saveCarWithFeignClient(userId, carro);
		return ResponseEntity.ok(nuevoCarro);
	}

	@CircuitBreaker(name = "vehiclesCB", fallbackMethod = "fallbackGetVehicles")
	@GetMapping("/vehicles/{userId}")
	public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("userId") long usuarioId) {
		Map<String, Object> resultado = userService.getUserAndVehiclesWithFeignClient(usuarioId);
		return ResponseEntity.ok(resultado);
	}

	public static ResponseEntity<?> fallbackGetCars(@PathVariable("userId") long id, Exception e) {
		return new ResponseEntity<>("Servicio de carros no disponible", HttpStatus.SERVICE_UNAVAILABLE);
	}

	public static ResponseEntity<?> fallbackSaveCars(@PathVariable("userId") long userId, @RequestBody CarDTO carro,
			Exception e) {
		return new ResponseEntity<>("Servicio de guardado de carros no disponible", HttpStatus.SERVICE_UNAVAILABLE);
	}

	public static ResponseEntity<?> fallbackGetVehicles(@PathVariable("userId") long id, Exception e) {
		return new ResponseEntity<>("Servicio de Vehiculos no disponible", HttpStatus.SERVICE_UNAVAILABLE);
	}
}
