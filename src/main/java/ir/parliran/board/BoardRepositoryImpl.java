package ir.parliran.board;

import ir.parliran.board.Board;
import ir.parliran.board.BoardRepository;
import ir.parliran.contact.Contact;
import ir.parliran.global.LookupGroupKey;
import ir.parliran.global.Page;
import ir.parliran.global.PagingHelper;
import ir.parliran.global.Utils;
import ir.parliran.lookup.Lookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class BoardRepositoryImpl implements BoardRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    BoardRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<Board> findAll(SearchCredential searchCredential) {
        StringJoiner conditions = new StringJoiner(" AND ", " WHERE ", "").setEmptyValue("");
        List<Object> params = new ArrayList<>();
        if (!Utils.isBlank(searchCredential.getKeyword())) {
            conditions.add("(title like ? or phone like ? or cell like ? or email like ? or fax like ?  )");
            params.add("%" + searchCredential.getKeyword()  + "%");
            params.add("%" + searchCredential.getKeyword()  + "%");
            params.add("%" + searchCredential.getKeyword()  + "%");
            params.add("%" + searchCredential.getKeyword()  + "%");
            params.add("%" + searchCredential.getKeyword()  + "%");
        }

        if (!Utils.isBlank(searchCredential.getLuBoardType())) {
            conditions.add("lu_board_type = ?");
            params.add(searchCredential.getLuBoardType());
        }

        if (!Utils.isBlank(searchCredential.getLuSessionPeriod())) {
            conditions.add("lu_session_period = ?");
            params.add(searchCredential.getLuSessionPeriod());
        }

        String sql = "select oid, title, lu_board_type, l1.value as lu_board_type_title, lu_session_period, " +
                "l2.value as lu_session_period_title, phone " +
                "from board " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.board_type +
                "' ) l1 on l1.`key` = lu_board_type " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.session_period +
                "') l2 on l2.`key` = lu_session_period " +
                conditions +
                " order by oid desc";

        return new PagingHelper<Board>().fetch(jdbcTemplate, sql, params.toArray(), searchCredential.getPageNo(),
                searchCredential.getPageSize(),
                new BeanPropertyRowMapper<>(Board.class)
        );
    }

    @Override
    public List<Board> findAll() {
        String sql = "select oid, title from board order by title ";
        return jdbcTemplate.query(
                sql,new BeanPropertyRowMapper<>(Board.class)
        );

    }

    @Override
    public Optional<Board> findById(long oid) {
        Optional<Board> item = Optional.empty();
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select board.*, l1.value as lu_board_type_title, l2.value as lu_session_period_title, " +
                                    "concat(l3.name, ' ', l3.family) as fk_chairman_oid_title, " +
                                    "concat(l4.name, ' ', l4.family) as fk_secretary_oid_title " +
                                    "from board " +
                                    "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.board_type +
                                    "' ) l1 on l1.`key` = lu_board_type " +
                                    "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.session_period +
                                    "') l2 on l2.`key` = lu_session_period " +
                                    "left outer join contact l3 on l3.oid = board.fk_chairman_oid " +
                                    "left outer join contact l4 on l4.oid = board.fk_secretary_oid " +
                                    "where board.oid = ?",
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(Board.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {}
        return item;

    }

    private void fillPreparedStatement(PreparedStatement ps, Board pm) throws SQLException {
        ps.setString(1, pm.getTitle());
        ps.setString(2, pm.getLuBoardType());
        ps.setObject(3, pm.getFkChairmanOid());
        ps.setObject(4, pm.getFkSecretaryOid());
        ps.setObject(5, pm.getObservationCount());
        ps.setString(6, pm.getLuSessionPeriod());
        ps.setString(7, pm.getRelatedLaw());
        ps.setString(8, pm.getPhone());
        ps.setString(9, pm.getFax());
        ps.setString(10, pm.getEmail());
    }

    @Override
    public Board add(Board pm) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into board " +
                            "(title, lu_board_type, fk_chairman_oid, fk_secretary_oid, observation_count, " +
                            "lu_session_period, related_law, phone, fax, email) " +
                            "values " +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            fillPreparedStatement(ps, pm);
            return ps;
        }, keyHolder);
        pm.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return pm;
    }

    @Override
    public int edit(Board pm) {
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "update board set " +
                            "title = ?, lu_board_type = ?, fk_chairman_oid = ?, fk_secretary_oid = ?, " +
                            "observation_count = ?, lu_session_period = ?, related_law = ?, " +
                            "phone = ?, fax = ?, email = ? " +
                            "where oid = ?"
            );
            fillPreparedStatement(ps, pm);
            ps.setLong(11, pm.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from board where oid = ?", oid);
    }
}
