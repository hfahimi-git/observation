package ir.parliran.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@RestController ()
@RequestMapping("/board-period-rest/{boardPeriodOid}/observer")
public class BoardObserverRestController {

    private final BoardPeriodService boardPeriodService;
    private final BoardObserverService boardObserverService;

    @Autowired
    public BoardObserverRestController(BoardPeriodService boardPeriodService, BoardObserverService boardObserverService) {
        this.boardPeriodService = boardPeriodService;
        this.boardObserverService = boardObserverService;
    }

    @ModelAttribute
    private void setLookups(@PathVariable("boardPeriodOid") long boardPeriodOid) throws Exception {
        BoardPeriod boardPeriodItem = boardPeriodService.findById(boardPeriodOid).orElse(null);
        if (boardPeriodItem == null)
            throw new Exception("invalid board period");
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET,  produces = "application/json")
    public List<BoardObserver> index(@PathVariable("boardPeriodOid") int boardPeriodOid) {
        return boardObserverService.findAll(boardPeriodOid);
    }

    @RequestMapping(value = "/{oid}")
    public Optional<BoardObserver> show(@PathVariable("oid") long oid) {
        return boardObserverService.findById(oid);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public BoardObserver add(@Valid @RequestBody BoardObserver boardObserverItem) {
        return boardObserverService.add(boardObserverItem);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST,  produces = "application/json")
    public Map<String, Integer> remove(@PathVariable(value = "oid") int oid) {
        Map<String, Integer> result = new HashMap<>();
        result.put("result", boardObserverService.remove(oid));
        return result;
    }

}