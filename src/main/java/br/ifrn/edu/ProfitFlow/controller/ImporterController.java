package br.ifrn.edu.ProfitFlow.controller;

import br.ifrn.edu.ProfitFlow.controller.docs.ImporterControllerDocs;
import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import br.ifrn.edu.ProfitFlow.services.ImporterService;
import lombok.SneakyThrows;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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


    @GetMapping("/template")
    public ResponseEntity<byte[]> getTemplate(){

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            XSSFSheet sheet = workbook.createSheet("Modelo Importação");

            // Criar cabeçalho
            Row header = sheet.createRow(0);

            String[] columns = {
                    "Tipo de Lançamento",
                    "Valor",
                    "Forma de Pagamento",
                    "Categoria",
                    "Nome Cliente/Fornecedor",
                    "CPF/CNPJ",
                    "Data Prevista",
                    "Data Pagamento"
            };

            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Auto size
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Criar uma linha de exemplo (opcional)
            Row exampleRow = sheet.createRow(1);
            exampleRow.createCell(0).setCellValue("RECEITA");
            exampleRow.createCell(1).setCellValue("1500.00");
            exampleRow.createCell(2).setCellValue("PIX");
            exampleRow.createCell(3).setCellValue("Vendas");
            exampleRow.createCell(4).setCellValue("João Silva");
            exampleRow.createCell(5).setCellValue("12345678900");
            exampleRow.createCell(6).setCellValue("10/02/2025");
            exampleRow.createCell(7).setCellValue("11/02/2025");

            // Converter para array de bytes
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            byte[] bytes = out.toByteArray();

            // Retornar arquivo para download
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=template_importacao.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(bytes);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
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
