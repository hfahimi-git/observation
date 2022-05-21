package ir.parliran.session;

import java.util.List;

interface SessionPresentRepository {

    int add(long fkSessionOid, List<Long> presents);

    void edit(long fkSessionOid, List<Long> presents);

    int remove(long fkSessionOid);

    boolean exists(long fkSessionOid, long fkObserverOid);

    List<Long> findAll(long fkBoardReportOid);
}
