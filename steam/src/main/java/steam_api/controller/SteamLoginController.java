package steam_api.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import steam_api.service.StreamLoginService;



@Controller
@RequestMapping("/steam")
public class SteamLoginController {
    private final StreamLoginService service;
    public SteamLoginController(StreamLoginService service) {
        this.service = service;
    }


    @GetMapping("/login/check")
    public ResponseEntity<String> check(
            @RequestParam(value = "openid.ns") String openidNs,
            @RequestParam(value = "openid.mode") String openidMode,
            @RequestParam(value = "openid.op_endpoint") String openidOpEndpoint,
            @RequestParam(value = "openid.claimed_id") String openidClaimedId,
            @RequestParam(value = "openid.identity") String openidIdentity,
            @RequestParam(value = "openid.return_to") String openidReturnTo,
            @RequestParam(value = "openid.response_nonce") String openidResponseNonce,
            @RequestParam(value = "openid.assoc_handle") String openidAssocHandle,
            @RequestParam(value = "openid.signed") String openidSigned,
            @RequestParam(value = "openid.sig") String openidSig
    ) {
       return service.checkSteamLogin(
                openidNs, openidMode, openidOpEndpoint, openidClaimedId, openidIdentity,
                openidReturnTo, openidResponseNonce, openidAssocHandle, openidSigned, openidSig
        );
    }
}