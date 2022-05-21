package ir.parliran.security;

import ir.parliran.global.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService(UserRepository repository, PasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    Page<User> findAll(SearchCredential searchCredential) {
        return repository.findAll(searchCredential);
    }

    Optional<User> findById(long oid) {
        return repository.findById(oid);
    }

    Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    User add(User user) {
        validation(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return repository.add(user);
    }

    int edit(User user) {
        validation(user);
        return repository.edit(user);
    }

    int editPassword(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return repository.editPassword(user);
    }

    int editStatus(User user) {
        return repository.editStatus(user);
    }

    int remove(long oid) {
        return repository.remove(oid);
    }

    private void validation(@NotNull User user) {
        if(similarCount(user) >= 1)
            throw new RuntimeException("user_duplicated");
    }

    private int similarCount(@NotNull User user) {
        return repository.similarCount(user);
    }

    public boolean exists(long oid) {
        return repository.exists(oid);
    }
}
