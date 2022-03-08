package quiet.com.ShopQA.DTO;

import lombok.Data;

@Data
public class CommentDTO {
	
	private Long id;
	
	private String commentDate;
	
	private String comment;
	
	private UserDTO userDTO;
	
	private ProductDTO productDTO;
}
