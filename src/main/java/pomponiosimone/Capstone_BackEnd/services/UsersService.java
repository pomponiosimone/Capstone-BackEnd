package pomponiosimone.Capstone_BackEnd.services;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pomponiosimone.Capstone_BackEnd.entities.User;
import pomponiosimone.Capstone_BackEnd.exceptions.BadRequestException;
import pomponiosimone.Capstone_BackEnd.exceptions.NotFoundException;
import pomponiosimone.Capstone_BackEnd.payloads.UserDTO;
import pomponiosimone.Capstone_BackEnd.repositories.UsersRepository;

import java.util.Set;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private Cloudinary cloudinary;

    public User saveUser(UserDTO body) {
        if (body == null) {
            throw new BadRequestException("L'email deve avere un body!");
        } else if (this.usersRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso!");
        } else {
            User user = new User( body.cognome(), body.email(), body.nome(),bcrypt.encode(body.password()));

            return this.usersRepository.save(user);
        }
    }
    public Page<User> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.usersRepository.findAll(pageable);
    }

    public User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato!"));
    }
    public User findById(UUID userId) {
        return this.usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }
    public User findByIdAndUpdate(UUID userId, UserDTO updateBody) {
        User found = findById(userId);
        if (this.usersRepository.existsByEmail(updateBody.email()) && !found.getEmail().equals(updateBody.email())) {
            throw new BadRequestException("L'email " + updateBody.email() + " è già in uso!");
        } else {
            found.setNome(updateBody.nome());
            found.setCognome(updateBody.cognome());
            found.setEmail(updateBody.email());
            found.setPassword(updateBody.password());
            return usersRepository.save(found);
        }
    }
    public void findByIdAndDelete(UUID userId) {
        User found = findById(userId);
        this.usersRepository.delete(found);
    }


}
