package ir.parliran.board;

import ir.parliran.global.Page;
import ir.parliran.global.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@RestController ()
@RequestMapping("/board-period-rest")
public class BoardPeriodRestController {

    private final BoardPeriodService boardPeriodService;
    @Value("${paging.page-size:10}")
    private int pageSize;

    @Autowired
    public BoardPeriodRestController(BoardPeriodService boardPeriodService) {
        this.boardPeriodService = boardPeriodService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public Page<BoardPeriod> index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "lu_period_no", required = false) String luPeriodNo,
            @RequestParam(value = "page", required = false, defaultValue = "1") String pageNo
    ) {
        return boardPeriodService.findAll(
                BoardPeriodSearchCredential.builder()
                .keyword(keyword)
                .luPeriodNo(Utils.getInt(luPeriodNo, 0))
                .pageNo(Utils.getInt(pageNo, 1))
                .pageSize(pageSize)
                .build()
        );
    }

}