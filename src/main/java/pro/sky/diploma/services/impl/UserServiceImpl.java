package pro.sky.diploma.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.diploma.dto.NewPasswordDTO;
import pro.sky.diploma.dto.UserDTO;
import pro.sky.diploma.entities.User;
import pro.sky.diploma.exceptions.ImageNotFoundException;
import pro.sky.diploma.exceptions.PasswordNotFoundException;
import pro.sky.diploma.exceptions.UserNameNotFoundException;
import pro.sky.diploma.exceptions.UserNotFoundException;
import pro.sky.diploma.mappers.UserMapper;
import pro.sky.diploma.repositories.UserRepository;
import pro.sky.diploma.security.UserSecurity;
import pro.sky.diploma.services.ImageService;
import pro.sky.diploma.services.UserService;

import java.io.IOException;

import static pro.sky.diploma.constants.ExceptionTextMessageConstant.*;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для всех пользователей зарегистрированных на платформе.
 * Реализует интерфейс {@link UserService}
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserSecurity userSecurity;
    private final ImageService imageService;

    /**
     * Реализация метода для изменения пароля зарегистрированного пользователя на платформе.
     * Этот метод может выбросить исключение {@link UserNotFoundException}, если пользователь не найден.
     * Также, метод может выбросить исключение {@link PasswordNotFoundException}, если неверно введен пароль пользователя
     *
     * @param currentPassword текущий пароль
     * @param newPassword     новый пароль
     * @return Возвращает сконвертированную DTO нового пароля пользователя
     */
    @Override
    public NewPasswordDTO setPassword(String currentPassword, String newPassword) {
        logger.info(SET_PASSWORD_USER_MESSAGE_LOGGER_SERVICE, currentPassword, newPassword);
        User user = userRepository.findUserByEmail(userSecurity.getUsername()).orElseThrow(() ->
                new UserNameNotFoundException(USER_NAME_NOT_FOUND_EXCEPTION));

        if (!(passwordEncoder.matches(currentPassword, user.getPassword()))) {
            throw new PasswordNotFoundException(PASSWORD_NOT_FOUND_EXCEPTION);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        User result = userRepository.save(user);
        return userMapper.importVariablesToDTO(result);
    }

    /**
     * Реализация метода для просмотра информации об авторизированном пользователе на платформе. <br>
     * Этот метод может выбросить исключение {@link UserNotFoundException}, если пользователь не найден
     *
     * @return Возвращает найденного по электронной почте пользователя
     */
    @Override
    public UserDTO getUser() {
        logger.info(GET_USER_MESSAGE_LOGGER_SERVICE);
        User user = userRepository.findUserByEmail(userSecurity.getUsername()).orElseThrow(() ->
                new UserNotFoundException(USER_NOT_FOUND_EXCEPTION));
        return userMapper.importEntityToDTO(user);
    }

    /**
     * Реализация метода для изменения информации об авторизированном пользователе на платформе. <br>
     * Этот метод может выбросить исключение {@link UserNotFoundException}, если пользователь не найден
     *
     * @param userDTO DTO зарегистрированного пользователя
     * @return Возвращает DTO измененного профиля пользователя
     */
    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        logger.info(UPDATE_USER_MESSAGE_LOGGER_SERVICE, userDTO);
        Long id = Long.valueOf(userDTO.getId());
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(USER_NOT_FOUND_EXCEPTION_2);
        }

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        User result = userRepository.save(user);
        return userMapper.importEntityToDTO(result);
    }

    /**
     * Реализация метода для изменения аватарки у авторизированного пользователя на платформе
     *
     * @param imageFile файл изображения
     * @return Возвращает DTO измененного профиля пользователя
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public UserDTO updateUserImage(MultipartFile imageFile) throws IOException {
        logger.info(UPDATE_USER_IMAGE_MESSAGE_LOGGER_SERVICE);
        User user = userRepository.findUserByEmail(userSecurity.getUsername()).orElseThrow(() ->
                new UserNotFoundException(USER_NOT_FOUND_EXCEPTION));

        if (user.getImage() == null) {
            imageService.addImageUser(user.getEmail(), imageFile);
        } else if (user.getImage() != null) {
            imageService.updateImageUser(user.getEmail(), imageFile);
        }
        return userMapper.importEntityToDTO(user);
    }

    /**
     * Реализация метода для получения аватарки у авторизированного пользователя на платформе по его идентификатору
     *
     * @param id идентификатор пользователя
     * @return Возвращает массив байт искомой аватарки
     */
    @Override
    public byte[] getUserImage(Long id) {
        logger.info(GET_IMAGE_USER_MESSAGE_LOGGER_SERVICE, id);
        byte[] image = imageService.getUserImage(id);
        if (image == null) {
            throw new ImageNotFoundException(IMAGE_NOT_FOUND_EXCEPTION);
        }
        return image;
    }
}
