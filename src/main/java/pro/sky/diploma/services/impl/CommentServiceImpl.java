package pro.sky.diploma.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.diploma.dto.CommentDTO;
import pro.sky.diploma.entities.Comment;
import pro.sky.diploma.exceptions.CommentNotFoundException;
import pro.sky.diploma.mappers.CommentMapper;
import pro.sky.diploma.repositories.CommentRepository;
import pro.sky.diploma.services.CommentService;

import java.time.LocalDateTime;

import static pro.sky.diploma.constants.ExceptionTextMessageConstant.COMMENT_NOT_FOUND_EXCEPTION;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.DELETE_COMMENT_MESSAGE_LOGGER_SERVICE;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.UPDATE_COMMENT_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс с бизнес-логикой для всех комментариев, опубликованных на платформе.
 * Реализует интерфейс {@link CommentService}
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    /**
     * Реализация метода для удаления комментария, опубликованного на платформе.
     *
     * @param adId      идентификатор объявления
     * @param commentId идентификатор комментария
     * @return Возвращает DTO удаленного комментария
     */
    @Override
    public CommentDTO deleteComment(Integer adId, Integer commentId) {
        logger.info(DELETE_COMMENT_MESSAGE_LOGGER_SERVICE, adId, commentId);
        Long id = Long.valueOf(commentId);
        Comment comment = commentRepository.findCommentById(id).orElse(null);
        if (comment != null) {
            commentRepository.delete(comment);
        }
        return commentMapper.importEntityToDTO(comment);
    }

    /**
     * Реализация метода для изменения комментария, опубликованного на платформе.
     * Этот метод может выбросить исключение {@link CommentNotFoundException}, если комментарий не найден
     *
     * @param adId       идентификатор объявления
     * @param commentDTO DTO комментария
     * @param commentId  идентификатор комментария
     * @return Возвращает DTO измененного комментария
     */
    @Override
    public CommentDTO updateComment(Integer adId, CommentDTO commentDTO, Integer commentId) {
        logger.info(UPDATE_COMMENT_MESSAGE_LOGGER_SERVICE, adId, commentDTO, commentId);
        Long id = Long.valueOf(commentId);
        Comment comment = commentRepository.findCommentById(id).orElse(null);
        if (comment == null) {
            throw new CommentNotFoundException(COMMENT_NOT_FOUND_EXCEPTION);
        }
        comment.setText(commentDTO.getText());
        comment.setDateTime(LocalDateTime.parse(commentDTO.getCreatedAt()));
        Comment result = commentRepository.save(comment);
        return commentMapper.importEntityToDTO(result);
    }
}
