package br.ifrn.edu.ProfitFlow.repository;

import br.ifrn.edu.ProfitFlow.models.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {
}
