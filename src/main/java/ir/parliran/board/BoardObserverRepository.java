package ir.parliran.board;

import ir.parliran.global.DatePair;

import java.util.List;
import java.util.Optional;

public interface BoardObserverRepository {
    List<BoardObserver> findAll(long fkBoardOid);

    Optional<BoardObserver> findById(long oid);

    BoardObserver add(BoardObserver boardObserver);

    int edit(BoardObserver boardObserver);

    int remove(long oid);

    int similarCount(BoardObserver boardObserver);

    String getObserverTitle(long oid);

    boolean exists(long oid);

    List<BoardObserver> findAll(DatePair sessionDate, long fkBoardPeriodOid);

    List<Long> findCurrentObservers(DatePair sessionDate, long fkBoardPeriodOid);
}
