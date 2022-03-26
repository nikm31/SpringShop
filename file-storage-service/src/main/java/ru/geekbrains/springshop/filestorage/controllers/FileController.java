package ru.geekbrains.springshop.filestorage.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.springshop.api.exeptions.ResourceNotFoundException;
import ru.geekbrains.springshop.filestorage.services.FileStoreService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FileController {

	private final FileStoreService fileStoreService;

	@PostMapping("/storefile")
	public ResponseEntity<String> uploadFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("subtype") Long subType,
			@RequestParam("user") String userName
	) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("File is empty");
		}

		String fileHash;

		try {
			fileHash = fileStoreService.storeFile(file.getBytes(), file.getOriginalFilename(), subType, userName);
		} catch (IOException | NoSuchAlgorithmException e) {
			return ResponseEntity.badRequest().body("Cant write file " + e);
		}

		return ResponseEntity.ok(fileHash);
	}

	@GetMapping("/deletefile")
	public ResponseEntity<?> deleteFile(@RequestParam String fileName, @RequestParam("user") String userName) {

		try {
			fileStoreService.removeFileFromUser(fileName, userName);
		} catch (IOException e) {
			return new ResponseEntity<>(new ResourceNotFoundException("Не удалось удалить файл: " + fileName + " ошибка: " + e),
					HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok("Файл удален: " + fileName);
	}

	@GetMapping("/getfile")
	public ResponseEntity<?> downloadFile(@RequestParam("hash") UUID hash) {
		byte[] array;

		try {
			array = fileStoreService.getFile(hash);
		} catch (IOException e) {
			return new ResponseEntity<>(new ResourceNotFoundException("Невозможно прочитать файл с hash: " + hash + " ошибка: " + e),
					HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new ByteArrayResource(array));
	}

	@GetMapping("/getfiles")
	public ResponseEntity<?> downloadFiles(@RequestParam("subtype") Long subtype) {
		return ResponseEntity.ok(fileStoreService.getMetaFiles(subtype));
	}
}
