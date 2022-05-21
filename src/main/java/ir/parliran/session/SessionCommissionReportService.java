package ir.parliran.session;

import ir.parliran.board.BoardCommissionService;
import ir.parliran.global.Labels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
class SessionCommissionReportService {

    private final SessionCommissionReportRepository repository;
    private final SessionService sessionService;
    private final BoardCommissionService boardCommissionService;
    private final SessionObserverReportService observerReportService;
    private final Labels labels;

    @Autowired
    SessionCommissionReportService(SessionCommissionReportRepository repository, SessionService sessionService,
                                   BoardCommissionService boardCommissionService, SessionObserverReportService observerReportService, Labels labels) {
        this.repository = repository;
        this.sessionService = sessionService;
        this.boardCommissionService = boardCommissionService;
        this.observerReportService = observerReportService;
        this.labels = labels;
    }

    List<SessionCommissionReport> findAll(long fkSessionOid) {
        List<SessionCommissionReport> result = repository.findAll(fkSessionOid);
        for (SessionCommissionReport row: result) {
            Optional<SessionObserverReport> o = observerReportService.findById(row.getFkObserverReportOid());
            o.ifPresent(or -> row.setFkObserverReportOidTitle(or.getFkBoardObserverOidTitle()));
            row.setFkBoardCommissionOidTitle(boardCommissionService.getTitle(row.getFkBoardCommissionOid()));
        }
        return result;
    }

    Optional<SessionCommissionReport> findById(long oid) {
        Optional<SessionCommissionReport> result = repository.findById(oid);
        if(result.isPresent()) {
            Optional<SessionObserverReport> o = observerReportService.findById(result.get().getFkObserverReportOid());
            o.ifPresent(or -> result.get().setFkObserverReportOidTitle(or.getFkBoardObserverOidTitle()));
            result.get().setFkBoardCommissionOidTitle(boardCommissionService.getTitle(result.get().getFkBoardCommissionOid()));
        }
        return result;
    }

    SessionCommissionReport add(SessionCommissionReport report) {
        validation(report);
        if (exists(report)) {
            throw new RuntimeException(labels.get("duplicated_data"));
        }
        return repository.add(report);
    }

    boolean exists(SessionCommissionReport report) {
        return repository.exists(report);
    }

    int edit(SessionCommissionReport report) {
        if(!exists(report.getOid())) report = null;
        validation(report);
        return repository.edit(report);
    }

    private boolean exists(long oid) {
        return repository.exists(oid);
    }

    int remove(long oid) {
        return repository.remove(oid);
    }

    private void validation(SessionCommissionReport report) {
        Optional.ofNullable(report).orElseThrow(()->new RuntimeException(labels.get("data_null")));

        if (!observerReportService.exists(report.getFkObserverReportOid())) {
            throw new RuntimeException(labels.get("fk_observer_report_oid"));
        }

        if (!boardCommissionService.exists(report.getFkBoardCommissionOid())) {
            throw new RuntimeException(labels.get("fk_board_commission_oid"));
        }

        if (!sessionService.exists(report.getFkSessionOid())) {
            throw new RuntimeException(labels.get("fk_session_oid}"));
        }

        if (repository.similarCount(report) > 0) {
            throw new RuntimeException(labels.get("duplicated_data"));
        }

    }

    public int removeFile(long oid) {
        return repository.removeFile(oid);
    }
}