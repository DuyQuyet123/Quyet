package quiet.com.ShopQA.Controler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import quiet.com.ShopQA.DTO.BillDTO;
import quiet.com.ShopQA.DTO.ProductDTO;
import quiet.com.ShopQA.DTO.UserDTO;
import quiet.com.ShopQA.service.BillService;
import quiet.com.ShopQA.service.ProductService;
import quiet.com.ShopQA.service.UserService;

@Controller
public class HomeControler {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private BillService billService;

	@GetMapping("/admin/home")
	public String adminHome(HttpServletRequest request) {
		Long Sum = (long) 0;
		List<ProductDTO> productDTOs = productService.search();
		List<UserDTO> userDTOs = userService.search1();
		List<BillDTO> billDTOs = billService.search();
		for (BillDTO billDTO : billDTOs) {
			Sum = Sum + billDTO.getPriceTotal();
		}

		List<UserDTO> listDTO = userService.search3(PageRequest.of(0, 4));
		List<BillDTO> listBill = billService.search2(PageRequest.of(0, 4));

		// System.out.println(listBill);
		request.setAttribute("numUser", userDTOs.size());
		request.setAttribute("numProduct", productDTOs.size());
		request.setAttribute("numBill", billDTOs.size());
		request.setAttribute("listDTOs", listDTO);
		request.setAttribute("listBills", listBill);
		return "admin/home";
	}

	@GetMapping("/admin/report")
	public String BaocaoGet(HttpServletRequest request) {
		Long Sum = (long) 0;
		List<ProductDTO> productDTOs = productService.search();
		List<UserDTO> userDTOs = userService.search1();
		List<UserDTO> listUser = userService.search3(PageRequest.of(0, 3));
		List<BillDTO> billDTOs = billService.search();
		for (BillDTO billDTO : billDTOs) {
			Sum = Sum + billDTO.getPriceTotal();
		}

		List<ProductDTO> listProductDTO = productService.search1(PageRequest.of(0, 5));
		List<BillDTO> listBill = billService.search2(PageRequest.of(0, 6));
		Long Sum1 = (long) 0;
		for (BillDTO billDTO : listBill) {
			Sum1 = Sum1 + billDTO.getPriceTotal();
		}
		List<ProductDTO> listProduct = productService.searchP();

		System.out.println(listBill);
		request.setAttribute("numUser", userDTOs.size());
		request.setAttribute("numProduct", productDTOs.size());
		request.setAttribute("numBill", billDTOs.size());
		request.setAttribute("listProduct", listProductDTO);
		request.setAttribute("listBills", listBill);
		request.setAttribute("sum", Sum);
		request.setAttribute("numP", listProduct.size());
		request.setAttribute("listP", listProduct);
		request.setAttribute("sum1", Sum1);
		request.setAttribute("userDTOs", listUser);
		return "admin/report/report";
	}

	@GetMapping("/manager/home")
	public String managerHome(HttpServletRequest request) {
		List<ProductDTO> productDTOs = productService.search();
		List<UserDTO> userDTOs = userService.search1();
		List<BillDTO> billDTOs = billService.search();
		List<UserDTO> listDTO = userService.search3(PageRequest.of(0, 4));
		List<BillDTO> listBill = billService.search2(PageRequest.of(0, 4));

		// System.out.println(listBill);
		request.setAttribute("numUser", userDTOs.size());
		request.setAttribute("numProduct", productDTOs.size());
		request.setAttribute("numBill", billDTOs.size());
		request.setAttribute("listDTOs", listDTO);
		request.setAttribute("listBills", listBill);
		return "manager/home";
	}

	@GetMapping("/manager/report")
	public String reportGet(HttpServletRequest request) {
		Long Sum = (long) 0;
		List<ProductDTO> productDTOs = productService.search();
		List<UserDTO> userDTOs = userService.search1();
		List<UserDTO> listUser = userService.search3(PageRequest.of(0, 3));
		List<BillDTO> billDTOs = billService.search();
		for (BillDTO billDTO : billDTOs) {
			Sum = Sum + billDTO.getPriceTotal();
		}

		List<ProductDTO> listProductDTO = productService.search1(PageRequest.of(0, 5));
		List<BillDTO> listBill = billService.search2(PageRequest.of(0, 6));
		Long Sum1 = (long) 0;
		for (BillDTO billDTO : listBill) {
			Sum1 = Sum1 + billDTO.getPriceTotal();
		}
		List<ProductDTO> listProduct = productService.searchP();

		request.setAttribute("numUser", userDTOs.size());
		request.setAttribute("numProduct", productDTOs.size());
		request.setAttribute("numBill", billDTOs.size());
		request.setAttribute("listProduct", listProductDTO);
		request.setAttribute("listBills", listBill);
		request.setAttribute("sum", Sum);
		request.setAttribute("numP", listProduct.size());
		request.setAttribute("listP", listProduct);
		request.setAttribute("sum1", Sum1);
		request.setAttribute("userDTOs", listUser);
		return "manager/reportmanager/report";
	}

	@GetMapping("/trangchu")
	public String Home() {

		return "redirect:/home";
	}
}
