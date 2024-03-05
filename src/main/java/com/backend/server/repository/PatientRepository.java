package com.backend.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.server.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE LOWER(p.name) LIKE CONCAT(LOWER(:query), '%')")
	List<Patient> findByNameStartsWith(@Param("query") String query);
	
	@Query("SELECT p FROM Patient p WHERE CAST(p.id AS String) LIKE CONCAT(:query, '%')")
	List<Patient> findByIDStartsWith(@Param("query") String query);

    @Query("SELECT p FROM Patient p WHERE LOWER(p.email) LIKE CONCAT(LOWER(:query), '%')")
	List<Patient> findByEmailStartsWith(@Param("query") String query);

    @Query("SELECT p FROM Patient p WHERE LOWER(p.uploadDate) LIKE CONCAT(LOWER(:query), '%')")
	List<Patient> findByUploadDateStartsWith(@Param("query") String query);

    @Query("SELECT p FROM Patient p WHERE p.dicomAttached = :query")
    List<Patient> findByDicomStatusStartsWith(@Param("query") boolean query);
    

    
}
