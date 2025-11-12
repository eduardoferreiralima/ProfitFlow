package br.ifrn.edu.ProfitFlow.config.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Contas", description = "Operações relacionadas a Contas a Pagar e Receber")
public interface ContasControllerDocs {

    @Operation(summary = "Lista todas as contas", description = "Retorna todas as contas pagáveis e recebíveis.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de contas retornada com sucesso")
    })
    ResponseEntity<?> getContas();

    @Operation(summary = "Retorna uma conta específica", description = "Busca uma conta pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta encontrada"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    ResponseEntity<?> getConta(
            @Parameter(description = "ID da conta", example = "1") Integer id
    );

    @Operation(summary = "Registra uma nova conta", description = "Cria uma nova conta (PAGAR ou RECEBER).")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Conta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<?> createConta();

    @Operation(summary = "Atualiza informações de uma conta", description = "Atualiza os dados de uma conta existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    ResponseEntity<?> updateConta(
            @Parameter(description = "ID da conta a ser atualizada", example = "1") Integer id
    );

    @Operation(summary = "Remove uma conta", description = "Exclui uma conta do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Conta removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    ResponseEntity<?> deleteConta(
            @Parameter(description = "ID da conta a ser removida", example = "1") Integer id
    );

    @Operation(summary = "Marca a conta como quitada", description = "Atualiza o status da conta para quitada (paga ou recebida).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta quitada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    ResponseEntity<?> updateQuitar(
            @Parameter(description = "ID da conta a ser quitada", example = "1") Integer id
    );

    @Operation(summary = "Lista todas as contas vencidas", description = "Retorna todas as contas que estão vencidas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de contas vencidas retornada com sucesso")
    })
    ResponseEntity<?> getVencidas();

    @Operation(summary = "Lista todas as contas pendentes", description = "Retorna todas as contas que ainda não foram quitadas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de contas pendentes retornada com sucesso")
    })
    ResponseEntity<?> getPendentes();

    @Operation(summary = "Lista contas por tipo", description = "Retorna contas filtradas por tipo (PAGAR ou RECEBER).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de contas filtrada por tipo retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma conta encontrada para o tipo informado")
    })
    ResponseEntity<?> getContasPorTipo(
            @Parameter(description = "Tipo da conta (PAGAR ou RECEBER)", example = "PAGAR") String tipo
    );
}
