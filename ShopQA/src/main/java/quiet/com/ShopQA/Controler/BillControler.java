package quiet.com.ShopQA.Controler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quiet.com.ShopQA.DTO.BillDTO;
import quiet.com.ShopQA.DTO.BillProductDTO;
import quiet.com.ShopQA.Export.BillExcelExport;
import quiet.com.ShopQA.service.BillProductService;
import quiet.com.ShopQA.service.BillService;

@Controller
public class BillControler {
	@Autowired
	private BillService billService;
	@Autowired
	private BillProductService billProductService;

	@GetMapping("/admin/search-bill")
	public String adminSearchBill(HttpServletRequest request) {

		List<BillDTO> billDTOs = billService.search();

		request.setAttribute("bills", billDTOs);
		return "admin/bill/searchbills";
	}

	@GetMapping("/admin/search-billproduct")
	public String adminSearchBillProduct(HttpServletRequest request, @RequestParam(name = "id") Long id) {

		List<BillProductDTO> billProductDTOs = billProductService.searchbyBill(id);

		request.setAttribute("billProductDTO", billProductDTOs);
		request.setAttribute("billId", id);
		return "admin/bill/searchbillproduct";
	}

	@GetMapping("/admin/delete-bill")
	public String adminDeleteBill(@RequestParam(name = "id", required = true) Long id) {
		billService.delete(id);
		return "redirect:/admin/search-bill";
	}

	@GetMapping("/admin/update-bill")
	public String adminUpdateBill(Model model, @RequestParam(name = "id", required = true) Long id) {
		BillDTO billDTO = billService.get(id);
		model.addAttribute("bill", billDTO);
		return "admin/bill/updatebill";
	}

	@PostMapping("/admin/update-bill")
	public String adminUpdateBill(@ModelAttribute(name = "updateBill") BillDTO billDTO) {
		billService.update2(billDTO);
		return "redirect:/admin/search-bill";
	}

	@GetMapping("/admin/delete-billproduct")
	public String adminDeleteBillProduct(HttpServletRequest request,
			@RequestParam(name = "id", required = true) Long id,
			@RequestParam(name = "billId", required = true) Long billId) {

		billProductService.delete(id);
		BillDTO billDTO = billService.get(billId);
		Long priceTotal = (long) 0;
		List<BillProductDTO> billProductDTOs = billProductService.searchbyBill(billId);
		for (BillProductDTO billProductDTO : billProductDTOs) {
			priceTotal = priceTotal + billProductDTO.getUnitPrice();
		}
		billDTO.setPriceTotal(priceTotal * billDTO.getDiscountPercent());
		billDTO.setPay(String.valueOf(priceTotal));
		billService.update1(billDTO);

		return "redirect:/admin/search-billproduct?id=" + billId;
	}

	@GetMapping("/admin/export/excel/bill")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=bill_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<BillDTO> billDTOs = billService.search();

		BillExcelExport excelExporter = new BillExcelExport(billDTOs);

		excelExporter.export(response);
	}

	// Manager
	@GetMapping("/manager/search-bill")
	public String adminSearchBillManager(HttpServletRequest request) {

		List<BillDTO> billDTOs = billService.search();

		request.setAttribute("bills", billDTOs);
		return "manager/bill/searchbills";
	}

	@GetMapping("/manager/search-billproduct")
	public String adminSearchBillProductManager(HttpServletRequest request, @RequestParam(name = "id") Long id) {

		List<BillProductDTO> billProductDTOs = billProductService.searchbyBill(id);

		request.setAttribute("billProductDTO", billProductDTOs);
		request.setAttribute("billId", id);
		return "manager/bill/searchbillproduct";
	}

	@GetMapping("/manager/delete-bill")
	public String adminDeleteBillManager(@RequestParam(name = "id", required = true) Long id) {
		billService.delete(id);
		return "redirect:/manager/search-bill";
	}

	@GetMapping("/manager/delete-billproduct")
	public String adminDeleteBillProductManager(HttpServletRequest request,
			@RequestParam(name = "id", required = true) Long id,
			@RequestParam(name = "billId", required = true) Long billId) {

		billProductService.delete(id);
		BillDTO billDTO = billService.get(billId);
		Long priceTotal = (long) 0;
		List<BillProductDTO> billProductDTOs = billProductService.searchbyBill(billId);
		for (BillProductDTO billProductDTO : billProductDTOs) {
			priceTotal = priceTotal + billProductDTO.getUnitPrice();
		}
		billDTO.setPriceTotal(priceTotal * billDTO.getDiscountPercent());
		billDTO.setPay(String.valueOf(priceTotal));
		billService.update1(billDTO);

		return "redirect:/manager/search-billproduct?id=" + billId;
	}

	// manager
	@GetMapping("/manager/update-bill")
	public String managerUpdateBill(Model model, @RequestParam(name = "id", required = true) Long id) {
		BillDTO billDTO = billService.get(id);
		model.addAttribute("bill", billDTO);
		return "manager/bill/updatebill";
	}

	@PostMapping("/manager/update-bill")
	public String managerUpdateBill(@ModelAttribute(name = "updateBill") BillDTO billDTO) {
		billService.update2(billDTO);
		return "redirect:/manager/search-bill";
	}

}
