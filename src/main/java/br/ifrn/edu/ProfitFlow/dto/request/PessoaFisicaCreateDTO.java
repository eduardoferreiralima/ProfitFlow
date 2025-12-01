package br.ifrn.edu.ProfitFlow.dto.request;

import br.ifrn.edu.ProfitFlow.models.enums.PessoaGenero;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Objeto utilizado para criação de uma Pessoa Física no sistema")
public class PessoaFisicaCreateDTO {

    @Schema(description = "Nome completo da pessoa",
            example = "Maria Souza")
    private String nome;

    @Schema(description = "Email válido para cadastro e login",
            example = "maria.souza@email.com")
    private String email;

    @Schema(description = "Senha de acesso à conta",
            example = "SenhaSegura123")
    private String senha;

    @Schema(description = "Telefone da pessoa, com DDD",
            example = "(84) 98822-3344")
    private String telefone;

    @Schema(description = "Endereço residencial completo",
            example = "Rua das Flores, 123, Lagoa Nova, Natal - RN")
    private String endereco;

    @Schema(description = "CPF da pessoa física, apenas números ou com formatação",
            example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Gênero da pessoa",
            example = "FEMININO")
    private PessoaGenero genero;
}

