package br.ifrn.edu.ProfitFlow.controller;

import br.ifrn.edu.ProfitFlow.controller.docs.RegistroFinanceiroControllerDocs;
import br.ifrn.edu.ProfitFlow.dto.request.RequestRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.services.RegistroFinanceiroService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/financeiro")
public class RegistroFinanceiroController implements RegistroFinanceiroControllerDocs {

    @Autowired
    private RegistroFinanceiroService registroFinanceiroService;

    @GetMapping
    public ResponseEntity<List<ResponseRegistroFinanceiroDTO>> getRegistroFinanceiro(){
        return ResponseEntity.ok(registroFinanceiroService.getRegistroFinanceiro());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseRegistroFinanceiroDTO> getRegistroFinanceiro(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(registroFinanceiroService.getRegistroFinanceiroPorId(id));
    }

    @PostMapping
    public ResponseEntity<ResponseRegistroFinanceiroDTO> createRegistroFinanceiro(@RequestBody RequestRegistroFinanceiroDTO rf) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(registroFinanceiroService.createRegistroFinanceiro(rf));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseRegistroFinanceiroDTO> updateRegistroFinanceiro(@PathVariable Long id, @RequestBody RequestRegistroFinanceiroDTO rf) throws Exception {
        return ResponseEntity.ok(registroFinanceiroService.updateRegistroFinanceiro(id, rf));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistroFinanceiro(@PathVariable Long id){
        registroFinanceiroService.deleteRegistroFinanceiro(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/quitar")
    public ResponseEntity<?> updateQuitar(@PathVariable Long id) throws Exception {
        registroFinanceiroService.updateQuitar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<ResponseRegistroFinanceiroDTO>> getRegistroFinanceiroPorCategoria(String categoria){
        return ResponseEntity.ok().body(registroFinanceiroService.getRegistroFinanceiroPorCategoria(categoria));
    }

    @GetMapping("/status")
    public ResponseEntity<List<ResponseRegistroFinanceiroDTO>> getRegistroFinanceiroPorStatus(@RequestParam ContaStatus status){
        return ResponseEntity.ok().body(registroFinanceiroService.getPorStatus(status));
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<ResponseRegistroFinanceiroDTO>> getRegistroFinanceiroPorTipo(@RequestParam ContaTipo tipo) throws BadRequestException {
        return ResponseEntity.ok().body(registroFinanceiroService.getRegistroFinanceiroPorTipo(tipo));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<ResponseRegistroFinanceiroDTO>> getRegistroFinanceiroPorPeriodo(
            @RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate inicio,
            @RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate fim
    ) {
        return ResponseEntity.ok().body(registroFinanceiroService.getRegistroFinanceiroPorPeriodo(inicio, fim));
    }
}
