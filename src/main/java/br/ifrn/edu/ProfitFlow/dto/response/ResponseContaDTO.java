package br.ifrn.edu.ProfitFlow.dto.response;

import br.ifrn.edu.ProfitFlow.models.ClienteFornecedor;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class ResponseContaDTO {

    private int id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private ContaTipo tipo;
    private ContaStatus status;
    private ClienteFornecedor clienteFornecedor;

}
