package com.hospitalsmanagement.rest.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospitalsmanagement.rest.entity.Hospital;
import com.hospitalsmanagement.rest.errors.ApiResponse;
import com.hospitalsmanagement.rest.service.HospitalService;




@RestController
@RequestMapping("/api/v1")
public class HospitalController {
	
	@Autowired
	HospitalService hospitalService;
	
	@PostMapping("/hospital")
	public ResponseEntity<Hospital> saveHospital(@RequestBody Hospital hospital ) {
		
		Hospital savedHospital = hospitalService.saveHospital(hospital);
	
		return ResponseEntity.status(HttpStatus.CREATED)
				.header("info", "hospital saved successfully")
				.body(savedHospital);
	}
	
	@PostMapping("/hospital/bulk")
	public ResponseEntity<List<Hospital>> saveHospitals(@RequestBody List<Hospital> hospitals ) {
		
		List<Hospital> savedHospitals=hospitalService.saveHospitals(hospitals);
	
		return ResponseEntity.status(HttpStatus.CREATED)
				.header("info", "hospital saved successfully")
				.body(savedHospitals);
	}
	
	@GetMapping("/hospital")
	public ResponseEntity<List<Hospital>> getAllHospital() {

		List<Hospital> hospitals=hospitalService.getAllHospital();
		
		return ResponseEntity.status(HttpStatus.OK)
				.header("info", "hospitals retrived successfully")
				.body(hospitals);
	}
	
	@GetMapping("/hospital/{id}")
	public ResponseEntity<?> getHospitalbyId(@PathVariable Long id) {

		Optional<Hospital> optional=hospitalService.getHospitalbyId(id);
		
		if(optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.header("info", "hospital retrived successfully")
					.body(optional.get());
		}
		
	else {
		ApiResponse apiResponse =new ApiResponse();
		apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		apiResponse.setMessage("Hospital not found by given id "+id);
		apiResponse.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.header("info", "hospital Not found ")
				.body(apiResponse);
	}
		
	}
	
	
	@GetMapping("/hospital/rating/greater-than/{rating}")
	public ResponseEntity<List<Hospital>> getByRatingGreaterthan(@PathVariable double rating) {
		
		List<Hospital> hospitals=hospitalService.getByRatingGreaterthan(rating);
		
		return ResponseEntity.status(HttpStatus.OK)
				.header("info", "hospitals retrived successfully")
				.body(hospitals);
		
	}
	
	@GetMapping("/hospital/rating/less-than/{rating}")
	public ResponseEntity<List<Hospital>> getByRatinglessthan(@PathVariable double rating) {
		
		List<Hospital> hospitals=hospitalService.getByRatingLessthan(rating);
		
		return ResponseEntity.status(HttpStatus.OK)
				.header("info", "hospitals retrived successfully")
				.body(hospitals);
		
	}
	
	@GetMapping("/hospital/rating-between/{maxRating}/{minRating}")
	public ResponseEntity<List<Hospital>> getByRatingBetween(@PathVariable double maxRating,@PathVariable double minRating) {
		
		List<Hospital> hospitals=hospitalService.getByRatingBetween(maxRating,minRating);
		
		return ResponseEntity.status(HttpStatus.OK)
				.header("info", "hospitals retrived successfully")
				.body(hospitals);
		
	}
	
	@GetMapping("/hospital/search/{location}/{name}")
	public ResponseEntity<?> getHospitalByLocationAndName(@PathVariable String location ,@PathVariable String name) {
		
		Optional<Hospital> hospital =hospitalService.getHospitalByLocationAndName(location,name);
		
		if(hospital.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.header("info", "hospitals retrived successfully")
					.body(hospital.get());
		}
		else {
			ApiResponse apiResponse =new ApiResponse();
			apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			apiResponse.setTimestamp(LocalDateTime.now());
			apiResponse.setMessage("hospital not found with given "+location +" and "+name+" name");
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.header("info", "data not found ")
					.body(apiResponse);
		}
		
	}
	
