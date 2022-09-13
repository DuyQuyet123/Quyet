package quiet.com.ShopQA.Export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import quiet.com.ShopQA.DTO.UserDTO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserExcelExport {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<UserDTO> userDTOs;

    public UserExcelExport(List<UserDTO> userDTOs) {
        this.userDTOs = userDTOs;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "User ID", style);
        createCell(row, 1, "E-mail", style);
        createCell(row, 2, "Name", style);
        createCell(row, 3, "UserName", style);
        createCell(row, 4, "PassWord", style);
        createCell(row, 5, "Roles", style);
        createCell(row, 6, "Enabled", style);
        createCell(row, 7, "Address", style);
        createCell(row, 8, "PhoneNumber", style);
        createCell(row, 9, "GioiTinh", style);

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

        for (UserDTO userDTO : userDTOs) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, userDTO.getId(), style);
            createCell(row, columnCount++, userDTO.getEmail(), style);
            createCell(row, columnCount++, userDTO.getName(), style);
            createCell(row, columnCount++, userDTO.getUsername(), style);
            createCell(row, columnCount++, userDTO.getPassword(), style);
            createCell(row, columnCount++, userDTO.getRole(), style);
            createCell(row, columnCount++, userDTO.getEnabled(), style);
            createCell(row, columnCount++, userDTO.getAddress(), style);
            createCell(row, columnCount++, userDTO.getPhone(), style);
            createCell(row, columnCount++, userDTO.getGioiTinh(), style);
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
