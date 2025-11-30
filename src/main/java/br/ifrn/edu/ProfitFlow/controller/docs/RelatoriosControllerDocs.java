package br.ifrn.edu.ProfitFlow.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@Tag(name = "Relatorios", description = "Operações relacionadas a relatórios financeiros")
public interface RelatoriosControllerDocs {

    @Operation(summary = "Retorna o fluxo de caixa por período")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fluxo de caixa retornado com sucesso"),
            @ApiResponse(responseCode = "204", description = "Não há transações no período informado")
    })
    ResponseEntity<?> getFluxoCaixa(LocalDate inicio, LocalDate fim);

    @Operation(summary = "Retorna o balanço mensal consolidado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balanço mensal retornado com sucesso"),
            @ApiResponse(responseCode = "204", description = "Não há dados para o mês/ano informado")
    })
    ResponseEntity<?> getBalancoMensal(LocalDate data);

    @Operation(summary = "Retorna o resumo geral da situação financeira")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resumo financeiro retornado com sucesso")
    })
    ResponseEntity<?> getSituacaoFinanceira();
}
