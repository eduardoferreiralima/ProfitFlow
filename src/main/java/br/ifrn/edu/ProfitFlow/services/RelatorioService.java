package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.BalancoMensalDTO;
import br.ifrn.edu.ProfitFlow.dto.FluxoCaixaDTO;
import br.ifrn.edu.ProfitFlow.dto.SituacaoFinanceiraDTO;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.repository.RegistroFinanceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private RegistroFinanceiroRepository repository;

    public List<FluxoCaixaDTO> obterFluxoCaixa(LocalDate inicio, LocalDate fim) {
        return repository.gerarFluxoCaixa(inicio, fim);
    }

    public BalancoMensalDTO obterBalancoMensal(LocalDate dataReferencia) {
        LocalDate inicio = dataReferencia.withDayOfMonth(1);
        LocalDate fim = dataReferencia.withDayOfMonth(dataReferencia.lengthOfMonth());

        BigDecimal receitas = repository.sumValorByTipoAndData(ContaTipo.RECEITA, inicio, fim);
        BigDecimal despesas = repository.sumValorByTipoAndData(ContaTipo.DESPESA, inicio, fim);

        // Tratamento de segurança para evitar NullPointerException
        receitas = (receitas != null) ? receitas : BigDecimal.ZERO;
        despesas = (despesas != null) ? despesas : BigDecimal.ZERO;

        return new BalancoMensalDTO(receitas, despesas, receitas.subtract(despesas));
    }

    public SituacaoFinanceiraDTO obterSituacaoFinanceira() {
        BigDecimal saldoAtual = repository.calcularSaldoAteHoje(LocalDate.now(), ContaStatus.PAGO);
        BigDecimal aReceber = repository.sumValorByTipoAndStatus(ContaTipo.RECEITA, ContaStatus.PENDENTE);

        // Somando pendentes e atrasados para as despesas
        BigDecimal aPagarPendente = repository.sumValorByTipoAndStatus(ContaTipo.DESPESA, ContaStatus.PENDENTE);
        BigDecimal aPagarAtrasado = repository.sumValorByTipoAndStatus(ContaTipo.DESPESA, ContaStatus.ATRASADO);

        return new SituacaoFinanceiraDTO(
                (saldoAtual != null) ? saldoAtual : BigDecimal.ZERO,
                (aReceber != null) ? aReceber : BigDecimal.ZERO,
                ((aPagarPendente != null) ? aPagarPendente : BigDecimal.ZERO)
                        .add((aPagarAtrasado != null) ? aPagarAtrasado : BigDecimal.ZERO)
        );
    }
}
