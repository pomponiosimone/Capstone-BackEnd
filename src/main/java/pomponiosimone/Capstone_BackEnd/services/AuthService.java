package pomponiosimone.Capstone_BackEnd.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pomponiosimone.Capstone_BackEnd.entities.User;
import pomponiosimone.Capstone_BackEnd.exceptions.UnauthorizedException;
import pomponiosimone.Capstone_BackEnd.payloads.UserLoginDTO;
import pomponiosimone.Capstone_BackEnd.security.JWTTools;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(UserLoginDTO body) {
        User found = this.usersService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
