package foodwarehouse.web.security;

import foodwarehouse.web.error.RestException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import foodwarehouse.core.user.UserRepository;

import java.sql.SQLException;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository
                    .findUserByUsername(username)
                    .map(user -> new User(user.username(), user.password(), List.of(new SimpleGrantedAuthority("ROLE_" + user.permission().value()))))
                    .orElseThrow(() -> new UsernameNotFoundException(username));
        } catch (SQLException sqlException) {
            throw new RestException("Unable to login user.");
        }
    }
}
