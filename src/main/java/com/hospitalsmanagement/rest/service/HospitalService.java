package com.hospitalsmanagement.rest.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsmanagement.rest.entity.Doctor;
import com.hospitalsmanagement.rest.entity.Hospital;
import com.hospitalsmanagement.rest.repository.HospitalRepository;

import jakarta.transaction.Transactional;

@Service
public class HospitalService {
	
	@Autowired
	HospitalRepository hospitalRepository ;	
	
	@Transactional
	public Hospital saveHospital(Hospital hospital) {
		return hospitalRepository.save(hospital);
	}

	@Transactional
	public List<Hospital> saveHospitals(List<Hospital> hospitals) {
		
		return hospitalRepository.saveAll(hospitals);
	}

	public List<Hospital> getAllHospital() {
		
		return hospitalRepository.findAll();
	}

	public Optional<Hospital> getHospitalbyId(Long id) {
		
		return hospitalRepository.findById(id);
		
	}

	public boolean deleteById(Long id) {
		if(hospitalRepository.existsById(id)) {
			hospitalRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public List<Hospital> getByRatingGreaterthan(double rating) {
		return hospitalRepository.findByRatingGreaterThan(rating);
	}
	
	public List<Hospital> getByRatingLessthan(double rating) {
		return hospitalRepository.findByRatingLessThan(rating);
	}

	public List<Hospital> getByRatingBetween(double maxRating, double minRating) {
		return hospitalRepository.findByRatingBetween(maxRating, minRating);
	}

	public Optional<Hospital> getHospitalByLocationAndName(String location, String name) {
		return hospitalRepository.findByLocationAndName(location, name);
	}

	public List<Hospital> getHospitalByLocationOrName(String location, String name) {
		return hospitalRepository.findByLocationOrName(location, name);
	}

	public List<Hospital> getHospitalsBylocationAndRatingRange(String location, double minRating, double maxRating) {
		
		return hospitalRepository.getHospitalsBylocationAndRatingRange(location,minRating,maxRating);
	}

	public List<Hospital> getHospitalByRatingInSorted() {
		
		return hospitalRepository.getHospitalByRatingInSorted();
	}

	public boolean deleteByEmail(String email) {
		
		if(hospitalRepository.existsByEmail(email)) {
			hospitalRepository.deleteByEmail(email);
			return true;
		}
		return false;
	}
	
	@Transactional
	public void deleteByRatingRange(double rating) {
		
		List<Hospital> hospitals=hospitalRepository.findByRatingGreaterThan(rating);
		
		hospitalRepository.deleteAll(hospitals);
		
	}

//	@Transactional
//	public boolean deleteByRatingRange(double minRating, double maxRating) {
//		
//		if(minRating>=0 &&maxRating<=10) {
//			hospitalRepository.findByRatingBetween(maxRating, minRating)
//			return true;
//		}
//		return false;
//	}
	
	
	
	
	
	
}