	@GetMapping(value = "/hospital/search",params = {"location","name"})
	public ResponseEntity<?> getHospitalByLocationOrName(@RequestParam String location ,@RequestParam String name) {
		
		List<Hospital> hospitals =hospitalService.getHospitalByLocationOrName(location,name);
		
		if(!(hospitals.isEmpty())) {
			return ResponseEntity.status(HttpStatus.OK)
					.header("info", "hospitals retrived successfully")
					.body(hospitals);
		}
		else {
			ApiResponse apiResponse =new ApiResponse();
			apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			apiResponse.setTimestamp(LocalDateTime.now());
			apiResponse.setMessage("hospital not found with given "+location +" or "+name+" name");
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.header("info", "data not found ")
					.body(apiResponse);
		}
	}
	
	@GetMapping("/hospital/{location}/{minRating}/{maxRating}")
	public ResponseEntity<?> getHospitalsBylocationAndRatingRange(@PathVariable String location,@PathVariable double minRating,@PathVariable double maxRating) {
		
		List<Hospital> hospitals=hospitalService.getHospitalsBylocationAndRatingRange(location,minRating,maxRating);
		if(!hospitals.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.header("info", "data retrived successfully")
					.body(hospitals);
		}
		else {
			ApiResponse apiResponse =new ApiResponse();
			apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			apiResponse.setTimestamp(LocalDateTime.now());
			apiResponse.setMessage("hospital not found with given "+location +" and rating between "+minRating+" and "+maxRating );
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.header("info", "data not found ")
					.body(apiResponse);
		}
	}
	

	@GetMapping("/hospital/sort-by-rating")
	public ResponseEntity<List<Hospital>> getHospitalByRatingInSorted() {
		
		List<Hospital> hospitals=hospitalService.getHospitalByRatingInSorted();
		
		return ResponseEntity.status(HttpStatus.OK)
				.header("info", "data retrived successfully")
				.body(hospitals);	
		}
	
	
	@DeleteMapping("/hospital/{id}")
	public ResponseEntity<ApiResponse> deleteHospitalbyId(@PathVariable Long id) {

			boolean result=hospitalService.deleteById(id);
			
			if(result) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.header("info", "employye deleted ")
						.build();
			}
			else {
				ApiResponse apiResponse =new ApiResponse();
				apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
				apiResponse.setTimestamp(LocalDateTime.now());
				apiResponse.setMessage("hospital not found with id "+id);
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.header("info", "data not found ")
						.body(apiResponse);
			}
		}
	
	
	@DeleteMapping("/hospital/email/{email}")
	public ResponseEntity<ApiResponse> deleteByEmail(@PathVariable String email){
	boolean result=hospitalService.deleteByEmail(email);
	
	if(result) {
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.header("info", "Hospital deleted ")
				.build();
	}
	else {
		ApiResponse apiResponse =new ApiResponse();
		apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		apiResponse.setTimestamp(LocalDateTime.now());
		apiResponse.setMessage("hospital not found with email "+email);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.header("info", "data not found ")
				.body(apiResponse);
	}
	}
	
	
//	@DeleteMapping("/hospital/rating/{minRating}/{maxRating}")
//	public ResponseEntity<?> deleteByRatingRange(@PathVariable double minRating, @PathVariable double maxRating){
//	
//		hospitalService.deleteByRatingRange(minRating,maxRating);
//	
//	
//		return ResponseEntity.status(HttpStatus.NO_CONTENT)
//				.header("info", "Hospitals deleted ")
//				.build();
//	
//	}
	
	@DeleteMapping("/hospital/rating/greater-than/{rating}")
	public ResponseEntity<?> deleteByRatingRange(@PathVariable double rating){
		
		hospitalService.deleteByRatingRange(rating);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
			.header("info", "Hospitals deleted ")
				.build();
	}
	
	
	
	
	
	
}


















