package com.happiest.test;

import com.happiest.constants.FileStorageConstants;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FileStorageConstantsTest {

    @Test
    void testUploadDirConstant() {
        assertEquals("uploads", FileStorageConstants.UPLOAD_DIR, "UPLOAD_DIR constant should be 'uploads'");
    }

    @Test
    void testFileStorageInitErrorConstant() {
        assertEquals(
                "Could not create the directory where the uploaded files will be stored.",
                FileStorageConstants.FILE_STORAGE_INIT_ERROR,
                "FILE_STORAGE_INIT_ERROR constant should match expected error message"
        );
    }

    @Test
    void testInvalidFileNameSequenceConstant() {
        assertEquals(
                "Filename contains invalid path sequence ",
                FileStorageConstants.INVALID_FILE_NAME_SEQUENCE,
                "INVALID_FILE_NAME_SEQUENCE constant should match expected error message"
        );
    }

    @Test
    void testFileNotFoundConstant() {
        assertEquals(
                "File not found ",
                FileStorageConstants.FILE_NOT_FOUND,
                "FILE_NOT_FOUND constant should match expected error message"
        );
    }

    @Test
    void testMalformedUrlErrorConstant() {
        assertEquals(
                "File not found due to malformed URL: ",
                FileStorageConstants.MALFORMED_URL_ERROR,
                "MALFORMED_URL_ERROR constant should match expected error message"
        );
    }

    @Test
    void testStoreFileErrorConstant() {
        assertEquals(
                "Could not store file ",
                FileStorageConstants.STORE_FILE_ERROR,
                "STORE_FILE_ERROR constant should match expected error message"
        );
    }

    @Test
    void testFileDownloadBaseUriConstant() {
        assertEquals(
                "http://localhost:9008/uploads/",
                FileStorageConstants.FILE_DOWNLOAD_BASE_URI,
                "FILE_DOWNLOAD_BASE_URI constant should match expected base URI"
        );
    }
}
