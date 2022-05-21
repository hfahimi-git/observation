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
@RequestMapping("/session-board-justification-report-rest/{sessionOid}")
public class SessionBoardJustificationReportRestController {

    private final SessionService sessionService;
    private final SessionBoardJustificationReportService service;
    private final StorageService storageService;

    @Autowired
    public SessionBoardJustificationReportRestController(SessionService sessionService,
                                                         SessionBoardJustificationReportService service,
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
    public Optional<SessionBoardJustificationReport> index(@PathVariable("sessionOid") long sessionOid) {
        return service.findBySessionOid(sessionOid);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public SessionBoardJustificationReport add(@PathVariable("sessionOid") String sessionOid,
                                               @Valid SessionBoardJustificationReport bjrItem,
                                               BindingResult bindingResult) {
        bjrItem.setFkSessionOid(Utils.getLong(sessionOid, 0));

        if (!bjrItem.getFile().isEmpty()) {
            String filename = storageService.store(bjrItem.getFile());
            bjrItem.setFilename(filename);
        }
        bjrItem.setFile(null);
         return service.add(bjrItem);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public int edit(@PathVariable("sessionOid") String sessionOid,
                                               @Valid SessionBoardJustificationReport bjrItem,
                                               BindingResult bindingResult) {
        bjrItem.setFkSessionOid(Utils.getLong(sessionOid, 0));

        if (!bjrItem.getFile().isEmpty()) {
            String filename = storageService.store(bjrItem.getFile());
            bjrItem.setFilename(filename);
        }
        return service.edit(bjrItem);
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