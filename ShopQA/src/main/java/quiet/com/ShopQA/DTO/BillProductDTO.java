package quiet.com.ShopQA.DTO;

import lombok.Data;

@Data
public class BillProductDTO {
	private Long id;
	
	private long unitPrice;
	
	private int quantity;
	
	private BillDTO billDTO;
	
	private ProductDTO productDTO;
}
