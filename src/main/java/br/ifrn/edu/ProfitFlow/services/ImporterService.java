package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import br.ifrn.edu.ProfitFlow.dto.request.RequestRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.file.importer.contract.FileImporter;
import br.ifrn.edu.ProfitFlow.file.importer.factory.FileImporterFactory;
import br.ifrn.edu.ProfitFlow.mapper.MapperRegistroFinanceiro;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.coyote.BadRequestException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.processing.FilerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ImporterService {
    
    @Autowired
    private MapperRegistroFinanceiro mapperRegistroFinanceiro;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RegistroFinanceiroService registroFinanceiroService;

    @Autowired
    private FileImporterFactory importer;

    public byte[] getTemplate() throws Exception {

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            XSSFSheet sheet = workbook.createSheet("Modelo Importação");

            // Criar cabeçalho
            Row header = sheet.createRow(0);

            String[] columns = {
                    "Tipo de Lançamento",
                    "Valor",
                    "Forma de Pagamento",
                    "Categoria",
                    "Nome/Razão Social",
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


            return bytes;

        } catch (Exception e) {
            throw new FileNotFoundException("Erro ao gerar o arquivo" + e); //substituir por excessão personalizada
        }
    }

    @Transactional
    public List<ImporterDTO> uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new BadRequestException("Please set a valid file");

        try (InputStream inputStream = file.getInputStream()) {
            String fileName = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(() -> new BadRequestException("File mame cannot be null"));

            FileImporter importer = this.importer.getFileImporter(fileName);

            List<ImporterDTO> dataImporter = importer.importFile(inputStream).stream().toList();

            for (ImporterDTO importerDTO : dataImporter){
                processImporterDTO(importerDTO);
            };

            return dataImporter;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void processImporterDTO(ImporterDTO importerDTO) throws Exception {
        ResponsePessoaDTO responsePessoaDTO = usuarioService.getResponsePessoaDTOWithImporterDTO(importerDTO);
        RequestRegistroFinanceiroDTO registroDTO = mapperRegistroFinanceiro.mapImporterToRequestRegistroFinanceiroDTO(importerDTO);
        registroDTO.setPessoaId(responsePessoaDTO.getId());
        registroFinanceiroService.createRegistroFinanceiro(registroDTO);
    }



    public ResponseEntity<?> deleteFile(String id) {
        return ResponseEntity.ok().build();
    }
}
