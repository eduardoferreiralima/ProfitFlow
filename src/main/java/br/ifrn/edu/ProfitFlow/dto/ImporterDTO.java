package br.ifrn.edu.ProfitFlow.dto;

import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.models.enums.FormaPagamento;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class ImporterDTO {
    @NotNull(message = "O tipo de conta (RECEITA/DESPESA) é obrigatório")
    private ContaTipo tipoLancamento;

    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser no mínimo 0.01")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
    private BigDecimal valor;

    @NotNull(message = "A forma de pagamento é obrigatória")
    private FormaPagamento formaPagamento;

    @NotBlank(message = "A categoria é obrigatória")
    private String categoria;

    @NotBlank(message = "O nome do Cliente/Fornecedor é obrigatório")
    private String nomeClienteFornecedor;

    @NotBlank(message = "O CPF ou CNPJ do Cliente/Fornecedor é obrigatório")
    private String cpfCnpj;

    @NotNull(message = "A data prevista é obrigatória")
    @FutureOrPresent(message = "A data prevista não pode ser retroativa")
    private LocalDate dataPrevista;

    @PastOrPresent(message = "A data de pagamento não pode ser uma data futura")
    private LocalDate dataPagamento;
}
