package ir.parliran.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
public class RoleService {

    private final RoleRepository repository;

    @Autowired
    RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    List<Role> findAll(String title) {
        return repository.findAll(title);
    }

    List<Role> findAll() {
        return findAll(null);
    }

    Optional<Role> findById(long oid) {
        return repository.findById(oid);
    }

    Role add(Role role) {
        validation(role);
        return repository.add(role);
    }

    int edit(Role role) {
        validation(role);
        return repository.edit(role);
    }

    int remove(long oid) {
        return repository.remove(oid);
    }

    private void validation(@NotNull Role role) {
        if(similarCount(role) >= 1)
            throw new RuntimeException("duplicated_data");
    }

    private int similarCount(@NotNull Role role) {
        return repository.similarCount(role);
    }

    public boolean exists(long oid) {
        return repository.exists(oid);
    }

}
