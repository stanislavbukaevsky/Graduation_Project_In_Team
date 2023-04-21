package pro.sky.diploma.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pro.sky.diploma.dto.RegisterReqDTO;
import pro.sky.diploma.dto.Role;
import pro.sky.diploma.entities.User;
import pro.sky.diploma.exceptions.PasswordNotFoundException;
import pro.sky.diploma.exceptions.UserNameAlreadyExistsException;
import pro.sky.diploma.repositories.UserRepository;
import pro.sky.diploma.security.CustomUserDetailsService;
import pro.sky.diploma.services.AuthService;

import java.time.LocalDateTime;

import static pro.sky.diploma.constants.ExceptionTextMessageConstant.*;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.LOGIN_MESSAGE_LOGGER_SERVICE;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.REGISTER_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс с бизнес-логикой для авторизации и регистрации пользователей на платформе.
 * Реализует интерфейс {@link AuthService}
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Этот метод реализует авторизацию пользователей на платформе
     *
     * @param userName имя пользователя
     * @param password пароль пользователя
     */
    @Override
    public void login(String userName, String password) {
        logger.info(LOGIN_MESSAGE_LOGGER_SERVICE, userName, password);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        if (!(passwordEncoder.matches(password, userDetails.getPassword()))) {
            throw new PasswordNotFoundException(PASSWORD_NOT_FOUND_EXCEPTION);
        }
    }

    /**
     * Этот метод реализует регистрацию новых пользователей на платформе
     *
     * @param registerReqDTO класс-DTO для регистрации пользователя на платформе
     */
    @Override
    public void register(RegisterReqDTO registerReqDTO) {
        logger.info(REGISTER_MESSAGE_LOGGER_SERVICE, registerReqDTO);
        Boolean ifUser = userRepository.existsUserByEmail(registerReqDTO.getUsername());
        if (ifUser) {
            throw new UserNameAlreadyExistsException(USER_NAME_ALREADY_EXISTS_EXCEPTION_1 + registerReqDTO.getUsername() + USER_NAME_ALREADY_EXISTS_EXCEPTION_2);
        } else {
            User user = new User();
            LocalDateTime dateTime = LocalDateTime.now();
            user.setFirstName(registerReqDTO.getFirstName());
            user.setLastName(registerReqDTO.getLastName());
            user.setPhoneNumber(registerReqDTO.getPhone());
            user.setEmail(registerReqDTO.getUsername());
            user.setPassword(passwordEncoder.encode(registerReqDTO.getPassword()));
            user.setRole(Role.USER);
            user.setActive(true);
            user.setDateTime(dateTime);
            userRepository.save(user);
        }
    }
}