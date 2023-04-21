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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.diploma.dto.LoginReq;
import pro.sky.diploma.dto.RegisterReq;
import pro.sky.diploma.dto.Role;
import pro.sky.diploma.services.AuthService;

import static pro.sky.diploma.constants.LoggerTextMessageConstant.LOGIN_MESSAGE_LOGGER_CONTROLLER;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.REGISTER_MESSAGE_LOGGER_CONTROLLER;
import static pro.sky.diploma.dto.Role.USER;

/**
 * Класс-контроллер для работы с регистрацией и авторизацией пользователей на платформе
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Работа с регистрацией и авторизацией", description = "Позволяет управлять методами по работе с регистрацией и авторизацией пользователей на платформе")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    /**
     * Этот метод реализует авторизацию пользователей на платформе
     *
     * @param req класс-DTO для авторизации пользователя на платформе
     * @return Возвращает авторизированного пользователя, если такой существует
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginReq.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенный пользователь"),
            @ApiResponse(responseCode = "404", description = "Не найденный пользователь")
    })
    @Operation(summary = "Метод авторизации пользователей на платформе", description = "Позволяет авторизироваться пользователю на платформе")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        logger.info(LOGIN_MESSAGE_LOGGER_CONTROLLER, req);
        if (authService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Этот метод реализует регистрацию новых пользователей на платформе
     *
     * @param req класс-DTO для регистрации пользователя на платформе
     * @return Возвращает зарегистрированного пользователя
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь добавлен", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RegisterReq.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенный пользователь"),
            @ApiResponse(responseCode = "404", description = "Не найденный пользователь")
    })
    @Operation(summary = "Метод регистрации пользователей на платформе", description = "Позволяет зарегистрироваться новому пользователю на платформе")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq req) {
        logger.info(REGISTER_MESSAGE_LOGGER_CONTROLLER, req);
        Role role = req.getRole() == null ? USER : req.getRole();
        if (authService.register(req, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
