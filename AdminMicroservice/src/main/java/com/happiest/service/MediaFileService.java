    package com.happiest.service;

    import com.happiest.model.MediaFile;
    import com.happiest.repository.MediaFileRepository;
    import com.happiest.utility.UploadFileResponse;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.Optional;

    @Service
    public class MediaFileService {

        @Autowired
        private MediaFileRepository repository;

        @Autowired
        private FileStorageService fileStorageService; // Assuming you have a FileStorageService

        public List<MediaFile> findAll() {
            return repository.findAll();
        }

        public MediaFile save(MediaFile file) {
            return repository.save(file);
        }

        public void deleteById(Long id) {
            repository.deleteById(id);
        }

        public List<MediaFile> findByAwarenessContentTitle(String title) {
            return repository.findByAwarenessContent_Title(title);
        }

        public Optional<MediaFile> findById(Long id) {
            return repository.findById(id);
        }

        public List<MediaFile> findByAwarenessContentId(Long awarenessContentId) {
            return repository.findByAwarenessContentId(awarenessContentId);
        }

        public MediaFile saveMediaFile(MediaFile mediaFile, MultipartFile file) throws IOException {
            if (file != null && !file.isEmpty()) {
                // Store the file using FileStorageService
                UploadFileResponse uploadFileResponse = fileStorageService.storeFile(file);
                String fileUrl = uploadFileResponse.getFileDownloadUri();

                // Set file details in the MediaFile object
                mediaFile.setFileUrl(fileUrl);
                mediaFile.setFileType(file.getContentType());
                mediaFile.setFileSize(file.getSize());
            }
            mediaFile.setCreatedAt(LocalDateTime.now()); // Setting the creation date
            return repository.save(mediaFile);
        }
    }
