package com.backend.server.repository;

import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import com.backend.server.entity.Dicom;


// @Repository
// public interface DicomRepository extends JpaRepository<Dicom,String>{


// }



// import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.server.entity.Dicom;


// public interface DicomRepository extends JpaRepository<Dicom, Integer> {

// 	   @Query
// 	    List<Dicom> findByHospitalId(@Param("hospital_id") int hospital_id);
// 	}

public interface DicomRepository extends JpaRepository<Dicom,Long> {
    Optional<Dicom> findByName(String f);
        @Query
	    List<Dicom> findByPatientId(@Param("p_id") Long p_id);
}