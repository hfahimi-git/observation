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
@RequestMapping("/session-deputy-justification-report-rest/{sessionOid}")
public class SessionDeputyJustificationReportRestController {

    private final SessionService sessionService;
    private final SessionDeputyJustificationReportService service;
    private final StorageService storageService;

    @Autowired
    public SessionDeputyJustificationReportRestController(SessionService sessionService,
                                                          SessionDeputyJustificationReportService service,
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
    public Optional<SessionDeputyJustificationReport> index(@PathVariable("sessionOid") long sessionOid) {
        return service.findBySessionOid(sessionOid);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public SessionDeputyJustificationReport add(@PathVariable("sessionOid") String sessionOid,
                                               @Valid SessionDeputyJustificationReport djrItem,
                                               BindingResult bindingResult) {
        djrItem.setFkSessionOid(Utils.getLong(sessionOid, 0));

        if (!djrItem.getFile().isEmpty()) {
            String filename = storageService.store(djrItem.getFile());
            djrItem.setFilename(filename);
        }
        djrItem.setFile(null);
         return service.add(djrItem);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public int edit(@PathVariable("sessionOid") String sessionOid,
                                               @Valid SessionDeputyJustificationReport djrItem,
                                               BindingResult bindingResult) {
        djrItem.setFkSessionOid(Utils.getLong(sessionOid, 0));

        if (!djrItem.getFile().isEmpty()) {
            String filename = storageService.store(djrItem.getFile());
            djrItem.setFilename(filename);
        }
        return service.edit(djrItem);
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