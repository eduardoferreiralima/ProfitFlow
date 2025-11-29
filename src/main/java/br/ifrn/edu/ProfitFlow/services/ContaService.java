package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.request.RequestContaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseContaDTO;
import br.ifrn.edu.ProfitFlow.mapper.MapperConta;
import br.ifrn.edu.ProfitFlow.models.Conta;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.repository.ContaRepository;
import br.ifrn.edu.ProfitFlow.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contasRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MapperConta mapper;


    public List<ResponseContaDTO> getContas() {

        List<Conta> contasEntities = contasRepository.findAll();
        List<ResponseContaDTO> contasDTO = new ArrayList<>();

        contasDTO = mapper.toResponseContaDTOList(contasEntities);

        return contasDTO;
    }

    public ResponseContaDTO getConta(Integer id) throws Exception {
        Conta conta = contasRepository.findById(id)
                .orElseThrow(()-> new Exception("Conta não encontrada"));

        ResponseContaDTO contaDTO = mapper.mapContaToResponseContaDTO(conta);

        return contaDTO;
    }

    public ResponseContaDTO createConta(RequestContaDTO contaDTO) throws Exception {
        Usuario user = usuarioRepository.findById(contaDTO.getPessoaId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cliente ou Fornecedor não encontrado com ID: " + contaDTO.getPessoaId()
                ));
        Conta conta = mapper.mapContaDtoToConta(contaDTO);
        conta.setPessoa(user);
        conta = contasRepository.save(conta);
        ResponseContaDTO contaResponse = mapper.mapContaToResponseContaDTO(conta);
        contaResponse.setPessoa(user);
        return contaResponse;
    }


    public ResponseContaDTO updateConta(Integer id, RequestContaDTO contaDTO) throws Exception {
        Conta conta = contasRepository.findById(id)
                .orElseThrow(() -> new Exception("Conta não encontrada com ID: " + id));
        mapper.updateContaFromDTO(contaDTO, conta);
        conta = contasRepository.save(conta);
        ResponseContaDTO contaResponse = mapper.mapContaToResponseContaDTO(conta);
        return contaResponse;
    }

    public boolean updateQuitar(Integer id) throws Exception {
        Conta conta = contasRepository.findById(id)
                .orElseThrow(() -> new Exception("Conta com ID " + id + " não encontrada!"));
        conta.setStatus(ContaStatus.PAGO);
        contasRepository.save(conta);
        return true;
    }
    public void deleteConta(Integer id){
        contasRepository.deleteById(id);
    }
    public ResponseContaDTO getVencidas(){
        return null;
    }
    public ResponseContaDTO getPendentes(){
        return null;
    }
    public ResponseContaDTO getContasPorTipo(String tipo) {
        return null;
    }
    public List<ResponseContaDTO> getCategoria(String categoria){
        return null;
    }
    public List<ResponseContaDTO> getPeriodo(LocalDate inicio, LocalDate fim){
        return null;
    }
    public List<ResponseContaDTO> getStatus(String status){
        return null;
    }
}
