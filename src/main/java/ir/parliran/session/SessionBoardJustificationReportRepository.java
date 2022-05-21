package ir.parliran.session;

import java.util.Optional;

public interface SessionBoardJustificationReportRepository {
    Optional<SessionBoardJustificationReport> findBySessionOid(long fkSessionOid);
    SessionBoardJustificationReport add(SessionBoardJustificationReport sessionBoardJustificationReport);
    int edit(SessionBoardJustificationReport sessionBoardJustificationReport);
    int remove(long oid);
    int similarCount(SessionBoardJustificationReport sessionBoardJustificationReport);

    boolean exists(long fkSessionOid);

    int removeFile(long fkSessionOid);
}
