package br.ifrn.edu.ProfitFlow.models;

import br.ifrn.edu.ProfitFlow.models.enums.PessoaGenero;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Getter @Setter
public class PessoaFisica extends Usuario {

    @CPF(message = "CPF Inválido!")
    @Column(unique = true)
    @NotBlank(message = "O CPF é obrigatório!")
    private String cpf;

    @Enumerated(EnumType.STRING)
    private PessoaGenero genero;

}
