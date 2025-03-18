package com.hospitalsmanagement.rest.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hospital {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String location;
	private double rating;
	private String email;
	
	@OneToOne(cascade = CascadeType.ALL ,orphanRemoval = true)
	@JoinColumn(name = "Address_id")
	private Address address;
	
	@OneToMany(cascade = CascadeType.ALL ,orphanRemoval = true)
	@JoinColumn(name = "Hospital_id")
	private List<Doctor> doctors;
}

