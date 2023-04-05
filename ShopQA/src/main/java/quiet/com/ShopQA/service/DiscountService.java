package quiet.com.ShopQA.service;

import java.util.List;

import quiet.com.ShopQA.DTO.DiscountDTO;



public interface DiscountService {
	
	void insert(DiscountDTO discountDTO);
	
	void update(DiscountDTO discountDTO);
	
	void delete(Long id);
	
	DiscountDTO get(Long id);
	
	List<DiscountDTO> search();
}
