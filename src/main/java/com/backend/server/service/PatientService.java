package com.backend.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.server.entity.Patient;
import com.backend.server.repository.PatientRepository;




@Service
public class PatientService {
    
    @Autowired
	private PatientRepository patientRepository;


    public List<Patient> searchPatientName(String query) {
		List<Patient> val = patientRepository.findByNameStartsWith(query);
		return val;
	}
	public List<Patient> searchPatientID(String query) {
		List<Patient> val = patientRepository.findByIDStartsWith(query);
		return val;
	}
    public List<Patient> searchPatientEmail(String query) {
		List<Patient> val = patientRepository.findByEmailStartsWith(query);
		return val;
	}
    public List<Patient> searchPatientUploadDate(String query) {
		List<Patient> val = patientRepository.findByUploadDateStartsWith(query);
		return val;
	}
    public List<Patient> searchPatientDicomStatus(String query) {
        List<Patient> patients = patientRepository.findByDicomStatusStartsWith(Boolean.parseBoolean(query));
        return patients;
    }

    @SuppressWarnings("null")
    public Patient getPatientById(Long id) {
		Optional<Patient> e = patientRepository.findById(id);
		return e.orElse(null);
	}




	// @Override
    // public Patient saveAttachment(MultipartFile file) throws Exception {
    //    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    //    try {
    //         if(fileName.contains("..")) {
    //             throw  new Exception("Filename contains invalid path sequence "
    //             + fileName);
    //         }

	// 		Patient patient= new Patient();
	// 		Patient(fileName,
	// 		file.getContentType(),
	// 		file.getBytes());
	// return patientRepository.save(patient);

    //    } catch (Exception e) {
    //         throw new Exception("Could not save File: " + fileName);
    //    }
    // }

    // private void Patient(String fileName, String contentType, byte[] bytes) {
		
	// }
	// @Override
    // public Patient getAttachment(String fileId) throws Exception {
    //     return PatientRepository
    //             .findById(fileId)
    //             .orElseThrow(
    //                     () -> new Exception("File not found with Id: " + fileId));
    // }
   
    
	
}
