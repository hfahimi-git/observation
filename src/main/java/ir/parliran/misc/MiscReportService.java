package ir.parliran.misc;

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
class MiscReportService {

    private final MiscReportRepository repository;
    private final BoardPeriodService boardPeriodService;

    @Autowired
    MiscReportService(MiscReportRepository repository, BoardPeriodService boardPeriodService) {
        this.repository = repository;
        this.boardPeriodService = boardPeriodService;
    }

    Page<MiscReport> findAll(SearchCredential searchCredential) {
        return repository.findAll(searchCredential);
    }

    Optional<MiscReport> findById(long oid) {
        return repository.findById(oid);
    }

    MiscReport add(MiscReport miscReport) {
        validation(miscReport);
        return repository.add(miscReport);
    }

    int edit(MiscReport miscReport) {
        validation(miscReport);
        return repository.edit(miscReport);
    }

    int remove(long oid) {
        return repository.remove(oid);
    }

    private void validation(MiscReport miscReport) {
        if (miscReport == null) {
            throw new RuntimeException("data_null");
        }

        if (!boardPeriodService.exists(miscReport.getFkBoardPeriodOid())) {
            throw new RuntimeException("fk_board_period_oid");
        }

        if (similarCount(miscReport) > 0) {
            throw new RuntimeException("duplicated_data");
        }

    }

    private int similarCount(MiscReport miscReport) {
        return repository.similarCount(miscReport);
    }

    public boolean exists(long oid) {
        return repository.exists(oid);
    }
}