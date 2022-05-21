package ir.parliran.session;

import ir.parliran.global.Utils;
import ir.parliran.global.upload.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@RestController ()
@RequestMapping("/session-board-report-rest/{sessionOid}")
public class SessionBoardReportRestController {

    private final SessionService sessionService;
    private final SessionBoardReportService service;
    private final StorageService storageService;

    @Autowired
    public SessionBoardReportRestController(SessionService sessionService,
                                            SessionBoardReportService service,
                                            StorageService storageService) {
        this.sessionService = sessionService;
        this.service = service;
        this.storageService = storageService;
    }

    @ModelAttribute
    private void setLookups(@PathVariable("sessionOid") long sessionOid) throws Exception {
        sessionService.findById(sessionOid).orElseThrow(() -> new Exception("invalid session"));
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET,  produces = "application/json")
    public Optional<SessionBoardReport> index(@PathVariable("sessionOid") long sessionOid) {
        return service.findBySessionOid(sessionOid);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public SessionBoardReport add(@PathVariable("sessionOid") String sessionOid,
                                               @Valid SessionBoardReport item,
                                               BindingResult bindingResult) {
        item.setFkSessionOid(Utils.getLong(sessionOid, 0));

        if (!item.getFile().isEmpty()) {
            String filename = storageService.store(item.getFile());
            item.setFilename(filename);
        }
        item.setFile(null);
         return service.add(item);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public int edit(@PathVariable("sessionOid") String sessionOid,
                                               @Valid SessionBoardReport item,
                                               BindingResult bindingResult) {
        item.setFkSessionOid(Utils.getLong(sessionOid, 0));

        if (!item.getFile().isEmpty()) {
            String filename = storageService.store(item.getFile());
            item.setFilename(filename);
        }
        return service.edit(item);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public int remove(@PathVariable(value = "sessionOid") long sessionOid) {
        return service.remove(sessionOid);
    }

    @RequestMapping(value = "/remove-file", method = RequestMethod.POST)
    public int removeFile(@PathVariable(value = "sessionOid") long sessionOid) {
        return service.removeFile(sessionOid);
    }

}