package br.ifrn.edu.ProfitFlow.controller;

import br.ifrn.edu.ProfitFlow.controller.docs.AuthControllerDocs;
import br.ifrn.edu.ProfitFlow.dto.AuthDataDTO;
import br.ifrn.edu.ProfitFlow.dto.ProfileDTO;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthControllerDocs {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile(@AuthenticationPrincipal Usuario logado){
        return ResponseEntity.ok(new ProfileDTO(logado));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDataDTO dados) {
        return ResponseEntity.ok(tokenService.generateDataToken(dados));
    }
}
