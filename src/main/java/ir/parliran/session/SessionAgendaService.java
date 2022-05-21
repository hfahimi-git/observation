package ir.parliran.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
class SessionAgendaService {

    private final SessionAgendaRepository repository;
    private final SessionService sessionService;

    @Autowired
    SessionAgendaService(SessionAgendaRepository repository, SessionService sessionService) {
        this.repository = repository;
        this.sessionService = sessionService;
    }

    List<SessionAgenda> findAll(long fkSessionOid) {
        return repository.findAll(fkSessionOid);
    }

    Optional<SessionAgenda> findById(long oid) {
        return repository.findById(oid);
    }

    SessionAgenda add(SessionAgenda sessionAgenda) {
        validation(sessionAgenda);
        return repository.add(sessionAgenda);
    }

    int edit(SessionAgenda sessionAgenda) {
        validation(sessionAgenda);
        return repository.edit(sessionAgenda);
    }

    int remove(int oid) {
        return repository.remove(oid);
    }

    private void validation(SessionAgenda sessionAgenda) {
        if (sessionAgenda == null) {
            throw new RuntimeException("data_null");
        }

        if (!sessionService.exists(sessionAgenda.getFkSessionOid())) {
            throw new RuntimeException("fk_session_oid");
        }

        if (similarCount(sessionAgenda) > 0) {
            throw new RuntimeException("duplicated_data");
        }

    }

    private int similarCount(SessionAgenda sessionAgenda) {
        return repository.similarCount(sessionAgenda);
    }

}
