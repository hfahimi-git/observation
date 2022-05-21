package ir.parliran.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * ©hFahimi.com @ 2019/12/18 09:03
 */

@Component
public class Labels {
    private final MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @Autowired
    public Labels(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, new Locale("fa", "IR"));
    }

    public String get(String code) {
        return accessor.getMessage(code);
    }
}
