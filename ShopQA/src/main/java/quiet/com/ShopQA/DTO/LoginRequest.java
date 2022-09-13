package quiet.com.ShopQA.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "username không được trống")
    private String username;

    @NotBlank(message = "password không được trống")
    private String password;

}