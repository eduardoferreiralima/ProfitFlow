package br.ifrn.edu.ProfitFlow.file.importer.impl;



import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import br.ifrn.edu.ProfitFlow.file.importer.contract.FileImporter;

import java.io.InputStream;
import java.util.List;

public class CsvImporter implements FileImporter {

    @Override
    public List<ImporterDTO> importFile(InputStream inputStream) throws Exception {
        return List.of();
    }
}
