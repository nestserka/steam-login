package steam_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SteamOpenIdDTO {
    private String openidNs;
    private String openidMode;
    private String openidOpEndpoint;
    private String openidClaimedId;
    private String openidIdentity;
    private String openidReturnTo;
    private String openidResponseNonce;
    private String openidAssocHandle;
    private String openidSigned;
    private String openidSig;
}