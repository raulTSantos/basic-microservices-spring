package com.userservice.app.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.userservice.app.dto.CarDTO;

@FeignClient(name = "car-service",path = "/api/cars")
public interface CarAPIFeignclient {
	@PostMapping
	public CarDTO save(CarDTO car);

	@GetMapping("/user/{id}")
	public List<CarDTO> getCars(@PathVariable("id") Long userId);
}
