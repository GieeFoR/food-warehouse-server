package security.services;

import com.pl.projectjava.models.User;
import com.pl.projectjava.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException("username is empty");
        }

        Optional<User> foundUser = userRepository.findByEmail(username);
        if(!foundUser.isPresent()){
            System.out.println("FOUND");
            //return foundUser.toCurrentUserDetails();
        }
        throw new UsernameNotFoundException( username + "is not found");
    }
}
