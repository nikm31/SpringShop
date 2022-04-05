package ru.geekbrains.springshop.filestorage.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.springshop.api.dto.ProductImageDto;
import ru.geekbrains.springshop.filestorage.converters.ProductImageConverter;
import ru.geekbrains.springshop.filestorage.repositories.ProductImagesRepository;
import ru.geekbrains.springshop.filestorage.utils.FileSystemProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final FileSystemProvider systemProvider;
    private final ProductImagesRepository productImagesRepository;
    private final ProductImageConverter productImageConverter;

    public List<ProductImageDto> getAllProductImagesById(Long productId) {
        return productImagesRepository.findByProductId(productId)
                .stream().map(productImageConverter::imageEntityToDto)
                .collect(Collectors.toList());
    }

    public byte[] getProductImage(String imagePath) throws IOException {
        File img = systemProvider.getProductImage(imagePath);
        return Files.readAllBytes(img.toPath());
    }

}
