package ir.parliran.security;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
class UserRoleRepositoryImpl implements UserRoleRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Role> findAllComplex(long fkUserOid) {
        String sql = "select oid, title from security_user_role " +
                "inner join security_role on oid = fk_role_oid " +
                "where fk_user_oid = ? ";

        return jdbcTemplate.query(sql,
                new Object[]{fkUserOid},
                new BeanPropertyRowMapper<>(Role.class));
    }

    @Override
    public List<Long> findAll(long fkUserOid) {
        return jdbcTemplate.queryForList(
                "select fk_role_oid from security_user_role where fk_user_oid = ? ",
                new Object[]{fkUserOid},
                Long.class);
    }

    @Transactional
    @Override
    public int[] add(long fkUserOid, @NotNull List<Long> roles) {
        return jdbcTemplate.batchUpdate(
                "insert into security_user_role(fk_user_oid, fk_role_oid) values(?, ?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, fkUserOid);
                        ps.setLong(2, roles.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return roles.size();
                    }
                });
    }

    @Override
    public int remove(long fkUserOid) {
        return jdbcTemplate.update("delete from security_user_role where fk_user_oid = ?", fkUserOid);
    }

    @Transactional
    @Override
    public void edit(long fkUserOid, @NotNull List<Long> roles) {
        remove(fkUserOid);
        add(fkUserOid, roles);

    }
}
