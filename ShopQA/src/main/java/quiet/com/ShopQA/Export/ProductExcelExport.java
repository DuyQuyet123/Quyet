package quiet.com.ShopQA.Export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import quiet.com.ShopQA.DTO.ProductDTO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductExcelExport {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ProductDTO> productDTOs;

    public ProductExcelExport(List<ProductDTO> productDTOs) {
        this.productDTOs = productDTOs;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Products");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ProductID", style);
        createCell(row, 1, "NameProduct", style);
        createCell(row, 2, "UrlImage", style);
        createCell(row, 3, "Description", style);
        createCell(row, 4, "Price", style);
        createCell(row, 5, "Quantity", style);
        createCell(row, 6, "Category", style);
        createCell(row, 7, "Size", style);
        createCell(row, 8, "Trademark", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (ProductDTO productDTO : productDTOs) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, productDTO.getId(), style);
            createCell(row, columnCount++, productDTO.getName(), style);
            createCell(row, columnCount++, productDTO.getImage(), style);
            createCell(row, columnCount++, productDTO.getDescription(), style);
            createCell(row, columnCount++, productDTO.getPrice(), style);
            createCell(row, columnCount++, productDTO.getQuantity(), style);
            createCell(row, columnCount++, productDTO.getCategoryDTO().getName(), style);
            createCell(row, columnCount++, productDTO.getSizeDTO().getName(), style);
            createCell(row, columnCount++, productDTO.getTrademarkDTO().getName(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
