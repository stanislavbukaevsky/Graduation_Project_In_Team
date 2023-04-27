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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.diploma.dto.CommentDTO;
import pro.sky.diploma.dto.ResponseWrapperCommentDTO;

import static pro.sky.diploma.constants.FrontServerUserConstant.FRONT_ADDRESS;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы с комментариями
 */
@RestController
@RequestMapping("/ads")
@AllArgsConstructor
@CrossOrigin(value = FRONT_ADDRESS)
@Tag(name = "Комментарии", description = "Позволяет управлять комментариями к объявлениям")
public class CommentController {
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

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
    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperCommentDTO> getCommentById(@Parameter(description = "Идентификатор объявления") @PathVariable(required = true) Integer id) {
        logger.info(GET_COMMENT_BY_ID_MESSAGE_LOGGER_CONTROLLER, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод добавления комментариев к объявлению по их id
     *
     * @param comment тело комментария
     * @return возвращает добавленный коммент
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенный комментарий"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    @Operation(summary = "Добавить комментарий к объявлению")
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO comment,
                                                 @Parameter(description = "Идентификатор объявления") @PathVariable(required = true) Integer id) {
        logger.info(ADD_COMMENT_MESSAGE_LOGGER_CONTROLLER, comment, id);
        return ResponseEntity.ok().build();
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
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@Parameter(description = "Идентификатор объявления") @PathVariable(required = true) Integer adId,
                                                @Parameter(description = "Идентификатор коммента") @PathVariable(required = true) Integer commentId) {
        //commentService.deleteComment(id);
        logger.info(DELETE_COMMENT_MESSAGE_LOGGER_CONTROLLER, adId, commentId);
        if (true) { //404
            return new ResponseEntity<>("Коммент не найден", HttpStatus.NOT_FOUND);
        } else if (false) { //403
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        } else if (true) { //401
            return new ResponseEntity<>("Необходимо войти в учётную запись для доступа", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Коммент удалён", HttpStatus.OK); //200
    }

    /**
     * Метод для изменения комментария
     *
     * @param adId      идентификатор объявления
     * @param comment   новый комментарий
     * @param commentId идентификатор коммента
     * @return возвращает изменённый комментарий
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь"),
            @ApiResponse(responseCode = "403", description = "Запрещенный комментарий"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    @Operation(summary = "Обновить комментарий")
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@Parameter(description = "Идентификатор объявления") @PathVariable(required = true) Integer adId,
                                                    @RequestBody CommentDTO comment,
                                                    @Parameter(description = "Идентификатор коммента") @PathVariable(required = true) Integer commentId) {
        logger.info(UPDATE_COMMENT_MESSAGE_LOGGER_CONTROLLER, adId, comment, commentId);
        return ResponseEntity.ok().build();
    }
}
