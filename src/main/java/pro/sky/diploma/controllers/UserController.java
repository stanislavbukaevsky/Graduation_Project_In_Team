package pro.sky.diploma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.diploma.dto.NewPassword;
import pro.sky.diploma.dto.User;

import static pro.sky.diploma.constants.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы со всеми зарегистрированными пользователями на платформе
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Работа со всеми зарегистрированными пользователями на платформе", description = "Позволяет управлять методами по работе со всеми зарегистрированными пользователями на платформе")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Этот метод позволяет изменить пароль зарегистрированному пользователю на платформе
     *
     * @param newPassword новый пароль
     * @return Возвращает измененный пароль
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = NewPassword.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пороль"),
            @ApiResponse(responseCode = "403", description = "Запрещенный пароль"),
            @ApiResponse(responseCode = "404", description = "Не найденный пароль")
    })
    @Operation(summary = "Метод для изменения пароля пользователя зарегистрированного на платформе", description = "Позволяет изменить пароль пользователя зарегистрированного на платформе")
    @PostMapping("/set_password")
    public NewPassword setPassword(@RequestBody NewPassword newPassword) {
        logger.info(SET_PASSWORD_MESSAGE_LOGGER_CONTROLLER, newPassword);
        return new NewPassword();
    }

    /**
     * Этот метод позволяет получить информацию об авторизированном пользователе на платформе
     *
     * @param user пользователь
     * @return Возвращает информацию о пользователе
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенный пользователь"),
            @ApiResponse(responseCode = "404", description = "Не найденный пользователь")
    })
    @Operation(summary = "Метод для просмотра информации об авторизированном пользователе на платформе", description = "Позволяет получить информацию об авторизированном пользователе на платформе")
    @GetMapping("/me")
    public User getUser(@RequestBody User user) {
        logger.info(GET_USER_MESSAGE_LOGGER_CONTROLLER, user);
        return new User();
    }

    /**
     * Этот метод позволяет изменить информацию об авторизированном пользователе на платформе
     *
     * @param user пользователь
     * @return Возвращает измененную информацию о пользователе
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "204", description = "Нет контента"),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенный пользователь"),
            @ApiResponse(responseCode = "404", description = "Не найденный пользователь")
    })
    @Operation(summary = "Метод для изменения информации об авторизированном пользователе на платформе", description = "Позволяет изменить информацию об авторизированном пользователе на платформе")
    @PatchMapping("/me")
    public User updateUser(@RequestBody User user) {
        logger.info(UPDATE_USER_MESSAGE_LOGGER_CONTROLLER, user);
        return new User();
    }

    /**
     * Этот метод позволяет изменить аватарку у авторизированного пользователя на платформе
     *
     * @param image ссылка на новую аватарку
     * @return Возвращает пользователя с измененной аватаркой
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Не найденна аватарка")
    })
    @Operation(summary = "Метод для изменения аватарки у авторизированного пользователя на платформе", description = "Позволяет изменить аватарку у авторизированного пользователя на платформе")
    @PatchMapping("/me/image")
    public User updateUserImage(@RequestBody String image) {
        logger.info(UPDATE_USER_IMAGE_MESSAGE_LOGGER_CONTROLLER, image);
        return new User();
    }
}
