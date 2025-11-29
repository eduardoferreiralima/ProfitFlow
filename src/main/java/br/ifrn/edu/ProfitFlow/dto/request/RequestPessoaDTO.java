package br.ifrn.edu.ProfitFlow.dto.request;

import br.ifrn.edu.ProfitFlow.models.enums.PessoaTipo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO para cadastro ou atualização de uma pessoa (Cliente ou Fornecedor)")
public class RequestPessoaDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 5, max = 80, message = "O nome deve ter entre 5 e 80 caracteres")
    @Schema(description = "Nome completo da pessoa", example = "João da Silva", required = true)
    private String nome;

    @Email(message = "Formato de email inválido")
    @NotEmpty(message = "O E-mail é obrigatório!")
    @Schema(description = "E-mail da pessoa", example = "joao@email.com", required = true)
    private String email;

    @NotBlank
    @Schema(description = "CPF ou CNPJ da pessoa", example = "123.456.789-00", required = true)
    private String cpfCnpj;

    @Schema(description = "Telefone de contato", example = "(84) 99999-9999")
    private String telefone;

    @Schema(description = "Endereço completo da pessoa", example = "Rua das Flores, 123, Natal/RN")
    private String endereco;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo da pessoa", example = "CLIENTE", allowableValues = {"CLIENTE", "FORNECEDOR"})
    private PessoaTipo pessoa;
}
