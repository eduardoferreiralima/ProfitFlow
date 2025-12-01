package br.ifrn.edu.ProfitFlow.models;

import br.ifrn.edu.ProfitFlow.annotations.PhoneIsValid;
import br.ifrn.edu.ProfitFlow.models.enums.UsuarioRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 5, max = 80, message = "O nome deve ter entre 5 e 80 caracteres")
    private String nome;

    @Email(message = "Formato de email inválido")
    private String email;

    private String senha;

    @PhoneIsValid
    private String telefone;

    private String endereco;

    @Enumerated(EnumType.STRING)
    private UsuarioRole role;

    private boolean ativo;

    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

}


