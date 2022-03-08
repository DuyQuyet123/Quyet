package quiet.com.ShopQA.Controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quiet.com.ShopQA.DTO.UserDTO;
import quiet.com.ShopQA.Service.UserService;
import quiet.com.ShopQA.ServiceImpl.UserServiceImpl;

@Controller
public class UserControler {
	@Autowired
	private UserService userService;

	@GetMapping("/admin/add-user")
	public String addUserGet() {

		return "admin/user/adduser";
	}

	@PostMapping("/admin/add-user")
	public String addUserPost(@ModelAttribute(name = "adduser") UserDTO userDTO) {
		userDTO.setEnabled(true);
		userService.insert(userDTO);
		return "redirect:/admin/search-user";
	}

	@GetMapping("/admin/update-user")
	public String updateUserGet(Model model, @RequestParam(name = "id") Long id) {
		UserDTO userDTO = userService.get(id);
		model.addAttribute("userDTO", userDTO);
		return "admin/user/updateuser";
	}

	@PostMapping("/admin/update-user")
	public String updateUserPost(@ModelAttribute(name = "user") UserDTO userDTO) {
		userDTO.setEnabled(true);
		userService.update(userDTO);
		return "redirect:/admin/search-user";
	}

	@GetMapping("/admin/delete-user")
	public String deleteUser(Long id) {
		userService.delete(id);
		return "redirect:/admin/search-user";
	}

	@GetMapping("/admin/search-user")
	public String searchUser(Model model
	// ,@RequestParam(value = "name",required = false) String name ,
	// @RequestParam(value = "pageable", required = false) Pageable pageable
	) {
		// pageable= pageable == null ? PageRequest.of(0, 10): pageable;
		// name = name == null ? "" : name;

		List<UserDTO> list = userService.search1();
		model.addAttribute("list", list);
		// model.addAttribute("page", pageable);
		// model.addAttribute("keyword", name);
		return "admin/user/searchuser";
	}

}
