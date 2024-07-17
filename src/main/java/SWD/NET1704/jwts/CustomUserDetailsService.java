package SWD.NET1704.jwts;

import SWD.NET1704.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import SWD.NET1704.dtos.UserPrincipal;
import SWD.NET1704.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    final
    UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserEntity user = userRepository.existsByEmail(email);
            return UserPrincipal.create(user);
        }catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException("user not found with username or email: " + email);
        }
    }
    //this method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(int userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("user not found with Id: " + userId));
        return UserPrincipal.create(user);
    }
}
