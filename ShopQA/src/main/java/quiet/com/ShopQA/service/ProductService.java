package quiet.com.ShopQA.service;

import java.util.List;

import org.springframework.data.domain.Pageable;


import quiet.com.ShopQA.DTO.ProductDTO;

public interface ProductService {
	
	void insert(ProductDTO productDTO);
	
	void update(ProductDTO productDTO);
	
	void delete(Long id);
	
	ProductDTO get(Long id);
	
	List<ProductDTO> search();
	
	List<ProductDTO> search1(Pageable pageable);
	
	List<ProductDTO> search2(int pageable);
	
	List<ProductDTO> searchP();
	
	List<ProductDTO> searchByCategory(String name,Pageable pageable);
	
	List<ProductDTO> searchbyProduct(String name,int pageable);
}
