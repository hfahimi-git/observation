package ir.parliran.session;

import java.util.List;
import java.util.Optional;

public interface SessionObserverReportRepository {
    List<SessionObserverReport> findAll(long fkSessionOid);

    Optional<SessionObserverReport> findById(long oid);

    SessionObserverReport add(SessionObserverReport sessionObserverReport);

    int edit(SessionObserverReport sessionObserverReport);

    int remove(long oid);

    int similarCount(SessionObserverReport sessionObserverReport);

    boolean exists(SessionObserverReport sessionObserverReport);

    int removeFile(long fkSessionOid);

    boolean exists(long oid);
}
