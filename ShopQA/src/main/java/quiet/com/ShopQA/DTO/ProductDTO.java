package quiet.com.ShopQA.DTO;

import lombok.Data;

@Data
public class ProductDTO {
	private Long id;
	
	private String name;
	
	private String image;
	
	private String description;
	
	private Long price;
	
	private Long quantity;
	
	private CategoryDTO categoryDTO;
	
	private SizeDTO sizeDTO;
	
	private TrademarkDTO trademarkDTO;
}
