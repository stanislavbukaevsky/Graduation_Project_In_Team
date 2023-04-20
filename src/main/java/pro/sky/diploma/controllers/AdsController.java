package pro.sky.diploma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
public class AdsController {
    /**
     * Метод получения комментариев по id объявления
     *
     * @param id идентификатор объявления
     * @return возвращает коммент
     */
    @Operation(summary = "Получить комментарии объявления")
    @GetMapping("/{id}/comments")
    public Comment getCommentById(@Parameter(description = "Идентификатор объявления") @PathVariable Long id) {
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
    public Comment addComment(@RequestBody Comment comment, @Parameter(description = "Идентификатор объявления") @PathVariable Long id) {
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
    public ResponseEntity<Object> deleteComment(@Parameter(description = "Идентификатор объявления") @PathVariable Long adId,
                                                @Parameter(description = "Идентификатор коммента") @PathVariable Long commentId) {
        //commentService.deleteComment(id);
        return ResponseEntity.ok().build();
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
    public Comment updateComment(@Parameter(description = "Идентификатор объявления") @PathVariable Long adId,
                                 @RequestBody Comment comment,
                                 @Parameter(description = "Идентификатор коммента") @PathVariable Long commentId) {
        return new Comment();
    }
}
