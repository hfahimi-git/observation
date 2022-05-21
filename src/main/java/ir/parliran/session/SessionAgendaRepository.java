package ir.parliran.session;

import java.util.List;
import java.util.Optional;

public interface SessionAgendaRepository {
    List<SessionAgenda> findAll(long fkSessionOid);

    Optional<SessionAgenda> findById(long oid);

    SessionAgenda add(SessionAgenda sessionAgenda);

    int edit(SessionAgenda sessionAgenda);

    int remove(long oid);

    int similarCount(SessionAgenda sessionAgenda);

    boolean exists(SessionAgenda item);

    boolean exists(long fkSessionOid, int orderby);
}
