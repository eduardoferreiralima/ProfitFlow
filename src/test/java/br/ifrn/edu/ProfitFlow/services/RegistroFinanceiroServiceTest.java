package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.request.RequestRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.exception.BusinessRuleException;
import br.ifrn.edu.ProfitFlow.mapper.MapperRegistroFinanceiro;
import br.ifrn.edu.ProfitFlow.models.PessoaFisica;
import br.ifrn.edu.ProfitFlow.models.RegistroFinanceiro;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.repository.RegistroFinanceiroRepository;
import br.ifrn.edu.ProfitFlow.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistroFinanceiroServiceTest {

    @Mock private RegistroFinanceiroRepository registroFinanceiroRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private MapperRegistroFinanceiro mapper;
    @InjectMocks private RegistroFinanceiroService service;

    private Usuario usuario;
    private Long usuarioId = 1L;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        usuario = new PessoaFisica();
        usuario.setId(usuarioId);
    }

    // --- TESTES DE CRIAÇÃO E ATUALIZAÇÃO ---

    @Test
    @DisplayName("Deve criar registro com sucesso")
    void testCreateRegistroFinanceiro_Sucesso() {
        RequestRegistroFinanceiroDTO dto = new RequestRegistroFinanceiroDTO();
        dto.setDataPrevista(LocalDate.now());
        dto.setTipo(ContaTipo.RECEITA);

        RegistroFinanceiro registro = new RegistroFinanceiro();
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(mapper.mapRegistroFinanceiroDtoToRegistroFinanceiro(dto)).thenReturn(registro);
        when(registroFinanceiroRepository.save(any())).thenReturn(registro);
        when(mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(any())).thenReturn(new ResponseRegistroFinanceiroDTO());

        assertNotNull(service.createRegistroFinanceiro(dto, usuarioId));
        verify(registroFinanceiroRepository).save(registro);
    }

    @Test
    @DisplayName("Deve quitar um registro do próprio usuário")
    void testUpdateQuitar_Sucesso() {
        RegistroFinanceiro registro = new RegistroFinanceiro();
        registro.setPessoa(usuario); // Dono do registro

        when(registroFinanceiroRepository.findById(10L)).thenReturn(Optional.of(registro));

        assertTrue(service.updateQuitar(10L, usuarioId));
        assertEquals(ContaStatus.PAGO, registro.getStatus());
        verify(registroFinanceiroRepository).save(registro);
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar quitar registro de outro usuário")
    void testUpdateQuitar_ErroOutroUsuario() {
        Usuario outroUsuario = new PessoaFisica();
        outroUsuario.setId(2L);
        RegistroFinanceiro registro = new RegistroFinanceiro();
        registro.setPessoa(outroUsuario);

        when(registroFinanceiroRepository.findById(10L)).thenReturn(Optional.of(registro));

        assertThrows(BusinessRuleException.class, () -> service.updateQuitar(10L, usuarioId));
    }

    // --- TESTES DE BUSCA (READ) ---

    @Test
    @DisplayName("Deve buscar registro por ID se for o dono")
    void testGetRegistroPorId_Sucesso() {
        RegistroFinanceiro registro = new RegistroFinanceiro();
        registro.setPessoa(usuario);
        when(registroFinanceiroRepository.findById(5L)).thenReturn(Optional.of(registro));
        when(mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(registro)).thenReturn(new ResponseRegistroFinanceiroDTO());

        assertNotNull(service.getRegistroFinanceiroPorId(5L, usuarioId));
    }

    @Test
    @DisplayName("Deve listar todos os registros do usuário")
    void testGetRegistroFinanceiro_Lista() {
        when(registroFinanceiroRepository.findByPessoaId(usuarioId)).thenReturn(List.of(new RegistroFinanceiro()));
        when(mapper.toResponseRegistroFinanceiroDTOList(any())).thenReturn(List.of(new ResponseRegistroFinanceiroDTO()));

        List<ResponseRegistroFinanceiroDTO> result = service.getRegistroFinanceiro(usuarioId);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Deve buscar por tipo e validar ContaTipo")
    void testGetPorTipo_Sucesso() throws Exception {
        when(registroFinanceiroRepository.findByTipoAndPessoaId(ContaTipo.RECEITA, usuarioId)).thenReturn(List.of(new RegistroFinanceiro()));

        List<ResponseRegistroFinanceiroDTO> result = service.getRegistroFinanceiroPorTipo(ContaTipo.RECEITA, usuarioId);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Deve buscar por período")
    void testGetPorPeriodo() {
        LocalDate inicio = LocalDate.now().minusDays(7);
        LocalDate fim = LocalDate.now();
        when(registroFinanceiroRepository.findByDataPagamentoBetweenAndPessoaId(inicio, fim, usuarioId)).thenReturn(List.of());

        assertNotNull(service.getRegistroFinanceiroPorPeriodo(inicio, fim, usuarioId));
    }

    // --- TESTES DE DELETE E EXCEÇÕES ---

    @Test
    @DisplayName("Deve deletar registro com sucesso")
    void testDeleteRegistroFinanceiro() {
        RegistroFinanceiro registro = new RegistroFinanceiro();
        registro.setPessoa(usuario);
        when(registroFinanceiroRepository.findById(10L)).thenReturn(Optional.of(registro));

        service.deleteRegistroFinanceiro(10L, usuarioId);
        verify(registroFinanceiroRepository).deleteById(10L);
    }

    @Test
    @DisplayName("Deve lançar erro ao deletar registro que não existe")
    void testDelete_NaoEncontrado() {
        when(registroFinanceiroRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.deleteRegistroFinanceiro(99L, usuarioId));
    }
}