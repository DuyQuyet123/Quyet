package quiet.com.ShopQA.Export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import quiet.com.ShopQA.DTO.BillDTO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BillExcelExport {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<BillDTO> billDTOs;

    public BillExcelExport(List<BillDTO> billDTOs) {
        this.billDTOs = billDTOs;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Bill");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "BillID", style);
        createCell(row, 1, "Name", style);
        createCell(row, 2, "Address", style);
        createCell(row, 3, "Phone", style);
        createCell(row, 4, "BuyDate", style);
        createCell(row, 5, "PriceTotal", style);
        createCell(row, 6, "DiscountFist", style);
        createCell(row, 7, "Status", style);
        createCell(row, 8, "Pay", style);
        //createCell(row, 9, "DiscountCode", style);

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

        for (BillDTO billDTO : billDTOs) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, billDTO.getId(), style);
            createCell(row, columnCount++, billDTO.getUserDTO().getName(), style);
            createCell(row, columnCount++, billDTO.getUserDTO().getAddress(), style);
            createCell(row, columnCount++, billDTO.getUserDTO().getPhone(), style);
            createCell(row, columnCount++, billDTO.getBuyDate(), style);
            createCell(row, columnCount++, billDTO.getPriceTotal(), style);
            createCell(row, columnCount++, billDTO.getDiscountPercent(), style);
            createCell(row, columnCount++, billDTO.getStatus(), style);
            createCell(row, columnCount++, billDTO.getPay(), style);
            //createCell(row, columnCount++, billDTO.getDiscountDTO().getDiscount(), style);
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
