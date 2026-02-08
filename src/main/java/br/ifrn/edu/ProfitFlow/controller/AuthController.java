package br.ifrn.edu.ProfitFlow.controller;

import br.ifrn.edu.ProfitFlow.controller.docs.AuthControllerDocs;
import br.ifrn.edu.ProfitFlow.dto.AuthDataDTO;
import br.ifrn.edu.ProfitFlow.dto.ProfileDTO;
import br.ifrn.edu.ProfitFlow.dto.request.RequestPessoaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.services.TokenService;
import br.ifrn.edu.ProfitFlow.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthControllerDocs {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile(@AuthenticationPrincipal Usuario logado){
        return ResponseEntity.ok(new ProfileDTO(logado));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getDetails() instanceof String token) {
            String cleanToken = token.replace("Bearer ", "");
            tokenService.logout(cleanToken);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDataDTO dados) {
        return ResponseEntity.ok(tokenService.generateDataToken(dados));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponsePessoaDTO> createUser(@Valid @RequestBody RequestPessoaDTO pessoaDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUser(pessoaDTO));
    }
}
