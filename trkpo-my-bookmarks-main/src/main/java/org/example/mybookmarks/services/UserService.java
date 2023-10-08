package org.example.mybookmarks.services;

import java.util.regex.Pattern;

import lombok.extern.java.Log;
import org.example.mybookmarks.datamodel.UserDAO;
import org.example.mybookmarks.datamodel.UsersRepository;
import org.example.mybookmarks.model.BadInputParameters;
import org.example.mybookmarks.model.ConflictDataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log
public class UserService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addNewUser(String email, String password) throws ConflictDataException {
        // see https://www.baeldung.com/java-password-hashing
        validateEmail(email);
        try {
            UserDAO saved = usersRepository.save(new UserDAO(email, passwordEncoder.encode(password)));
            log.info("User created " + saved);
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictDataException("You cannot use " + email, exception);
        }
    }

    public static void validateEmail(String emailAddress) {
        if (!Pattern.compile("^(.+)@(\\S+)$")
                .matcher(emailAddress)
                .matches()) {
            throw new BadInputParameters("invalid email");
        }
    }

    public void deleteAccount(Long userId) {
        usersRepository.deleteById(userId);
    }
}
