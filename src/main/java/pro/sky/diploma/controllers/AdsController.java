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
import pro.sky.diploma.dto.Ads;
import pro.sky.diploma.dto.CreateAds;
import pro.sky.diploma.dto.FullAds;
import pro.sky.diploma.dto.ResponseWrapperAds;

import static pro.sky.diploma.constants.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы со всеми объявлениями, опубликованными на платформе
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Работа со всеми объявлениями размещенными на платформе", description = "Позволяет управлять методами по работе со всеми объявлениями размещенными на платформе")
public class AdsController {
    private final Logger logger = LoggerFactory.getLogger(AdsController.class);

    /**
     * Этот метод позволяет получить и просмотреть все объявления, опубликованные на платформе
     *
     * @return Возвращает все опубликованные объявления на платформе
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperAds.class)))
    })
    @Operation(summary = "Метод для получения всех объявлений на платформе", description = "Позволяет просмотреть все объявления, размещенные на платформе")
    @GetMapping
    public ResponseWrapperAds getAllAds() {
        logger.info(GET_ALL_ADS_MESSAGE_LOGGER_CONTROLLER);
        return new ResponseWrapperAds();
    }

    /**
     * Этот метод позволяет добавлять новые объявления на платформу
     *
     * @param createAds добавляемое объявление
     * @param image     ссылка на изображение
     * @return Возвращает новое, добавленное объявление на платформу
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Объявление добавлено на платформу", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Ads.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенное объявление"),
            @ApiResponse(responseCode = "404", description = "Не найденное объявление")

    })
    @Operation(summary = "Метод для добавления объявлений на платформу", description = "Позволяет добавить объявление на платформу")
    @PostMapping
    public Ads addAds(CreateAds createAds, String image) {
        logger.info(ADD_ADS_MESSAGE_LOGGER_CONTROLLER, createAds, image);
        return new Ads();
    }

    /**
     * Этот метод позволяет получить дополнительную информацию об объявлении, размещенного на платформе
     *
     * @param id идентификатор объявления
     * @return Возвращает искомое объявление
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FullAds.class))),
            @ApiResponse(responseCode = "404", description = "Такого объвления на платформе нет")
    })
    @Operation(summary = "Метод для получения информации об объявлении, размещенного на платформе", description = "Позволяет получить информацию об объявлении, размещенном на платформе")
    @GetMapping("/{id}")
    public FullAds getAds(@PathVariable(required = true) Integer id) {
        logger.info(GET_ADS_MESSAGE_LOGGER_CONTROLLER, id);
        return new FullAds();
    }

    /**
     * Этот метод позволяет удалить объявление с платформы по его идентификатору
     *
     * @param id идентификатор удаляемого объявления
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Объявление удалено"),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Такого объвления на платформе нет")
    })
    @Operation(summary = "Метод для удаления объявления, размещенного на платформе", description = "Позволяет удалить объявление, размещенное на платформе")
    @DeleteMapping("/{id}")
    public void removeAds(@PathVariable(required = true) Integer id) {
        logger.info(REMOVE_ADS_MESSAGE_LOGGER_CONTROLLER, id);
    }

    /**
     * Этот метод позволяет изменить информацию об объявлении, размещенного на платформе
     *
     * @param id        идентификатор изменяемого объявления
     * @param createAds объявление
     * @return Возвращает измененное объявление на платформе
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Ads.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенное объявление"),
            @ApiResponse(responseCode = "404", description = "Не найденное объявление")
    })
    @Operation(summary = "Метод для изменения информации об объявления, размещенного на платформе", description = "Позволяет изменить информацию об объявлении, размещенном на платформе")
    @PatchMapping("/{id}")
    public Ads updateAds(@PathVariable(required = true) Integer id, @RequestBody CreateAds createAds) {
        logger.info(UPDATE_ADS_MESSAGE_LOGGER_CONTROLLER, id, createAds);
        return new Ads();
    }

    /**
     * Этот метод позволяет получить объявление авторизированного пользователя, размещенного на платформе
     *
     * @param responseWrapperAds объявления
     * @return Возвращает объявления авторизированного пользователя, размещенного на платформе
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperAds.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенное объявление")
    })
    @Operation(summary = "Метод для получения объявления авторизированного пользователя, размещенного на платформе", description = "Позволяет получить объявление авторизированного пользователя, размещенного на платформе")
    @GetMapping("/me")
    public ResponseWrapperAds getAdsMe(ResponseWrapperAds responseWrapperAds) {
        logger.info(GET_ADS_ME_MESSAGE_LOGGER_CONTROLLER, responseWrapperAds);
        return new ResponseWrapperAds();
    }

    /**
     * Этот метод позволяет изменить изображение у объявления, размещенного на платформе
     *
     * @param id    идентификатор
     * @param image ссылка на новое изображение
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Не найденное изображение у объявления")
    })
    @Operation(summary = "Метод для изменения изображения для объявления, размещенного на платформе", description = "Позволяет изменить изображение для объявления, размещенного на платформе")
    @PatchMapping("/{id}/image")
    public void updateImage(@PathVariable(required = true) Integer id, @RequestBody String image) {
        logger.info(UPDATE_IMAGE_MESSAGE_LOGGER_CONTROLLER, id, image);
    }
}
