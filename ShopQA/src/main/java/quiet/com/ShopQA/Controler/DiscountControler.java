package quiet.com.ShopQA.Controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quiet.com.ShopQA.DTO.DiscountDTO;
import quiet.com.ShopQA.service.DiscountService;

@Controller
public class DiscountControler {
	@Autowired
	private DiscountService discountService;

	@GetMapping("/admin/discount/add")
	public String addDiscountGet() {

		return "admin/discount/adddiscount";
	}

	@PostMapping("/admin/discount/add")
	public String addDiscountPost(@ModelAttribute(name = "adddiscount") DiscountDTO discountDTO) {
		discountService.insert(discountDTO);
		return "redirect:/admin/discount/search";
	}

	@GetMapping("/admin/discount/update")
	public String updateDiscountGet(Model model, @RequestParam(name = "id") Long id) {
		DiscountDTO discountDTO = discountService.get(id);
		model.addAttribute("discount", discountDTO);
		return "admin/discount/updatediscount";
	}

	@PostMapping("/admin/discount/update")
	public String updateDiscountPost(@ModelAttribute(name = "updatediscount") DiscountDTO discountDTO) {
		discountService.update(discountDTO);
		return "redirect:/admin/discount/search";
	}

	@GetMapping("/admin/discount/delete")
	public String deleteDiscountGet(Long id) {
		discountService.delete(id);
		return "redirect:/admin/discount/search";
	}

	@GetMapping("/admin/discount/search")
	public String searchDiscountGet(Model model) {
		List<DiscountDTO> discountDTOs = discountService.search();

		model.addAttribute("listDiscount", discountDTOs);
		return "admin/discount/searchdiscount";
	}

	// manager
	@GetMapping("/manager/discount/add")
	public String addDiscountGet1() {

		return "manager/discount/adddiscount";
	}

	@PostMapping("/manager/discount/add")
	public String addDiscountPost1(@ModelAttribute(name = "adddiscount") DiscountDTO discountDTO) {
		discountService.insert(discountDTO);
		return "redirect:/manager/discount/search";
	}

	@GetMapping("/manager/discount/update")
	public String updateDiscountGet1(Model model, @RequestParam(name = "id") Long id) {
		DiscountDTO discountDTO = discountService.get(id);
		model.addAttribute("discount", discountDTO);
		return "manager/discount/updatediscount";
	}

	@PostMapping("/manager/discount/update")
	public String updateDiscountPost1(@ModelAttribute(name = "updatediscount") DiscountDTO discountDTO) {
		discountService.update(discountDTO);
		return "redirect:/manager/discount/search";
	}

	@GetMapping("/manager/discount/delete")
	public String deleteDiscountGet1(Long id) {
		discountService.delete(id);
		return "redirect:/manager/discount/search";
	}

	@GetMapping("/manager/discount/search")
	public String searchDiscountGet1(Model model) {
		List<DiscountDTO> discountDTOs = discountService.search();

		model.addAttribute("listDiscount", discountDTOs);
		return "manager/discount/searchdiscount";
	}
}
