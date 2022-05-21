package ir.parliran.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
class SessionDeputyJustificationReportService {

    private final SessionDeputyJustificationReportRepository repository;
    private final SessionService sessionService;

    @Autowired
    SessionDeputyJustificationReportService(SessionDeputyJustificationReportRepository repository, SessionService sessionService) {
        this.repository = repository;
        this.sessionService = sessionService;
    }

    Optional<SessionDeputyJustificationReport> findBySessionOid(long fkSessionOid) {
        return repository.findBySessionOid(fkSessionOid);
    }

    SessionDeputyJustificationReport add(SessionDeputyJustificationReport report) {
        validation(report);
        if (exists(report.getFkSessionOid())) {
            throw new RuntimeException("duplicated_data");
        }

        return repository.add(report);
    }

    boolean exists(long fkSessionOid) {
        return repository.exists(fkSessionOid);
    }

    int edit(SessionDeputyJustificationReport report) {
        validation(report);
        return repository.edit(report);
    }

    int remove(long fkSessionOid) {
        return repository.remove(fkSessionOid);
    }

    private void validation(SessionDeputyJustificationReport report) {
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