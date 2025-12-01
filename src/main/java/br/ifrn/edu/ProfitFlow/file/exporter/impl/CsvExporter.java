package br.ifrn.edu.ProfitFlow.file.exporter.impl;



import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import br.ifrn.edu.ProfitFlow.file.exporter.contract.FileExporter;

import java.io.InputStream;
import java.util.List;

public class CsvExporter implements FileExporter {

    @Override
    public List<ImporterDTO> importFile(InputStream inputStream) throws Exception {
        return List.of();
    }
}
