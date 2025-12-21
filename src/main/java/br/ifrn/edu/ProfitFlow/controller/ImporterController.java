package br.ifrn.edu.ProfitFlow.controller;

import br.ifrn.edu.ProfitFlow.controller.docs.ImporterControllerDocs;
import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import br.ifrn.edu.ProfitFlow.services.ImporterService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/importer")
public class ImporterController implements ImporterControllerDocs {

    @Autowired
    private ImporterService importerService;


    @SneakyThrows
    @GetMapping("/template")
    public ResponseEntity<byte[]> getTemplate() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=template_importacao.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(importerService.getTemplate());
    }

    @SneakyThrows
    @PostMapping(
            value="/uploadFile",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<List<ImporterDTO>> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(importerService.uploadFile(file));
    }
}
