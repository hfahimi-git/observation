package ir.parliran.session;

import java.util.Optional;

public interface SessionBoardReportRepository {
    Optional<SessionBoardReport> findBySessionOid(long fkSessionOid);
    SessionBoardReport add(SessionBoardReport sessionBoardReport);
    int edit(SessionBoardReport sessionBoardReport);
    int remove(long oid);
    int similarCount(SessionBoardReport sessionBoardReport);

    boolean exists(long fkSessionOid);

    int removeFile(long fkSessionOid);
}
