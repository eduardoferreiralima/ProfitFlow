package br.ifrn.edu.ProfitFlow.dto.response;

import br.ifrn.edu.ProfitFlow.models.ClienteFornecedor;
import br.ifrn.edu.ProfitFlow.models.enums.ReceitaStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class ResponseReceitaDTO {
    private int id;

    private String descricao;
    private String categoria;

    private ReceitaStatus status;

    private BigDecimal valor;

    private LocalDate dataRecebimento;
    private LocalDate dataPrevista;

    private ClienteFornecedor cliente;
}
