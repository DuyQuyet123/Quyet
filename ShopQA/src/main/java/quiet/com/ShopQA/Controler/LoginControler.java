package quiet.com.ShopQA.Controler;

import java.io.File;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import quiet.com.ShopQA.Repostory.UserRepository;
import quiet.com.ShopQA.Service.UserService;

@Controller
public class LoginControler {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;

	@GetMapping("/login")
	public String Getlogin(HttpServletRequest request) {
		return "login/login";
	}

	@GetMapping("/access-deny")
	public String GetAccessdeny(HttpServletRequest request) {
		return "login/access-deny";
	}

	@GetMapping("/dashboard")
	public String dashboard(HttpServletRequest req) {
		if (req.isUserInRole("ADMIN"))
			return "redirect:/admin/home";
		else if (req.isUserInRole("MANAGER"))
			return "redirect:/manager/home";
		else if (req.isUserInRole("MEMBER"))
			return "redirect:/trangchu";

		return "redirect:/login";
	}

//	@GetMapping("/register")
//	public String addUserGet() {
//		return "login/register";
//	}
//	
//	@PostMapping("/register")
//	public String addUserPost(@ModelAttribute(name = "adduser") UserDTO userDTO) {
//		userDTO.setEnabled(true);
//		userServiceImpl.insert(userDTO);
//		return"redirect:/login";
//	}

	@GetMapping("/download") // ?filename = abc.jpg
	public void downloadFile(@RequestParam("filename") String filename, HttpServletResponse response) {
		try {
			String FOLDER_SAVE = "D:\\Images\\";
			File inputFile = new File(FOLDER_SAVE + filename);

			if (inputFile.exists()) {
				Files.copy(inputFile.toPath(), response.getOutputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
