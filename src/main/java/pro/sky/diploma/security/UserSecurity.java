package pro.sky.diploma.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import pro.sky.diploma.entities.User;

import java.util.Collection;
import java.util.List;

/**
 * Этот класс предоставляет всю необходимую информацию для построения объекта аутентификации.
 * Здесь находятся методы, которые достают данные пользователя из базы данных. <br>
 * Реализует методы интерфейса {@link UserDetails}
 */
@RequiredArgsConstructor
@Component
@RequestScope
@Setter
@Getter
public class UserSecurity implements UserDetails {
    private User user;

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getActive();
    }

    @Override
    public boolean isEnabled() {
        return user.getActive();
    }

}
