package pro.sky.diploma.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pro.sky.diploma.entities.User;
import pro.sky.diploma.repositories.UserRepository;

import static pro.sky.diploma.constants.ExceptionTextMessageConstant.USER_NOT_FOUND_EXCEPTION;

/**
 * Этот класс используется для того, чтобы создать объект {@link UserSecurity} путем реализации единственного метода
 * интерфейса {@link UserDetailsService}
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION));

        return UserSecurity.fromUser(user);
    }
}