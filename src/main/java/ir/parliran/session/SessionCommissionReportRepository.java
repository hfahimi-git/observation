package ir.parliran.session;

import java.util.List;
import java.util.Optional;

public interface SessionCommissionReportRepository {
    List<SessionCommissionReport> findAll(long fkSessionOid);

    Optional<SessionCommissionReport> findById(long oid);

    SessionCommissionReport add(SessionCommissionReport sessionCommissionReport);

    int edit(SessionCommissionReport sessionCommissionReport);

    int remove(long oid);

    int similarCount(SessionCommissionReport sessionCommissionReport);

    boolean exists(SessionCommissionReport item);

    int removeFile(long fkSessionOid);

    boolean exists(long oid);
}
