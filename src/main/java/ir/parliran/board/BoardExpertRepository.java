package ir.parliran.board;

import java.util.List;
import java.util.Optional;

public interface BoardExpertRepository {
    List<BoardExpert> findAll(long fkBoardOid);
    Optional<BoardExpert> findById(long oid);
    BoardExpert add(BoardExpert boardExpert);
    int edit(BoardExpert boardExpert);
    int remove(int oid);

    int similarCount(BoardExpert boardExpert);
}
