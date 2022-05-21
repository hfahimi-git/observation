package ir.parliran.board;

import ir.parliran.global.LookupGroupKey;
import ir.parliran.lookup.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@Controller()
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final LookupService lookupService;
    @Value("${paging.page-size:10}")
    private int pageSize;

    @Autowired
    public BoardController(BoardService boardService, LookupService lookupService) {
        this.boardService = boardService;
        this.lookupService = lookupService;
    }

    @ModelAttribute
    private void setLookups(Model model) {
        model.addAttribute("boardTypeItems", lookupService.findByGroupKey(LookupGroupKey.board_type.name()));
        model.addAttribute("sessionPeriodItems", lookupService.findByGroupKey(LookupGroupKey.session_period.name()));
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "lu_board_type", required = false) String luBoardType,
            @RequestParam(value = "lu_session_period", required = false) String luSessionPeriod,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNo
    ) {

        ModelAndView mav = new ModelAndView("/board/index");
        mav.addObject("boardItems",
                boardService.findAll(
                        SearchCredential.builder()
                                .keyword(keyword)
                                .luBoardType(luBoardType)
                                .luSessionPeriod(luSessionPeriod)
                                .pageNo(pageNo)
                                .pageSize(pageSize)
                                .build()
                ));
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("/board/add-edit", "boardItem", new Board());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid @ModelAttribute("boardItem") Board boardItem, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/board/add-edit", bindingResult.getModel());
        }

        boardService.add(boardItem);

        return new ModelAndView("redirect:/board");
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("oid") long oid) {
        Board item = boardService.findById(oid).orElse(null);
        if (item == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/board/add-edit", "boardItem", item);
    }

    @RequestMapping(value = "/edit/{oid}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable(value = "oid") long oid,
                             @Valid @ModelAttribute("boardItem") Board boardItem, BindingResult bindingResult) {

        Board pm = boardService.findById(oid).orElse(null);
        if (pm == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/board/add-edit", bindingResult.getModel());
        }

        boardService.edit(boardItem);

        return new ModelAndView("redirect:/board");
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable("oid") long oid) {
        Board board = boardService.findById(oid).orElse(null);
        if (board == null) {
            return new ModelAndView("/error/404", HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("/board/remove", "boardItem", board);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST)
    public ModelAndView removePost(@PathVariable(value = "oid") long oid) {
        boardService.remove(oid);
        return new ModelAndView("redirect:/board");
    }


}