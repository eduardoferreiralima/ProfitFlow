package br.ifrn.edu.ProfitFlow.dto.request;


import br.ifrn.edu.ProfitFlow.annotations.CPFouCNPJ;
import br.ifrn.edu.ProfitFlow.models.enums.PessoaTipo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestPessoaDTO {
    private static final String REGEX_TELEFONE_BRASIL = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$";

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 5, max = 80, message = "O nome deve ter entre 5 e 80 caracteres")
    private String nome;

    @Email(message = "Formato de email inválido")
    @NotEmpty(message = "O E-mail é obrigatório!")
    private String email;

    private String cpfCnpj;

    @Pattern(regexp = REGEX_TELEFONE_BRASIL, message = "Telefone inválido. Use um formato como (XX) 9XXXX-XXXX.")
    private String telefone;

    private String endereco;
    @Enumerated(EnumType.STRING)
    private PessoaTipo pessoa;
}
