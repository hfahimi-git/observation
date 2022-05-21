package ir.parliran.board;

import ir.parliran.global.DatePair;
import ir.parliran.pm.ParliamentMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
public class BoardObserverService {

    private final BoardObserverRepository repository;
    private final BoardPeriodService boardPeriodService;
    private final ParliamentMemberService parliamentMemberService;

    @Autowired
    BoardObserverService(BoardObserverRepository repository, BoardPeriodService boardPeriodService, ParliamentMemberService parliamentMemberService) {
        this.repository = repository;
        this.boardPeriodService = boardPeriodService;
        this.parliamentMemberService = parliamentMemberService;
    }

    public List<BoardObserver> findAll(long fkBoardPeriodOid) {
        return repository.findAll(fkBoardPeriodOid);
    }

    public List<BoardObserver> findAll(DatePair sessionDate, long fkBoardPeriodOid) {
        return repository.findAll(sessionDate, fkBoardPeriodOid);
    }

    public List<Long> findCurrentObserver(DatePair sessionDate, long fkBoardPeriodOid) {
        return repository.findCurrentObservers(sessionDate, fkBoardPeriodOid);
    }

    Optional<BoardObserver> findById(long oid) {
        return repository.findById(oid);
    }

    public String getTitle(long oid) {
        return repository.getObserverTitle(oid);
    }

    BoardObserver add(BoardObserver boardObserver) {
        if(similarCount(boardObserver) > 0) {
            throw new RuntimeException("duplicated data");
        }
        return repository.add(boardObserver);
    }

    int edit(BoardObserver boardObserver) {
        Optional<BoardObserver> oldItem = findById(boardObserver.getOid());
        if(oldItem.isEmpty())
            return 0;
        if(similarCount(boardObserver) > 0 ) {
            throw new RuntimeException("duplicated data");
        }
        return repository.edit(boardObserver);
    }

    int remove(long oid) {
        return repository.remove(oid);
    }

    private void validation(BoardObserver boardObserver) {
        if(boardObserver == null)
            throw new RuntimeException("data_null");

        if(!boardPeriodService.exists(boardObserver.getFkBoardPeriodOid()))
            throw new RuntimeException("fk_board_period_oid");

        if(!parliamentMemberService.exists(boardObserver.getFkObserverOid()))
            throw new RuntimeException("fk_observer_oid");

        if (similarCount(boardObserver) > 0) {
            throw new RuntimeException("duplicated_data");
        }

    }

    private int similarCount (BoardObserver boardObserver) {
        return repository.similarCount(boardObserver);
    }

    public boolean exists(long oid) {
        return repository.exists(oid);
    }
}
