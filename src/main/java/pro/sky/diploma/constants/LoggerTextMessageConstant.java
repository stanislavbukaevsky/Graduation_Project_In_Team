package pro.sky.diploma.constants;

/**
 * Этот класс содержит текстовые константные переменные для всех логов в приложении
 */
public class LoggerTextMessageConstant {
    // Логи для методов в контроллерах
    public static final String LOGIN_MESSAGE_LOGGER_CONTROLLER = "Вызван метод авторизации пользователя на платформе в контроллере. Авторизированный пользователь: {}";
    public static final String REGISTER_MESSAGE_LOGGER_CONTROLLER = "Вызван метод регистрации нового пользователя на платформе в контроллере. Зарегистрированный пользователь: {}";
    public static final String SET_PASSWORD_MESSAGE_LOGGER_CONTROLLER = "Вызван метод изменения пароля зарегистрированного пользователя на платформе в контроллере. Новый пароль: {}";
    public static final String GET_USER_MESSAGE_LOGGER_CONTROLLER = "Вызван метод получения информации об авторизованном пользователе на платформе в контроллере. Пользователь: {}";
    public static final String UPDATE_USER_MESSAGE_LOGGER_CONTROLLER = "Вызван метод изменения информации об авторизованном пользователе на платформе в контроллере. Пользователь: {}";
    public static final String UPDATE_USER_IMAGE_MESSAGE_LOGGER_CONTROLLER = "Вызван метод изменения аватврки у авторизованного пользователя на платформе в контроллере. Аватарка: {}";
    public static final String GET_ALL_ADS_MESSAGE_LOGGER_CONTROLLER = "Вызван метод получения всех объявлений, размещенных на платформе в контроллере";
    public static final String ADD_ADS_MESSAGE_LOGGER_CONTROLLER = "Вызван метод добавления объявления на платформу в контроллере. Добавляемое объявление: {}. Фотография: {}";
    public static final String GET_ADS_MESSAGE_LOGGER_CONTROLLER = "Вызван метод получения информации об объявлении, размещенного на платформе в контроллере. Идентификатор искомого объявления: {}";
    public static final String REMOVE_ADS_MESSAGE_LOGGER_CONTROLLER = "Вызван метод удаления объявления, размещенного на платформе в контроллере. Идентификатор удаляемого объявления: {}";
    public static final String UPDATE_ADS_MESSAGE_LOGGER_CONTROLLER = "Вызван метод изменения информации об объявлении, размещенного на платформе в контроллере. Идентификатор изменяемого объявления: {}. Объявление: {}";
    public static final String GET_ADS_ME_MESSAGE_LOGGER_CONTROLLER = "Вызван метод получения объявления авторизированного пользователя, размещенного на платформе в контроллере";
    public static final String UPDATE_IMAGE_MESSAGE_LOGGER_CONTROLLER = "Вызван метод изменения изображения для объявления, размещенного на платформе в контроллере. Идентификатор изменяемого изображения: {}. Изображение: {}";
    public static final String GET_COMMENT_BY_ID_MESSAGE_LOGGER_CONTROLLER = "Вызван метод получения комментариев под объявлением, размещенный на платформе в контроллере. Идентификатор комментария: {}";
    public static final String ADD_COMMENT_MESSAGE_LOGGER_CONTROLLER = "Вызван метод добавления комментариев под объявлением, размещенного на платформе в контроллере. Комментарий: {}. Идентификатор комментария: {}";
    public static final String DELETE_COMMENT_MESSAGE_LOGGER_CONTROLLER = "Вызван метод удаления комментариев под объявлением, размещенного на платформе в контроллере. Идентификатор объявления: {}. Идентификатор комментария: {}";
    public static final String UPDATE_COMMENT_MESSAGE_LOGGER_CONTROLLER = "Вызван метод изменения комментария под объявлением, размещенного на платформе в контроллере. Идентификатор объявления: {}. Комментарий: {}. Идентификатор комментария: {}";
    // Логи для методов в сервисах
    public static final String LOGIN_MESSAGE_LOGGER_SERVICE = "Вызван метод авторизации пользователя на платформе в сервисе. Имя пользователя: {}. Пароль: {}";
    public static final String REGISTER_MESSAGE_LOGGER_SERVICE = "Вызван метод регистрации нового пользователя на платформе в сервисе. Зарегистрированный пользователь: {}. Роль пользователя: {}";
}
