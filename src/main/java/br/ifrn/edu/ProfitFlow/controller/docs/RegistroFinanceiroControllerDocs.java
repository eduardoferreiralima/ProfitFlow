package br.ifrn.edu.ProfitFlow.controller.docs;

import br.ifrn.edu.ProfitFlow.dto.request.RequestRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@Tag(
        name = "Registro Financeiro",
        description = "Operações relacionadas ao gerenciamento de registros financeiros (receitas e despesas)"
)
public interface RegistroFinanceiroControllerDocs {


    @Operation(
            summary = "Lista todos os registros financeiros",
            description = "Retorna todos os registros financeiros cadastrados, incluindo receitas e despesas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    ResponseEntity<List<ResponseRegistroFinanceiroDTO>> getRegistroFinanceiro();


    @Operation(
            summary = "Retorna um registro financeiro específico",
            description = "Busca um registro financeiro usando seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    ResponseEntity<ResponseRegistroFinanceiroDTO> getRegistroFinanceiro(
            @Parameter(description = "ID do registro financeiro", example = "1") Long id
    ) throws Exception;


    @Operation(
            summary = "Cria um novo registro financeiro",
            description = "Registra uma nova entrada financeira do tipo RECEITA ou DESPESA."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<ResponseRegistroFinanceiroDTO> createRegistroFinanceiro(
            @Parameter(
                    description = "Dados do registro financeiro a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RequestRegistroFinanceiroDTO.class))
            )
            RequestRegistroFinanceiroDTO registro
    ) throws Exception;


    @Operation(
            summary = "Atualiza um registro financeiro",
            description = "Atualiza os dados de um registro financeiro existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    ResponseEntity<ResponseRegistroFinanceiroDTO> updateRegistroFinanceiro(
            @Parameter(description = "ID do registro financeiro", example = "1") Long id,
            @Parameter(
                    description = "Novos dados do registro financeiro",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RequestRegistroFinanceiroDTO.class))
            )
            RequestRegistroFinanceiroDTO registro
    ) throws Exception;


    @Operation(
            summary = "Remove um registro financeiro",
            description = "Exclui um registro financeiro do sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    ResponseEntity<?> deleteRegistroFinanceiro(
            @Parameter(description = "ID do registro a ser removido", example = "1") Long id
    );

    @Operation(
            summary = "Marca um registro financeiro como quitado",
            description = "Atualiza o status do registro financeiro para PAGO (despesa) ou RECEBIDO (receita)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro quitado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    ResponseEntity<?> updateQuitar(
            @Parameter(description = "ID do registro financeiro a ser quitado", example = "1") Long id
    ) throws Exception;

    @Operation(
            summary = "Lista registros por tipo e status",
            description = "Retorna registros financeiros filtrados pelo tipo (RECEITA ou DESPESA) e status (PENDENTE, PAGO ou ATRASADO)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registros filtrados retornados com sucesso")
    })
    ResponseEntity<?> getRegistroFinanceiroPorCategoria(
            @Parameter(description = "Status do registro financeiro", example = "Marketing") String categoria
    );

    @Operation(
            summary = "Lista registros por status",
            description = "Retorna registros financeiros filtrados por status (PENDENTE, PAGO, ATRASADO)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    ResponseEntity<?> getRegistroFinanceiroPorStatus(
            @Parameter(description = "Status do registro financeiro", example = "PENDENTE") ContaStatus status
    );

    @Operation(
            summary = "Lista registros por tipo",
            description = "Retorna registros financeiros filtrados pelo tipo (RECEITA ou DESPESA)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum registro encontrado para o tipo informado")
    })
    ResponseEntity<?> getRegistroFinanceiroPorTipo(
            @Parameter(description = "Tipo do registro financeiro (RECEITA ou DESPESA)", example = "RECEITA") ContaTipo tipo
    );

    @Operation(
            summary = "Exibir dados Financeiros por Periodo",
            description = "Exibir dados Financeiros por Periodo com Data de Inicio e Fim"
    )
    public ResponseEntity<List<ResponseRegistroFinanceiroDTO>> getRegistroFinanceiroPorPeriodo(
            @Parameter(description = "Data de Inicio", example = "11/25/2024") LocalDate inicio,
            @Parameter(description = "Data de Fim", example = "11/25/2025") LocalDate fin
    );
}
