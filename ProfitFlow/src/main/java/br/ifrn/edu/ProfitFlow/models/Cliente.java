package br.ifrn.edu.ProfitFlow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String cpfCnpj;
    private String telefone;
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
