package br.ifrn.edu.ProfitFlow.dto.request;

import br.ifrn.edu.ProfitFlow.models.enums.PessoaGenero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaFisicaCreateDTO {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    private String cpf;
    private PessoaGenero genero;
}
