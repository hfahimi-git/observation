package ir.parliran.board;

import ir.parliran.global.Labels;
import ir.parliran.global.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
public class BoardPeriodService {

    private final BoardPeriodRepository repository;
    private final Labels labels;

    @Autowired
    BoardPeriodService(BoardPeriodRepository repository, Labels labels) {
        this.repository = repository;
        this.labels = labels;
    }

    List<BoardPeriod> findAll(long fkBoardOid) {
        return repository.findAll(fkBoardOid);
    }

    Page<BoardPeriod> findAll(BoardPeriodSearchCredential boardPeriodSearchCredential) {
        return repository.findAll(boardPeriodSearchCredential);
    }

    public Optional<BoardPeriod> findById(long oid) {
        return repository.findById(oid);
    }

    public boolean exists(long oid) {
        return repository.exists(oid);
    }

    BoardPeriod add(BoardPeriod boardPeriod) {
        if(similarCount(boardPeriod.getFkBoardOid(), boardPeriod.getLuPeriodNo()) > 0) {
            throw new RuntimeException(labels.get("luPeriodNo"));
        }
        return repository.add(boardPeriod);
    }

    int edit(BoardPeriod boardPeriod) {
        Optional<BoardPeriod> oldItem = findById(boardPeriod.getOid());
        if(oldItem.isEmpty())
            return 0;
        if(
                !oldItem.get().getLuPeriodNo().equals(boardPeriod.getLuPeriodNo()) &&
                        similarCount(boardPeriod.getFkBoardOid(), boardPeriod.getLuPeriodNo()) > 0
        ) {
            throw new RuntimeException(labels.get("luPeriodNo"));
        }
        return repository.edit(boardPeriod);
    }

    int remove(long oid) {
        return repository.remove(oid);
    }

    private int similarCount (long fkBoardOid, int luPeriodNo) {
        return repository.similarCount(fkBoardOid, luPeriodNo);
    }

}
