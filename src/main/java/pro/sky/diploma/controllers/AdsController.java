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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.diploma.dto.Ads;
import pro.sky.diploma.dto.CreateAds;
import pro.sky.diploma.dto.FullAds;
import pro.sky.diploma.dto.ResponseWrapperAds;

import static pro.sky.diploma.constants.FrontServerUserConstants.FRONT_ADDRESS;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы со всеми объявлениями, опубликованными на платформе
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@CrossOrigin(value = FRONT_ADDRESS)
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
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        logger.info(GET_ALL_ADS_MESSAGE_LOGGER_CONTROLLER);
        return ResponseEntity.ok().build();
    }

    /**
     * Этот метод позволяет добавлять новые объявления на платформу
     *
     * @param createAds     добавляемое объявление
     * @param multipartFile изображение
     * @return Возвращает новое, добавленное объявление на платформу
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Объявление добавлено на платформу", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Ads.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенное объявление"),
            @ApiResponse(responseCode = "404", description = "Не найденное объявление")

    })
    @Operation(summary = "Метод для добавления объявлений на платформу", description = "Позволяет добавить объявление на платформу")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ads> addAds(@RequestPart(name = "properties") CreateAds createAds, @RequestPart(name = "image") MultipartFile multipartFile) {
        logger.info(ADD_ADS_MESSAGE_LOGGER_CONTROLLER, createAds, multipartFile);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<FullAds> getAds(@PathVariable(required = true) Integer id) {
        logger.info(GET_ADS_MESSAGE_LOGGER_CONTROLLER, id);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Ads> removeAds(@PathVariable(required = true) Integer id) {
        logger.info(REMOVE_ADS_MESSAGE_LOGGER_CONTROLLER, id);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Ads> updateAds(@PathVariable(required = true) Integer id, @RequestBody CreateAds createAds) {
        logger.info(UPDATE_ADS_MESSAGE_LOGGER_CONTROLLER, id, createAds);
        return ResponseEntity.ok().build();
    }

    /**
     * Этот метод позволяет получить объявление авторизированного пользователя, размещенного на платформе
     *
     * @return Возвращает объявления авторизированного пользователя, размещенного на платформе
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperAds.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенное объявление")
    })
    @Operation(summary = "Метод для получения объявления авторизированного пользователя, размещенного на платформе", description = "Позволяет получить объявление авторизированного пользователя, размещенного на платформе")
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe() {
        logger.info(GET_ADS_ME_MESSAGE_LOGGER_CONTROLLER);
        return ResponseEntity.ok().build();
    }

    /**
     * Этот метод позволяет изменить изображение у объявления, размещенного на платформе
     *
     * @param id            идентификатор
     * @param multipartFile изображение
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Не найденное изображение у объявления")
    })
    @Operation(summary = "Метод для изменения изображения для объявления, размещенного на платформе", description = "Позволяет изменить изображение для объявления, размещенного на платформе")
    @PatchMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateImage(@PathVariable(required = true) Integer id, @RequestPart(name = "image") MultipartFile multipartFile) {
        logger.info(UPDATE_IMAGE_MESSAGE_LOGGER_CONTROLLER, id, multipartFile);
        return ResponseEntity.ok().build();
    }
}
