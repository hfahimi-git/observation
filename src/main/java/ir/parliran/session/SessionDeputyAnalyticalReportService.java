package ir.parliran.session;

import ir.parliran.global.Labels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
class SessionDeputyAnalyticalReportService {

    private final SessionDeputyAnalyticalReportRepository repository;
    private final SessionService sessionService;
    private final SessionCommissionReportService commissionReportService;
    private final Labels labels;

    @Autowired
    SessionDeputyAnalyticalReportService(SessionDeputyAnalyticalReportRepository repository, SessionService sessionService,
                                         SessionCommissionReportService commissionReportService, Labels labels) {
        this.repository = repository;
        this.sessionService = sessionService;
        this.commissionReportService = commissionReportService;
        this.labels = labels;
    }

    List<SessionDeputyAnalyticalReport> findAll(long fkSessionOid) {
        List<SessionDeputyAnalyticalReport> result = repository.findAll(fkSessionOid);
        for (SessionDeputyAnalyticalReport row: result) {
            Optional<SessionCommissionReport> o = commissionReportService.findById(row.getFkCommissionReportOid());
            o.ifPresent(or -> row.setFkCommissionReportOidTitle(or.getFkBoardCommissionOidTitle() + " - " + or.getFkObserverReportOidTitle()));
        }
        return result;
    }

    Optional<SessionDeputyAnalyticalReport> findById(long oid) {
        Optional<SessionDeputyAnalyticalReport> result = repository.findById(oid);
        if(result.isPresent()) {
            Optional<SessionCommissionReport> o = commissionReportService.findById(result.get().getFkCommissionReportOid());
            o.ifPresent(or -> result.get().setFkCommissionReportOidTitle(or.getFkBoardCommissionOidTitle() + " - " + or.getFkObserverReportOidTitle()));
        }
        return result;
    }

    SessionDeputyAnalyticalReport add(SessionDeputyAnalyticalReport report) {
        validation(report);
        if (exists(report)) {
            throw new RuntimeException(labels.get("duplicated_data"));
        }
        return repository.add(report);
    }

    boolean exists(SessionDeputyAnalyticalReport report) {
        return repository.exists(report);
    }

    int edit(SessionDeputyAnalyticalReport report) {
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

    private void validation(SessionDeputyAnalyticalReport report) {
        Optional.ofNullable(report).orElseThrow(()->new RuntimeException(labels.get("data_null")));

        if (!sessionService.exists(report.getFkSessionOid())) {
            throw new RuntimeException(labels.get("fk_session_oid"));
        }

        if (repository.similarCount(report) > 0) {
            throw new RuntimeException(labels.get("duplicated_data"));
        }

    }

    public int removeFile(long oid) {
        return repository.removeFile(oid);
    }
}