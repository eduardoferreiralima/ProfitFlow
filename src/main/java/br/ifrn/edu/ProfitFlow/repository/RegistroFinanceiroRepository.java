package br.ifrn.edu.ProfitFlow.repository;

import br.ifrn.edu.ProfitFlow.models.RegistroFinanceiro;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RegistroFinanceiroRepository extends JpaRepository<RegistroFinanceiro, Long> {
    List<RegistroFinanceiro> findByStatus(ContaStatus status);
    List<RegistroFinanceiro> findByTipo(ContaTipo tipo);
    List<RegistroFinanceiro> findByDataPagamentoBetween(LocalDate inicio, LocalDate fim);
    List<RegistroFinanceiro> findByCategoria(String categoria);
}
