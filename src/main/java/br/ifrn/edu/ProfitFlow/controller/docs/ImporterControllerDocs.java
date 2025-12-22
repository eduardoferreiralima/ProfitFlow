package br.ifrn.edu.ProfitFlow.controller.docs;

import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Tag(name = "Importar Dados", description = "Operações relacionadas à importação de arquivos e consulta de histórico")
public interface ImporterControllerDocs {

    @Operation(
            summary = "Obter template de importação",
            description = "Retorna o arquivo template que deve ser usado como base para importar dados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Template retornado com sucesso")
    })
    ResponseEntity<?> getTemplate() throws Exception;


    @Operation(
            summary = "Fazer upload de arquivo",
            description = "Recebe um arquivo via multipart/form-data e realiza o processamento de importação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Arquivo importado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImporterDTO.class))),
            @ApiResponse(responseCode = "400", description = "Arquivo inválido")
    })
    ResponseEntity<List<ImporterDTO>> uploadFile(
            @Parameter(
                    description = "Arquivo a ser enviado",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            MultipartFile file
    );

}
