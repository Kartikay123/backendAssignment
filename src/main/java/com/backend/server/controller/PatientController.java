package com.backend.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import com.backend.server.entity.Patient;
import com.backend.server.exception.PatientNotFoundException;
import com.backend.server.repository.DicomRepository;
import com.backend.server.repository.PatientDetailsRepository;
import com.backend.server.repository.PatientRepository;
import com.backend.server.service.PatientService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;


@RestController
@CrossOrigin("http://localhost:3000")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Autowired
    private DicomRepository dicomRepository;

    @PostMapping("/patient")
   Patient newPatient(@NonNull @RequestBody Patient newPatient)
   {
     return patientRepository.save(newPatient);
   }

   @GetMapping("/patient")
   List<Patient> getAllPatient()
   {
        return patientRepository.findAll();
   }
   
    @SuppressWarnings("null")
    @GetMapping("/patient/{id}")
    Patient getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    @SuppressWarnings("null")
    @PutMapping("/patient/{id}")
    Patient updatePatient(@RequestBody Patient newUser, @PathVariable Long id) {
        return patientRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setGender(newUser.getGender());
                    user.setDateOfBirth(newUser.getDateOfBirth());
                    user.setUploadDate(newUser.getUploadDate());
                    user.setDicomAttached(newUser.isDicomAttached());
                    
                    return patientRepository.save(user); 
                })
                .orElseThrow(() -> new PatientNotFoundException(id));
    }
    

    @SuppressWarnings("null")
    @DeleteMapping("/patient/{id}")
    String deletePatient(@PathVariable Long id){
        if(!patientRepository.existsById(id)){
            throw new PatientNotFoundException(id);
        }
        if(patientDetailsRepository.existsById(id))
        {
            patientDetailsRepository.deleteById(id);
        }
        if(dicomRepository.existsById(id))
        {
            dicomRepository.deleteById(id);
        }
        patientRepository.deleteById(id);
        return  "User with id "+id+" has been deleted success.";
    }


    @GetMapping("/patient/SearchByName")
    public ResponseEntity<List<Patient>> searchPatientbyName(@RequestParam("query") String query) {
        return ResponseEntity.ok(patientService.searchPatientName(query));
    }

    @GetMapping("/patient/SearchByID")
    public ResponseEntity<List<Patient>> searchPatientbyID(@RequestParam("query") String query) {
        return ResponseEntity.ok(patientService.searchPatientID(query));
    }

    @GetMapping("/patient/SearchByEmail")
    public ResponseEntity<List<Patient>> searchPatientbyEmail(@RequestParam("query") String query) {
        return ResponseEntity.ok(patientService.searchPatientEmail(query));
    }
    @GetMapping("/patient/SearchByUploadDate")
    public ResponseEntity<List<Patient>> searchPatientbyUploadDate(@RequestParam("query") String query) {
        return ResponseEntity.ok(patientService.searchPatientUploadDate(query));
    }
    @GetMapping("/patient/SearchByDicomStatus")
    public ResponseEntity<List<Patient>> searchPatientbyDicomStatus(@RequestParam("query") String query) {
        return ResponseEntity.ok(patientService.searchPatientDicomStatus(query));
    }

    

}
