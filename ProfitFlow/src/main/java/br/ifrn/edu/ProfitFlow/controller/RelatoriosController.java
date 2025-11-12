package br.ifrn.edu.ProfitFlow.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/relatorios")
@Tag(name = "Relatorios")
public class RelatoriosController {

    @GetMapping("/fluxo-caixa")
    public ResponseEntity<?> getFluxoCaixa(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balanco-mensal")
    public ResponseEntity<?> getBalancoMensal(@RequestParam LocalDate data){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/situacao-financeira")
    public ResponseEntity<?> getSituacaoFinanceira(){
        return ResponseEntity.ok().build();
    }
}
