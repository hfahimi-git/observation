package ir.parliran.session;

import java.util.List;
import java.util.Optional;

public interface SessionDeputyAnalyticalReportRepository {
    List<SessionDeputyAnalyticalReport> findAll(long oid);

    Optional<SessionDeputyAnalyticalReport> findById(long oid);

    SessionDeputyAnalyticalReport add(SessionDeputyAnalyticalReport sessionDeputyAnalyticalReport);

    int edit(SessionDeputyAnalyticalReport sessionDeputyAnalyticalReport);

    int remove(long oid);

    int similarCount(SessionDeputyAnalyticalReport sessionDeputyAnalyticalReport);

    boolean exists(SessionDeputyAnalyticalReport item);

    int removeFile(long oid);

    boolean exists(long oid);
}
