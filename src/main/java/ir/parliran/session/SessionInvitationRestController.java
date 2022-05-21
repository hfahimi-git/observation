package ir.parliran.session;

import ir.parliran.global.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:47
 */

@RestController ()
@RequestMapping("/session-invitation-rest/{sessionOid}")
public class SessionInvitationRestController {

    private final SessionService sessionService;
    private final SessionInvitationService service;

    @Autowired
    public SessionInvitationRestController(SessionService sessionService, SessionInvitationService service) {
        this.sessionService = sessionService;
        this.service = service;
    }

    @ModelAttribute
    private void setLookups(@PathVariable("sessionOid") long sessionOid) throws Exception {
        sessionService.findById(sessionOid).orElseThrow(() -> new Exception("invalid session"));
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public Optional<SessionInvitation> index(@PathVariable("sessionOid") long sessionOid) {
        return service.findBySessionOid(sessionOid);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public SessionInvitation add(@PathVariable("sessionOid") String sessionOid,
                                 @Valid @RequestBody SessionInvitation sessionInvitationItem) {
        sessionInvitationItem.setFkSessionOid(Utils.getLong(sessionOid, 0));

        return service.add(sessionInvitationItem);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,  produces = "application/json")
    public int edit(@PathVariable("sessionOid") String sessionOid,
                    @Valid SessionInvitation sessionInvitationItem,
                    BindingResult bindingResult) {
        sessionInvitationItem.setFkSessionOid(Utils.getLong(sessionOid, 0));

        return service.edit(sessionInvitationItem);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public int remove(@PathVariable(value = "sessionOid") long sessionOid) {
        return service.remove(sessionOid);
    }

}