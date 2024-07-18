package com.example.backend.service.objectstorage;

import org.springframework.web.multipart.MultipartFile;

public interface ObjectStorageService {
    String uploadFile(String bucketName, String directoryPath, MultipartFile file);
}
