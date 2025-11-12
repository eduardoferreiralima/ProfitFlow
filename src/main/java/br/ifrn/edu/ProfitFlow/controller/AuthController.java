package br.ifrn.edu.ProfitFlow.controller;


import br.ifrn.edu.ProfitFlow.config.docs.AuthControllerDocs;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthControllerDocs {
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String email, @RequestParam String senha){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok().build();
    }
}
