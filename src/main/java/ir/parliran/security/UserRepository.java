package ir.parliran.security;

import ir.parliran.global.Page;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:14
 */
interface UserRepository {
    Page<User> findAll(SearchCredential searchCredential);
    Optional<User> findById(long oid);

    Optional<User> findByUsername(String username);

    User add(User user);
    int edit(User user);
    int editPassword(User user);
    int editStatus(User user);
    int remove(long oid);
    boolean exists(String username);
    boolean exists(long oid);
    int similarCount(User user);
}