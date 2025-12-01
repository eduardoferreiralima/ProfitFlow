package br.ifrn.edu.ProfitFlow.file.importer.contract;

import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {
    List<ImporterDTO> importFile(InputStream inputStream) throws Exception;
}
