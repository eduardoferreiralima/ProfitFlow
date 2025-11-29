package br.ifrn.edu.ProfitFlow.controller;


import br.ifrn.edu.ProfitFlow.config.docs.ContasControllerDocs;
import br.ifrn.edu.ProfitFlow.dto.request.RequestContaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseContaDTO;
import br.ifrn.edu.ProfitFlow.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financeiro")
public class ContaController implements ContasControllerDocs {

    @Autowired
    private ContaService contasService;

    @GetMapping
    public ResponseEntity<List<ResponseContaDTO>> getContas(){
        return ResponseEntity.ok(contasService.getContas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseContaDTO> getConta(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(contasService.getConta(id));
    }

    @PostMapping
    public ResponseEntity<ResponseContaDTO> createConta(@RequestBody RequestContaDTO conta) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(contasService.createConta(conta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseContaDTO> updateConta(@PathVariable Integer id, @RequestBody RequestContaDTO conta) throws Exception {
        return ResponseEntity.ok(contasService.updateConta(id, conta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteConta(@PathVariable Integer id){
        contasService.deleteConta(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/quitar")
    public ResponseEntity<?> updateQuitar(@PathVariable Integer id) throws Exception {
        contasService.updateQuitar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/vencidas")
    public ResponseEntity<ResponseContaDTO> getVencidas(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pendentes")
    public ResponseEntity<ResponseContaDTO> getPendentes(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tipo")
    public ResponseEntity<ResponseContaDTO> getContasPorTipo(String tipo) {
        return null;
    }
}
