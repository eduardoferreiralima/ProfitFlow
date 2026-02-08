package br.ifrn.edu.ProfitFlow.controller;

import br.ifrn.edu.ProfitFlow.config.security.AuthenticationProvider;
import br.ifrn.edu.ProfitFlow.controller.docs.RelatoriosControllerDocs;
import br.ifrn.edu.ProfitFlow.dto.FluxoCaixaDTO;
import br.ifrn.edu.ProfitFlow.services.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatoriosController implements RelatoriosControllerDocs {

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    private RelatorioService  relatorioService;

    @GetMapping("/fluxo-caixa")
    public ResponseEntity<List<FluxoCaixaDTO>> getFluxoCaixa(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){
        return ResponseEntity.ok(relatorioService.obterFluxoCaixa(inicio, fim, authenticationProvider.getUsuarioId()));
    }

    @GetMapping("/balanco-mensal")
    public ResponseEntity<?> getBalancoMensal(@RequestParam LocalDate data){
        return ResponseEntity.ok(relatorioService.obterBalancoMensal(data, authenticationProvider.getUsuarioId()));
    }

    @GetMapping("/situacao-financeira")
    public ResponseEntity<?> getSituacaoFinanceira(){
        return ResponseEntity.ok(relatorioService.obterSituacaoFinanceira(authenticationProvider.getUsuarioId()));
    }
}
