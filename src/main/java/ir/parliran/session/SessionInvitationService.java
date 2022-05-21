package ir.parliran.session;

import ir.parliran.board.BoardPeriodService;
import ir.parliran.global.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
class SessionInvitationService {

    private final SessionInvitationRepository repository;
    private final SessionService sessionService;

    @Autowired
    SessionInvitationService(SessionInvitationRepository repository, SessionService sessionService) {
        this.repository = repository;
        this.sessionService = sessionService;
    }

    Optional<SessionInvitation> findBySessionOid(long fkSessionOid) {
        return repository.findBySessionOid(fkSessionOid);
    }

    SessionInvitation add(SessionInvitation sessionInvitation) {
        validation(sessionInvitation);
        if (exists(sessionInvitation.getFkSessionOid())) {
            throw new RuntimeException("duplicated_data");
        }

        return repository.add(sessionInvitation);
    }

    boolean exists(long fkSessionOid) {
        return repository.exists(fkSessionOid);
    }

    int edit(SessionInvitation sessionInvitation) {
        validation(sessionInvitation);
        return repository.edit(sessionInvitation);
    }

    int remove(long fkSessionOid) {
        return repository.remove(fkSessionOid);
    }

    private void validation(SessionInvitation sessionInvitation) {
        if (sessionInvitation == null) {
            throw new RuntimeException("data_null");
        }

        if (!sessionService.exists(sessionInvitation.getFkSessionOid())) {
            throw new RuntimeException("fk_session_oid");
        }

        if (repository.similarCount(sessionInvitation) > 0) {
            throw new RuntimeException("duplicated_data");
        }

    }

}