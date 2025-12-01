package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.request.RequestRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.models.PessoaFisica;
import br.ifrn.edu.ProfitFlow.models.PessoaJuridica;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.repository.UsuarioRepository;
import br.ifrn.edu.ProfitFlow.repository.RegistroFinanceiroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RegistroFinanceiroServiceIntegrationTest {

    @Autowired
    private RegistroFinanceiroService service;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroFinanceiroRepository registroRepository;

    @Test
    void testCreateRegistroFinanceiro_Integracao() throws Exception {
        PessoaFisica user = new PessoaFisica();
        user.setNome("Usuario pf");
        user.setCpf("859.060.360-16");
        usuarioRepository.save(user);

        RequestRegistroFinanceiroDTO dto = new RequestRegistroFinanceiroDTO();
        dto.setPessoaId(user.getId());
        dto.setTipo(ContaTipo.RECEITA);
        dto.setValor(BigDecimal.valueOf(500.0));
        dto.setDataPrevista(LocalDate.now());
        dto.setDataPagamento(LocalDate.now());

        var response = service.createRegistroFinanceiro(dto);

        assertNotNull(response);
        assertEquals(new BigDecimal("500.0"), response.getValor());
        assertEquals(ContaStatus.PAGO, response.getStatus());
    }

    @Test
    void testUpdateQuitar_Integracao() throws Exception {
        PessoaJuridica user = new PessoaJuridica();
        user.setNome("Usuario pj");
        user.setCnpj("17.845.973/0001-24");
        usuarioRepository.save(user);

        RequestRegistroFinanceiroDTO dto = new RequestRegistroFinanceiroDTO();
        dto.setPessoaId(user.getId());
        dto.setValor(BigDecimal.valueOf(150.0));
        dto.setDataPrevista(LocalDate.now());

        var registro = service.createRegistroFinanceiro(dto);

        boolean result = service.updateQuitar(registro.getId());

        assertTrue(result);
        assertEquals(
                ContaStatus.PAGO,
                registroRepository.findById(registro.getId()).get().getStatus()
        );
    }
}
