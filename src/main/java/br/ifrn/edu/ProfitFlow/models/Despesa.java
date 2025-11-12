package br.ifrn.edu.ProfitFlow.models;

import br.ifrn.edu.ProfitFlow.models.enums.DespesaStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Despesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String descricao;
    private String categoria;

    @Enumerated(EnumType.STRING)
    private DespesaStatus status;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    private LocalDate dataPagamento;
    private LocalDate dataVencimento;



    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private ClienteFornecedor fornecedor;
}
