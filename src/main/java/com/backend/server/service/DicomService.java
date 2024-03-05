package com.backend.server.service;

// // import org.springframework.web.multipart.MultipartFile;

// // import com.backend.server.entity.Dicom;




// // public interface DicomService 
// // {
// //     Dicom saveAttachment(MultipartFile file) throws Exception;

// //     Dicom getAttachment(String fileId) throws Exception;
// // }



// import java.io.File;
// import java.io.IOException;
// import java.net.MalformedURLException;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.text.SimpleDateFormat;
// import java.time.LocalDate;
// import java.time.Month;
// import java.time.ZoneId;
// import java.util.*;

// import org.dcm4che3.data.Attributes;
// import org.dcm4che3.data.Tag;
// import org.dcm4che3.io.DicomInputStream;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.io.UrlResource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.multipart.MultipartFile;

// import com.backend.server.repository.DicomRepository;




// @Component
// public class DicomService {

//     private String FOLDER_PATH = "C:\\Users\\rauna\\OneDrive\\Desktop\\Hospital Managment System\\backend\\Hospital_Management_System\\src\\main\\resources\\Dicoms\\";

//     @Autowired
//     public DicomRepository dicomRepository;

//     @Autowired
//     public PatientService patient_service;

//     public String uploadImageToFileSystem(MultipartFile file,int hospital_id) throws IOException {


//         Random random = new Random();
//         String filePath=FOLDER_PATH+file.getOriginalFilename() + random.nextInt();



//         Dicom dicom = new Dicom();
//         dicom.setFileUrl(filePath);
//         dicom.setHospital(hospital_service.getHospitalById(hospital_id));


//         File newFile = new File(filePath);
//         file.transferTo(newFile);

//         Path dicomFile = newFile.toPath();
//         @SuppressWarnings("resource")
//         DicomInputStream dis = new DicomInputStream(dicomFile.toFile());




        
//         @SuppressWarnings("deprecation")
//         Attributes fileAttributes = dis.readDataset(-1 , -1);

//         LocalDate localDate = LocalDate.of(1970, Month.JANUARY, 01);

//         // DCM4CHE reads patient Attributes
//         String dicomPatientId = fileAttributes.getString(Tag.PatientID) == null ? "NONE" : fileAttributes.getString(Tag.PatientID);
//         String patientName = fileAttributes.getString(Tag.PatientName) == null ? "NONE" : fileAttributes.getString(Tag.PatientName);
//         Date date = fileAttributes.getDate(Tag.StudyDate) == null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : fileAttributes.getDate(Tag.StudyDate);
//         String modality = fileAttributes.getString(Tag.Modality) == null ? "NONE" : fileAttributes.getString(Tag.Modality) ;
//         String age = fileAttributes.getString(Tag.PatientAge) == null ? "NONE" : fileAttributes.getString(Tag.PatientAge);
//         Date birthDate = fileAttributes.getDate(Tag.PatientBirthDate) == null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())  : fileAttributes.getDate(Tag.PatientBirthDate);
//         String patientSex = fileAttributes.getString(Tag.PatientSex) == null ? "NONE" : fileAttributes.getString(Tag.PatientSex);

        
//         //Setting The attributes in Dicom entity
//         dicom.setPatientId(dicomPatientId);
//         dicom.setPatientName(patientName);
//         dicom.setStudyDate(date);
//         dicom.setModality(modality);
//         dicom.setAge(age);
//         dicom.setBirthDate(birthDate);
//         dicom.setPatientSex(patientSex);





//         Dicom fileData=dicomRepository.save(dicom);

//         if (fileData != null) {
//             return  filePath;
//         }
//         return null;
//     }


//     public org.springframework.core.io.Resource downloadImage(int dicomId) throws IOException{

//         Optional<Dicom> optional_dicom= dicomRepository.findById(dicomId);
//         Dicom dicom;
//         if(!optional_dicom.isPresent()){
//             throw new DicomNotFoundException("Dicom not available");
//         }

//         dicom=optional_dicom.get();
//         String fileUrl = dicom.getFileUrl();



// // Convert URI to file system path
//         Path path = Paths.get(fileUrl);

//         org.springframework.core.io.Resource resource;
//         try {
//             resource = new UrlResource(path.toUri());
//         } catch (MalformedURLException e) {
//             throw new RuntimeException("Issue in reading the file", e);
//         }

//         if (resource.exists() && resource.isReadable()) {
//             return resource;
//         } else {
//             throw new RuntimeException("The file doesn't exist or is not readable");
//         }

//     }

//    public List<String>  getPatientDicomDetails(int dicom_id) {
//          Optional<Dicom> optional_dicom= dicomRepository.findById(dicom_id);
//          Dicom dicom;
//          if(!optional_dicom.isPresent()){
//              throw new DicomNotFoundException("Dicom not available");
//          }

//              dicom=optional_dicom.get();

//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        // Convert Date object to string
//        String StudydateString = dateFormat.format(dicom.getStudyDate());
//        String BirthDateString = dateFormat.format(dicom.getBirthDate());

//        List<String>patientDicomDetails= new ArrayList<>();

//        patientDicomDetails.add(dicom.getPatientId());
//        patientDicomDetails.add(dicom.getPatientName());
//        patientDicomDetails.add(dicom.getModality());
//        patientDicomDetails.add(StudydateString);
//        patientDicomDetails.add(dicom.getAge());

//        patientDicomDetails.add(dicom.getPatientSex());
//        patientDicomDetails.add(BirthDateString);


