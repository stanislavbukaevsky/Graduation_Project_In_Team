package pro.sky.diploma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.diploma.dto.Comment;

/**
 * Класс-контроллер для работы с комментариями
 */
@RestController
@RequestMapping("/ads")
@AllArgsConstructor
@Tag(name = "Комментарии", description = "Позволяет управлять комментариями к объявлениям")
public class CommentController {
    /**
     * Метод получения комментариев по id объявления
     *
     * @param id идентификатор объявления
     * @return возвращает коммент
     */
    @Operation(summary = "Получить комментарии объявления")
    @GetMapping("/{id}/comments")
    public Comment getCommentById(@Parameter(description = "Идентификатор объявления") @PathVariable int id) {
        return new Comment();
    }

    /**
     * Метод добавления комментариев к объявлению по их id
     *
     * @param comment тело комментария
     * @return возвращает добавленный коммент
     */
    @Operation(summary = "Добавить комментарий к объявлению")
    @PostMapping("/{id}/comments")
    public Comment addComment(@RequestBody Comment comment,
                              @Parameter(description = "Идентификатор объявления") @PathVariable int id) {
        return new Comment();
    }

    /**
     * Метод удаления комментария
     *
     * @param adId      идентификатор объявления
     * @param commentId идентификатор комментария
     * @return возвращает http статус
     */
    @Operation(summary = "Удалить комментарий")
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@Parameter(description = "Идентификатор объявления") @PathVariable int adId,
                                                @Parameter(description = "Идентификатор коммента") @PathVariable int commentId) {
        //commentService.deleteComment(id);
        if (true){ //404
            return new ResponseEntity<>("Коммент не найден", HttpStatus.NOT_FOUND);
        }
        else if (false){ //403
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        } else if (true){ //401
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
    @Operation(summary = "Обновить комментарий")
    @PatchMapping("{adId}/comments/{commentId}")
    public Comment updateComment(@Parameter(description = "Идентификатор объявления") @PathVariable int adId,
                                 @RequestBody Comment comment,
                                 @Parameter(description = "Идентификатор коммента") @PathVariable int commentId) {
        return new Comment();
    }
}
