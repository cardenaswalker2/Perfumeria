package com.profumi.profumi.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {

    private final String uploadDir = "src/main/resources/static/uploads/perfumes";

    public FileService() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            System.err.println("Error al crear directorio de carga: " + e.getMessage());
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) return null;

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path targetPath = Paths.get(uploadDir).resolve(fileName);
        
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return "/uploads/perfumes/" + fileName;
    }
}
