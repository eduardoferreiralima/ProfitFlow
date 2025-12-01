package br.ifrn.edu.ProfitFlow.dto.response;

import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@Schema(description = "Objeto de resposta contendo as informações completas de um registro financeiro")
public class ResponseRegistroFinanceiroDTO {

    @Schema(description = "Identificador único do registro financeiro",
            example = "150")
    private Long id;

    @Schema(description = "Descrição da movimentação financeira",
            example = "Pagamento da conta de energia")
    private String descricao;

    @Schema(description = "Categoria atribuída ao registro financeiro",
            example = "Residência")
    private String categoria;

    @Schema(description = "Tipo do registro (RECEITA ou DESPESA)",
            example = "DESPESA")
    private ContaTipo tipo;

    @Schema(description = "Status atual da conta (PENDENTE, ATRASADO, PAGO)",
            example = "PENDENTE")
    private ContaStatus status;

    @Schema(description = "Valor monetário do registro financeiro",
            example = "245.90")
    private BigDecimal valor;

    @Schema(description = "Data prevista para pagamento ou recebimento",
            example = "11/30/2025")
    private LocalDate dataPrevista;

    @Schema(description = "Data em que o pagamento ou recebimento foi realizado, caso exista",
            example = "11/25/2025",
            nullable = true)
    private LocalDate dataPagamento;

    @Schema(description = "Informações da pessoa associada a este registro financeiro")
    private Usuario pessoa;
}

