package pro.sky.diploma.constants;

/**
 * Этот класс содержит текстовые константные переменные для всех путей к веб-сайту
 */
public class FrontServerUserConstant {
    // Локальный адрес к фронтенду
    public static final String FRONT_ADDRESS = "http://localhost:3000";

    // ADS CONTROLLER
    public static final String REQUEST_MAPPING_ADS_CONTROLLER = "/ads";
    public static final String GET_MAPPING_GET_ADS_CONTROLLER = "/{id}";
    public static final String DELETE_MAPPING_REMOVE_ADS_CONTROLLER = "/{id}";
    public static final String PATCH_MAPPING_UPDATE_ADS_CONTROLLER = "/{id}";
    public static final String GET_MAPPING_GET_ME_ADS_CONTROLLER = "/me";
    public static final String PATCH_MAPPING_UPDATE_IMAGE_CONTROLLER = "/{id}/image";

    // AUTH_CONTROLLER
    public static final String POST_MAPPING_LOGIN_AUTH_CONTROLLER = "/login";
    public static final String POST_MAPPING_REGISTER_AUTH_CONTROLLER = "/register";

    // COMMENT_CONTROLLER
    public static final String REQUEST_MAPPING_COMMENT_CONTROLLER = "/ads";
    public static final String GET_MAPPING_GET_COMMENT_BY_ID_COMMENT_CONTROLLER = "/{id}/comments";
    public static final String POST_MAPPING_ADD_COMMENT_CONTROLLER = "/{id}/comments";
    public static final String DELETE_MAPPING_DELETE_COMMENT_CONTROLLER = "{adId}/comments/{commentId}";
    public static final String PATCH_MAPPING_UPDATE_COMMENT_CONTROLLER = "{adId}/comments/{commentId}";

    // USER CONTROLLER
    public static final String REQUEST_MAPPING_USER_CONTROLLER = "/users";
    public static final String POST_MAPPING_SET_PASSWORD_CONTROLLER = "/set_password";
    public static final String GET_MAPPING_GET_USER_CONTROLLER = "/me";
    public static final String PATCH_MAPPING_UPDATE_USER_CONTROLLER = "/me";
    public static final String PATCH_MAPPING_UPDATE_USER_IMAGE = "/me/image";
}