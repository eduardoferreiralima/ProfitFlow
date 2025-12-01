package br.ifrn.edu.ProfitFlow.file.exporter.contract;

import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;

import java.io.InputStream;
import java.util.List;

public interface FileExporter {
    List<ImporterDTO> importFile(InputStream inputStream) throws Exception;
}
