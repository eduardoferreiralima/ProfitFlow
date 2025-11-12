package br.ifrn.edu.ProfitFlow.controller;


import br.ifrn.edu.ProfitFlow.dto.request.RequestPessoaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.models.ClienteFornecedor;
import br.ifrn.edu.ProfitFlow.services.ClienteFornecedorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas")
@Tag(name = "Pessoas")
public class ClienteFornecedorController {

    @Autowired
    private ClienteFornecedorService clienteFornecedorService;


    @GetMapping
    public ResponseEntity<List<ClienteFornecedor>> findAll(){
        return ResponseEntity.ok(clienteFornecedorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ClienteFornecedor>> findById(@PathVariable Integer id){
        return ResponseEntity.ok(clienteFornecedorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ResponsePessoaDTO> save(@Valid @RequestBody RequestPessoaDTO pessoaDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteFornecedorService.salvar(pessoaDTO));
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
    public ResponseEntity<List<ClienteFornecedor>> findByNome(@RequestParam String nome){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ClienteFornecedor>> findByTipo(@PathVariable String tipo){
        return ResponseEntity.ok(null);
    }


}
