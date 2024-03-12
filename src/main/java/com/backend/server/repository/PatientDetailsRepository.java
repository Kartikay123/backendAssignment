package com.backend.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.server.entity.PatientDetails;

public interface PatientDetailsRepository extends JpaRepository<PatientDetails,Long> {
    
}
