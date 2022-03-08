package quiet.com.ShopQA.DTO;

import java.util.List;

import lombok.Data;

@Data
public class BillDTO {
	private Long id;

	private UserDTO userDTO;

	private String buyDate;

	private Long priceTotal;

	private Integer discountPercent;

	private String status;

	private String pay;

	private List<BillProductDTO> billProductDTOs;
}
