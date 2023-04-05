package quiet.com.ShopQA.service;

import quiet.com.ShopQA.DTO.TrademarkDTO;
import java.util.List;

public interface TrademarkService {
	
	void insert(TrademarkDTO trademarkDTO);
	
	void update(TrademarkDTO trademarkDTO);
	
	void delete(Long id);
	
	TrademarkDTO get(Long id);
	
	List<TrademarkDTO> search();
}
