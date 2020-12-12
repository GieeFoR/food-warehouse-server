package foodwarehouse.web.user;

import foodwarehouse.core.user.UserRepository;
import foodwarehouse.web.common.SuccessResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class CreateUserAccount {

    private final UserRepository userRepository;

    public CreateUserAccount(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/username")
    public SuccessResponse<UsernameResponse> checkUsername(@RequestBody LoginUsername loginUsername) {
        System.out.println(loginUsername.username());
        boolean exists = userRepository.findByUsername(loginUsername.username()).isPresent();
        System.out.println(exists);
        return new SuccessResponse<>(new UsernameResponse(exists));
    }

    @PostMapping("/email")
    public SuccessResponse<EmailResponse> checkEmail(@RequestBody LoginEmail loginEmail) {
        System.out.println(loginEmail.email());
        boolean exists = userRepository.findByEmail(loginEmail.email()).isPresent();
        System.out.println(exists);
        return new SuccessResponse<>(new EmailResponse(exists));
    }

/*    @PostMapping
    public User registerNewUserAccount(UserDto userDto)
            throws UserAlreadyExistException {

        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            +  userDto.getEmail());
        }
    }*/
}
