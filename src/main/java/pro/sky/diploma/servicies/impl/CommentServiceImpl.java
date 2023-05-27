package pro.sky.diploma.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pro.sky.diploma.dto.CommentDTO;
import pro.sky.diploma.dto.ResponseWrapperCommentDTO;
import pro.sky.diploma.entities.Ads;
import pro.sky.diploma.entities.Comment;
import pro.sky.diploma.entities.User;
import pro.sky.diploma.exceptions.AdsNotFoundException;
import pro.sky.diploma.exceptions.CommentNotFoundException;
import pro.sky.diploma.exceptions.UserNotFoundException;
import pro.sky.diploma.mappers.CommentMapper;
import pro.sky.diploma.repositories.AdsRepository;
import pro.sky.diploma.repositories.CommentRepository;
import pro.sky.diploma.repositories.UserRepository;
import pro.sky.diploma.security.CustomUserDetailsService;
import pro.sky.diploma.security.UserSecurity;
import pro.sky.diploma.services.CommentService;

import java.time.LocalDateTime;
import java.util.List;

import static pro.sky.diploma.constants.ExceptionTextMessageConstant.*;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для всех комментариев, опубликованных на платформе.
 * Реализует интерфейс {@link CommentService}
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final UserSecurity userSecurity;
    private final CommentMapper commentMapper;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Реализация метода для получения комментариев по id объявления
     *
     * @param id идентификатор объявления
     * @return Возвращает комментарий по идентификатору объявления
     */
    public ResponseWrapperCommentDTO getCommentById(Integer id) {
        logger.info(GET_COMMENT_BY_ID_COMMENT_MESSAGE_LOGGER_SERVICE, id);
        Long idComment = Long.valueOf(id);
        Ads ads = adsRepository.getAdsById(idComment).orElseThrow(() ->
                new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION));
        List<Comment> comments = ads.getComments();
        return commentMapper.importVariablesToDTO(comments.size(), comments);
    }

    /**
     * Реализация метода добавления комментариев к объявлению по их идентификатору
     *
     * @param commentDTO тело комментария
     * @param id         идентификатор объявления
     * @return Возвращает добавленный комментарий по идентификатору объявления
     */
    public CommentDTO addComment(CommentDTO commentDTO, Integer id) {
        logger.info(ADD_COMMENT_MESSAGE_LOGGER_SERVICE, commentDTO, id);
        Long adsId = Long.valueOf(id);
        LocalDateTime dateTime = LocalDateTime.now();
        Ads ads = adsRepository.getAdsById(adsId).orElseThrow(() ->
                new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION));
        User user = userRepository.findUserByEmail(userSecurity.getUsername()).orElseThrow(() ->
                new UserNotFoundException(USER_NOT_FOUND_EXCEPTION));
        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setDateTime(dateTime);
        comment.setUser(user);
        comment.setAds(ads);
        Comment result = commentRepository.save(comment);
        return commentMapper.importEntityToDTO(result);
    }

    /**
     * Реализация метода для удаления комментария, опубликованного на платформе.
     *
     * @param adId      идентификатор объявления
     * @param commentId идентификатор комментария
     */
    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        logger.info(DELETE_COMMENT_MESSAGE_LOGGER_SERVICE, adId, commentId);
        Long idAds = Long.valueOf(adId);
        Long idComment = Long.valueOf(commentId);
        Comment comment = commentRepository.findAdsByIdAndId(idAds, idComment).orElseThrow(() ->
                new CommentNotFoundException(COMMENT_AND_ADS_NOT_FOUND_EXCEPTION));

        if (comment != null && checkUsersByComment(commentId)) {
            commentRepository.delete(comment);
        }
        commentMapper.importEntityToDTO(comment);
    }

    /**
     * Реализация метода для изменения комментария, опубликованного на платформе.
     * Этот метод может выбросить исключение {@link CommentNotFoundException}, если комментарий не найден
     *
     * @param adId         идентификатор объявления
     * @param commentDTO   DTO комментария
     * @param commentId    идентификатор комментария
     * @return Возвращает DTO измененного комментария
     */
    @Override
    public CommentDTO updateComment(Integer adId, CommentDTO commentDTO, Integer commentId) {
        logger.info(UPDATE_COMMENT_MESSAGE_LOGGER_SERVICE, adId, commentDTO, commentId);
        LocalDateTime dateTime = LocalDateTime.now();
        Long idAds = Long.valueOf(adId);
        Long idComment = Long.valueOf(commentId);
        Comment comment = commentRepository.findAdsByIdAndId(idAds, idComment).orElseThrow(() ->
                new CommentNotFoundException(COMMENT_AND_ADS_NOT_FOUND_EXCEPTION));
        Integer commentIdInteger = comment.getId().intValue();
        checkUsersByComment(commentIdInteger);
        comment.setText(commentDTO.getText());
        comment.setDateTime(dateTime);
        Comment result = commentRepository.save(comment);
        return commentMapper.importEntityToDTO(result);
    }

    /**
     * Приватный метод, который проверяет авторизированного пользователя, размещающего комментарий на платформе и пользователя по его роли.
     * Этот метод может выбросить исключение {@link ResponseStatusException} со статусом 403, если у пользователя нет доступа для действия
     *
     * @param id           идентификатор объявления
     * @return Возвращает true, если условие выполняется, в противном случае выбрасывает исключение
     */
    private boolean checkUsersByComment(Integer id) {
        logger.info(CHECK_USERS_COMMENT_MESSAGE_LOGGER_SERVICE, id);
        if (!(customUserDetailsService.checkAuthUserToComment(id) || customUserDetailsService.checkAdmin())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, USER_NAME_NOT_FOUND_EXCEPTION_2);
        }
        return true;
    }
}
