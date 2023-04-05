package quiet.com.ShopQA.service;

import java.util.List;


import quiet.com.ShopQA.DTO.CategoryDTO;

public interface CategoryService {
	
	void insert(CategoryDTO categoryDTO);
	
	void update(CategoryDTO categoryDTO);
	
	void delete(Long id);
	
	CategoryDTO get(Long id);
	
	List<CategoryDTO> search();
}
