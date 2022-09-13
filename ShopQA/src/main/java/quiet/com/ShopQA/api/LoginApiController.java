package quiet.com.ShopQA.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quiet.com.ShopQA.DTO.LoginRequest;
import quiet.com.ShopQA.DTO.LoginResponse;
import quiet.com.ShopQA.DTO.UserPrincipal;
import quiet.com.ShopQA.sercurity.JwtTokenProvider;

@RestController
@RequestMapping("/api")
@Api(tags = "Login V1", description = "Login để lấy các request tiếp theo")
public class LoginApiController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    //    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE
//            , produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/login")
    @ApiOperation(value = "Đăng nhập để lấy token")
    public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Xác thực từ username và password.
        System.out.println(loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((UserPrincipal) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }
}
