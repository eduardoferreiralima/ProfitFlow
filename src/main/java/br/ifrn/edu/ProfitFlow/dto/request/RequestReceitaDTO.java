package br.ifrn.edu.ProfitFlow.dto.request;

import br.ifrn.edu.ProfitFlow.models.enums.ReceitaStatus;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RequestReceitaDTO {
    private String descricao;
    private String categoria;
    private ReceitaStatus status;
    private BigDecimal valor;
    private LocalDate dataRecebimento;
    private LocalDate dataPrevista;
    private Integer clienteId;
}
