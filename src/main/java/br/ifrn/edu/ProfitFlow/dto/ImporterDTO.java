package br.ifrn.edu.ProfitFlow.dto;

import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.models.enums.FormaPagamento;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class ImporterDTO {
    private ContaTipo tipoLancamento;
    private BigDecimal valor;
    private FormaPagamento formaPagamento;
    private String categoria;
    private String nomeClienteFornecedor;
    private String cpfCnpj;
    private LocalDate dataPrevista;
    private LocalDate dataPagamento;
}
