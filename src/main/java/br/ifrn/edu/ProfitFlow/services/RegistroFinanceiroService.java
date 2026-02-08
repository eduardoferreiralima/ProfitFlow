package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.request.RequestRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.exception.BusinessRuleException;
import br.ifrn.edu.ProfitFlow.mapper.MapperRegistroFinanceiro;
import br.ifrn.edu.ProfitFlow.models.RegistroFinanceiro;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.repository.RegistroFinanceiroRepository;
import br.ifrn.edu.ProfitFlow.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
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


    @Cacheable(value = "getAllRF", key = "#usuarioId")
    public List<ResponseRegistroFinanceiroDTO> getRegistroFinanceiro(Long usuarioId) {
        List<RegistroFinanceiro> rfEntities = registroFinanceiroRepository.findByPessoaId(usuarioId);
        List<ResponseRegistroFinanceiroDTO> registroDTO = mapper.toResponseRegistroFinanceiroDTOList(rfEntities);
        return registroDTO;
    }

    @Cacheable(value = "getRFById", key = "#usuarioId + '-' + #id")
    public ResponseRegistroFinanceiroDTO getRegistroFinanceiroPorId(Long id, Long usuarioId) throws EntityNotFoundException {
        RegistroFinanceiro registroFinanceiro = registroFinanceiroRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("RegistroFinanceiro não encontrada"));
        if (registroFinanceiro.getPessoa().getId().equals(usuarioId)) {
            ResponseRegistroFinanceiroDTO registroDTO = mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(registroFinanceiro);
            return registroDTO;
        } else throw new BusinessRuleException("Não é possível acessar os dados financeiros de outro usuário!");
    }

    @Transactional
    @CacheEvict(value = {"getAllRF", "getRFById", "getRFByTipo", "getRFByCategoria", "getRFByPeriodo", "fluxoCaixa", "balancoMensal", "situacaoFinanceira"}, allEntries = true)
    public ResponseRegistroFinanceiroDTO createRegistroFinanceiro(RequestRegistroFinanceiroDTO registroDTO, Long usuarioId) throws EntityNotFoundException {
        Usuario user = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cliente ou Fornecedor não encontrado com ID: " + usuarioId
                ));
        RegistroFinanceiro registroFinanceiro = mapper.mapRegistroFinanceiroDtoToRegistroFinanceiro(registroDTO);
        registroFinanceiro.setPessoa(user);
        registroFinanceiro.setStatus(definirStatusPagamento(registroDTO.getDataPagamento(), registroDTO.getDataPrevista(), registroDTO.getTipo()));
        registroFinanceiro = registroFinanceiroRepository.save(registroFinanceiro);
        ResponseRegistroFinanceiroDTO rfResponse = mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(registroFinanceiro);
        return rfResponse;
    }

    @Transactional
    @CacheEvict(value = {"getAllRF", "getRFById", "getRFByTipo", "getRFByCategoria", "getRFByPeriodo", "fluxoCaixa", "balancoMensal", "situacaoFinanceira"}, allEntries = true)
    public ResponseRegistroFinanceiroDTO updateRegistroFinanceiro(Long id, RequestRegistroFinanceiroDTO registroDTO, Long usuarioId) throws EntityNotFoundException {
        RegistroFinanceiro registroFinanceiro = registroFinanceiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RegistroFinanceiro não encontrada com ID: " + id));
        if (registroFinanceiro.getPessoa().getId().equals(usuarioId)) {
            mapper.updateRegistroFinanceiroFromDTO(registroDTO, registroFinanceiro);
            registroFinanceiro.setStatus(definirStatusPagamento(registroDTO.getDataPagamento(), registroDTO.getDataPrevista(), registroDTO.getTipo()));
            registroFinanceiro = registroFinanceiroRepository.save(registroFinanceiro);
            ResponseRegistroFinanceiroDTO rfResponse = mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(registroFinanceiro);
            return rfResponse;
        }else throw new BusinessRuleException("Não é possível alterar um registro financeiro de outro usuário!");
    }

    @Transactional
    @CacheEvict(value = {"getAllRF", "getRFById", "getRFByTipo", "getRFByCategoria", "getRFByPeriodo", "fluxoCaixa", "balancoMensal", "situacaoFinanceira"}, allEntries = true)
    public boolean updateQuitar(Long id, Long usuarioId) throws EntityNotFoundException {
        RegistroFinanceiro registroFinanceiro = registroFinanceiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RegistroFinanceiro com ID " + id + " não encontrada!"));
        if (registroFinanceiro.getDataPagamento() != null && registroFinanceiro.getStatus() == ContaStatus.PAGO) {
            throw new BusinessRuleException("O Registro Financeiro já está quitado! \n" + "Data do pagamento: " + registroFinanceiro.getDataPagamento());
        }
        if(registroFinanceiro.getPessoa().getId().equals(usuarioId)){
            registroFinanceiro.setStatus(ContaStatus.PAGO);
            registroFinanceiro.setDataPagamento(LocalDate.now());
            registroFinanceiroRepository.save(registroFinanceiro);
            return true;
        }else throw new BusinessRuleException("Não é possível alterar um registro financeiro de outro usuário!");

    }

    @Transactional
    @CacheEvict(value = {"getAllRF", "getRFById", "getRFByTipo", "getRFByCategoria", "getRFByPeriodo", "fluxoCaixa", "balancoMensal", "situacaoFinanceira"}, allEntries = true)
    public void deleteRegistroFinanceiro(Long id, Long usuarioId){
        RegistroFinanceiro registroFinanceiro = registroFinanceiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro não encontrado!"));
        if (registroFinanceiro.getPessoa().getId().equals(usuarioId)) {
            registroFinanceiroRepository.deleteById(id);
        }else throw new BusinessRuleException("Não é possível deletar um registro financeiro de outro usuário!");

    }

    public List<ResponseRegistroFinanceiroDTO> getPorStatus(ContaStatus contaStatus, Long usuarioId){
        List<RegistroFinanceiro> registroFinanceiro = registroFinanceiroRepository.findByStatusAndPessoaId(contaStatus, usuarioId);
        return registroFinanceiro.stream()
                .map(rf -> mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(rf))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "getRFByTipo", key = "#usuarioId + '-' + #tipo")
    public List<ResponseRegistroFinanceiroDTO> getRegistroFinanceiroPorTipo(ContaTipo tipo, Long usuarioId) throws BadRequestException {
        List<RegistroFinanceiro> registroFinanceiro = registroFinanceiroRepository.findByTipoAndPessoaId(tipo, usuarioId);
        if (tipo != ContaTipo.RECEITA && tipo != ContaTipo.DESPESA) {
            throw new BadRequestException("O tipo " + tipo + " é inválido!\n"+"use RECEITA ou DESPESA");
        }
        return registroFinanceiro.stream()
                .map(rf -> mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(rf))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "getRFByCategoria", key = "#usuarioId + '-' + #categoria")
    public List<ResponseRegistroFinanceiroDTO> getRegistroFinanceiroPorCategoria(String categoria, Long usuarioId){
        List<RegistroFinanceiro> registroFinanceiro = registroFinanceiroRepository.findByCategoriaAndPessoaId(categoria, usuarioId);
        return registroFinanceiro.stream()
                .map(rf -> mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(rf))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "getRFByPeriodo", key = "#usuarioId + '-' + #inicio.toString() + '-' + #fim.toString()")
    public List<ResponseRegistroFinanceiroDTO> getRegistroFinanceiroPorPeriodo(LocalDate inicio, LocalDate fim, Long usuarioId){
        List<RegistroFinanceiro> registroFinanceiro = registroFinanceiroRepository.findByDataPagamentoBetweenAndPessoaId(inicio,fim, usuarioId);
        return registroFinanceiro.stream()
                .map(rf -> mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(rf))
                .collect(Collectors.toList());
    }


    public ContaStatus definirStatusPagamento(LocalDate pagamento, LocalDate prevista, ContaTipo tipo) {
        LocalDate hoje = LocalDate.now();

        if (pagamento != null) {
            if (pagamento.isAfter(hoje)) {
                // Novo Status: A transação já tem data, mas ainda não ocorreu.
                return ContaStatus.AGENDADO;
            } else {
                // Data passada ou hoje: PAGO/RECEBIDO
                return (tipo == ContaTipo.DESPESA) ? ContaStatus.PAGO : ContaStatus.RECEBIDO;
            }
        }

        if (prevista.isBefore(hoje)) {
            return ContaStatus.ATRASADO;
        }
        return ContaStatus.PENDENTE;
    }
}
