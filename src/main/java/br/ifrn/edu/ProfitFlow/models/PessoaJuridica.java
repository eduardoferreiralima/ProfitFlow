package br.ifrn.edu.ProfitFlow.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Getter @Setter
public class PessoaJuridica extends Usuario {

    @CNPJ
    @Column(unique = true)
    private String cnpj;
}