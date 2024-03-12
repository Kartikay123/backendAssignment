package com.backend.server.entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DICOM_DATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dicom {

   
    @Id
    private Long p_id;
    private String name;
    private String type;
    private String filePath;

   
     @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "id")
    private Patient patient;

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
