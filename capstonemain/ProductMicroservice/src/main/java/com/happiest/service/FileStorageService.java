package com.happiest.service;

import com.happiest.constants.FileStorageConstants;
import com.happiest.exception.FileStorageException;
import com.happiest.exception.MyFileNotFoundException;
import com.happiest.utility.UploadFileResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private static final Logger logger = LogManager.getLogger(FileStorageService.class);

    private final Path fileStorageLocation;


    @Autowired
    public FileStorageService(@Value("${file.upload-dir:" + FileStorageConstants.UPLOAD_DIR + "}") String uploadDir) throws FileStorageException {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            logger.info("File storage directory created successfully at: {}", this.fileStorageLocation);
        } catch (Exception ex) {
            logger.error(FileStorageConstants.FILE_STORAGE_INIT_ERROR, ex);
            throw new FileStorageException(FileStorageConstants.FILE_STORAGE_INIT_ERROR + ": " + ex.getMessage());
        }
    }

    public UploadFileResponse storeFile(MultipartFile file) throws FileStorageException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        logger.info("Storing file: {}", fileName);

        try {
            if (fileName.contains("..")) {
                logger.warn(FileStorageConstants.INVALID_FILE_NAME_SEQUENCE + fileName);
                throw new FileStorageException(FileStorageConstants.INVALID_FILE_NAME_SEQUENCE + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.info("File stored successfully at: {}", targetLocation);

            String fileDownloadUri = FileStorageConstants.FILE_DOWNLOAD_BASE_URI + fileName;
            logger.debug("File download URI generated: {}", fileDownloadUri);

            return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        } catch (IOException ex) {
            logger.error(FileStorageConstants.STORE_FILE_ERROR + fileName, ex);
            throw new FileStorageException(FileStorageConstants.STORE_FILE_ERROR + fileName + ": " + ex.getMessage());
        }
    }

    public Resource loadFileAsResource(String fileName) throws MyFileNotFoundException {
        logger.info("Loading file as resource: {}", fileName);
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                logger.info("File found and loaded as resource: {}", fileName);
                return resource;
            } else {
                logger.warn(FileStorageConstants.FILE_NOT_FOUND + fileName);
                throw new MyFileNotFoundException(FileStorageConstants.FILE_NOT_FOUND + fileName);
            }
        } catch (MalformedURLException ex) {
            logger.error(FileStorageConstants.MALFORMED_URL_ERROR + fileName, ex);
            throw new MyFileNotFoundException(FileStorageConstants.FILE_NOT_FOUND + fileName + ": " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("Unexpected error occurred while loading file as resource: {}", fileName, ex);
            throw new MyFileNotFoundException("Unexpected error occurred while loading file: " + fileName + ": " + ex.getMessage());
        }
    }
}
