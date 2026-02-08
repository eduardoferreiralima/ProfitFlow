package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.request.RequestPessoaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.mapper.MapperUsuario;
import br.ifrn.edu.ProfitFlow.models.PessoaFisica;
import br.ifrn.edu.ProfitFlow.models.PessoaJuridica;
import br.ifrn.edu.ProfitFlow.repository.PessoaFisicaRepository;
import br.ifrn.edu.ProfitFlow.repository.PessoaJuridicaRepository;
import br.ifrn.edu.ProfitFlow.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PessoaJuridicaRepository pessoaJuridicaRepository;
    @Mock
    private PessoaFisicaRepository pessoaFisicaRepository;
    @Mock
    private MapperUsuario mapper;

    @InjectMocks
    private UsuarioService usuarioService;

    private RequestPessoaDTO requestDTO;

    @BeforeEach
    void setup() {
        requestDTO = new RequestPessoaDTO();
        requestDTO.setNome("Eduardo");
        requestDTO.setSenha("senha123");
    }

    @Test
    @DisplayName("Deve criar Pessoa Física com sucesso quando CPF for válido")
    void deveCriarPessoaFisicaComSucesso() throws Exception {
        // Arrange
        String cpf = "12345678901";
        requestDTO.setCpfCnpj(cpf);
        PessoaFisica pf = new PessoaFisica();

        when(passwordEncoder.encode(any())).thenReturn("senhaCripto");
        when(mapper.toEntityPessoaFisica(requestDTO)).thenReturn(pf);
        when(pessoaFisicaRepository.save(any(PessoaFisica.class))).thenReturn(pf);
        when(mapper.mapPFtoResponsePessoaDTO(any())).thenReturn(new ResponsePessoaDTO());

        // Act
        ResponsePessoaDTO result = usuarioService.createUser(requestDTO);

        // Assert
        assertNotNull(result);
        verify(pessoaFisicaRepository, times(1)).save(any());
        verify(passwordEncoder).encode("senha123");
    }

    @Test
    @DisplayName("Deve criar Pessoa Jurídica com sucesso quando CNPJ for válido")
    void deveCriarPessoaJuridicaComSucesso() throws Exception {
        // Arrange
        String cnpj = "12345678901234";
        requestDTO.setCpfCnpj(cnpj);
        PessoaJuridica pj = new PessoaJuridica();

        when(passwordEncoder.encode(any())).thenReturn("senhaCripto");
        when(mapper.toEntityPessoaJuridica(requestDTO)).thenReturn(pj);
        when(pessoaJuridicaRepository.save(any(PessoaJuridica.class))).thenReturn(pj);
        when(mapper.mapPJtoResponsePessoaDTO(any())).thenReturn(new ResponsePessoaDTO());

        // Act
        ResponsePessoaDTO result = usuarioService.createUser(requestDTO);

        // Assert
        assertNotNull(result);
        verify(pessoaJuridicaRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF/CNPJ for inválido")
    void deveLancarExcecaoDocumentoInvalido() {
        // Arrange
        requestDTO.setCpfCnpj("123"); // Tamanho errado

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.createUser(requestDTO);
        });
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao buscar ID inexistente")
    void deveLancarErroAoNaoEncontrarId() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.findById(1L);
        });
    }
}
