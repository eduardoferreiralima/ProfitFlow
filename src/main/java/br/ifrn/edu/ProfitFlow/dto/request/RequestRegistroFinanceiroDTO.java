package br.ifrn.edu.ProfitFlow.dto.request;

import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.models.enums.FormaPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private String categoria;

    @Schema(description = "Tipo da conta (RECEITA ou DESPESA)",
            example = "DESPESA")
    private ContaTipo tipo;

    @Schema(description = "Status atual da conta",
            example = "PENDENTE")
    private ContaStatus status;

    @Schema(description = "Valor monetário da transação",
            example = "245.90")
    private BigDecimal valor;

    @Schema(description = "Data prevista para pagamento ou recebimento",
            example = "2025-11-30")
    private LocalDate dataPrevista;

    @Schema(description = "Data efetiva em que o pagamento ou recebimento ocorreu",
            example = "2025-11-28",
            nullable = true)
    private LocalDate dataPagamento;

    @Schema(description = "ID da pessoa associada ao registro financeiro",
            example = "12")
    private Long pessoaId;

    @Schema(description = "Forma de pagamento utilizada",
            example = "PIX")
    private FormaPagamento formaPagamento;
}
