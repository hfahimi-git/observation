package ir.parliran.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
class SessionBoardReportService {

    private final SessionBoardReportRepository repository;
    private final SessionService sessionService;

    @Autowired
    SessionBoardReportService(SessionBoardReportRepository repository, SessionService sessionService) {
        this.repository = repository;
        this.sessionService = sessionService;
    }

    Optional<SessionBoardReport> findBySessionOid(long fkSessionOid) {
        return repository.findBySessionOid(fkSessionOid);
    }

    SessionBoardReport add(SessionBoardReport report) {
        validation(report);
        if (exists(report.getFkSessionOid())) {
            throw new RuntimeException("duplicated_data");
        }

        return repository.add(report);
    }

    boolean exists(long fkSessionOid) {
        return repository.exists(fkSessionOid);
    }

    int edit(SessionBoardReport report) {
        validation(report);
        return repository.edit(report);
    }

    int remove(long fkSessionOid) {
        return repository.remove(fkSessionOid);
    }

    private void validation(SessionBoardReport report) {
        Optional.ofNullable(report).orElseThrow(()->new RuntimeException("data_null"));

        if (!sessionService.exists(report.getFkSessionOid())) {
            throw new RuntimeException("fk_session_oid");
        }

        if (repository.similarCount(report) > 0) {
            throw new RuntimeException("duplicated_data");
        }

    }

    public int removeFile(long fkSessionOid) {
        return repository.removeFile(fkSessionOid);
    }
}