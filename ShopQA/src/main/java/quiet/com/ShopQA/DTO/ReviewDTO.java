package quiet.com.ShopQA.DTO;

import lombok.Data;

@Data
public class ReviewDTO {
	private Long id;
	
	private int starNumber;
	
	private String reviewDate;
	
	private UserDTO userDTO;
	
	private ProductDTO productDTO;
	
}
