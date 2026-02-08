package br.ifrn.edu.ProfitFlow.dto;

import java.math.BigDecimal;

public record SituacaoFinanceiraDTO(
        BigDecimal saldoEmConta,
        BigDecimal contasAReceber,
        BigDecimal contasAPagar) {}
