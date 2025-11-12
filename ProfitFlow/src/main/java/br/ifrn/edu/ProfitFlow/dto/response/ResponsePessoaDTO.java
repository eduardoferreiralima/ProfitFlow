package br.ifrn.edu.ProfitFlow.dto.response;

import br.ifrn.edu.ProfitFlow.models.enums.PessoaTipo;
import br.ifrn.edu.ProfitFlow.models.enums.UsuarioRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
public class ResponsePessoaDTO {
    private Integer id;
    private String nome;
    private String email;
    private String cpfCnpj;
    private String telefone;
    private String endereco;
    @Enumerated(EnumType.STRING)
    private PessoaTipo pessoa;

    @Enumerated(EnumType.STRING)
    private UsuarioRole role;

    private boolean ativo;

    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
}
