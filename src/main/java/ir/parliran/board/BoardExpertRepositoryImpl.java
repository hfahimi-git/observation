package ir.parliran.board;

import ir.parliran.global.DatePair;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class BoardExpertRepositoryImpl implements BoardExpertRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    BoardExpertRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BoardExpert> findAll(long fkBoardPeriodOid) {
        String sql = "select board_expert.*,  concat(username, \' (\', name, ' ', family, \')\') as fk_user_oid_title " +
                "from board_expert " +
                "left outer join security_user su on fk_user_oid = su.oid " +
                "left outer join contact c on su.fk_contact_oid = c.oid " +
                "where fk_board_period_oid = ? " +
                " order by board_expert.oid desc";

        return jdbcTemplate.query(sql, new Object[]{fkBoardPeriodOid},
                new BeanPropertyRowMapper<>(BoardExpert.class)
        );
    }

    @Override
    public Optional<BoardExpert> findById(long oid) {
        Optional<BoardExpert> item = Optional.empty();
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select board_expert.*, concat(username, \'(\', name, ' ', family, \')\') as fk_user_oid_title " +
                                    "from board_expert " +
                                    "left outer join security_user su on fk_user_oid = su.oid " +
                                    "left outer join contact c on c.fk_contact_oid = c.oid " +
                                    "where board_expert.oid = ? ",
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(BoardExpert.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {}
        return item;

    }

    private void fillPreparedStatement(PreparedStatement ps, BoardExpert item) throws SQLException {
        ps.setLong(1, item.getFkUserOid());
        ps.setObject(2, Optional.ofNullable(item.getStartDate().getGregorianDate()).orElse(null));
        ps.setObject(3, Optional.ofNullable(item.getEndDate().getGregorianDate()).orElse(null));
        ps.setLong(4, item.getFkBoardPeriodOid());
    }

    @Override
    public BoardExpert add(BoardExpert item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into board_expert " +
                            "(fk_user_oid, start_date, end_date, fk_board_period_oid) " +
                            "values " +
                            "(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            fillPreparedStatement(ps, item);
            return ps;
        }, keyHolder);
        item.setOid(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return item;
    }

    public int edit(BoardExpert item) {
        return jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update board_expert set fk_user_oid = ?, start_date = ?, end_date = ? where oid = ?"
            );
            fillPreparedStatement(ps, item);
            ps.setLong(4, item.getFkBoardPeriodOid());
            return ps;
        });
    }

    @Override
    public int remove(int oid) {
        return jdbcTemplate.update("delete from board_expert where oid = ? ", oid);
    }

    @Override
    public int similarCount(BoardExpert item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from board_expert " +
                        "where " +
                        "oid <> ? and " +
                        "fk_board_period_oid = ? and " +
                        "fk_user_oid = ? and " +
                        "( " +
                        "((date(?) between start_date and end_date) or (date(?)  between start_date and end_date))" +
                        " or " +
                        " (start_date > date(?) and end_date < date(?))" +
                        ") ",
                new Object[]{
                        item.getOid(),
                        item.getFkBoardPeriodOid(), item.getFkUserOid(),
                        Optional.ofNullable(item.getStartDate().getGregorianDate()).orElse(null),
                        Optional.ofNullable(item.getEndDate().getGregorianDate()).orElse(null),
                        Optional.ofNullable(item.getStartDate().getGregorianDate()).orElse(null),
                        Optional.ofNullable(item.getEndDate().getGregorianDate()).orElse(null)
                },
                Integer.class
        );
        return result != null? result: -1;
    }
}
