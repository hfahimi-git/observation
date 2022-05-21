package ir.parliran.security;

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

@RestController()
@RequestMapping("/user-rest")
public class UserRestController {

    private final UserService userService;
    @Value("${paging.page-size:10}")
    private int pageSize;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public Page<User> index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "lu_status", required = false) String luStatus,
            @RequestParam(value = "page", required = false, defaultValue = "1") String pageNo
    ) {
        return userService.findAll(new SearchCredential(keyword, luStatus, Utils.getInt(pageNo, 1), pageSize));
    }

}