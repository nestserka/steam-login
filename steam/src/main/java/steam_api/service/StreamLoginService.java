package steam_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import steam_api.dto.SteamOpenIdDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class StreamLoginService {

    @Value("${steam.community-login-url}")
    private String steamLoginUrl;
    private static final Logger logger = LoggerFactory.getLogger(StreamLoginService.class);

    public ResponseEntity<String> checkSteamLogin(SteamOpenIdDTO steamOpenIdDTO) {
        try {
            WebClient.create(steamLoginUrl)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/openid/login")
                            .queryParam("openid.ns", steamOpenIdDTO.getNs())
                            .queryParam("openid.mode", "check_authentication")
                            .queryParam("openid.op_endpoint", steamOpenIdDTO.getOpEndpoint())
                            .queryParam("openid.claimed_id", steamOpenIdDTO.getClaimedId())
                            .queryParam("openid.identity", steamOpenIdDTO.getIdentity())
                            .queryParam("openid.return_to", steamOpenIdDTO.getReturnTo())
                            .queryParam("openid.response_nonce", steamOpenIdDTO.getResponseNonce())
                            .queryParam("openid.assoc_handle", steamOpenIdDTO.getAssocHandle())
                            .queryParam("openid.signed", steamOpenIdDTO.getSigned())
                            .queryParam("openid.sig", steamOpenIdDTO.getSig())
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            String steamId = extractSteamId(steamOpenIdDTO.getIdentity());
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
