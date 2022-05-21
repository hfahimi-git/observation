package ir.parliran.board;

import ir.parliran.global.DatePair;
import ir.parliran.global.LookupGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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
class BoardObserverRepositoryImpl implements BoardObserverRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    BoardObserverRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BoardObserver> findAll(long fkBoardPeriodOid) {
        String sql = "select board_observer.*,  concat(name, ' ', family) as fk_observer_oid_title, " +
                "l1.value as lu_membership_type_title, l2.value as lu_how_to_elect_title " +
                "from board_observer " +
                "left outer join parliament_member on parliament_member.oid = fk_observer_oid " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.observer_membership +
                "' ) l1 on l1.`key` = lu_membership_type " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.observer_elect_type +
                "' ) l2 on l2.`key` = lu_how_to_elect " +

                "where fk_board_period_oid = ? " +
                " order by oid desc";

        return jdbcTemplate.query(sql, new Object[]{fkBoardPeriodOid},
                new BeanPropertyRowMapper<>(BoardObserver.class)
        );
    }

    @Override
    public List<BoardObserver> findAll(DatePair sessionDate, long fkBoardPeriodOid) {
        String sql = "select board_observer.*,  concat(name, ' ', family) as fk_observer_oid_title, " +
                "l1.value as lu_membership_type_title, l2.value as lu_how_to_elect_title " +
                "from board_observer " +
                "left outer join parliament_member on parliament_member.oid = fk_observer_oid " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.observer_membership +
                "' ) l1 on l1.`key` = lu_membership_type " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.observer_elect_type +
                "' ) l2 on l2.`key` = lu_how_to_elect " +

                "where fk_board_period_oid = ? and date(?) between observation_start_date and observation_end_date " +
                "order by oid desc";

        return jdbcTemplate.query(sql, new Object[]{fkBoardPeriodOid, Optional.ofNullable(sessionDate.getGregorianDate()).orElse(null)},
                new BeanPropertyRowMapper<>(BoardObserver.class)
        );
    }

    @Override
    public List<Long> findCurrentObservers(DatePair sessionDate, long fkBoardPeriodOid) {
        String sql = "select fk_observer_oid " +
                "from board_observer " +
                "where fk_board_period_oid = ? and date(?) between observation_start_date and observation_end_date " +
                "order by oid desc";

        return jdbcTemplate.queryForList(
                sql,
                new Object[]{fkBoardPeriodOid, Optional.ofNullable(sessionDate.getGregorianDate()).orElse(null)},
                Long.class
        );
    }

    @Override
    public Optional<BoardObserver> findById(long oid) {
        Optional<BoardObserver> item = Optional.empty();
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select board_observer.*,  concat(name, ' ', family) as fk_observer_oid_title, " +
                                    "l1.value as lu_membership_type_title, l2.value as lu_how_to_elect_title " +
                                    "from board_observer " +
                                    "left outer join parliament_member on parliament_member.oid = fk_observer_oid " +
                                    "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.observer_membership +
                                    "' ) l1 on l1.`key` = lu_membership_type " +
                                    "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.observer_elect_type +
                                    "' ) l2 on l2.`key` = lu_how_to_elect " +
                                    "where oid = ? ",
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(BoardObserver.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {
        }
        return item;

    }

    private void fillPreparedStatement(PreparedStatement ps, BoardObserver item) throws SQLException {
        ps.setLong(1, item.getFkObserverOid());
        ps.setString(2, item.getLuMembershipType());
        ps.setObject(3, Optional.ofNullable(item.getVotingDate().getGregorianDate()).orElse(null));
        ps.setString(4, item.getStatuteLetterNo());
        ps.setObject(5, Optional.ofNullable(item.getStatuteLetterDate().getGregorianDate()).orElse(null));
        ps.setObject(6, Optional.ofNullable(item.getCommuniqueIssuanceDate().getGregorianDate()).orElse(null));
        ps.setObject(7, Optional.ofNullable(item.getObservationStartDate().getGregorianDate()).orElse(null));
        ps.setObject(8, Optional.ofNullable(item.getObservationEndDate().getGregorianDate()).orElse(null));
        ps.setString(9, item.getLuHowToElect());
        ps.setLong(10, item.getFkBoardPeriodOid());
    }

    @Override
    public BoardObserver add(BoardObserver item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into board_observer " +
                            "(fk_observer_oid, lu_membership_type, voting_date, statute_letter_no, statute_letter_date, " +
                            "communique_issuance_date, observation_start_date, observation_end_date, lu_how_to_elect, " +
                            "fk_board_period_oid) " +
                            "values " +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            fillPreparedStatement(ps, item);
            return ps;
        }, keyHolder);
        item.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return item;
    }

    public int edit(BoardObserver item) {
        return jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update board_observer set fk_observer_oid = ?, lu_membership_type = ?, voting_date = ?, " +
                            "statute_letter_no = ?, statute_letter_date = ?, communique_issuance_date = ?, " +
                            "observation_start_date = ?, observation_end_date = ?, lu_how_to_elect = ? " +
                            "where oid = ?"
            );
            fillPreparedStatement(ps, item);
            ps.setLong(10, item.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from board_observer where oid = ? ", oid);
    }

    @Override
    public int similarCount(BoardObserver item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from board_observer " +
                        "where " +
                        "oid <> ? and " +
                        "fk_board_period_oid = ? and " +
                        "fk_observer_oid = ? and " +
                        "( " +
                        "( (? between observation_start_date and observation_end_date) or (?  between observation_start_date and observation_end_date) )" +
                        " or " +
                        " (observation_start_date > ? and observation_end_date < ?)" +
                        ") ",
                new Object[]{item.getOid(), item.getFkBoardPeriodOid(), item.getFkObserverOid(),
                        Optional.ofNullable(item.getObservationStartDate().getGregorianDate()).orElse(null),
                        Optional.ofNullable(item.getObservationEndDate().getGregorianDate()).orElse(null),
                        Optional.ofNullable(item.getObservationStartDate().getGregorianDate()).orElse(null),
                        Optional.ofNullable(item.getObservationEndDate().getGregorianDate()).orElse(null)
                },
                Integer.class
        );
        return result != null ? result : -1;
    }

    @Override
    public String getObserverTitle(long oid) {
        return jdbcTemplate.queryForObject(
                "select concat(name, ' ', family) as title " +
                        "from board_observer " +
                        "left outer join parliament_member on parliament_member.oid = fk_observer_oid " +
                        "where board_observer.oid = ? ",
                new Object[]{oid},
                String.class);
    }

    @Override
    public boolean exists(long oid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from board_observer " +
                        "where oid = ?",
                new Object[]{oid},
                Integer.class
        );
        return result != null && result >= 1;
    }

}
