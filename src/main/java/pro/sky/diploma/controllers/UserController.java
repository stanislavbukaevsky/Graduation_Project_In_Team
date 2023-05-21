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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.diploma.dto.NewPasswordDTO;
import pro.sky.diploma.dto.UserDTO;
import pro.sky.diploma.entities.User;
import pro.sky.diploma.mappers.UserMapper;
import pro.sky.diploma.security.UserSecurity;
import pro.sky.diploma.services.UserService;

import java.io.IOException;

import static pro.sky.diploma.constants.FrontServerUserConstant.*;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы со всеми зарегистрированными пользователями на платформе
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(REQUEST_MAPPING_USER_CONTROLLER)
@CrossOrigin(value = FRONT_ADDRESS)
@Tag(name = "Работа со всеми зарегистрированными пользователями на платформе", description = "Позволяет управлять методами по работе со всеми зарегистрированными пользователями на платформе")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Этот метод позволяет изменить пароль зарегистрированному пользователю на платформе
     *
     * @param newPassword новый пароль
     * @return Возвращает измененный пароль
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = NewPasswordDTO.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пороль"),
            @ApiResponse(responseCode = "403", description = "Запрещенный пароль"),
            @ApiResponse(responseCode = "404", description = "Не найденный пароль")
    })
    @Operation(summary = "Метод для изменения пароля пользователя зарегистрированного на платформе", description = "Позволяет изменить пароль пользователя зарегистрированного на платформе")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping(POST_MAPPING_SET_PASSWORD_CONTROLLER)
    public ResponseEntity<NewPasswordDTO> setPassword(@RequestBody NewPasswordDTO newPassword, Authentication authentication) {
        logger.info(SET_PASSWORD_MESSAGE_LOGGER_CONTROLLER, newPassword);
        NewPasswordDTO resultPassword = userService.setPassword(authentication.getName(), newPassword.getCurrentPassword(), newPassword.getNewPassword());
        return ResponseEntity.ok(resultPassword);
    }

    /**
     * Этот метод позволяет получить информацию об авторизированном пользователе на платформе
     *
     * @param userDTO пользователь
     * @return Возвращает информацию о пользователе
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенный пользователь"),
            @ApiResponse(responseCode = "404", description = "Не найденный пользователь")
    })
    @Operation(summary = "Метод для просмотра информации об авторизированном пользователе на платформе", description = "Позволяет получить информацию об авторизированном пользователе на платформе")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(GET_MAPPING_GET_USER_CONTROLLER)
    public ResponseEntity<UserDTO> getUser(@RequestBody UserDTO userDTO) {
        logger.info(GET_USER_MESSAGE_LOGGER_CONTROLLER, userDTO);
        User user = userService.getUser(userDTO.getEmail());
        return ResponseEntity.ok(userMapper.importEntityToDTO(user));
    }

    /**
     * Этот метод позволяет изменить информацию об авторизированном пользователе на платформе
     *
     * @param userDTO пользователь
     * @return Возвращает измененную информацию о пользователе
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "204", description = "Нет контента"),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенный пользователь"),
            @ApiResponse(responseCode = "404", description = "Не найденный пользователь")
    })
    @Operation(summary = "Метод для изменения информации об авторизированном пользователе на платформе", description = "Позволяет изменить информацию об авторизированном пользователе на платформе")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping(PATCH_MAPPING_UPDATE_USER_CONTROLLER)
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        logger.info(UPDATE_USER_MESSAGE_LOGGER_CONTROLLER, userDTO);
        return ResponseEntity.ok(userService.updateUser(userDTO));
    }

    /**
     * Этот метод позволяет изменить аватарку у авторизированного пользователя на платформе
     *
     * @param multipartFile аватарка
     * @param userSecurity  класс, с авторизированными пользователями
     * @return Возвращает пользователя с измененной аватаркой
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Не найденна аватарка")
    })
    @Operation(summary = "Метод для изменения аватарки у авторизированного пользователя на платформе", description = "Позволяет изменить аватарку у авторизированного пользователя на платформе")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping(path = PATCH_MAPPING_UPDATE_USER_IMAGE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUserImage(@RequestPart(name = "image") MultipartFile multipartFile, UserSecurity userSecurity) throws IOException {
        logger.info(UPDATE_USER_IMAGE_MESSAGE_LOGGER_CONTROLLER, multipartFile);
        return ResponseEntity.ok(userService.updateUserImage(multipartFile, userSecurity));
    }

    /**
     * Этот метод позволяет получить аватарку у авторизированного пользователя на платформе
     *
     * @param id идентификатор пользователя
     * @return Возвращает изображение пользователю
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(implementation = byte[].class))),
            @ApiResponse(responseCode = "404", description = "Не найденна аватарка")
    })
    @Operation(summary = "Метод для получения аватарки у авторизированного пользователя на платформе", description = "Позволяет получить аватарку у авторизированного пользователя на платформе")
    @GetMapping(value = GET_MAPPING_GET_USER_IMAGE_CONTROLLER, produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getUserImage(@PathVariable(required = true) Long id) {
        return ResponseEntity.ok(userService.getUserImage(id));
    }
}
