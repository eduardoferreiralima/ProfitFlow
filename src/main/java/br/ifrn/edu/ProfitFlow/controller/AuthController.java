package br.ifrn.edu.ProfitFlow.controller;


import br.ifrn.edu.ProfitFlow.controller.docs.AuthControllerDocs;
import br.ifrn.edu.ProfitFlow.dto.request.RequestPessoaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthControllerDocs {


    @Autowired
    private UsuarioService usuarioService;


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



    @GetMapping("/findAll")
    public ResponseEntity<List<Usuario>> findAll(){
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> findById(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ResponsePessoaDTO> createUser(@Valid @RequestBody RequestPessoaDTO pessoaDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUser(pessoaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePessoaDTO> update(@PathVariable Integer id, @RequestBody RequestPessoaDTO pessoaDTO){
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePessoaDTO> delete(@PathVariable Integer id){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Usuario>> findByNome(@RequestParam String nome){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Usuario>> findByTipo(@PathVariable String tipo){
        return ResponseEntity.ok(null);
    }
}
