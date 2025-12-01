package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.request.RequestRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.mapper.MapperRegistroFinanceiro;
import br.ifrn.edu.ProfitFlow.models.RegistroFinanceiro;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.repository.RegistroFinanceiroRepository;
import br.ifrn.edu.ProfitFlow.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistroFinanceiroService {

    @Autowired
    private RegistroFinanceiroRepository registroFinanceiroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MapperRegistroFinanceiro mapper;


    public List<ResponseRegistroFinanceiroDTO> getRegistroFinanceiro() {

        List<RegistroFinanceiro> rfEntities = registroFinanceiroRepository.findAll();
        List<ResponseRegistroFinanceiroDTO> registroDTO = new ArrayList<>();

        registroDTO = mapper.toResponseRegistroFinanceiroDTOList(rfEntities);

        return registroDTO;
    }

    public ResponseRegistroFinanceiroDTO getRegistroFinanceiroPorId(Long id) throws EntityNotFoundException {
        RegistroFinanceiro registroFinanceiro = registroFinanceiroRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("RegistroFinanceiro não encontrada"));

        ResponseRegistroFinanceiroDTO registroDTO = mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(registroFinanceiro);

        return registroDTO;
    }

    @Transactional
    public ResponseRegistroFinanceiroDTO createRegistroFinanceiro(RequestRegistroFinanceiroDTO registroDTO) throws EntityNotFoundException {
        Usuario user = usuarioRepository.findById(registroDTO.getPessoaId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cliente ou Fornecedor não encontrado com ID: " + registroDTO.getPessoaId()
                ));
        RegistroFinanceiro registroFinanceiro = mapper.mapRegistroFinanceiroDtoToRegistroFinanceiro(registroDTO);
        registroFinanceiro.setPessoa(user);
        registroFinanceiro.setStatus(definirStatusPagamento(registroDTO.getDataPagamento(), registroDTO.getDataPrevista()));
        registroFinanceiro = registroFinanceiroRepository.save(registroFinanceiro);
        ResponseRegistroFinanceiroDTO rfResponse = mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(registroFinanceiro);
        rfResponse.setPessoa(user);
        return rfResponse;
    }

    @Transactional
    public ResponseRegistroFinanceiroDTO updateRegistroFinanceiro(Long id, RequestRegistroFinanceiroDTO registroDTO) throws EntityNotFoundException {
        RegistroFinanceiro registroFinanceiro = registroFinanceiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RegistroFinanceiro não encontrada com ID: " + id));
        mapper.updateRegistroFinanceiroFromDTO(registroDTO, registroFinanceiro);
        registroFinanceiro = registroFinanceiroRepository.save(registroFinanceiro);
        ResponseRegistroFinanceiroDTO rfResponse = mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(registroFinanceiro);
        return rfResponse;
    }

    @Transactional
    public boolean updateQuitar(Long id) throws EntityNotFoundException {
        RegistroFinanceiro registroFinanceiro = registroFinanceiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RegistroFinanceiro com ID " + id + " não encontrada!"));
        registroFinanceiro.setStatus(ContaStatus.PAGO);
        registroFinanceiro.setDataPagamento(LocalDate.now());
        registroFinanceiroRepository.save(registroFinanceiro);
        return true;
    }

    @Transactional
    public void deleteRegistroFinanceiro(Long id){
        registroFinanceiroRepository.deleteById(id);
    }

    public List<ResponseRegistroFinanceiroDTO> getPorStatus(ContaStatus contaStatus){
        List<RegistroFinanceiro> registroFinanceiro = registroFinanceiroRepository.findByStatus(contaStatus);
        return registroFinanceiro.stream()
                .map(rf -> mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(rf))
                .collect(Collectors.toList());
    }

    public List<ResponseRegistroFinanceiroDTO> getRegistroFinanceiroPorTipo(ContaTipo tipo) {
        List<RegistroFinanceiro> registroFinanceiro = registroFinanceiroRepository.findByTipo(tipo);
        return registroFinanceiro.stream()
                .map(rf -> mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(rf))
                .collect(Collectors.toList());
    }

    public List<ResponseRegistroFinanceiroDTO> getRegistroFinanceiroPorCategoria(String categoria){
        List<RegistroFinanceiro> registroFinanceiro = registroFinanceiroRepository.findByCategoria(categoria);
        return registroFinanceiro.stream()
                .map(rf -> mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(rf))
                .collect(Collectors.toList());
    }

    public List<ResponseRegistroFinanceiroDTO> getRegistroFinanceiroPorPeriodo(LocalDate inicio, LocalDate fim){
        List<RegistroFinanceiro> registroFinanceiro = registroFinanceiroRepository.findByDataPagamentoBetween(inicio,fim);
        return registroFinanceiro.stream()
                .map(rf -> mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(rf))
                .collect(Collectors.toList());
    }


    public ContaStatus definirStatusPagamento(LocalDate pagamento, LocalDate prevista) {

        LocalDate hoje = LocalDate.now();

        // Regra 1: Não permitir pagamento futuro
        if (pagamento != null && pagamento.isAfter(hoje)) {
            return ContaStatus.PENDENTE;
        }

        // Regra 2: Se foi pago → PAGO
        if (pagamento != null) {
             return ContaStatus.PAGO;
        }

        // Regra 3: Sem pagamento → verificar vencimento
        if (prevista.isBefore(hoje)) {
             return ContaStatus.ATRASADO;
        } else {
            return ContaStatus.PENDENTE;
        }
    }
}
