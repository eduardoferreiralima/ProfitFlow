package br.ifrn.edu.ProfitFlow.dto;

import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.models.enums.FormaPagamento;
import br.ifrn.edu.ProfitFlow.models.enums.PessoaTipo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter @Setter
public class ImporterDTO {
    private ContaTipo tipoLancamento;
    private String descricao;
    private BigDecimal valor;
    private ContaStatus status;
    private FormaPagamento formaPagamento;
    private String categoria;
    private String nomeClienteFornecedor;
    private PessoaTipo tipoClienteFornecedor;
    private String cpfCnpj;
    private String email;
    private LocalDate dataPrevista;
    private LocalDate dataPagamento;
    private String observacoes;

}
