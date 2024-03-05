package com.backend.server.controller;

import java.io.IOException;

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

    
    // @PostMapping(value = "/uploadfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // public String uploadImageToFileSystem(@RequestPart("file") MultipartFile file, @PathVariable("id") Long id)
    //         throws IOException {

    //     return dicom_service.uploadImageToFileSystem(file);
    // }

    // @GetMapping("/downloadImage/{filename}")
    // public ResponseEntity<?> downLoadImage(@PathVariable("filename") String filename, HttpServletRequest request) throws IOException {

    //     byte[] imageData=dicom_service.downloadImageFromFileSystem(filename);
	// 	return ResponseEntity.status(HttpStatus.OK)
	// 			.contentType(MediaType.valueOf("image/png"))
	// 			.body(imageData);
    // }


    @PostMapping("/upload/{p_id}")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("file") MultipartFile file, @PathVariable("p_id")Long p_id) throws IOException {
        // Use the id parameter in your backend logic
        String uploadImage = dicom_service.uploadImageToFileSystem(file,p_id);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

	// @GetMapping("/fileSystem/{fileName}")
	// public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
	// 	byte[] imageData=dicom_service.downloadImageFromFileSystem(fileName);
	// 	return ResponseEntity.status(HttpStatus.OK)
	// 			.contentType(MediaType.valueOf("image/png"))
	// 			.body(imageData);

	// }

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
    

}

// // package com.backend.server.controller;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.http.HttpStatus;
// // import org.springframework.http.MediaType;
// // import org.springframework.http.ResponseEntity;
// // import org.springframework.web.bind.annotation.*;
// // import org.springframework.web.multipart.MultipartFile;
// // import org.springframework.web.bind.annotation.PathVariable;

// // import com.backend.server.service.DicomService;

// // import java.io.IOException;

// // @RestController
// // public class FileController {

// // @Autowired
// // private DicomService dicomService;

// // @PostMapping("/upload/{id}")
// // public ResponseEntity<?> uploadFile(@PathVariable Long id,
// @RequestParam("file") MultipartFile file) {
// // try {
// // if (file.isEmpty()) {
// // return ResponseEntity.badRequest().body("Please select a file to upload");
// // }
// // byte[] data = file.getBytes();
// // dicomService.saveFile(id, data); // Pass id along with the file data to
// save
// // return ResponseEntity.ok().build();
// // } catch (IOException e) {
// // e.printStackTrace();
// // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
// // }
// // }

// // @SuppressWarnings("null")
// // @GetMapping("/view/{id}")
// // public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
// // byte[] fileData = dicomService.getFileById(id);
// // if (fileData != null) {
// // return
// ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileData);
// // } else {
// // return ResponseEntity.notFound().build();
// // }
// // }
// // }
