package br.ifrn.edu.ProfitFlow.models;

import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Contas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String descricao;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    private ContaTipo tipo;

    @Enumerated(EnumType.STRING)
    private ContaStatus status;

    @ManyToOne
    @JoinColumn(name = "cliente_fornecedor_id")
    private ClienteFornecedor clienteFornecedor;

}
