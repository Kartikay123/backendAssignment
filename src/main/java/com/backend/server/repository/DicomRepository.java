package com.backend.server.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.server.entity.Dicom;

public interface DicomRepository extends JpaRepository<Dicom,Long> {
    
       
}