//        // PatientDicomDetails patientDicomDetails = new PatientDicomDetails(dicom.getPatientId(),dicom.getPatientName(),dicom.getModality(),StudydateString,dicom.getAge(),dicom.getPatientSex(),BirthDateString);
//         return patientDicomDetails;
//    }
   
//    public List<Dicom> getAllDicomsByHospitalId(int hospital_id){
//        return dicomRepository.findByHospitalId(hospital_id);
//    }

//    public void CommentOnDicom(int dicomId,String comment){
//        Optional<Dicom> optional_dicom= dicomRepository.findById(dicomId);
//        Dicom dicom;
//        if(!optional_dicom.isPresent()){
//            throw new DicomNotFoundException("Dicom not available");
//        }

//        dicom=optional_dicom.get();
//        dicom.setComment(comment);
//        dicomRepository.save(dicom);

//    }

//    public String getCommentyrr(int dicomId){
//        Optional<Dicom> optional_dicom= dicomRepository.findById(dicomId);
//        Dicom dicom;
//        if(!optional_dicom.isPresent()){
//            throw new DicomNotFoundException("Dicom not available");
//        }

//        dicom=optional_dicom.get();
//        return dicom.getComment();
//    }

//    public String updateDicom(int dicomId,MultipartFile file) throws IOException{

//        Random random = new Random();
//        String filePath=FOLDER_PATH+file.getOriginalFilename() + random.nextInt();



//        Optional<Dicom> optional_dicom= dicomRepository.findById(dicomId);
//        Dicom dicom;
//        if(!optional_dicom.isPresent()){
//            throw new DicomNotFoundException("Dicom not available");
//        }

//        dicom=optional_dicom.get();
//        dicom.setFileUrl(filePath);
//        dicom.setHospital(dicom.getHospital());


//        File newFile = new File(filePath);
//        file.transferTo(newFile);

//        Path dicomFile = newFile.toPath();
//        @SuppressWarnings("resource")
//     DicomInputStream dis = new DicomInputStream(dicomFile.toFile());


//        @SuppressWarnings("deprecation")
//        Attributes fileAttributes = dis.readDataset(-1 , -1);

//        LocalDate localDate = LocalDate.of(1970, Month.JANUARY, 01);

//        // DCM4CHE reads patient Attributes
//        String dicomPatientId = fileAttributes.getString(Tag.PatientID) == null ? "NONE" : fileAttributes.getString(Tag.PatientID);
//        String patientName = fileAttributes.getString(Tag.PatientName) == null ? "NONE" : fileAttributes.getString(Tag.PatientName);
//        Date date = fileAttributes.getDate(Tag.StudyDate) == null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : fileAttributes.getDate(Tag.StudyDate);
//        String modality = fileAttributes.getString(Tag.Modality) == null ? "NONE" : fileAttributes.getString(Tag.Modality) ;
//        String age = fileAttributes.getString(Tag.PatientAge) == null ? "NONE" : fileAttributes.getString(Tag.PatientAge);
//        Date birthDate = fileAttributes.getDate(Tag.PatientBirthDate) == null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())  : fileAttributes.getDate(Tag.PatientBirthDate);
//        String patientSex = fileAttributes.getString(Tag.PatientSex) == null ? "NONE" : fileAttributes.getString(Tag.PatientSex);


//        dicom.setPatientId(dicomPatientId);
//        dicom.setPatientName(patientName);
//        dicom.setStudyDate(date);
//        dicom.setModality(modality);
//        dicom.setAge(age);
//        dicom.setBirthDate(birthDate);
//        dicom.setPatientSex(patientSex);

//        Dicom fileData=dicomRepository.save(dicom);
//        if (fileData != null) {
//            return  filePath;
//        }
//        return null;
//    }
// }



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.server.entity.Dicom;
import com.backend.server.exception.DicomNotFoundException;
import com.backend.server.repository.DicomRepository;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class DicomService {

  
    @Autowired
    private DicomRepository fileDataRepository;

    @Autowired
    public PatientService patient_service;

    private final String FOLDER_PATH="D:\\SpringBoot\\DeeptekAssignment\\server\\src\\main\\resources\\DicomFiles\\";


    @SuppressWarnings("unused")
    public String uploadImageToFileSystem(MultipartFile file, Long p_id) throws IOException {
        String filePath=FOLDER_PATH+ Long.toString(p_id)+file.getOriginalFilename();

        @SuppressWarnings("null")
        Dicom fileData=fileDataRepository.save(Dicom.builder()
                .name(file.getOriginalFilename())
                .p_id(p_id)
                .type(file.getContentType())
                .filePath(filePath).build());
                
               
                
        fileData.setPatient(patient_service.getPatientById(p_id));

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }

   

    // public byte[] downloadImageFromFileSystem(String f) throws IOException {
    //     Optional<Dicom> fileData = fileDataRepository.findByName(f);
    //     String filePath=fileData.get().getFilePath();
    //     byte[] images = Files.readAllBytes(new File(filePath).toPath());
    //     return images;
    // }



@SuppressWarnings("null")
public org.springframework.core.io.Resource downloadImage(Long p_id) throws IOException{

        Optional<Dicom> optional_dicom= fileDataRepository.findById(p_id);
        Dicom dicom;
        if(!optional_dicom.isPresent()){
            throw new DicomNotFoundException("Dicom not available");
        }

        dicom=optional_dicom.get();
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

    

    // public String updateDicomFileSystem(Long id, MultipartFile file) {
        


        
    //     Dicom fileData=DicomRepository.save(Dicom);
    //     if (fileData != null) {
    //         return  filePath;
    //     }
    //     return null;
    // }

    

   

   


}