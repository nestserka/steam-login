package steam_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import steam_api.dto.SteamOpenIdDTO;
import steam_api.service.StreamLoginService;

import java.util.Map;


@Controller
@RequestMapping("/steam")
public class SteamLoginController {
    private final StreamLoginService service;
    public SteamLoginController(StreamLoginService service) {
        this.service = service;
    }
    @GetMapping("/login/check")
    public ResponseEntity<String> check (@RequestParam Map<String, String> params) {
        SteamOpenIdDTO openIdDTO = new SteamOpenIdDTO(params);
        return service.checkSteamLogin(openIdDTO);
    }
}