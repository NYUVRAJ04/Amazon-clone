package com.happiest.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {


    @Autowired
    private MessageSource messageSource;
    @ExceptionHandler(FileStorageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleFileStorageException(FileStorageException ex, Locale locale) {
        return messageSource.getMessage("error.file.storage", new Object[]{ex.getMessage()}, locale);
    }

    @ExceptionHandler(MyFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleMyFileNotFoundException(MyFileNotFoundException ex, Locale locale) {
        return messageSource.getMessage("file.not.found", new Object[]{ex.getMessage()}, locale);
    }

    @ExceptionHandler(ServiceCommunicationException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public String handleServiceCommunicationException(ServiceCommunicationException ex) {
        return "Service communication error: " + ex.getMessage();
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public String handleInvalidFileFormatException(InvalidFileFormatException ex) {
        return "Invalid file format: " + ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleGenericException(Exception ex) {
        return "An unexpected error occurred: " + ex.getMessage();
    }
}
