package ir.parliran.pm;

import ir.parliran.global.Page;
import ir.parliran.global.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@RestController()
@RequestMapping("/parliament-member-rest")
public class ParliamentMemberRestController {

    private final ParliamentMemberService parliamentMemberService;
    @Value("${paging.page-size:10}")
    private int pageSize;

    @Autowired
    public ParliamentMemberRestController(ParliamentMemberService parliamentMemberService) {
        this.parliamentMemberService = parliamentMemberService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public Page<ParliamentMember> index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "lu_period_no", required = false) String luPeriodNo,
            @RequestParam(value = "page", required = false, defaultValue = "1") String pageNo
    ) {
        //todo: set period list for drop down search box
        return parliamentMemberService.findAll(
                SearchCredential.builder()
                        .keyword(keyword)
                        .luPeriodNo(Utils.getInt(luPeriodNo, 0))
                        .pageNo(Utils.getInt(pageNo, 1))
                        .pageSize(pageSize)
                        .build()
        );
    }

}