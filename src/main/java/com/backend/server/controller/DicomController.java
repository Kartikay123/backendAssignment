package com.backend.server.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.server.service.DicomService;

import jakarta.servlet.http.HttpServletRequest;


@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class DicomController {

    @Autowired
    private DicomService dicom_service;

    
   
   

    @PostMapping("/upload/{p_id}")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("file") MultipartFile file, @PathVariable("p_id")Long p_id) throws IOException {
        // Use the id parameter in your backend logic
        String uploadImage = dicom_service.uploadImageToFileSystem(file,p_id);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

	

     @GetMapping("/view/{p_id}")
    public ResponseEntity<?> downLoadImage (@PathVariable("p_id") Long p_id, HttpServletRequest request) throws IOException{
        Resource resource= dicom_service.downloadImage(p_id);
        String mimeType;

        try{
            mimeType= request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch(IOException e){
            mimeType=  MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        mimeType= (mimeType==null)? MediaType.APPLICATION_OCTET_STREAM_VALUE : mimeType;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }
    @GetMapping("/view/details/{patient_did}")
    public List<String> getPatientDicomDetails(@PathVariable("patient_did") Long patient_did){

        List<String> response=  dicom_service.getPatientDicomDetails(patient_did);
        return response;

    }
    

}

