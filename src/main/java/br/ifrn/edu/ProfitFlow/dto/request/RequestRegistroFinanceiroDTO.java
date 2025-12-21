package br.ifrn.edu.ProfitFlow.dto.request;

import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.models.enums.FormaPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@Schema(description = "Objeto utilizado para criação ou atualização de um Registro Financeiro (contas a pagar ou receber)")
public class RequestRegistroFinanceiroDTO {

    @Schema(description = "Descrição detalhada do registro financeiro",
            example = "Pagamento da conta de energia")
    private String descricao;

    @Schema(description = "Categoria do registro financeiro",
            example = "Residência")
    @NotBlank(message = "A categoria é obrigatória")
    private String categoria;

    @Schema(description = "Tipo da conta (RECEITA ou DESPESA)",
            example = "DESPESA")
    @NotNull(message = "O tipo de conta (RECEITA/DESPESA) é obrigatório")
    private ContaTipo tipo;

    @Schema(description = "Valor monetário da transação",
            example = "245.90")
    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser no mínimo 0.01")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
    private BigDecimal valor;

    @Schema(description = "Data prevista para pagamento ou recebimento",
            example = "2025-11-30")
    @NotNull(message = "A data prevista é obrigatória")
    @FutureOrPresent(message = "A data prevista não pode ser retroativa")
    private LocalDate dataPrevista;

    @Schema(description = "Data efetiva em que o pagamento ou recebimento ocorreu",
            example = "2025-11-28",
            nullable = true)
    @PastOrPresent(message = "A data de pagamento não pode ser uma data futura")
    private LocalDate dataPagamento;

    @Schema(description = "ID da pessoa associada ao registro financeiro",
            example = "12")
    @NotNull(message = "O ID da pessoa é obrigatório")
    private Long pessoaId;

    @Schema(description = "Forma de pagamento utilizada",
            example = "PIX")
    @NotNull(message = "A forma de pagamento é obrigatória")
    private FormaPagamento formaPagamento;
}
