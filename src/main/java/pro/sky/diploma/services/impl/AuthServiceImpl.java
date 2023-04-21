package pro.sky.diploma.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import pro.sky.diploma.dto.RegisterReq;
import pro.sky.diploma.dto.Role;
import pro.sky.diploma.services.AuthService;

import static pro.sky.diploma.constants.LoggerTextMessageConstant.LOGIN_MESSAGE_LOGGER_SERVICE;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.REGISTER_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс для авторизации и регистрации пользователей на платформе.
 * Реализует интерфейс {@link AuthService}
 */
@Service
public class AuthServiceImpl implements AuthService {
  private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
  private final UserDetailsManager manager;
  private final PasswordEncoder encoder;

  public AuthServiceImpl(UserDetailsManager manager, PasswordEncoder passwordEncoder) {
    this.manager = manager;
    this.encoder = passwordEncoder;
  }

  /**
   * Этот метод реализует авторизацию пользователей на платформе
   *
   * @param userName имя пользователя
   * @param password пароль пользователя
   * @return Возвращает авторизированного пользователя, если такой существует
   */
  @Override
  public boolean login(String userName, String password) {
    logger.info(LOGIN_MESSAGE_LOGGER_SERVICE, userName, password);
    if (!manager.userExists(userName)) {
      return false;
    }
    UserDetails userDetails = manager.loadUserByUsername(userName);
    return encoder.matches(password, userDetails.getPassword());
  }

  /**
   * Этот метод реализует регистрацию новых пользователей на платформе
   *
   * @param registerReq класс-DTO для регистрации пользователя на платформе
   * @param role        роль пользователя
   * @return Возвращает зарегистрированного пользователя
   */
  @Override
  public boolean register(RegisterReq registerReq, Role role) {
    logger.info(REGISTER_MESSAGE_LOGGER_SERVICE, registerReq, role);
    if (manager.userExists(registerReq.getUsername())) {
      return false;
    }
    manager.createUser(
            User.builder()
                    .passwordEncoder(this.encoder::encode)
                    .password(registerReq.getPassword())
                    .username(registerReq.getUsername())
                    .roles(role.name())
                    .build());
    return true;
  }
}
