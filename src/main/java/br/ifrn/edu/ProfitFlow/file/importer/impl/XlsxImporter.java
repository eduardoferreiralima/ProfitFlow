package br.ifrn.edu.ProfitFlow.file.importer.impl;

import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import br.ifrn.edu.ProfitFlow.file.importer.contract.FileImporter;
import br.ifrn.edu.ProfitFlow.models.enums.ContaTipo;
import br.ifrn.edu.ProfitFlow.models.enums.FormaPagamento;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporter implements FileImporter {
    private final DataFormatter formatter = new DataFormatter();

    @Override
    public List<ImporterDTO> importFile(InputStream inputStream) throws Exception {

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0); //Pega a aba usada na planilha
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) rowIterator.next(); //Pula a primeira linha da planilha

            return parseRowsToImportDTOList(rowIterator);

        }
    }

    private List<ImporterDTO> parseRowsToImportDTOList(Iterator<Row> rowIterator) {
        List<ImporterDTO>  importedData = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isRowValid(row)) {
                importedData.add(parseRowToImporterDTO(row));
            } else {
                System.out.println("Linha ignorada, célula obrigatória ausente: " + row.getRowNum());
                continue; // pula linha inteira

                //Adicionar lógica para montar e retornar uma planilha com os dados incompletos para correção
            }
        }
        return importedData;
    }

    private ImporterDTO parseRowToImporterDTO(Row row) {
        ImporterDTO dto = new ImporterDTO();

        dto.setTipoLancamento(parseEnum(ContaTipo.class, getString(row, 0)));
        dto.setValor(parseBigDecimal(row, 1));
        dto.setFormaPagamento(parseEnum(FormaPagamento.class, getString(row, 2)));
        dto.setCategoria(getString(row, 3));
        dto.setNomeClienteFornecedor(getString(row, 4));
        dto.setCpfCnpj(getString(row, 5));
        dto.setDataPrevista(parseDate(row, 6));
        dto.setDataPagamento(parseDate(row, 7));

        return dto;
    }

    private boolean isRowValid(Row row) {
        if (row == null) return false;

        // Definindo colunas obrigatórias
        int[] mandatoryColumns = {0, 1, 2, 3, 4, 5};

        for (int col : mandatoryColumns) {
            Cell cell = row.getCell(col, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell == null) return false;

            String value = formatter.formatCellValue(cell);
            if (value == null || value.isBlank()) return false; // célula vazia
        }

        return true; // todas as células obrigatórias têm valor
    }

    private String getString(Row row, int index) {
        Cell cell = row.getCell(index, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

        if (cell == null) {
            return ""; // Retorna vazio sem estourar exceção
        }
        return formatter.formatCellValue(cell);
    }

    private LocalDate parseDate(Row row, int index) {
        Cell cell = row.getCell(index, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null) return null;

        // 1) Se for número (data do Excel), é a forma mais segura
        if (cell.getCellType() == CellType.NUMERIC) {
            try {
                return cell.getLocalDateTimeCellValue().toLocalDate();
            } catch (Exception ignored) {}
        }

        // 2) String
        String value = formatter.formatCellValue(cell).trim();
        if (value.isBlank()) return null;

        String[] patterns = {
                "dd/MM/yyyy", "d/M/yyyy",
                "MM/dd/yyyy", "M/d/yyyy",
                "yyyy-MM-dd",
                "dd-MM-yyyy", "d-M-yyyy",
                "MM-dd-yyyy", "M-d-yyyy"
        };

        for (String p : patterns) {
            try {
                DateTimeFormatter f = DateTimeFormatter.ofPattern(p);
                return LocalDate.parse(value, f);
            } catch (Exception ignored) {}
        }

        throw new RuntimeException("Formato de data inválido: " + value);
    }
    private <T extends Enum<T>> T parseEnum(Class<T> enumClass, String value) {
        if (value == null || value.isBlank()) return null;
        value = value.trim().toUpperCase().replace(" ", "_");

        try {
            return Enum.valueOf(enumClass, value);
        } catch (Exception e) {
            System.out.println("Valor inválido para enum " + enumClass.getSimpleName() + ": " + value);
            return null;
        }
    }
    private BigDecimal parseBigDecimal(Row row, int index) {
        String value = getString(row, index);
        if (value == null || value.isBlank()) return null;

        value = value.replace(",", ".").trim();

        try {
            return new BigDecimal(value);
        } catch (Exception e) {
            System.out.println("Erro ao converter valor: " + value);
            return null;
        }
    }
}
