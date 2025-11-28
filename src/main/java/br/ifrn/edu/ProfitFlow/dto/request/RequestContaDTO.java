package br.ifrn.edu.ProfitFlow.dto.request;

import br.ifrn.edu.ProfitFlow.models.ClienteFornecedor;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class RequestContaDTO {

    private String descricao;
    private BigDecimal valor;

    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

    private ContaTipo tipo;
    private ContaStatus status;

    private Integer clienteFornecedorId;

}
