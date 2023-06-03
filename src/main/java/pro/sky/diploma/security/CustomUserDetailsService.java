package pro.sky.diploma.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pro.sky.diploma.dto.Role;
import pro.sky.diploma.entities.User;
import pro.sky.diploma.exceptions.AdsNotFoundException;
import pro.sky.diploma.exceptions.CommentNotFoundException;
import pro.sky.diploma.repositories.AdsRepository;
import pro.sky.diploma.repositories.CommentRepository;
import pro.sky.diploma.repositories.UserRepository;

import static pro.sky.diploma.constants.ExceptionTextMessageConstant.*;

/**
 * Этот класс используется для того, чтобы создать объект {@link UserSecurity} путем реализации единственного метода
 * интерфейса {@link UserDetailsService}
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserSecurity userSecurity;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION));

        userSecurity.setUser(user);
        return userSecurity;
    }

    public boolean checkAuthUserToAds(Long id) {
        return findAdsById(id).equals(userSecurity.getUsername());
    }

    public boolean checkAuthUserToComment(Long id) {
        return findCommentById(id).equals(userSecurity.getUsername());
    }

    public boolean checkAdmin() {
        return userSecurity.getAuthorities().stream().anyMatch(us -> us.getAuthority().contains(Role.ADMIN.name()));
    }

    private String findAdsById(Long id) {
        return adsRepository.findAdsById(id).orElseThrow(() ->
                new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION)).getUser().getEmail();
    }

    private String findCommentById(Long id) {
        return commentRepository.findCommentById(id).orElseThrow(() ->
                new CommentNotFoundException(COMMENT_NOT_FOUND_EXCEPTION)).getUser().getEmail();
    }
}