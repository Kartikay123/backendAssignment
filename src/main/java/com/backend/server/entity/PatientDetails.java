package com.backend.server.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Patient_Details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDetails {

    @Id
    private Long patient_did;
    private String patientName;
    private String patientId;
    private String age;
    private String sex;
    private Date birthDate;
    private Date studyUploadDate;




    public Long getpatient_did() {
        return patient_did;
    }

    public void setpatient_did(Long patient_did) {
        this.patient_did = patient_did;
    }


   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "id")
    private Patient patient;

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
