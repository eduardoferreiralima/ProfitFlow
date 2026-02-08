package br.ifrn.edu.ProfitFlow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FluxoCaixaDTO(
        LocalDate data,
        BigDecimal entradas,
        BigDecimal saidas,
        BigDecimal saldo) {}
