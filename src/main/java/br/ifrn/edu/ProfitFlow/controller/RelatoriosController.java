package br.ifrn.edu.ProfitFlow.controller;

import br.ifrn.edu.ProfitFlow.controller.docs.RelatoriosControllerDocs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/relatorios")
public class RelatoriosController implements RelatoriosControllerDocs {

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
