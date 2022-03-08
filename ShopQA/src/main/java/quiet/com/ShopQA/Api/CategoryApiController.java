package quiet.com.ShopQA.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import quiet.com.ShopQA.DTO.CategoryDTO;
import quiet.com.ShopQA.Service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryApiController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/category/add")
	public CategoryDTO categoryAdd(@RequestBody CategoryDTO categoryDTO) {
		categoryService.insert(categoryDTO);
		return categoryDTO;
	}
	@PutMapping("/category/update")
	@ResponseStatus()
	public void categoryUpdate(@RequestBody CategoryDTO categoryDTO) {
		categoryService.update(categoryDTO);
		
	}
	@DeleteMapping(value = "/category/delete")
	public void delete(@RequestParam(name = "id") Long id) {
		categoryService.delete(id);
	}
	
	@GetMapping(value = "/category/{id}")
	public CategoryDTO get(@PathVariable(name = "id") Long id) {
		return categoryService.get(id);
	}
	
	@GetMapping("/category/search")
	public List<CategoryDTO> searchCategory() {
		
		List<CategoryDTO> categoryDTOs = categoryService.search();

		return categoryDTOs;
	}
	
}
