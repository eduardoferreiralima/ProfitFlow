package br.ifrn.edu.ProfitFlow.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Getter @Setter
public class PessoaJuridica extends Usuario {

    @CNPJ(message = "CNPJ Inválido!")
    @Column(unique = true)
    @NotBlank(message = "O CNPJ é obrigatório!")
    private String cnpj;
}