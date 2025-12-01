package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import br.ifrn.edu.ProfitFlow.dto.request.RequestPessoaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.mapper.MapperUsuario;
import br.ifrn.edu.ProfitFlow.models.PessoaFisica;
import br.ifrn.edu.ProfitFlow.models.PessoaJuridica;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.repository.PessoaFisicaRepository;
import br.ifrn.edu.ProfitFlow.repository.PessoaJuridicaRepository;
import br.ifrn.edu.ProfitFlow.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private MapperUsuario mapper;

    public List<ResponsePessoaDTO> findAll(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(user -> mapper.mapUsuariotoResponsePessoaDTO(user))
                .collect(Collectors.toList());
    }

    public ResponsePessoaDTO findById(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Usuario não Encontrado"));
        ResponsePessoaDTO  responsePessoaDTO = mapper.mapUsuariotoResponsePessoaDTO(usuario);
        return responsePessoaDTO;
    }

    public ResponsePessoaDTO createUser(RequestPessoaDTO pessoa) throws Exception {

        ResponsePessoaDTO responsePessoaDTO = new ResponsePessoaDTO();

        if (isCnpj(pessoa.getCpfCnpj())){

            PessoaJuridica pj = mapper.toEntityPessoaJuridica(pessoa);
            pj = pessoaJuridicaRepository.save(pj);
            responsePessoaDTO = mapper.mapPJtoResponsePessoaDTO(pj);

        } else if (isCpf(pessoa.getCpfCnpj())) {

            PessoaFisica pf = mapper.toEntityPessoaFisica(pessoa);
            pf = pessoaFisicaRepository.save(pf);
            responsePessoaDTO = mapper.mapPFtoResponsePessoaDTO(pf);

        }else {
            throw new IllegalArgumentException("CPF ou CNPJ Inválido!");
        }

        return responsePessoaDTO;
    }

    public ResponsePessoaDTO updateUser(Long id, RequestPessoaDTO pessoaDTO) {
        ResponsePessoaDTO response = new ResponsePessoaDTO();

        if (isCnpj(pessoaDTO.getCpfCnpj())) {
            PessoaJuridica pj = pessoaJuridicaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            mapper.updatePessoaJuridicaFromDTO(pessoaDTO, pj);
            pj = pessoaJuridicaRepository.save(pj);
            response = mapper.mapPJtoResponsePessoaDTO(pj);

        } else if (isCpf(pessoaDTO.getCpfCnpj())) {
            PessoaFisica pf = pessoaFisicaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            mapper.updatePessoaFisicaFromDTO(pessoaDTO, pf);
            pf = pessoaFisicaRepository.save(pf);
            response = mapper.mapPFtoResponsePessoaDTO(pf);

        } else {
            throw new IllegalArgumentException("CPF ou CNPJ inválido!");
        }

        return response;
    }

    public boolean delete(Long id){
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("Usuário não Encontrado!"));
        usuarioRepository.delete(user);
        return true;
    }

    public List<ResponsePessoaDTO> findByNome(String nome){
        List<Usuario> usuario = usuarioRepository.findByNome(nome);
        return usuario.stream()
                .map(user -> mapper.mapUsuariotoResponsePessoaDTO(user))
                .collect(Collectors.toList());
    }

    public ResponsePessoaDTO findByCpfOrCnpj(String cpfCnpj){
        ResponsePessoaDTO responsePessoaDTO = new ResponsePessoaDTO();
        if (isCnpj(cpfCnpj)) {
            PessoaJuridica pj = pessoaJuridicaRepository.findByCnpj(cpfCnpj);
            responsePessoaDTO = mapper.mapPJtoResponsePessoaDTO(pj);
        } else if (isCpf(cpfCnpj)) {
            PessoaFisica pf = pessoaFisicaRepository.findByCpf(cpfCnpj);
            responsePessoaDTO = mapper.mapPFtoResponsePessoaDTO(pf);
        } else{
            throw new NoSuchElementException("Usuário não encontrado!");
        }
        return responsePessoaDTO;
    }



    public boolean isCpf(String value) {
        String digits = value.replaceAll("\\D", "");
        return digits.length() == 11;
    }

    public boolean isCnpj(String value) {
        String digits = value.replaceAll("\\D", "");
        return digits.length() == 14;
    }

    public static String normalize(String value) {
        return value.replaceAll("\\D", "");
    }

    public ResponsePessoaDTO getResponsePessoaDTOWithImporterDTO(ImporterDTO importerDTO) throws Exception {
        ResponsePessoaDTO responsePessoaDTO = new ResponsePessoaDTO();
        PessoaJuridica pj =  pessoaJuridicaRepository.findByCnpj(importerDTO.getCpfCnpj());
        PessoaFisica pf = pessoaFisicaRepository.findByCpf(importerDTO.getCpfCnpj());
        if (pj == null && pf == null) {
            RequestPessoaDTO pessoa = mapper.mapimportertoResponsePessoaDTO(importerDTO);
            responsePessoaDTO = createUser(pessoa);
        } else if (pj != null) {
            responsePessoaDTO.setId(pj.getId());
        } else if (pf != null) {
            responsePessoaDTO.setId(pf.getId());
        }
        return responsePessoaDTO;
    }

}

