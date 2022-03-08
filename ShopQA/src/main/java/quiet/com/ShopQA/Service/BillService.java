package quiet.com.ShopQA.Service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import quiet.com.ShopQA.DTO.BillDTO;


public interface BillService {
	void insert(BillDTO billDTO);
	
	void update(BillDTO billDTO);
	
	void delete(Long id);
	
	BillDTO get(Long id);
	
	void update2(BillDTO billDTO);
	
	void update1(BillDTO billDTO);
	
	List<BillDTO> search();
	
	List<BillDTO> searchByBuyerId(Long id);
	
	List<BillDTO> search2(Pageable pageable);
}
