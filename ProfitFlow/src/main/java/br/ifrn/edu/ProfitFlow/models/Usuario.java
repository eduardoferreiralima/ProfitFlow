package br.ifrn.edu.ProfitFlow.models;

import br.ifrn.edu.ProfitFlow.models.enums.UsuarioRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String email;

    private String senha;

    @Enumerated(EnumType.STRING)
    private UsuarioRole role;

    private boolean ativo;

    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

}
