package pro.sky.diploma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pro.sky.diploma.dto.CommentDTO;
import pro.sky.diploma.dto.ResponseWrapperCommentDTO;
import pro.sky.diploma.services.CommentService;

import static pro.sky.diploma.constants.FrontServerUserConstant.*;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы с комментариями
 */
@RestController
@RequestMapping(REQUEST_MAPPING_COMMENT_CONTROLLER)
@AllArgsConstructor
@CrossOrigin(value = FRONT_ADDRESS)
@Tag(name = "Комментарии", description = "Позволяет управлять комментариями к объявлениям")
public class CommentController {
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    /**
     * Метод получения комментариев по id объявления
     *
     * @param id идентификатор объявления
     * @return возвращает коммент
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperCommentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    @Operation(summary = "Получить комментарии объявления")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(GET_MAPPING_GET_COMMENT_BY_ID_COMMENT_CONTROLLER)
    public ResponseEntity<ResponseWrapperCommentDTO> getCommentById(@Parameter(description = "Идентификатор объявления") @PathVariable(required = true) Integer id) {
        logger.info(GET_COMMENT_BY_ID_MESSAGE_LOGGER_CONTROLLER, id);
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    /**
     * Метод добавления комментариев к объявлению по их id
     *
     * @param commentDTO тело комментария
     * @param id         идентификатор объявления
     * @return возвращает добавленный коммент
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенный комментарий"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    @Operation(summary = "Добавить комментарий к объявлению")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping(POST_MAPPING_ADD_COMMENT_CONTROLLER)
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO,
                                                 @Parameter(description = "Идентификатор объявления") @PathVariable(required = true) Integer id) {
        logger.info(ADD_COMMENT_MESSAGE_LOGGER_CONTROLLER, commentDTO, id);
        return ResponseEntity.ok(commentService.addComment(commentDTO, id));
    }

    /**
     * Метод удаления комментария
     *
     * @param adId      идентификатор объявления
     * @param commentId идентификатор комментария
     * @return возвращает http статус
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенный комментарий"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    @Operation(summary = "Удалить комментарий")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(DELETE_MAPPING_DELETE_COMMENT_CONTROLLER)
    public ResponseEntity<CommentDTO> deleteComment(@Parameter(description = "Идентификатор объявления") @PathVariable(required = true) Integer adId,
                                                    @Parameter(description = "Идентификатор коммента") @PathVariable(required = true) Integer commentId) {
        logger.info(DELETE_COMMENT_MESSAGE_LOGGER_CONTROLLER, adId, commentId);
        return ResponseEntity.ok(commentService.deleteComment(adId, commentId));
    }

    /**
     * Метод для изменения комментария
     *
     * @param adId       идентификатор объявления
     * @param commentDTO новый комментарий
     * @param commentId  идентификатор коммента
     * @return возвращает изменённый комментарий
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенный комментарий"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    @Operation(summary = "Обновить комментарий")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(PATCH_MAPPING_UPDATE_COMMENT_CONTROLLER)
    public ResponseEntity<CommentDTO> updateComment(@Parameter(description = "Идентификатор объявления") @PathVariable(required = true) Integer adId,
                                                    @RequestBody CommentDTO commentDTO,
                                                    @Parameter(description = "Идентификатор коммента") @PathVariable(required = true) Integer commentId) {
        logger.info(UPDATE_COMMENT_MESSAGE_LOGGER_CONTROLLER, adId, commentDTO, commentId);
        return ResponseEntity.ok(commentService.updateComment(adId, commentDTO, commentId));
    }
}
