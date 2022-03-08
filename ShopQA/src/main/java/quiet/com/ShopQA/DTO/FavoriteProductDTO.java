package quiet.com.ShopQA.DTO;



import lombok.Data;

@Data
public class FavoriteProductDTO {
	private long id;
	
	private String date;
	
	private UserDTO userDTO;
	
	private ProductDTO productDTO;
}
