package ir.parliran.board;

import ir.parliran.global.Page;

import java.util.List;
import java.util.Optional;

public interface BoardPeriodRepository {
    List<BoardPeriod> findAll(long fkBoardOid);
    Page<BoardPeriod> findAll(BoardPeriodSearchCredential boardPeriodSearchCredential);
    Optional<BoardPeriod> findById(long oid);
    BoardPeriod add(BoardPeriod boardPeriod);
    int edit(BoardPeriod boardPeriod);
    int remove(long oid);

    int similarCount(long fkBoardOid, int luPeriodNo);

    boolean exists(long oid);
}
