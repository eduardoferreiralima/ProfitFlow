package br.ifrn.edu.ProfitFlow.repository;

import br.ifrn.edu.ProfitFlow.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNome(String nome);
}
