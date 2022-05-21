package ir.parliran.session;

import java.util.Optional;

public interface SessionDeputyJustificationReportRepository {
    Optional<SessionDeputyJustificationReport> findBySessionOid(long fkSessionOid);
    SessionDeputyJustificationReport add(SessionDeputyJustificationReport sessionDeputyJustificationReport);
    int edit(SessionDeputyJustificationReport sessionDeputyJustificationReport);
    int remove(long oid);
    int similarCount(SessionDeputyJustificationReport sessionDeputyJustificationReport);

    boolean exists(long fkSessionOid);

    int removeFile(long fkSessionOid);
}
