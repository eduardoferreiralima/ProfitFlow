package br.ifrn.edu.ProfitFlow.config.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@Tag(name = "Financeiro", description = "Operações relacionadas a receitas e despesas")
public interface ReceitaControllerDocs {

    @Operation(summary = "Lista todas as transações (receitas e despesas)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de transações retornada com sucesso")
    })
    ResponseEntity<?> getPendentes();

    @Operation(summary = "Retorna uma transação específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação encontrada"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    ResponseEntity<?> getPendentes(Integer id);

    @Operation(summary = "Registra uma nova transação (RECEITA ou DESPESA)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<?> createReceita();

    @Operation(summary = "Atualiza uma transação existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    ResponseEntity<?> updateReceita(Integer id);

    @Operation(summary = "Remove uma transação")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Transação removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    ResponseEntity<?> deleteReceita(Integer id);

    @Operation(summary = "Filtra transações por período")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transações filtradas por período retornadas")
    })
    ResponseEntity<?> getPeriodo(LocalDate inicio, LocalDate fim);

    @Operation(summary = "Filtra transações por status (pendente, pago, recebido)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transações filtradas por status retornadas")
    })
    ResponseEntity<?> getStatus(String status);

    @Operation(summary = "Filtra transações por categoria (ex: vendas, aluguel, salários)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transações filtradas por categoria retornadas")
    })
    ResponseEntity<?> getCategoria(String categoria);

    @Operation(summary = "Filtra transações por tipo (RECEITA ou DESPESA)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transações filtradas por tipo retornadas")
    })
    ResponseEntity<?> getTipo(String tipo);
}
