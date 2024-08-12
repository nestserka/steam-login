package steam_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class SteamOpenIdDTO {
    private final String ns;
    private final String mode;
    private final String opEndpoint;
    private final String claimedId;
    private final String identity;
    private final String returnTo;
    private final String responseNonce;
    private final String assocHandle;
    private final String signed;
    private final String sig;

    public SteamOpenIdDTO(Map<String, String> params) {
        this.ns = params.get("openid.ns");
        this.mode = params.get("openid.mode");
        this.opEndpoint = params.get("openid.op_endpoint");
        this.claimedId = params.get("openid.claimed_id");
        this.identity = params.get("openid.identity");
        this.returnTo = params.get("openid.return_to");
        this.responseNonce = params.get("openid.response_nonce");
        this.assocHandle = params.get("openid.assoc_handle");
        this.signed = params.get("openid.signed");
        this.sig = params.get("openid.sig");
    }
}