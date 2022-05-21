package ir.parliran.session;

import java.util.List;

public interface SessionBoardJustificationReportAgendaRepository {
    List<Long> findAllBySessionOid(long fkSessionOid);
    int add(long fkSessionOid, List<Integer> agendaNumbers);
    void edit(long fkSessionOid, List<Integer> agendaNumbers);
    int remove(long fkSessionOid);
    boolean exists(long fkSessionOid, int agendaNo);
}
