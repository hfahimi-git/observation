package ir.parliran.board;

import ir.parliran.contact.Contact;
import ir.parliran.global.LookupGroupKey;
import ir.parliran.global.Page;
import ir.parliran.global.PagingHelper;
import ir.parliran.global.Utils;
import ir.parliran.pm.ParliamentMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class BoardPeriodRepositoryImpl implements BoardPeriodRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    BoardPeriodRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BoardPeriod> findAll(long fkBoardOid) {
        String sql = "select board_period.*,  l1.value as lu_period_no_title, board.title as fk_board_oid_title " +
                "from board_period " +
                "left outer join board on board.oid = fk_board_oid " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.parliament_period +
                "' ) l1 on l1.`key` = lu_period_no " +
                "where fk_board_oid = ? " +
                " order by lu_period_no desc";

        return jdbcTemplate.query(sql, new Object[]{fkBoardOid},
                new BeanPropertyRowMapper<>(BoardPeriod.class)
        );
    }

    @Override
    public Page<BoardPeriod> findAll(BoardPeriodSearchCredential bpSearchCredential) {
        StringJoiner conditions = new StringJoiner(" AND ", " WHERE ", "").setEmptyValue("");
        List<Object> params = new ArrayList<>();
        if (!Utils.isBlank(bpSearchCredential.getKeyword())) {
            conditions.add("(title like ?)");
            params.add(bpSearchCredential.getKeyword());
        }

        if (bpSearchCredential.getLuPeriodNo() != null && bpSearchCredential.getLuPeriodNo() > 0 ) {
            conditions.add("lu_period_no = ?");
            params.add(bpSearchCredential.getLuPeriodNo());
        }

        String sql = "select board_period.*,  l1.value as lu_period_no_title, board.title as fk_board_oid_title " +
                "from board_period " +
                "left outer join board on board.oid = fk_board_oid " +
                "left outer join (select `key`, value from lookup where group_key = '" +
                    LookupGroupKey.parliament_period + "' ) l1 on l1.`key` = lu_period_no " +
                conditions +
                " order by board_period.oid desc";

        return new PagingHelper<BoardPeriod>().fetch(jdbcTemplate, sql, params.toArray(),
                bpSearchCredential.getPageNo(), bpSearchCredential.getPageSize(),
                new BeanPropertyRowMapper<>(BoardPeriod.class)
        );
    }

    @Override
    public Optional<BoardPeriod> findById(long oid) {
        Optional<BoardPeriod> item = Optional.empty();
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select board_period.*, l1.value as lu_period_no_title, board.title as fk_board_oid_title " +
                                    "from board_period " +
                                    "left join board on board.oid = fk_board_oid " +
                                    "left outer join (select `key`, value from lookup where group_key = '" +
                                    LookupGroupKey.parliament_period + "' ) l1 on l1.`key` = lu_period_no " +
                                    "where board_period.oid = ? ",
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(BoardPeriod.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {}
        return item;

    }

    @Override
    public BoardPeriod add(BoardPeriod item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into board_period " +
                            "(fk_board_oid, lu_period_no) " +
                            "values " +
                            "(?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setObject(1, item.getFkBoardOid());
            ps.setObject(2, item.getLuPeriodNo());
            return ps;
        }, keyHolder);
        item.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return item;
    }

    public int edit(BoardPeriod item) {
        return jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update board_period set lu_period_no = ? where oid = ?"
            );
            ps.setInt(1, item.getLuPeriodNo());
            ps.setLong(2, item.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from board_period where oid = ? ", oid);
    }

    @Override
    public int similarCount(long fkBoardOid, int luPeriodNo) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from board_period where fk_board_oid = ?  and lu_period_no = ?",
                new Object[]{fkBoardOid, luPeriodNo},
                Integer.class
        );
        if (result == null)
            result = -1;
        return result;

    }

    @Override
    public boolean exists(long oid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from board_period where oid = ?",
                new Object[]{oid},
                Integer.class
        );

        return result != null && result >= 1;
    }
}
