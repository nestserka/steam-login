package steam_api.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import steam_api.security.SecurityUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class StreamLoginService {

    public String extractSteamId(String openidIdentity) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(openidIdentity);
        if (matcher.find()) {
            return matcher.group();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public SecurityUser createSecurityUser(String steamId) {
        return SecurityUser.builder()
                .username("steam_" + steamId)
                .build();
    }

    public HttpSession createNewSession(HttpServletRequest request) {
        Optional.ofNullable(request.getSession(false)).ifPresent(HttpSession::invalidate);
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        return newSession;
    }

    public void setSessionCookie(HttpSession session, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
