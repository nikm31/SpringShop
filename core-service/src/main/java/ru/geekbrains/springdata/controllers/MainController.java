package ru.geekbrains.springdata.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.springshop.api.dto.PageDto;

@RestController
@RequestMapping("/api/v1/")
@Tag(name = "Главная страница", description = "На данный момент ничего не отдает и не выполняет контроллер")
public class MainController {

    @Operation(
            summary = "Получение главной страницы",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/")
    public void showHomePage() {
    }
}
