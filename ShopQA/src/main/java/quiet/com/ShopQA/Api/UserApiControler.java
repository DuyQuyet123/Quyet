package quiet.com.ShopQA.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quiet.com.ShopQA.DTO.UserDTO;
import quiet.com.ShopQA.Service.UserService;

@RestController
@RequestMapping("/api")
public class UserApiControler {
	@Autowired
	private UserService userService;
	@PostMapping("/user/add")
	public UserDTO userAdd(@RequestBody UserDTO userDTO) {
		userService.insert(userDTO);
		return userDTO;
	}
	@PutMapping("/user/update")
	public void userUpdate(@RequestBody UserDTO userDTO) {
		userService.update(userDTO);	
	}
	@DeleteMapping(value = "/user/delete")
	public void delete(@RequestParam(name = "id") Long id) {
		userService.delete(id);
	}
	@GetMapping("/user/search")
	public List<UserDTO> searchCategory() {
		List<UserDTO> userDTOs = userService.search1();
		return userDTOs;
	}
}
