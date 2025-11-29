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
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private String categoria;

    @Enumerated(EnumType.STRING)
    private ContaTipo tipo; // RECEITA ou DESPESA

    @Enumerated(EnumType.STRING)
    private ContaStatus status; // PENDENTE | PAGO | ATRASADO

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    private LocalDate dataPrevista;    // vencimento/prevista
    private LocalDate dataPagamento;   // pagamento/recebimento

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario pessoa;
}
