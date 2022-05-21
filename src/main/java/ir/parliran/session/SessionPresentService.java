package ir.parliran.session;

import ir.parliran.board.BoardObserverService;
import ir.parliran.board.BoardPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
class SessionPresentService {

    private final SessionPresentRepository repository;
    private final SessionService sessionService;
    private final BoardObserverService boardObserverService;

    @Autowired
    SessionPresentService(SessionPresentRepository repository, SessionService sessionService,
                          BoardObserverService boardObserverService) {
        this.repository = repository;
        this.sessionService = sessionService;
        this.boardObserverService = boardObserverService;
    }

    List<Long> findAll(long fkSessionOid) {
        return repository.findAll(fkSessionOid);
    }

    int add(long fkSessionOid, List<Long> presents) {
        checkPresents(fkSessionOid, presents);
        return repository.add(fkSessionOid, presents);
    }

    void edit(long fkSessionOid, List<Long> presents) {
        checkPresents(fkSessionOid, presents);
        repository.edit(fkSessionOid, presents);
    }

    void checkPresents(long fkSessionOid, List<Long> presents) {
        Session session = sessionService.findById(fkSessionOid).orElseThrow(()->new RuntimeException("invalid_session"));

        List<Long> currentObservers = boardObserverService.findCurrentObserver(session.getDate(), session.getFkBoardPeriodOid());
        if(currentObservers == null || currentObservers.size() < 1) {
            throw new RuntimeException("no_observers");
        }

        for (Long present: presents) {
            if(!currentObservers.contains(present)){
                throw new RuntimeException("invalid_observer : #" + present);
            }
        }
    }

    int remove(long fkSessionOid) {
        return repository.remove(fkSessionOid);
    }

    boolean exists(long fkSessionOid, long fkObserverOid) {
        return repository.exists(fkSessionOid, fkObserverOid);
    }

}