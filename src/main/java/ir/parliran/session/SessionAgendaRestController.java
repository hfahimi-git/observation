package ir.parliran.session;

import ir.parliran.global.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@RestController ()
@RequestMapping("/session-agenda-rest/{sessionOid}")
public class SessionAgendaRestController {

    private final SessionService sessionService;
    private final SessionAgendaService agendaService;

    @Autowired
    public SessionAgendaRestController(SessionService sessionService, SessionAgendaService agendaService) {
        this.sessionService = sessionService;
        this.agendaService = agendaService;
    }

    @ModelAttribute
    private void setLookups(@PathVariable("sessionOid") long sessionOid) throws Exception {
        sessionService.findById(sessionOid).orElseThrow(() -> new Exception("invalid session"));
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public List<SessionAgenda> index(@PathVariable("sessionOid") String sessionOid) {
        return agendaService.findAll(Utils.getLong(sessionOid, 0));
    }

    @RequestMapping(value = "/show/{oid}", method = RequestMethod.GET)
    public Optional<SessionAgenda> show(@PathVariable("oid") long oid) {
        return agendaService.findById(oid);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public SessionAgenda add(@PathVariable("sessionOid") String sessionOid, @Valid @RequestBody SessionAgenda sessionAgenda) {
        sessionAgenda.setFkSessionOid(Utils.getLong(sessionOid, 0));
        return agendaService.add(sessionAgenda);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public int edit(@PathVariable("sessionOid") String sessionOid, @Valid @RequestBody SessionAgenda sessionAgenda) {
        sessionAgenda.setFkSessionOid(Utils.getLong(sessionOid, 0));
        return agendaService.edit(sessionAgenda);
    }

    @RequestMapping(value = "/remove/{oid}", method = RequestMethod.POST,  produces = "application/json")
    public int remove(@PathVariable(value = "oid") int oid) {
        return agendaService.remove(oid);
    }


}