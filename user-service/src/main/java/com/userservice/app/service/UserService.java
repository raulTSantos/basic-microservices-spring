package com.userservice.app.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.userservice.app.dto.CarDTO;
import com.userservice.app.feignclient.CarAPIFeignclient;
import com.userservice.app.model.User;
import com.userservice.app.repository.UserRepository;

@Service
public class UserService {
	private final String URI_CARS_USER_ID = "http://car-service/api/cars/user/";
	
	@Autowired
	private UserRepository repository;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CarAPIFeignclient carFeignClient;

	public List<User> getAll() {
		return repository.findAll();
	}

	public User getUserById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public User saveUser(User user) {
		return repository.save(user);
	}

	public List<CarDTO> getCars(Long userId) {
		List<CarDTO> cars = Arrays.asList(restTemplate.getForObject(URI_CARS_USER_ID + userId, CarDTO[].class));
		return cars;
	}

	public CarDTO saveCarWithFeignClient(Long userId, CarDTO car) {
		car.setUserId(userId);
		return carFeignClient.save(car);
	}

	public Map<String, Object> getUserAndVehiclesWithFeignClient(Long userId) {
		Map<String, Object> resultado = new HashMap<>();
		User usuario = repository.findById(userId).orElse(null);

		if (usuario == null) {
			resultado.put("Mensaje", "El usuario no existe");
			return resultado;
		}
		resultado.put("Usuario", usuario);
		List<CarDTO> carros = carFeignClient.getCars(userId);
		if (carros.isEmpty()) {
			resultado.put("Carros", "El usuario no tiene carros");
		} else {
			resultado.put("Carros", carros);
		}
		return resultado;
	}

}
