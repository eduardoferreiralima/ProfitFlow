package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.BalancoMensalDTO;
import br.ifrn.edu.ProfitFlow.dto.FluxoCaixaDTO;
import br.ifrn.edu.ProfitFlow.dto.SituacaoFinanceiraDTO;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.repository.RegistroFinanceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private RegistroFinanceiroRepository repository;

    @Cacheable(value = "fluxoCaixa", key = "#usuarioId +  #inicio.toString() + '-' + #fim.toString()")
    public List<FluxoCaixaDTO> obterFluxoCaixa(LocalDate inicio, LocalDate fim, Long usuarioId) {
        return repository.gerarFluxoCaixa(inicio, fim, usuarioId);
    }

    @Cacheable(value = "balancoMensal", key = "#usuarioId + '-' + #dataReferencia.toString()")
    public BalancoMensalDTO obterBalancoMensal(LocalDate dataReferencia, Long usuarioId) {
        LocalDate inicio = dataReferencia.withDayOfMonth(1);
        LocalDate fim = dataReferencia.withDayOfMonth(dataReferencia.lengthOfMonth());

        BigDecimal receitas = repository.sumValorByTipoAndData(ContaTipo.RECEITA, inicio, fim, usuarioId);
        BigDecimal despesas = repository.sumValorByTipoAndData(ContaTipo.DESPESA, inicio, fim, usuarioId);

        receitas = (receitas != null) ? receitas : BigDecimal.ZERO;
        despesas = (despesas != null) ? despesas : BigDecimal.ZERO;

        return new BalancoMensalDTO(receitas, despesas, receitas.subtract(despesas));
    }

    @Cacheable(value = "situacaoFinanceira", key = "#usuarioId")
    public SituacaoFinanceiraDTO obterSituacaoFinanceira(Long usuarioId) {
        BigDecimal saldoAtual = repository.calcularSaldoAteHoje(LocalDate.now(), ContaStatus.PAGO, usuarioId);
        BigDecimal aReceber = repository.sumValorByTipoAndStatus(ContaTipo.RECEITA, ContaStatus.PENDENTE, usuarioId);

        BigDecimal aPagarPendente = repository.sumValorByTipoAndStatus(ContaTipo.DESPESA, ContaStatus.PENDENTE, usuarioId);
        BigDecimal aPagarAtrasado = repository.sumValorByTipoAndStatus(ContaTipo.DESPESA, ContaStatus.ATRASADO, usuarioId);

        return new SituacaoFinanceiraDTO(
                (saldoAtual != null) ? saldoAtual : BigDecimal.ZERO,
                (aReceber != null) ? aReceber : BigDecimal.ZERO,
                ((aPagarPendente != null) ? aPagarPendente : BigDecimal.ZERO)
                        .add((aPagarAtrasado != null) ? aPagarAtrasado : BigDecimal.ZERO)
        );
    }
}
