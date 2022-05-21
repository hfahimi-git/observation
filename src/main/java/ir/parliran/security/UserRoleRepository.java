package ir.parliran.security;

import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

interface UserRoleRepository {
    List<Role> findAllComplex(long fkUserOid);

    List<Long> findAll(long fkUserOid);

    @Transactional
    int[] add(long fkUserOid, @NotNull List<Long> roles);

    int remove(long fkUserOid);

    @Transactional
    void edit(long fkUserOid, @NotNull List<Long> roles);

}
