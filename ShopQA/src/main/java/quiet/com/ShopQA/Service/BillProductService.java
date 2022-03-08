package quiet.com.ShopQA.Service;

import java.util.List;


import quiet.com.ShopQA.DTO.BillProductDTO;


public interface BillProductService {
	
	void insert(BillProductDTO billProductDTO);
	
	void update(BillProductDTO billProductDTO);
	
	void delete(Long id);
	
	BillProductDTO get(Long id);
	
	List<BillProductDTO> search();
	
	List<BillProductDTO> searchbyBill(Long id);
}
