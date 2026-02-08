package br.ifrn.edu.ProfitFlow.repository;

import br.ifrn.edu.ProfitFlow.dto.FluxoCaixaDTO;
import br.ifrn.edu.ProfitFlow.models.RegistroFinanceiro;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.ifrn.edu.ProfitFlow.dto.FluxoCaixaDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RegistroFinanceiroRepository extends JpaRepository<RegistroFinanceiro, Long> {
    List<RegistroFinanceiro> findByStatus(ContaStatus status);
    List<RegistroFinanceiro> findByTipo(ContaTipo tipo);
    List<RegistroFinanceiro> findByDataPagamentoBetween(LocalDate inicio, LocalDate fim);
    List<RegistroFinanceiro> findByCategoria(String categoria);

    @Query("""
    SELECT new br.ifrn.edu.ProfitFlow.dto.FluxoCaixaDTO(
        r.dataPagamento,
        SUM(CASE WHEN r.tipo = 'RECEITA' THEN r.valor ELSE 0 END),
        SUM(CASE WHEN r.tipo = 'DESPESA' THEN r.valor ELSE 0 END),
        SUM(CASE WHEN r.tipo = 'RECEITA' THEN r.valor ELSE -r.valor END)
    )
    FROM RegistroFinanceiro r
    WHERE r.dataPagamento BETWEEN :inicio AND :fim
    AND r.status = 'PAGO'
    GROUP BY r.dataPagamento
    ORDER BY r.dataPagamento ASC
""")
    List<FluxoCaixaDTO> gerarFluxoCaixa(LocalDate inicio, LocalDate fim);

    @Query("SELECT SUM(r.valor) FROM RegistroFinanceiro r WHERE r.tipo = :tipo AND r.dataPrevista BETWEEN :inicio AND :fim")
    BigDecimal sumValorByTipoAndData(ContaTipo tipo, LocalDate inicio, LocalDate fim);

    @Query("SELECT SUM(r.valor) FROM RegistroFinanceiro r WHERE r.tipo = :tipo AND r.status = :status")
    BigDecimal sumValorByTipoAndStatus(ContaTipo tipo, ContaStatus status);

    @Query("SELECT SUM(CASE WHEN r.tipo = 'RECEITA' THEN r.valor ELSE -r.valor END) FROM RegistroFinanceiro r WHERE r.status = :status AND r.dataPagamento <= :data")
    BigDecimal calcularSaldoAteHoje(LocalDate data, ContaStatus status);
}
