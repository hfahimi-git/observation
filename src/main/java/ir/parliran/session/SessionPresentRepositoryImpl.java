package ir.parliran.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.StringJoiner;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class SessionPresentRepositoryImpl implements SessionPresentRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    SessionPresentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Long> findAll(long fkSessionOid) {
        String sql = "select fk_observer_oid from sess_absent where fk_session_oid = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{fkSessionOid}, Long.class);
    }

    @Override
    public int add(long fkSessionOid, List<Long> presents) {
        if (presents == null) {
            return 0;
        }
        StringJoiner sql = new StringJoiner(", ",
                " insert into sess_absent(fk_session_oid, fk_observer_oid) values ", ";")
                .setEmptyValue("");

        for (Long present : presents) {
            if (present != null && present > 0) {
                sql.add("(" + fkSessionOid + ", " + present + ")");
            } else {
                presents.remove(present);
            }
        }
        if (sql.length() < 1) {
            return 0;
        }
        return jdbcTemplate.update(sql.toString());
    }

    public void edit(long fkSessionOid, List<Long> presents) {
        remove(fkSessionOid);
        add(fkSessionOid, presents);
    }

    @Override
    public int remove(long fkSessionOid) {
        return jdbcTemplate.update("delete from sess_absent where fk_session_oid = ? ", fkSessionOid);
    }

    @Override
    public boolean exists(long fkSessionOid, long fkObserverOid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_absent " +
                        "where fk_session_oid = ? and fk_observer_oid = ? ",
                new Object[]{fkSessionOid, fkObserverOid},
                Integer.class
        );
        return result != null && result >= 1;
    }

}
