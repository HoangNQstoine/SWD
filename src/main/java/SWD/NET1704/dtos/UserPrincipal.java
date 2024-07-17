package SWD.NET1704.dtos;

import SWD.NET1704.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserPrincipal implements UserDetails {
    private final UserEntity userEntity;
    private Collection<? extends GrantedAuthority> authorities;
    public UserPrincipal(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
        this.userEntity = userEntity;
        this.authorities = authorities;
    }
    public static UserPrincipal create(UserEntity userEntity) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRoleEntity().getRoleName()));

        return new
                UserPrincipal(
                userEntity,
                authorities);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public int getId() {
        return userEntity.getUserId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(userEntity.getUserId(), that.userEntity.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEntity.getUserId());
    }
}
