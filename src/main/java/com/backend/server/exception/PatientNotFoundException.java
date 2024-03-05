package com.backend.server.exception;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(Long id){
        super("Could not found the Patient with id "+ id);
    }
}


