package br.ifrn.edu.ProfitFlow.models;

import br.ifrn.edu.ProfitFlow.models.enums.PessoaGenero;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Getter @Setter
public class PessoaFisica extends Usuario {

    @CPF
    @Column(unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private PessoaGenero genero;

}
