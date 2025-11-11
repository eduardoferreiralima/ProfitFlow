package br.ifrn.edu.ProfitFlow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String cnpj;
    private String telefone;
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
