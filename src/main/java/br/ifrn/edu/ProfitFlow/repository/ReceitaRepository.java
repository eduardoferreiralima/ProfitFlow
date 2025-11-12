package br.ifrn.edu.ProfitFlow.repository;

import br.ifrn.edu.ProfitFlow.models.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Integer> {
}
