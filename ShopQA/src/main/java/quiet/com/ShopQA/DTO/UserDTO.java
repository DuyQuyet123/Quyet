package quiet.com.ShopQA.DTO;


import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	
	private String name;
	
	private String username;
	
	private String password;
	
	private Boolean enabled;
	
	private String gioiTinh;
	
	private String email;
	
	private String role;
	
	private String phone;
	 
	private String address;
}
