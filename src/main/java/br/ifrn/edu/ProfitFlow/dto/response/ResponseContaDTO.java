package br.ifrn.edu.ProfitFlow.dto.response;

import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class ResponseContaDTO {

    private Long id;
    private String descricao;
    private String categoria;
    private ContaTipo tipo;
    private ContaStatus status;
    private BigDecimal valor;
    private LocalDate dataPrevista;    // vencimento/prevista
    private LocalDate dataPagamento;   // pagamento/recebimento
    private Usuario pessoa;

}
