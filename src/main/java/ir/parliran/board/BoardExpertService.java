package ir.parliran.board;

import ir.parliran.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
class BoardExpertService {

    private final BoardExpertRepository repository;
    private final BoardPeriodService boardPeriodService;
    private final UserService userService;

    @Autowired
    BoardExpertService(BoardExpertRepository repository, BoardPeriodService boardPeriodService, UserService userService) {
        this.repository = repository;
        this.boardPeriodService = boardPeriodService;
        this.userService = userService;
    }

    List<BoardExpert> findAll(long fkBoardPeriodOid) {
        return repository.findAll(fkBoardPeriodOid);
    }

    Optional<BoardExpert> findById(long oid) {
        return repository.findById(oid);
    }

    BoardExpert add(BoardExpert boardExpert) {
        validation(boardExpert);
        return repository.add(boardExpert);
    }

    int edit(BoardExpert boardExpert) {
        validation(boardExpert);
        return repository.edit(boardExpert);
    }

    int remove(int oid) {
        return repository.remove(oid);
    }

    private void validation(BoardExpert boardExpert) {
        if (boardExpert == null) {
            throw new RuntimeException("data_null");
        }

        if (!boardPeriodService.exists(boardExpert.getFkBoardPeriodOid())) {
            throw new RuntimeException("fk_board_period_oid");
        }

        if (boardExpert.getStartDate() != null &&
                boardExpert.getEndDate() != null &&
                boardExpert.getStartDate().getGregorianDate() != null &&
                boardExpert.getEndDate().getGregorianDate() != null &&
                boardExpert.getEndDate().getGregorianDate().isBefore(boardExpert.getStartDate().getGregorianDate())
        ) {
            throw new RuntimeException("end_date_is_before_start_date");
        }

        if (!userService.exists(boardExpert.getFkUserOid())) {
            throw new RuntimeException("fk_user_oid");
        }

        if (similarCount(boardExpert) > 0) {
            throw new RuntimeException("duplicated_data");
        }

    }

    private int similarCount(BoardExpert boardExpert) {
        return repository.similarCount(boardExpert);
    }

}
