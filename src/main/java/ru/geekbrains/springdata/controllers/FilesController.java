package ru.geekbrains.springdata.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.springdata.services.FileStoreService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FilesController {

    private final FileStoreService fileStoreService;

    @PostMapping("/storefile")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("subtype") Long subType,
            @RequestParam("user") String userName
    ) throws IOException, NoSuchAlgorithmException, InterruptedException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        String fileHash = fileStoreService.storeFile(file.getBytes(), file.getOriginalFilename(), subType, userName);
        return ResponseEntity.ok(fileHash); // возвращаем имя
    }

    @GetMapping("/deletefile")
    public void deleteFile(@RequestParam String fileName, @RequestParam("user") String userName) {
        try {
            fileStoreService.removeFileFromUser(fileName, userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/getfile") // скачиваем на вход хеш
    public ResponseEntity<Resource> downloadFile(@RequestParam("hash") UUID hash) throws IOException {
        byte[] array = fileStoreService.getFile(hash);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(array));
    }

    @GetMapping("/getfiles")
    public ResponseEntity<?> downloadFile(@RequestParam("subtype") Long subtype) throws IOException {
        return ResponseEntity.ok(fileStoreService.getMetaFiles(subtype));
    }
}
