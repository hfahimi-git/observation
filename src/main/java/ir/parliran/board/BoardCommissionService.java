package ir.parliran.board;

import ir.parliran.global.LookupGroupKey;
import ir.parliran.lookup.LookupService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
public class BoardCommissionService {

    private final BoardCommissionRepository repository;
    private final BoardPeriodService boardPeriodService;
    private final LookupService lookupService;

    @Autowired
    BoardCommissionService(BoardCommissionRepository repository, BoardPeriodService boardPeriodService, LookupService lookupService) {
        this.repository = repository;
        this.boardPeriodService = boardPeriodService;
        this.lookupService = lookupService;
    }

    public List<BoardCommission> findAll(long fkBoardOid) {
        return repository.findAll(fkBoardOid);
    }

    Optional<BoardCommission> findById(long oid) {
        return repository.findById(oid);
    }

    public String getTitle(long oid) {
        return repository.getTitle(oid);
    }

    BoardCommission add(BoardCommission boardCommission) {
        validation(boardCommission);
        return repository.add(boardCommission);
    }

    int edit(BoardCommission boardCommission) {
        validation(boardCommission);
        return repository.edit(boardCommission);
    }

    private void validation(BoardCommission boardCommission) {
        if(boardCommission == null)
            throw new RuntimeException("data_null");

        if(lookupService.findByGroupKeyAndKey(LookupGroupKey.commission.name(), boardCommission.getLuCommission()).isEmpty())
            throw new RuntimeException("lu_commission");

        if (!boardPeriodService.exists(boardCommission.getFkBoardPeriodOid()))
            throw new RuntimeException("fk_board_period_oid");

        if (similarCount(boardCommission) > 0)
            throw new RuntimeException("duplicated_data");
    }

    int remove(long oid) {
        return repository.remove(oid);
    }

    private int similarCount(BoardCommission boardCommission) {
        return repository.similarCount(boardCommission);
    }

    public boolean exists(long oid) {
        return repository.exists(oid);
    }

}
