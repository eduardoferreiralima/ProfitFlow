package br.ifrn.edu.ProfitFlow.models;

import br.ifrn.edu.ProfitFlow.models.enums.ReceitaStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String descricao;
    private String categoria;

    @Enumerated(EnumType.STRING)
    private ReceitaStatus status;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    private LocalDate dataRecebimento;
    private LocalDate dataPrevista;



    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
