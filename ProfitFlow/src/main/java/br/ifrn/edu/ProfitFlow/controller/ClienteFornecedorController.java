package br.ifrn.edu.ProfitFlow.controller;


import br.ifrn.edu.ProfitFlow.models.Cliente;
import br.ifrn.edu.ProfitFlow.services.ClienteFornecedorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
@Tag(name = "Pessoas")
public class ClienteFornecedorController {

    @Autowired
    private ClienteFornecedorService clienteFornecedorService;


    @GetMapping
    public ResponseEntity<List<Cliente>> findAll(){
        return ResponseEntity.ok(clienteFornecedorService.findAll());
    }

    @GetMapping("")



}
