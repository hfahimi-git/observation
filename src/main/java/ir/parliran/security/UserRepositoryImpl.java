package ir.parliran.security;

import ir.parliran.global.LookupGroupKey;
import ir.parliran.global.Page;
import ir.parliran.global.PagingHelper;
import ir.parliran.global.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    UserRepositoryImpl(JdbcTemplate jdbcTemplate, UserRoleRepository userRoleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Page<User> findAll(SearchCredential searchCredential) {
        StringJoiner conditions = new StringJoiner(" AND ", " WHERE ", "").setEmptyValue("");
        List<Object> params = new ArrayList<>();
        if (!Utils.isBlank(searchCredential.getKeyword())) {
            conditions.add("(convert(username using utf8) like ? or name like ? or family like ? )");
            params.add("%" + searchCredential.getKeyword() + "%");
            params.add("%" + searchCredential.getKeyword() + "%");
            params.add("%" + searchCredential.getKeyword() + "%");
        }

        if (!Utils.isBlank(searchCredential.getLuStatus())) {
            conditions.add("lu_status = ?");
            params.add(searchCredential.getLuStatus());
        }

        String sql = "select security_user.*, l1.value as lu_status_title, concat(name, ' ', family) as fk_contact_oid_title " +
                "from security_user " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.user_status +
                "' ) l1 on l1.`key` = lu_status " +
                "left outer join contact on contact.oid = security_user.fk_contact_oid " +
                conditions +
                " order by security_user.oid desc";

        return new PagingHelper<User>().fetch(jdbcTemplate, sql, params.toArray(), searchCredential.getPageNo(),
                searchCredential.getPageSize(), new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public Optional<User> findById(long oid) {
        Optional<User> item = Optional.empty();
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select security_user.*, l1.value as lu_status_title, concat(name, ' ', family) as fk_contact_oid_title " +
                                    "from security_user " +
                                    "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.user_status +
                                    "' ) l1 on l1.`key` = lu_status " +
                                    "left outer join contact on contact.oid = security_user.fk_contact_oid " +
                                    "where security_user.oid = ?",
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(User.class)
                    )
            );
            item.ifPresent(user -> user.setRoles(userRoleRepository.findAll(oid)));
            item.ifPresent(user -> user.setRolesComplex(userRoleRepository.findAllComplex(oid)));
        } catch (EmptyResultDataAccessException ignore) {
        }
        return item;

    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> item = Optional.empty();
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select security_user.*, l1.value as lu_status_title, concat(name, ' ', family) as fk_contact_oid_title " +
                                    "from security_user " +
                                    "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.user_status +
                                    "' ) l1 on l1.`key` = lu_status " +
                                    "left outer join contact on contact.oid = security_user.fk_contact_oid " +
                                    "where security_user.username = ?",
                            new Object[]{username},
                            new BeanPropertyRowMapper<>(User.class)
                    )
            );
            item.ifPresent(user -> user.setRoles(userRoleRepository.findAll(user.getOid())));
            item.ifPresent(user -> user.setRolesComplex(userRoleRepository.findAllComplex(user.getOid())));
        } catch (EmptyResultDataAccessException ignore) {
        }
        return item;

    }

    @Transactional
    @Override
    public User add(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into security_user " +
                            "(fk_contact_oid, username, password, lu_status) " +
                            "values " +
                            "(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setObject(1, user.getFkContactOid());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getLuStatus());
            return ps;
        }, keyHolder);
        user.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());
        if(user.getOid() > 0) {
            userRoleRepository.add(user.getOid(), user.getRoles());
        }
        return user;
    }

    @Transactional
    @Override
    public int edit(User user) {
        int result = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "update security_user set fk_contact_oid = ?, lu_status = ?, password = ? where oid = ?"
            );
            ps.setObject(1, user.getFkContactOid());
            ps.setString(2, user.getLuStatus());
            ps.setString(3, user.getPassword());
            ps.setLong(4, user.getOid());
            return ps;
        });
        if (result > 0) {
            userRoleRepository.remove(user.getOid());
            userRoleRepository.add(user.getOid(), user.getRoles());
        }
        return result;
    }

    @Override
    public int editPassword(User pm) {
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("update security_user set password = ? where oid = ?");
            ps.setString(1, pm.getPassword());
            ps.setLong(2, pm.getOid());
            return ps;
        });
    }

    @Override
    public int editStatus(User pm) {
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("update security_user set lu_status = ? where oid = ?");
            ps.setString(1, pm.getLuStatus());
            ps.setLong(2, pm.getOid());
            return ps;
        });
    }

    @Transactional
    @Override
    public int remove(long oid) {
        Optional<User> user = findById(oid);
        user.ifPresent(item -> userRoleRepository.remove(oid));
        return jdbcTemplate.update("delete from security_user where oid = ?", oid);
    }

    @Override
    public int similarCount(User user) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from security_user where username = ? and oid <> ?",
                new Object[]{user.getUsername(), user.getOid()},
                Integer.class
        );
        return result != null ? result : -1;
    }

    @Override
    public boolean exists(long oid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from security_user where oid = ?",
                new Object[]{oid},
                Integer.class
        );

        return result != null && result >= 1;
    }

    @Override
    public boolean exists(String username) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from security_user where username = ?",
                new Object[]{username},
                Integer.class
        );

        return result != null && result >= 1;
    }
}
