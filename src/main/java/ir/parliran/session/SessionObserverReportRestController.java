package ir.parliran.session;

import ir.parliran.global.Utils;
import ir.parliran.global.upload.StorageService;
import ir.parliran.pm.ParliamentMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@RestController ()
@RequestMapping("/session-observer-report-rest/{sessionOid}")
public class SessionObserverReportRestController {

    private final SessionService sessionService;
    private final SessionObserverReportService service;
    private final StorageService storageService;

    @Autowired
    public SessionObserverReportRestController(SessionService sessionService,
                                               SessionObserverReportService service,
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
    public List<SessionObserverReport> index(@PathVariable("sessionOid") long sessionOid) {
        return service.findAll(sessionOid);
    }

    @RequestMapping(value = "/show/{oid}", method = RequestMethod.GET)
    public Optional<SessionObserverReport> show(@PathVariable("oid") long oid) {
        return service.findById(oid);
    }

    private void checkBindingError(BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new RuntimeException(bindingResult.toString());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public SessionObserverReport add(@PathVariable("sessionOid") String sessionOid,
                                               @Valid SessionObserverReport observerItem,
                                               BindingResult bindingResult) {
        checkBindingError(bindingResult);
        observerItem.setFkSessionOid(Utils.getLong(sessionOid, 0));

        if (!observerItem.getFile().isEmpty()) {
            String filename = storageService.store(observerItem.getFile());
            observerItem.setFilename(filename);
        }
        observerItem.setFile(null);
         return service.add(observerItem);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public int edit(@PathVariable("sessionOid") String sessionOid,
                                               @Valid SessionObserverReport observerItem,
                                               BindingResult bindingResult) {
        checkBindingError(bindingResult);
        observerItem.setFkSessionOid(Utils.getLong(sessionOid, 0));

        if (!observerItem.getFile().isEmpty()) {
            String filename = storageService.store(observerItem.getFile());
            observerItem.setFilename(filename);
        }
        return service.edit(observerItem);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST)
    public int remove(@PathVariable(value = "oid") long oid) {
        return service.remove(oid);
    }

    @RequestMapping(value = "/remove-file/{oid}", method = RequestMethod.POST)
    public int removeFile(@PathVariable(value = "oid") long oid) {
        return service.removeFile(oid);
    }

}