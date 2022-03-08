package quiet.com.ShopQA.Service;

import java.util.List;


import quiet.com.ShopQA.DTO.SizeDTO;

public interface SizeService {
	
	void insert(SizeDTO sizeDTO);
	
	void update(SizeDTO sizeDTO);
	
	void delete(Long id);
	
	SizeDTO get(Long id);
	
	List<SizeDTO> search();
}
