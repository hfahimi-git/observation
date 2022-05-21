package ir.parliran.session;

import ir.parliran.board.BoardPeriodService;
import ir.parliran.global.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
class SessionService {

    private final SessionRepository repository;
    private final BoardPeriodService boardPeriodService;

    @Autowired
    SessionService(SessionRepository repository, BoardPeriodService boardPeriodService) {
        this.repository = repository;
        this.boardPeriodService = boardPeriodService;
    }

    List<Session> findAll(long fkBoardPeriodOid) {
        return repository.findAll(fkBoardPeriodOid);
    }

    Page<Session> findAll(SearchCredential searchCredential) {
        return repository.findAll(searchCredential);
    }

    Optional<Session> findById(long oid) {
        return repository.findById(oid);
    }

    Session add(Session session) {
        validation(session);
        return repository.add(session);
    }

    int edit(Session session) {
        validation(session);
        return repository.edit(session);
    }

    int remove(long oid) {
        return repository.remove(oid);
    }

    private void validation(Session session) {
        if (session == null) {
            throw new RuntimeException("data_null");
        }

        if (!boardPeriodService.exists(session.getFkBoardPeriodOid())) {
            throw new RuntimeException("fk_board_period_oid");
        }

        if (similarCount(session) > 0) {
            throw new RuntimeException("duplicated_data");
        }

    }

    private int similarCount(Session session) {
        return repository.similarCount(session);
    }

    public boolean exists(long oid) {
        return repository.exists(oid);
    }
}