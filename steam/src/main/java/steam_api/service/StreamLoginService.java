package steam_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class StreamLoginService {

    @Value("${steam.community-login-url}")
    private String steamLoginUrl;
    private static final Logger logger = LoggerFactory.getLogger(StreamLoginService.class);

    public ResponseEntity<String> checkSteamLogin(String openidNs, String openidMode, String openidOpEndpoint,
                                                  String openidClaimedId, String openidIdentity, String openidReturnTo,
                                                  String openidResponseNonce, String openidAssocHandle, String openidSigned,
                                                  String openidSig) {
        try {
            WebClient.create(steamLoginUrl)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/openid/login")
                            .queryParam("openid.ns", openidNs)
                            .queryParam("openid.mode", "check_authentication")
                            .queryParam("openid.op_endpoint", openidOpEndpoint)
                            .queryParam("openid.claimed_id", openidClaimedId)
                            .queryParam("openid.identity", openidIdentity)
                            .queryParam("openid.return_to", openidReturnTo)
                            .queryParam("openid.response_nonce", openidResponseNonce)
                            .queryParam("openid.assoc_handle", openidAssocHandle)
                            .queryParam("openid.signed", openidSigned)
                            .queryParam("openid.sig", openidSig)
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            String steamId = extractSteamId(openidIdentity);
            return ResponseEntity.ok(steamId);

        } catch (Exception e) {
            logger.error("An error occurred during Steam login check", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to verify Steam login");
        }
    }

    public String extractSteamId(String openidIdentity) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(openidIdentity);
        if (matcher.find()) {
            return matcher.group();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
