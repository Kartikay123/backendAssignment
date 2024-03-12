package com.backend.server.service;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.server.entity.Dicom;
import com.backend.server.entity.PatientDetails;
import com.backend.server.exception.DicomNotFoundException;
import com.backend.server.repository.DicomRepository;
import com.backend.server.repository.PatientDetailsRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DicomService {

    @Autowired
    private DicomRepository fileDataRepository;

    @Autowired
    public PatientService patient_service;

    @Autowired
    public PatientDetailsRepository patient_dDetailsRepository;

    private final String FOLDER_PATH = "D:\\SpringBoot\\DeeptekAssignment\\server\\src\\main\\resources\\DicomFiles\\";

    @SuppressWarnings("unused")
    public String uploadImageToFileSystem(MultipartFile file, Long p_id) throws IOException {
        String filePath = FOLDER_PATH + Long.toString(p_id) + file.getOriginalFilename();

        @SuppressWarnings("null")
        Dicom fileData = fileDataRepository.save(Dicom.builder()
                .name(file.getOriginalFilename())
                .p_id(p_id)
                .type(file.getContentType())
                .filePath(filePath).build());

        fileData.setPatient(patient_service.getPatientById(p_id));

        File newFile = new File(filePath);
        file.transferTo(newFile);

        Path dicomFile = newFile.toPath();
        // @SuppressWarnings("resource");
        @SuppressWarnings("resource")
        DicomInputStream dis = new DicomInputStream(dicomFile.toFile());

        @SuppressWarnings("deprecation")
        Attributes fileAttributes = dis.readDataset(-1, -1);

        LocalDate localDate = LocalDate.of(1970, Month.JANUARY, 01);

        // DCM4CHE reads patient Attributes
        String dicomPatientId = fileAttributes.getString(Tag.PatientID) == null ? "NONE"
                : fileAttributes.getString(Tag.PatientID);
        String patientName = fileAttributes.getString(Tag.PatientName) == null ? "NONE"
                : fileAttributes.getString(Tag.PatientName);
        Date date = fileAttributes.getDate(Tag.StudyDate) == null
                ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
                : fileAttributes.getDate(Tag.StudyDate);
        String age = fileAttributes.getString(Tag.PatientAge) == null ? "NONE"
                : fileAttributes.getString(Tag.PatientAge);
        Date birthDate = fileAttributes.getDate(Tag.PatientBirthDate) == null
                ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
                : fileAttributes.getDate(Tag.PatientBirthDate);
        String patientSex = fileAttributes.getString(Tag.PatientSex) == null ? "NONE"
                : fileAttributes.getString(Tag.PatientSex);

        PatientDetails patientDetails = new PatientDetails();


        // Calculate age
        LocalDate birthDateLocal = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate studyDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period agePeriod = Period.between(birthDateLocal, studyDate);
        String ageString = agePeriod.getYears() + " years";

        // Set ageString to patientDetails
        // Setting The attributes in Dicom entity
        patientDetails.setpatient_did(p_id);
        patientDetails.setPatientId(dicomPatientId);
        patientDetails.setPatientName(patientName);
        patientDetails.setStudyUploadDate(date);
        patientDetails.setAge(ageString);
        patientDetails.setBirthDate(birthDate);
        patientDetails.setSex(patientSex);

        PatientDetails patientDetails2 = patient_dDetailsRepository.save(patientDetails);
        if (fileData != null) {
            return filePath;
        }
        return null;

    }

    @SuppressWarnings("null")
    public org.springframework.core.io.Resource downloadImage(Long p_id) throws IOException {

        Optional<Dicom> optional_dicom = fileDataRepository.findById(p_id);
        Dicom dicom;
        if (!optional_dicom.isPresent()) {
            throw new DicomNotFoundException("Dicom not available");
        }

        dicom = optional_dicom.get();
        String fileUrl = dicom.getFilePath();

        Path path = Paths.get(fileUrl);

        org.springframework.core.io.Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("The file doesn't exist or is not readable");
        }

    }

    public List<String> getPatientDicomDetails(Long patient_did) {
        @SuppressWarnings("null")
        Optional<PatientDetails> optional_dicom = patient_dDetailsRepository.findById(patient_did);
        PatientDetails dicom;
        if (!optional_dicom.isPresent()) {
            throw new DicomNotFoundException("Dicom not available");
        }

        dicom = optional_dicom.get();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Convert Date objects to strings
        String StudydateString = dateFormat.format(dicom.getStudyUploadDate());
        String BirthDateString = dateFormat.format(dicom.getBirthDate());

        List<String> patientDicomDetails = new ArrayList<>();

        // Add values to the list after conversion to strings
        patientDicomDetails.add(dicom.getPatientId()); // Convert to string if needed
        patientDicomDetails.add(dicom.getPatientName());
        patientDicomDetails.add(StudydateString);
        patientDicomDetails.add(dicom.getAge()); // Convert to string if needed
        patientDicomDetails.add(dicom.getSex());
        patientDicomDetails.add(BirthDateString);

        return patientDicomDetails;
    }

    // public String updateDicomFileSystem(Long id, MultipartFile file) {

    // Dicom fileData=DicomRepository.save(Dicom);
    // if (fileData != null) {
    // return filePath;
    // }
    // return null;
    // }

}