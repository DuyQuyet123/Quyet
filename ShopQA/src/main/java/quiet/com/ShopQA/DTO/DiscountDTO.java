package quiet.com.ShopQA.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiscountDTO {
	private Long id;
	
	private String name;
	
	private Long discount;

	private LocalDateTime dayCreate;
}
