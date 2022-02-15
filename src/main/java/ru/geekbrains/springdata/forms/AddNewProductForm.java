package ru.geekbrains.springdata.forms;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class AddNewProductForm {
    private String title;
    private int price;
    private String category;
    private MultipartFile file;
}
