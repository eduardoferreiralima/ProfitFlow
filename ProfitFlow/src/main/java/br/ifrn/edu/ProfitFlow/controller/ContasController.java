package br.ifrn.edu.ProfitFlow.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financeiro")
@Tag(name = "Contas")
public class ContasController {

    @GetMapping
    public ResponseEntity<?> getContas(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConta(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createConta(){
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateConta(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteConta(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/quitar")
    public ResponseEntity<?> updateQuitar(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/vencidas")
    public ResponseEntity<?> getVencidas(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pendentes")
    public ResponseEntity<?> getPendentes(){
        return ResponseEntity.ok().build();
    }



}
