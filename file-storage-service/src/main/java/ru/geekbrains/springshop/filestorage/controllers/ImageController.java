package ru.geekbrains.springshop.filestorage.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.springshop.api.dto.ProductImageDto;
import ru.geekbrains.springshop.api.exeptions.ResourceNotFoundException;
import ru.geekbrains.springshop.filestorage.services.ImageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

	@GetMapping("/getimg")
	public ResponseEntity<?> getImage(@RequestParam("path") String imgPath) {
		byte[] image;

		try {
			image = imageService.getProductImage(imgPath);
		} catch (IOException e) {
			return new ResponseEntity<>(new ResourceNotFoundException("Невозможно прочитать файл: " + imgPath + " ошибка: " + e),
					HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(image);
	}

	// все фото для станицы карточки товара
	@GetMapping("/get_all_img")
	public List<ProductImageDto> getAllProductImages(@RequestParam("id") Long productId) throws IOException {
		return imageService.getAllProductImagesById(productId);
	}
}
