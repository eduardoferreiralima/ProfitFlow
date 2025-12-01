package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.request.RequestRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.mapper.MapperRegistroFinanceiro;
import br.ifrn.edu.ProfitFlow.models.PessoaFisica;
import br.ifrn.edu.ProfitFlow.models.RegistroFinanceiro;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.repository.RegistroFinanceiroRepository;
import br.ifrn.edu.ProfitFlow.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class RegistroFinanceiroServiceTest {

    @Mock
    private RegistroFinanceiroRepository registroFinanceiroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private MapperRegistroFinanceiro mapper;

    @InjectMocks
    private RegistroFinanceiroService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRegistroFinanceiro_Sucesso() throws Exception {
        // Arrange
        RequestRegistroFinanceiroDTO dto = new RequestRegistroFinanceiroDTO();
        dto.setPessoaId(1L);

        Usuario usuario = new PessoaFisica();
        usuario.setId(1L);

        RegistroFinanceiro registro = new RegistroFinanceiro();
        RegistroFinanceiro registroSalvo = new RegistroFinanceiro();
        ResponseRegistroFinanceiroDTO response = new ResponseRegistroFinanceiroDTO();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(mapper.mapRegistroFinanceiroDtoToRegistroFinanceiro(dto)).thenReturn(registro);
        when(registroFinanceiroRepository.save(registro)).thenReturn(registroSalvo);
        when(mapper.mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(registroSalvo)).thenReturn(response);
        dto.setDataPrevista(LocalDate.now());


        // Act
        ResponseRegistroFinanceiroDTO result = service.createRegistroFinanceiro(dto);

        // Assert
        assertNotNull(result);
        verify(registroFinanceiroRepository).save(registro);
    }

    @Test
    void testCreateRegistroFinanceiro_UsuarioNaoEncontrado() {
        RequestRegistroFinanceiroDTO dto = new RequestRegistroFinanceiroDTO();
        dto.setPessoaId(99L);

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.createRegistroFinanceiro(dto));
    }

    @Test
    void testUpdateQuitar_Sucesso() throws Exception {
        RegistroFinanceiro registro = new RegistroFinanceiro();
        registro.setId(1L);

        when(registroFinanceiroRepository.findById(1L)).thenReturn(Optional.of(registro));

        boolean result = service.updateQuitar(1L);

        assertTrue(result);
        assertEquals(ContaStatus.PAGO, registro.getStatus());
        assertEquals(LocalDate.now(), registro.getDataPagamento());
        verify(registroFinanceiroRepository).save(registro);
    }

    @Test
    void testUpdateQuitar_RegistroNaoEncontrado() {
        when(registroFinanceiroRepository.findById(77L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> service.updateQuitar(77L));
    }

    @Test
    void testDeleteRegistroFinanceiro() {
        Long id = 10L;

        service.deleteRegistroFinanceiro(id);

        verify(registroFinanceiroRepository).deleteById(id);
    }
}
