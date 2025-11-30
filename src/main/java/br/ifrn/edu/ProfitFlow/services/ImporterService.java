package br.ifrn.edu.ProfitFlow.services;


import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import br.ifrn.edu.ProfitFlow.dto.request.RequestContaDTO;
import br.ifrn.edu.ProfitFlow.dto.request.RequestPessoaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.file.importer.contract.FileImporter;
import br.ifrn.edu.ProfitFlow.file.importer.factory.FileImporterFactory;
import br.ifrn.edu.ProfitFlow.mapper.MapperConta;
import br.ifrn.edu.ProfitFlow.mapper.MapperUsuario;
import br.ifrn.edu.ProfitFlow.models.Conta;
import br.ifrn.edu.ProfitFlow.models.PessoaFisica;
import br.ifrn.edu.ProfitFlow.models.PessoaJuridica;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.repository.PessoaFisicaRepository;
import br.ifrn.edu.ProfitFlow.repository.PessoaJuridicaRepository;
import br.ifrn.edu.ProfitFlow.repository.UsuarioRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ImporterService {

    @Autowired
    private MapperUsuario mapperUser;
    
    @Autowired
    private MapperConta mapperConta;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContaService contaService;

    @Autowired
    private FileImporterFactory importer;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

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
        ResponsePessoaDTO responsePessoaDTO = new ResponsePessoaDTO();
        PessoaJuridica pj =  pessoaJuridicaRepository.findByCnpj(importerDTO.getCpfCnpj());
        PessoaFisica pf = pessoaFisicaRepository.findByCpf(importerDTO.getCpfCnpj());
        if (pj == null && pf == null) {
            RequestPessoaDTO pessoa = mapperUser.mapimportertoResponsePessoaDTO(importerDTO);
            responsePessoaDTO = usuarioService.createUser(pessoa);
        } else if (pj != null) {
            responsePessoaDTO.setId(pj.getId());
        } else if (pf != null) {
            responsePessoaDTO.setId(pf.getId());
        }
        RequestContaDTO contaDTO = mapperConta.mapImporterToRequestContaDTO(importerDTO);
        contaDTO.setPessoaId(responsePessoaDTO.getId());
        contaService.createConta(contaDTO);
    }


    public ResponseEntity<?> deleteFile(String id) {
        return ResponseEntity.ok().build();
    }


}
