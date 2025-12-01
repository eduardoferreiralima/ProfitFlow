package br.ifrn.edu.ProfitFlow.controller;

import br.ifrn.edu.ProfitFlow.controller.docs.AdministrarUsuariosControllerDocs;
import br.ifrn.edu.ProfitFlow.dto.request.RequestPessoaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class AdministrarUsuariosController implements AdministrarUsuariosControllerDocs {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/findAll")
    public ResponseEntity<List<ResponsePessoaDTO>> findAll(){
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePessoaDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ResponsePessoaDTO> createUser(@Valid @RequestBody RequestPessoaDTO pessoaDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUser(pessoaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePessoaDTO> update(@PathVariable Long id, @RequestBody RequestPessoaDTO pessoaDTO){
        return ResponseEntity.ok(usuarioService.updateUser(id, pessoaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(usuarioService.delete(id));
    }

    @GetMapping("/search/{nome}")
    public ResponseEntity<List<ResponsePessoaDTO>> findByNome(@PathVariable String nome){
        return ResponseEntity.ok(usuarioService.findByNome(nome));
    }

    @GetMapping("/search/{cpfCnpj}")
    public ResponseEntity<ResponsePessoaDTO> findByCpfOrCnpj(@PathVariable String cpfCnpj){
        return ResponseEntity.ok(usuarioService.findByCpfOrCnpj(cpfCnpj));
    }
}
