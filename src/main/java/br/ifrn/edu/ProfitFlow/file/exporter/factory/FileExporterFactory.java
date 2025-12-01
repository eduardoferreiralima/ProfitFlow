package br.ifrn.edu.ProfitFlow.file.exporter.factory;


import br.ifrn.edu.ProfitFlow.file.exporter.contract.FileExporter;
import br.ifrn.edu.ProfitFlow.file.exporter.impl.CsvExporter;
import br.ifrn.edu.ProfitFlow.file.exporter.impl.XlsxExporter;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext applicationContext;

    public FileExporter getFileImporter(String filename) throws BadRequestException {
        if (filename.endsWith(".xlsx")) {
            return applicationContext.getBean(XlsxExporter.class);
        }else if (filename.endsWith(".csv")) {
            return applicationContext.getBean(CsvExporter.class);
        }else {
            throw new BadRequestException("Filename doesn't end with .xlsx or .csv");
        }
    }
}
