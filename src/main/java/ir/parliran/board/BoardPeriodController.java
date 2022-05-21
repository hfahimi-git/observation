package ir.parliran.board;

import ir.parliran.global.Labels;
import ir.parliran.global.LookupGroupKey;
import ir.parliran.lookup.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@Controller()
@RequestMapping("/board-period")
public class BoardPeriodController {

    private final BoardService boardService;
    private final BoardPeriodService boardPeriodService;
    private final LookupService lookupService;
    private final Labels labels;

    @Autowired
    public BoardPeriodController(BoardService boardService, BoardPeriodService boardPeriodService, LookupService lookupService, Labels labels) {
        this.boardService = boardService;
        this.boardPeriodService = boardPeriodService;
        this.lookupService = lookupService;
        this.labels = labels;
    }

    @ModelAttribute
    private void setLookups(@RequestParam("board_oid") long boardOid, Model model) throws Exception {
        model.addAttribute("periodItems", lookupService.findByGroupKeyOrderBy(
                LookupGroupKey.parliament_period.name(), "orderby desc")
        );

        Board boardItem = boardService.findById(boardOid).orElse(null);
        if (boardItem == null)
            throw new Exception("invalid board");

        model.addAttribute("boardItem", boardItem);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {

        ModelAndView mav = new ModelAndView("board/period.index");
        mav.addObject("boardPeriodItems", boardPeriodService.findAll(
                ((Board) Objects.requireNonNull(model.getAttribute("boardItem"))).getOid()
        ));
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("/board/period.add-edit", "boardPeriodItem", new BoardPeriod());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid @ModelAttribute("boardPeriodItem") BoardPeriod boardPeriodItem, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/board/period.add-edit", bindingResult.getModel());
        }

        try {
            boardPeriodService.add(boardPeriodItem);
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("boardPeriodItem", ex.getMessage(), labels.get("lu_period_no_duplicated")));
            return new ModelAndView("/board/period.add-edit", bindingResult.getModel());
        }

        return new ModelAndView("redirect:/board-period?board_oid=" + boardPeriodItem.getFkBoardOid());
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("oid") long oid) {
        BoardPeriod item = boardPeriodService.findById(oid).orElse(null);
        if (item == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/board/period.add-edit", "boardPeriodItem", item);
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable(value = "oid") long oid,
                             @Valid @ModelAttribute("boardPeriodItem") BoardPeriod boardPeriodItem,
                             BindingResult bindingResult) {

        BoardPeriod item = boardPeriodService.findById(oid).orElse(null);
        if (item == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/board/period.add-edit", bindingResult.getModel());
        }

        try {
            boardPeriodService.edit(boardPeriodItem);
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("boardPeriodItem", ex.getMessage(), labels.get("lu_period_no_duplicated")));
            return new ModelAndView("/board/period.add-edit", bindingResult.getModel());
        }

        return new ModelAndView("redirect:/board-period?board_oid=" + boardPeriodItem.getFkBoardOid());
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable("oid") long oid) {
        BoardPeriod item = boardPeriodService.findById(oid).orElse(null);
        if (item == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/board/period.remove", "boardPeriodItem", item);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST)
    public ModelAndView removePost(@PathVariable(value = "oid") long oid, Model model) {
        boardPeriodService.remove(oid);
        return new ModelAndView("redirect:/board-period?board_oid=" +
                ((Board) Objects.requireNonNull(model.getAttribute("boardItem"))).getOid());
    }

    @RequestMapping(value = "/setting/{oid}", method = RequestMethod.GET)
    public ModelAndView setting(@PathVariable("oid") long oid) {
        BoardPeriod boardPeriodItem = boardPeriodService.findById(oid).orElse(null);
        if (boardPeriodItem == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        Board boardItem = boardService.findById(boardPeriodItem.getFkBoardOid()).orElse(null);
        if (boardItem == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        ModelAndView mav = new ModelAndView("/board/period.setting");
        mav.addObject("boardCommissionItem", new BoardCommission());
        mav.addObject("boardExpertItem", new BoardExpert());
        mav.addObject("boardObserverItem", new BoardObserver());

        mav.addObject("commissionItems", lookupService.findByGroupKey(LookupGroupKey.commission.name()));
        mav.addObject("boardMembershipItems", lookupService.findByGroupKey(LookupGroupKey.observer_membership.name()));
        mav.addObject("boardElectionTypeItems", lookupService.findByGroupKey(LookupGroupKey.observer_elect_type.name()));
        mav.addObject("periodItems", lookupService.findByGroupKeyOrderBy(LookupGroupKey.parliament_period.name(), "orderby desc"));

        mav.addObject("boardItem", boardItem);
        mav.addObject("boardPeriodItem", boardPeriodItem);
        return mav;
    }


}