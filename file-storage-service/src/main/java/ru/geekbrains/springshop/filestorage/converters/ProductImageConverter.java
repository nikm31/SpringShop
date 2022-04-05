package ru.geekbrains.springshop.filestorage.converters;

import org.springframework.stereotype.Service;
import ru.geekbrains.springshop.api.dto.ProductImageDto;
import ru.geekbrains.springshop.filestorage.entity.ProductImage;

@Service
public class ProductImageConverter {
	public ProductImageDto imageEntityToDto (ProductImage productImage){
		ProductImageDto imageDto = new ProductImageDto();
		imageDto.setImagePath(productImage.getImagePath());
		return imageDto;
	}
}
