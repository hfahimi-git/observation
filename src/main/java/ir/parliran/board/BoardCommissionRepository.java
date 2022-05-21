package ir.parliran.board;

import java.util.List;
import java.util.Optional;

public interface BoardCommissionRepository {
    List<BoardCommission> findAll(long fkBoardPeriodOid);
    Optional<BoardCommission> findById(long oid);
    BoardCommission add(BoardCommission boardCommission);
    int edit(BoardCommission boardCommission);
    int remove(long oid);

    int similarCount(BoardCommission boardCommission);

    boolean exists(long oid);

    String getTitle(long oid);
}
