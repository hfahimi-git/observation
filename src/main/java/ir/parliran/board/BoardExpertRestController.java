package ir.parliran.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@RestController ()
@RequestMapping("/board-period-rest/{boardPeriodOid}/expert")
public class BoardExpertRestController {

    private final BoardPeriodService boardPeriodService;
    private final BoardExpertService boardExpertService;

    @Autowired
    public BoardExpertRestController(BoardPeriodService boardPeriodService, BoardExpertService boardExpertService) {
        this.boardPeriodService = boardPeriodService;
        this.boardExpertService = boardExpertService;
    }

    @ModelAttribute
    private void setLookups(@PathVariable("boardPeriodOid") long boardPeriodOid) throws Exception {
        BoardPeriod boardPeriodItem = boardPeriodService.findById(boardPeriodOid).orElse(null);
        if (boardPeriodItem == null)
            throw new Exception("invalid board period");
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET,  produces = "application/json")
    public List<BoardExpert> index(@PathVariable("boardPeriodOid") int boardPeriodOid) {
        return boardExpertService.findAll(boardPeriodOid);
    }

    @RequestMapping(value = "/{oid}")
    public Optional<BoardExpert> show(@PathVariable("oid") long oid) {
        return boardExpertService.findById(oid);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public BoardExpert add(@Valid @RequestBody BoardExpert boardExpertItem) {
        return boardExpertService.add(boardExpertItem);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST,  produces = "application/json")
    public Map<String, Integer> remove(@PathVariable(value = "oid") int oid) {
        Map<String, Integer> result = new HashMap<>();
        result.put("result", boardExpertService.remove(oid));
        return result;
    }

}