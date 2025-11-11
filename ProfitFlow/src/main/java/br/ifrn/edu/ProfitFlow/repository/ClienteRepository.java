package br.ifrn.edu.ProfitFlow.repository;

import br.ifrn.edu.ProfitFlow.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
