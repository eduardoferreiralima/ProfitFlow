package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import br.ifrn.edu.ProfitFlow.dto.request.RequestRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.file.importer.contract.FileImporter;
import br.ifrn.edu.ProfitFlow.file.importer.factory.FileImporterFactory;
import br.ifrn.edu.ProfitFlow.mapper.MapperRegistroFinanceiro;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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

    public ResponseEntity<?> getTemplate(){
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getImports(){
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getImports(String id){
        return ResponseEntity.ok().build();
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
        definirStatusPagamento(importerDTO, registroDTO);
        registroDTO.setPessoaId(responsePessoaDTO.getId());
        registroFinanceiroService.createRegistroFinanceiro(registroDTO);
    }

    private static void definirStatusPagamento(ImporterDTO importerDTO, RequestRegistroFinanceiroDTO registroDTO) {

        LocalDate hoje = LocalDate.now();
        LocalDate pagamento = importerDTO.getDataPagamento();
        LocalDate prevista = importerDTO.getDataPrevista();

        // Regra 1: Não permitir pagamento futuro
        if (pagamento != null && pagamento.isAfter(hoje)) {
            registroDTO.setStatus(ContaStatus.PENDENTE);
        }

        // Regra 2: Se foi pago → PAGO
        if (pagamento != null) {
            registroDTO.setStatus(ContaStatus.PAGO);
            return;
        }

        // Regra 3: Sem pagamento → verificar vencimento
        if (prevista.isBefore(hoje)) {
            registroDTO.setStatus(ContaStatus.ATRASADO);
        } else {
            registroDTO.setStatus(ContaStatus.PENDENTE); // hoje ou no futuro
        }
    }

    public ResponseEntity<?> deleteFile(String id) {
        return ResponseEntity.ok().build();
    }
}
