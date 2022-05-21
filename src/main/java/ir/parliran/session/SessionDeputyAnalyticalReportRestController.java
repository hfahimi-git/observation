package ir.parliran.session;

import ir.parliran.global.AjaxBindingErrorChecker;
import ir.parliran.global.Utils;
import ir.parliran.global.upload.StorageService;
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
@RequestMapping("/session-deputy-analytical-report-rest/{sessionOid}")
public class SessionDeputyAnalyticalReportRestController {

    private final SessionService sessionService;
    private final SessionDeputyAnalyticalReportService service;
    private final StorageService storageService;
    private final AjaxBindingErrorChecker bindingErrorChecker;

    @Autowired
    public SessionDeputyAnalyticalReportRestController(SessionService sessionService,
                                                       SessionDeputyAnalyticalReportService service,
                                                       StorageService storageService, AjaxBindingErrorChecker bindingErrorChecker) {
        this.sessionService = sessionService;
        this.service = service;
        this.storageService = storageService;
        this.bindingErrorChecker = bindingErrorChecker;
    }

    @ModelAttribute
    private void setLookups(@PathVariable("sessionOid") long sessionOid) throws Exception {
        sessionService.findById(sessionOid).orElseThrow(() -> new Exception("invalid session"));
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET,  produces = "application/json")
    public List<SessionDeputyAnalyticalReport> index(@PathVariable("sessionOid") long sessionOid) {
        return service.findAll(sessionOid);
    }

    @RequestMapping(value = "/show/{oid}", method = RequestMethod.GET)
    public Optional<SessionDeputyAnalyticalReport> show(@PathVariable("oid") long oid) {
        return service.findById(oid);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public SessionDeputyAnalyticalReport add(@PathVariable("sessionOid") String sessionOid,
                                               @Valid SessionDeputyAnalyticalReport item,
                                               BindingResult bindingResult) {
        bindingErrorChecker.checkBindingError(bindingResult);

        if (!item.getFile().isEmpty()) {
            String filename = storageService.store(item.getFile());
            item.setFilename(filename);
        }
        item.setFile(null);
         return service.add(item);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public int edit(@PathVariable("sessionOid") String sessionOid,
                                               @Valid SessionDeputyAnalyticalReport item,
                                               BindingResult bindingResult) {
        bindingErrorChecker.checkBindingError(bindingResult);

        if (!item.getFile().isEmpty()) {
            String filename = storageService.store(item.getFile());
            item.setFilename(filename);
        }
        return service.edit(item);
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