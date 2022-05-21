package ir.parliran.board;

import ir.parliran.global.LookupGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class BoardCommissionRepositoryImpl implements BoardCommissionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    BoardCommissionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BoardCommission> findAll(long fkBoardPeriodOid) {
        String sql = "select *,  l1.value as lu_commission_title " +
                "from board_commission " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.commission +
                "' ) l1 on l1.`key` = lu_commission " +
                "where fk_board_period_oid = ? " +
                " order by lu_commission_title";

        return jdbcTemplate.query(sql, new Object[]{fkBoardPeriodOid},
                (rs, i) ->
                        BoardCommission.builder()
                                .oid(rs.getLong("oid"))
                                .fkBoardPeriodOid(rs.getLong("fk_board_period_oid"))
                                .luCommission(rs.getString("lu_commission"))
                                .luCommissionTitle(rs.getString("lu_commission_title"))
                                .build()
        );
    }

    @Override
    public Optional<BoardCommission> findById(long oid) {
        Optional<BoardCommission> item = Optional.empty();
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select *, l1.value as lu_commission_title " +
                                    "from board_commission " +
                                    "left outer join (select `key`, value from lookup where group_key = '" +
                                    LookupGroupKey.commission + "' ) l1 on l1.`key` = lu_commission " +
                                    "where oid = ? ",
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(BoardCommission.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {
        }
        return item;

    }

    @Override
    public BoardCommission add(BoardCommission item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into board_commission " +
                            "(fk_board_period_oid, lu_commission) " +
                            "values " +
                            "(?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setObject(1, item.getFkBoardPeriodOid());
            ps.setString(2, item.getLuCommission());
            return ps;
        }, keyHolder);
        item.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return item;
    }

    public int edit(BoardCommission item) {
        return jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update board_commission set lu_commission = ? where oid = ?"
            );
            ps.setString(1, item.getLuCommission());
            ps.setLong(2, item.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from board_commission where oid = ? ", oid);
    }

    @Override
    public int similarCount(BoardCommission boardCommission) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from board_commission where " +
                        "oid <> ? and fk_board_period_oid = ?  and lu_commission = ?",
                new Object[]{boardCommission.getOid(), boardCommission.getFkBoardPeriodOid(), boardCommission.getLuCommission()},
                Integer.class
        );
        if (result == null)
            result = -1;
        return result;

    }

    @Override
    public boolean exists(long oid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from board_commission " +
                        "where oid = ? ",
                new Object[]{oid},
                Integer.class
        );
        return result != null && result >= 1;
    }

    @Override
    public String getTitle(long oid) {
        return jdbcTemplate.queryForObject(
                "select l1.value as title " +
                        "from board_commission " +
                        "left outer join (select `key`, value from lookup where group_key = '" +
                        LookupGroupKey.commission + "' ) l1 on l1.`key` = lu_commission " +
                        "where oid = ? ",
                new Object[]{oid},
                String.class
        );
    }
}
