package quiet.com.ShopQA.Controler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quiet.com.ShopQA.DTO.TrademarkDTO;
import quiet.com.ShopQA.Export.TrademarkExcelExport;
import quiet.com.ShopQA.Service.TrademarkService;

import javax.servlet.http.HttpServletResponse;

@Controller
public class TrademarkControler {
	@Autowired
	private TrademarkService trademarkService;

	@GetMapping("/admin/add-trademark")
	public String addTrademarkGet() {
		return "admin/trademark/addtrademark";
	}

	@PostMapping("/admin/add-trademark")
	public String addTrademarkPost(@ModelAttribute(name = "addtrademark") TrademarkDTO trademarkDTO) {
		trademarkService.insert(trademarkDTO);
		return "redirect:/admin/search-trademark";
	}

	@PostMapping("/admin/trademark/add")
	public String trademarkAddPost(@ModelAttribute(name = "addtrademark") TrademarkDTO trademarkDTO) {
		trademarkService.insert(trademarkDTO);
		return "redirect:/admin/add-product";
	}

	@GetMapping("/admin/update-trademark")
	public String updateTrademarkGet(Model model, @RequestParam(name = "id") Long id) {
		TrademarkDTO trademarkDTO = trademarkService.get(id);
		model.addAttribute("trademark", trademarkDTO);
		return "admin/trademark/updatetrademark";

	}

	@PostMapping("/admin/update-trademark")
	public String updateTrademarkPost(@ModelAttribute(name = "updateTrademark") TrademarkDTO trademarkDTO) {
		trademarkService.update(trademarkDTO);
		return "redirect:/admin/search-trademark";
	}

	@GetMapping("/admin/delete-trademark")
	public String deleteTrademark(Long id) {
		trademarkService.delete(id);
		return "redirect:/admin/search-trademark";
	}

	@GetMapping("/admin/search-trademark")
	public String searchTrademark(Model model
	// ,@RequestParam(name = "pageable",required = false) Pageable pageable
	) {
		// pageable= pageable == null ? PageRequest.of(0, 10): pageable;
		List<TrademarkDTO> listTrademarkDTOs = trademarkService.search();
		model.addAttribute("listTr", listTrademarkDTOs);
		// model.addAttribute("pageable",pageable);
		return "admin/trademark/searchtrademark";
	}

	@GetMapping("/admin/export/excel/trademark")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=trademark_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<TrademarkDTO> trademarkDTOs = trademarkService.search();

		TrademarkExcelExport excelExporter = new TrademarkExcelExport(trademarkDTOs);

		excelExporter.export(response);
	}

	// Manager

	@GetMapping("/manager/add-trademark")
	public String addTrademarkGetManager() {
		return "manager/trademark/addtrademark";
	}

	@PostMapping("/manager/add-trademark")
	public String addTrademarkPostManager(@ModelAttribute(name = "addtrademark") TrademarkDTO trademarkDTO) {
		trademarkService.insert(trademarkDTO);
		return "redirect:/manager/search-trademark";
	}

	@PostMapping("/manager/trademark/add")
	public String trademarkAddPostManager(@ModelAttribute(name = "addtrademark") TrademarkDTO trademarkDTO) {
		trademarkService.insert(trademarkDTO);
		return "redirect:/manager/add-product";
	}

	@GetMapping("/manager/update-trademark")
	public String updateTrademarkGetManager(Model model, @RequestParam(name = "id") Long id) {
		TrademarkDTO trademarkDTO = trademarkService.get(id);
		model.addAttribute("trademark", trademarkDTO);
		return "manager/trademark/updatetrademark";

	}

	@PostMapping("/manager/update-trademark")
	public String updateTrademarkPostManager(@ModelAttribute(name = "updateTrademark") TrademarkDTO trademarkDTO) {
		trademarkService.update(trademarkDTO);
		return "redirect:/manager/search-trademark";
	}

	@GetMapping("/manager/delete-trademark")
	public String deleteTrademarkManager(Long id) {
		trademarkService.delete(id);
		return "redirect:/manager/search-trademark";
	}

	@GetMapping("/manager/search-trademark")
	public String searchTrademarkManager(Model model
	// ,@RequestParam(name = "pageable",required = false) Pageable pageable
	) {
		// pageable= pageable == null ? PageRequest.of(0, 10): pageable;
		List<TrademarkDTO> listTrademarkDTOs = trademarkService.search();
		model.addAttribute("listTr", listTrademarkDTOs);
		// model.addAttribute("pageable",pageable);
		return "manager/trademark/searchtrademark";
	}
}
