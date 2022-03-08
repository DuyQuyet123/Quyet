package quiet.com.ShopQA.Controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quiet.com.ShopQA.DTO.SizeDTO;
import quiet.com.ShopQA.ServiceImpl.SizeServiceImpl;

@Controller
public class SizeControler {
	@Autowired
	private SizeServiceImpl sizeServiceImpl;

	@GetMapping("/admin/add-size")
	public String addSizeGet() {
		return "admin/size/addsize";
	}

	@PostMapping("/admin/add-size")
	public String addSizePost(@ModelAttribute(name = "addsize") SizeDTO sizeDTO) {
		sizeServiceImpl.insert(sizeDTO);
		return "redirect:/admin/search-size";
	}

	@PostMapping("/admin/size/add")
	public String sizeAddPost(@ModelAttribute(name = "addsize") SizeDTO sizeDTO) {
		sizeServiceImpl.insert(sizeDTO);
		return "redirect:/admin/add-product";
	}

	@GetMapping("/admin/update-size")
	public String updateSizeGet(Model model, @RequestParam(name = "id") Long id) {
		SizeDTO sizeDTO = sizeServiceImpl.get(id);
		model.addAttribute("sizeDTO", sizeDTO);
		return "admin/size/updatesize";
	}

	@PostMapping("/admin/update-size")
	public String updateSizePost(@ModelAttribute(name = "updateSize") SizeDTO sizeDTO) {

		sizeServiceImpl.update(sizeDTO);
		return "redirect:/admin/search-size";

	}

	@GetMapping("/admin/delete-size")
	public String deleteSize(Long id) {
		sizeServiceImpl.delete(id);
		return "redirect:/admin/search-size";
	}

	@GetMapping("/admin/search-size")
	public String search(Model model
	// , @RequestParam(name = "pageable",required = false) Pageable pageable
	) {
		// pageable= pageable == null ? PageRequest.of(0, 10): pageable;
		List<SizeDTO> sizeDTOs = sizeServiceImpl.search();
		model.addAttribute("sizeDTOs", sizeDTOs);
		// model.addAttribute("pageable",pageable);
		return "admin/size/searchsize";
	}

	// Manager
	@GetMapping("/manager/add-size")
	public String addSizeGetManager() {
		return "manager/size/addsize";
	}

	@PostMapping("/manager/add-size")
	public String addSizePostManager(@ModelAttribute(name = "addsize") SizeDTO sizeDTO) {
		sizeServiceImpl.insert(sizeDTO);
		return "redirect:/manager/search-size";
	}

	@PostMapping("/manager/size/add")
	public String sizeAddPostManager(@ModelAttribute(name = "addsize") SizeDTO sizeDTO) {
		sizeServiceImpl.insert(sizeDTO);
		return "redirect:/manager/add-product";
	}

	@GetMapping("/manager/update-size")
	public String updateSizeGetManager(Model model, @RequestParam(name = "id") Long id) {
		SizeDTO sizeDTO = sizeServiceImpl.get(id);
		model.addAttribute("sizeDTO", sizeDTO);
		return "manager/size/updatesize";
	}

	@PostMapping("/manager/update-size")
	public String updateSizePostManager(@ModelAttribute(name = "updateSize") SizeDTO sizeDTO) {

		sizeServiceImpl.update(sizeDTO);
		return "redirect:/manager/search-size";

	}

	@GetMapping("/manager/delete-size")
	public String deleteSizeManager(Long id) {
		sizeServiceImpl.delete(id);
		return "redirect:/manager/search-size";
	}

	@GetMapping("/manager/search-size")
	public String searchManager(Model model
	// , @RequestParam(name = "pageable",required = false) Pageable pageable
	) {
		// pageable= pageable == null ? PageRequest.of(0, 10): pageable;
		List<SizeDTO> sizeDTOs = sizeServiceImpl.search();
		model.addAttribute("sizeDTOs", sizeDTOs);
		// model.addAttribute("pageable",pageable);
		return "manager/size/searchsize";
	}
}
