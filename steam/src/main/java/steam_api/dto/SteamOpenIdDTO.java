package steam_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SteamOpenIdDTO {
    @URL
    @NotBlank
    private String ns;

    @URL
    @NotBlank
    private String op_endpoint;

    @URL
    @NotBlank
    private String claimed_id;

    @URL
    @NotBlank
    private String identity;

    @URL
    @NotBlank
    private String return_to;

    @NotBlank
    private String response_nonce;

    @NotBlank
    private String assoc_handle;

    @NotBlank
    @Pattern(regexp = "^\\w+(?:,\\w+)*$")
    private String signed;

    @NotBlank
    private String sig;
}

