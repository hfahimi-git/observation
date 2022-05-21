package ir.parliran.session;

import ir.parliran.board.BoardObserverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
class SessionObserverReportService {

    private final SessionObserverReportRepository repository;
    private final SessionService sessionService;
    private final BoardObserverService boardObserverService;

    @Autowired
    SessionObserverReportService(SessionObserverReportRepository repository, SessionService sessionService, BoardObserverService boardObserverService) {
        this.repository = repository;
        this.sessionService = sessionService;
        this.boardObserverService = boardObserverService;
    }

    List<SessionObserverReport> findAll(long fkSessionOid) {
        List<SessionObserverReport> result = repository.findAll(fkSessionOid);
        result.forEach(e -> e.setFkBoardObserverOidTitle(boardObserverService.getTitle(e.getFkBoardObserverOid())));
        return result;
    }

    Optional<SessionObserverReport> findById(long oid) {
        Optional<SessionObserverReport> result = repository.findById(oid);
        result.ifPresent(e -> e.setFkBoardObserverOidTitle(boardObserverService.getTitle(e.getFkBoardObserverOid())));
        return result;
    }

    SessionObserverReport add(SessionObserverReport report) {
        validation(report);
        if (exists(report)) {
            throw new RuntimeException("duplicated_data");
        }

        return repository.add(report);
    }

    boolean exists(SessionObserverReport report) {
        return repository.exists(report);
    }

    boolean exists(long oid) {
        return repository.exists(oid);
    }

    int edit(SessionObserverReport report) {
        if(!exists(report.getOid())) report = null;
        Optional.ofNullable(report).orElseThrow(()->new RuntimeException("data_null"));
        validation(report);
        return repository.edit(report);
    }

    int remove(long oid) {
        return repository.remove(oid);
    }

    private void validation(SessionObserverReport report) {
        Optional.ofNullable(report).orElseThrow(()->new RuntimeException("data_null"));

        if (!sessionService.exists(report.getFkSessionOid())) {
            throw new RuntimeException("fk_session_oid");
        }

        if (!boardObserverService.exists(report.getFkBoardObserverOid())) {
            throw new RuntimeException("fk_session_oid");
        }

        if (repository.similarCount(report) > 0) {
            throw new RuntimeException("duplicated_data");
        }

    }

    public int removeFile(long oid) {
        return repository.removeFile(oid);
    }
}