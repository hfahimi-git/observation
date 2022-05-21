package ir.parliran.global;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class ControllerCommonInterceptor implements HandlerInterceptor {

    private Map<String, List<String>> splitQuery(String url) {
        if(url == null || url.trim().length() < 1) {
            return null;
        }
        final Map<String, List<String>> queryPairs = new LinkedHashMap<>();
        final String[] pairs = url.split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8) : pair;
            if (!queryPairs.containsKey(key)) {
                queryPairs.put(key, new LinkedList<>());
            }
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8) : "";
            queryPairs.get(key).add(value);
        }
        return queryPairs;
    }

    private String joinQuery(Map<String, List<String>> keyValue) {
        if(keyValue == null || keyValue.size() == 0) {
            return "";
        }
        StringJoiner result = new StringJoiner("&");
        for (String key: keyValue.keySet()) {
            if (keyValue.get(key) != null && keyValue.get(key).size() > 0) {
                for (String val : keyValue.get(key)) {
                    result.add(key + "=" + val);
                }
            }
            else {
                result.add(key + "=" + keyValue.get(key));
            }
        }
        return result.toString();
    }


    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object object, ModelAndView model) {
        Map<String, List<String>> parsedQs = splitQuery(request.getQueryString());
        if(parsedQs != null) {
            parsedQs.remove("page");
        }
        request.setAttribute("qsWithoutPageNo", joinQuery(parsedQs));
        request.setAttribute("ONLINE_USER", SecurityContextHolder.getContext().getAuthentication());

    }


}
