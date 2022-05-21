package ir.parliran.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@RestController ()
@RequestMapping("/board-period-rest/{boardPeriodOid}/commission")
public class BoardCommissionRestController {

    private final BoardPeriodService boardPeriodService;
    private final BoardCommissionService boardCommissionService;

    @Autowired
    public BoardCommissionRestController(BoardPeriodService boardPeriodService, BoardCommissionService boardCommissionService) {
        this.boardPeriodService = boardPeriodService;
        this.boardCommissionService = boardCommissionService;
    }

    @ModelAttribute
    private void setLookups(@PathVariable("boardPeriodOid") long boardPeriodOid) throws Exception {
        BoardPeriod boardPeriodItem = boardPeriodService.findById(boardPeriodOid).orElse(null);
        if (boardPeriodItem == null)
            throw new Exception("invalid board period");
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET,  produces = "application/json")
    public List<BoardCommission> index(@PathVariable("boardPeriodOid") long boardPeriodOid) {
        return boardCommissionService.findAll(boardPeriodOid);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public BoardCommission add(@Valid @RequestBody BoardCommission boardCommissionItem) {
        return boardCommissionService.add(boardCommissionItem);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST,  produces = "application/json")
    public Map<String, Integer> remove(@PathVariable(value = "oid") long oid) {
        Map<String, Integer> result = new HashMap<>();
        result.put("result", boardCommissionService.remove(oid));
        return result;
    }

}