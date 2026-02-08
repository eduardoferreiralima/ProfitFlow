package br.ifrn.edu.ProfitFlow.controller.docs;

import br.ifrn.edu.ProfitFlow.dto.FluxoCaixaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Relatorios", description = "Operações relacionadas a relatórios financeiros")
public interface RelatoriosControllerDocs {

    @Operation(summary = "Retorna uma lista do fluxo de caixa diário", description = "Esta consulta consolida o Regime de Caixa. Registros com status PENDENTE ou ATRASADO são desconsiderados, garantindo que o relatório reflita apenas o dinheiro que efetivamente transitou pela conta do usuário no período selecionado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fluxo de caixa retornado com sucesso"),
            @ApiResponse(responseCode = "204", description = "Não há transações no período informado")
    })
    ResponseEntity<List<FluxoCaixaDTO>> getFluxoCaixa(
            @Parameter(description = "Data inicial", example = "2024-01-01", schema = @Schema(type = "string", format = "date"))
            @RequestParam LocalDate inicio,
            @Parameter(description = "Data final", example = "2024-12-31", schema = @Schema(type = "string", format = "date"))
            @RequestParam LocalDate fim);

    @Operation(summary = "Retorna o balanço mensal consolidado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balanço mensal retornado com sucesso"),
            @ApiResponse(responseCode = "204", description = "Não há dados para o mês/ano informado")
    })
    ResponseEntity<?> getBalancoMensal(
            @Parameter(description = "Data", example = "2024-01-01", schema = @Schema(type = "string", format = "date"))
            @RequestParam LocalDate data
            );

    @Operation(summary = "Retorna o resumo geral da situação financeira")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resumo financeiro retornado com sucesso")
    })
    ResponseEntity<?> getSituacaoFinanceira();
}
