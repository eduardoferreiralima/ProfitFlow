package br.ifrn.edu.ProfitFlow.dto;

import java.math.BigDecimal;

public record BalancoMensalDTO(
        BigDecimal totalReceitas,
        BigDecimal totalDespesas,
        BigDecimal lucroPrejuizo) {}
