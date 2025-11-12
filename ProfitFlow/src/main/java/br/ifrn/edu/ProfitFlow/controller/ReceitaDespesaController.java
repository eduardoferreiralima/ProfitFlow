package br.ifrn.edu.ProfitFlow.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/transacoes")
@Tag(name = "Financeiro")
public class ReceitaDespesaController {

    @GetMapping
    public ResponseEntity<?> getPendentes(){
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPendentes(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/periodo")
    public ResponseEntity<?> getPeriodo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getStatus(@PathVariable String status){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> getCategoria(@PathVariable String categoria){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> getTipo(@PathVariable String tipo){
        return ResponseEntity.ok().build();
    }

}
