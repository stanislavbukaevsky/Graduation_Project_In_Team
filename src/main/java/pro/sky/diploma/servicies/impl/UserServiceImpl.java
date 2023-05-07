package pro.sky.diploma.servicies.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.diploma.dto.UserDTO;
import pro.sky.diploma.entities.User;
import pro.sky.diploma.exception.UserNotFoundException;
import pro.sky.diploma.mappers.UserMapper;
import pro.sky.diploma.repositories.UserRepository;
import pro.sky.diploma.servicies.UserService;

import static pro.sky.diploma.constants.ExceptionTextMessageConstant.USER_NOT_FOUND_EXCEPTION;
import static pro.sky.diploma.constants.ExceptionTextMessageConstant.USER_NOT_FOUND_EXCEPTION_2;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.GET_USER_MESSAGE_LOGGER_SERVICE;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.UPDATE_USER_MESSAGE_LOGGER_SERVICE;

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

    /**
     * Реализация метода для просмотра информации об авторизированном пользователе на платформе.
     * Этот метод может выбросить исключение {@link UserNotFoundException}, если пользователь не найден
     *
     * @param username имя пользователя (логин)
     * @return Возвращает найденного по электронной почте пользователя
     */
    @Override
    public User getUser(String username) {
        logger.info(GET_USER_MESSAGE_LOGGER_SERVICE, username);
        User user = userRepository.findUserByEmail(username).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(USER_NOT_FOUND_EXCEPTION);
        }
        return user;
    }

    /**
     * Реализация метода для изменения информации об авторизированном пользователе на платформе.
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
}