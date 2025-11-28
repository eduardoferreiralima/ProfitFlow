package br.ifrn.edu.ProfitFlow.dto.request;

import br.ifrn.edu.ProfitFlow.models.enums.ReceitaStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RequestDespesaDTO {
    private String descricao;
    private String categoria;
    private ReceitaStatus status;
    private BigDecimal valor;
    private LocalDate dataPagamento;
    private LocalDate dataVencimento;
    private Integer fornecedorId;
}
