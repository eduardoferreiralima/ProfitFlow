package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.request.RequestRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.models.PessoaFisica;
import br.ifrn.edu.ProfitFlow.models.enums.UserRole;
import br.ifrn.edu.ProfitFlow.repository.PessoaFisicaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
public class CacheIntegrationTest {

    @Autowired
    private RegistroFinanceiroService service;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private PessoaFisicaRepository usuarioRepository;

    @Test
    public void deveArmazenarELimparOCache() {
        PessoaFisica usuario = new PessoaFisica();
        usuario.setNome("Eduardo");
        usuario.setEmail("eduardo@teste.com");
        usuario.setPassword("123456");
        usuario.setRole(UserRole.USUARIO);
        usuario.setCpf("235.328.120-61");
        usuario.setAtivo(true);
        usuario = usuarioRepository.save(usuario);

        Long usuarioId = usuario.getId();

        cacheManager.getCache("getAllRF").clear();

        service.getRegistroFinanceiro(usuarioId);
        assertNotNull(cacheManager.getCache("getAllRF").get(usuarioId));

        RequestRegistroFinanceiroDTO dto = new RequestRegistroFinanceiroDTO();
        dto.setValor(new BigDecimal("100.00"));
        dto.setDescricao("Teste Cache");
        dto.setDataPrevista(LocalDate.now().plusDays(5));
        service.createRegistroFinanceiro(dto, usuarioId);

        assertNull(cacheManager.getCache("getAllRF").get(usuarioId));
    }
}