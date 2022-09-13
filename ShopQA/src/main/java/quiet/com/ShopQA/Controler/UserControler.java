package quiet.com.ShopQA.Controler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;
import quiet.com.ShopQA.DTO.UserDTO;
import quiet.com.ShopQA.Export.UserExcelExport;
import quiet.com.ShopQA.Service.UserService;
import quiet.com.ShopQA.ServiceImpl.UserServiceImpl;

import javax.servlet.http.HttpServletResponse;

@Controller
public class UserControler {
	@Autowired
	private UserService userService;

	@GetMapping("/admin/add-user")
	public String addUserGet() {

		return "admin/user/adduser";
	}

	@PostMapping("/admin/add-user")
	public String addUserPost(@ModelAttribute(name = "adduser") UserDTO userDTO) {
		userDTO.setEnabled(true);
		userService.insert(userDTO);
		return "redirect:/admin/search-user";
	}

	@GetMapping("/admin/update-user")
	public String updateUserGet(Model model, @RequestParam(name = "id") Long id) {
		UserDTO userDTO = userService.get(id);
		model.addAttribute("userDTO", userDTO);
		return "admin/user/updateuser";
	}

	@PostMapping("/admin/update-user")
	public String updateUserPost(@ModelAttribute(name = "user") UserDTO userDTO) {
		userDTO.setEnabled(true);
		userService.update(userDTO);
		return "redirect:/admin/search-user";
	}

	@GetMapping("/admin/delete-user")
	public String deleteUser(Long id) {
		userService.delete(id);
		return "redirect:/admin/search-user";
	}

	@GetMapping("/admin/search-user")
	public String searchUser(Model model
	// ,@RequestParam(value = "name",required = false) String name ,
	// @RequestParam(value = "pageable", required = false) Pageable pageable
	) {
		// pageable= pageable == null ? PageRequest.of(0, 10): pageable;
		// name = name == null ? "" : name;

		List<UserDTO> list = userService.search1();
		model.addAttribute("list", list);
		// model.addAttribute("page", pageable);
		// model.addAttribute("keyword", name);
		return "admin/user/searchuser";
	}

	@GetMapping("/admin/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<UserDTO> userDTOs = userService.search1();

		UserExcelExport excelExporter = new UserExcelExport(userDTOs);

		excelExporter.export(response);
	}

	@PostMapping("/import/user")
	public String mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			UserDTO userDTO = new UserDTO();

			XSSFRow row = worksheet.getRow(i);
			userDTO.setEmail(row.getCell(1).getStringCellValue());
			userDTO.setName(row.getCell(2).getStringCellValue());
			userDTO.setUsername(row.getCell(3).getStringCellValue());
			userDTO.setPassword(row.getCell(4).getStringCellValue());
			userDTO.setEnabled(row.getCell(5).getBooleanCellValue());
			userDTO.setAddress(row.getCell(6).getStringCellValue());
			userDTO.setPhone(row.getCell(7).getStringCellValue());
			userDTO.setGioiTinh(row.getCell(8).getStringCellValue());
			userService.insert(userDTO);
			System.out.println("Thành công");

		}
		return "redirect:/admin/search-user";
	}

}
