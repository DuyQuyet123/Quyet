package quiet.com.ShopQA.Controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quiet.com.ShopQA.DTO.CategoryDTO;
import quiet.com.ShopQA.service.CategoryService;


@Controller
public class CategoryControler {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/admin/add-category")
	public String addCategoryGet() {

		return "admin/category/addcategory";
	}

	@PostMapping("/admin/add-category")
	public String addCategoryPost(@ModelAttribute(name = "addcategory") CategoryDTO categoryDTO) {
		categoryService.insert(categoryDTO);
		return "redirect:/admin/search-category";
	}

	@PostMapping("/admin/category/add")
	public String categoryAddPost(@ModelAttribute(name = "addcategory") CategoryDTO categoryDTO) {
		categoryService.insert(categoryDTO);
		return "redirect:/admin/add-product";
	}

	@GetMapping("/admin/update-category")
	public String updateCategoryGet(Model model, @RequestParam(name = "id") Long id) {
		CategoryDTO categoryDTO = categoryService.get(id);
		model.addAttribute("categoryDTO", categoryDTO);
		return "admin/category/updatecategory";
	}

	@PostMapping("/admin/update-category")
	public String updateCategoryPost(@ModelAttribute(name = "updatecategory") CategoryDTO categoryDTO) {
		categoryService.update(categoryDTO);
		return "redirect:/admin/search-category";
	}

	@GetMapping("/admin/delete-category")
	public String deleteCategory(Long id) {
		categoryService.delete(id);
		return "redirect:/admin/search-category";
	}

	@GetMapping("/admin/search-category")
	public String searchCategory(Model model
	// , @RequestParam(name = "pageable",required = false) Pageable pageable
	) {
		// if(pageable==null) {
		// pageable = PageRequest.of(0, 10);
		// }
		List<CategoryDTO> categoryDTOs = categoryService.search();
		model.addAttribute("categoryDTOs", categoryDTOs);
		// model.addAttribute("pageable",pageable);
		return "admin/category/searchcategory";
	}

	// Manager

	@GetMapping("/manager/add-category")
	public String addCategoryGetManager() {

		return "manager/category/addcategory";
	}

	@PostMapping("/manager/add-category")
	public String addCategoryPostManager(@ModelAttribute(name = "addcategory") CategoryDTO categoryDTO) {
		categoryService.insert(categoryDTO);
		return "redirect:/manager/search-category";
	}

	@PostMapping("/manager/category/add")
	public String categoryAddPostManager(@ModelAttribute(name = "addcategory") CategoryDTO categoryDTO) {
		categoryService.insert(categoryDTO);
		return "redirect:/manager/add-product";
	}

	@GetMapping("/manager/update-category")
	public String updateCategoryGetManager(Model model, @RequestParam(name = "id") Long id) {
		CategoryDTO categoryDTO = categoryService.get(id);
		model.addAttribute("categoryDTO", categoryDTO);
		return "manager/category/updatecategory";
	}

	@PostMapping("/manager/update-category")
	public String updateCategoryPostManager(@ModelAttribute(name = "updatecategory") CategoryDTO categoryDTO) {
		categoryService.update(categoryDTO);
		return "redirect:/manager/search-category";
	}

	@GetMapping("/manager/delete-category")
	public String deleteCategoryManager(Long id) {
		categoryService.delete(id);
		return "redirect:/manager/search-category";
	}

	@GetMapping("/manager/search-category")
	public String searchCategoryManager(Model model
	// , @RequestParam(name = "pageable",required = false) Pageable pageable
	) {
		List<CategoryDTO> categoryDTOs = categoryService.search();
		model.addAttribute("categoryDTOs", categoryDTOs);
		// model.addAttribute("pageable",pageable);
		return "manager/category/searchcategory";
	}
}
