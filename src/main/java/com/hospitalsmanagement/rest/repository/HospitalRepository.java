package com.hospitalsmanagement.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hospitalsmanagement.rest.entity.Hospital;

import jakarta.transaction.Transactional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Long> {

	
	public List<Hospital> findByRatingGreaterThan(double rating);
	
	public List<Hospital> findByRatingLessThan(double rating);
	
	public List<Hospital> findByRatingBetween(double maxRating ,double minRating);
	
	public Optional<Hospital> findByLocationAndName(String location,String name);
	
	public List<Hospital> findByLocationOrName(String location,String name);

//	jpql
	@Query("select h from Hospital h where h.location=?1 and h.rating between ?2 and ?3")
	public List<Hospital> getHospitalsBylocationAndRatingRange(String location, double minRating, double maxRating);

//	native query
	@Query(value = "select * from hospital order by rating ",nativeQuery = true)
	public List<Hospital> getHospitalByRatingInSorted();


	@Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Hospital h WHERE h.email = :email")
	 boolean existsByEmail( @Param("email") String email);

	@Transactional
	@Modifying
	public void deleteByEmail(String email);

//	public void deleteByRatingGreaterThan(double rating);
	
//	@Modifying
//	@Transactional
//	@Query("delete from Hospital h where h.rating between ?1 and ?2 ")
//	public void deleteByRatingBetween(double minRating, double maxRating);

	
}
