package ir.parliran.security;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:14
 */
interface RoleRepository {
    Optional<Role> findByTitle(String title);

    List<Role> findAll(String keyword);

    Optional<Role> findById(long oid);

    Role add(Role role);

    int edit(Role role);

    int remove(long oid);

    boolean exists(String title);

    int similarCount(Role role);

    boolean exists(long oid);
}