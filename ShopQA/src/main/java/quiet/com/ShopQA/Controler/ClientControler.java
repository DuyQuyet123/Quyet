package quiet.com.ShopQA.Controler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quiet.com.ShopQA.DTO.BillProductDTO;
import quiet.com.ShopQA.DTO.CategoryDTO;
import quiet.com.ShopQA.DTO.CommentDTO;
import quiet.com.ShopQA.DTO.ProductDTO;
import quiet.com.ShopQA.DTO.ReviewDTO;
import quiet.com.ShopQA.DTO.SizeDTO;
import quiet.com.ShopQA.DTO.TrademarkDTO;
import quiet.com.ShopQA.DTO.UserDTO;
import quiet.com.ShopQA.Repostory.UserRepository;
import quiet.com.ShopQA.Service.CategoryService;
import quiet.com.ShopQA.Service.CommentService;
import quiet.com.ShopQA.Service.ProductService;
import quiet.com.ShopQA.Service.ReviewService;
import quiet.com.ShopQA.Service.SizeService;
import quiet.com.ShopQA.Service.TrademarkService;
import quiet.com.ShopQA.Service.UserService;
import quiet.com.ShopQA.ServiceImpl.CategoryServiceImpl;
import quiet.com.ShopQA.ServiceImpl.ProductServiceImpl;
import quiet.com.ShopQA.ServiceImpl.SizeServiceImpl;
import quiet.com.ShopQA.ServiceImpl.TrademarkServiceimpl;
import quiet.com.ShopQA.ServiceImpl.UserServiceImpl;

@Controller
public class ClientControler {
	@Autowired
    UserRepository userRepository;

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SizeService sizeService;
	@Autowired
	private TrademarkService trademarkService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/dangki")
	public String addUserGet() {
		return "login/register";
	}
	
	@PostMapping("/dangki")
	public String addUserPost(@ModelAttribute(name = "adduser") UserDTO userDTO) {
		userDTO.setEnabled(true);
		userDTO.setGioiTinh("Nam");
		userService.insert(userDTO);
		System.out.println(userDTO.getUsername());
		return"redirect:/login";
	}
	
	@GetMapping("/home")
	public String home(HttpServletRequest request,@RequestParam(value = "pageable", required = false) Pageable pageable) {
		pageable= pageable == null ? PageRequest.of(0, 8): pageable;
	List<ProductDTO> productDTOs = productService.search1(PageRequest.of(0, 8));
	
	List<ProductDTO> productDTOs2 = productService.search2(0);
	//int index = (int) productDTOs.size()/8;
	//pageable= pageable == null ? PageRequest.of(index, 8): pageable;
	List<CategoryDTO> categoryDTOs = categoryService.search();
	List<SizeDTO> sizeSTOs = sizeService.search();
	List<TrademarkDTO> trademarkDTOs = trademarkService.search();
	request.setAttribute("product2", productDTOs2);
	request.setAttribute("trademarkList", trademarkDTOs);
	request.setAttribute("sizeList", sizeSTOs);
	request.setAttribute("categoryList", categoryDTOs);
	request.setAttribute("productList", productDTOs);
	
		return "client/home";
	}
	
	@GetMapping("/products")
	public String findProducts(HttpServletRequest request) {
		Integer pageable = request.getParameter("page") == null ? 0 : Integer.valueOf(request.getParameter("page"));
		String name = request.getParameter("name") == null ? ""
				: request.getParameter("name");
	int p =0;
	List<ProductDTO> listProductDTOs = productService.searchbyProduct(name, p);
	List<ProductDTO> productDTOs = productService.search2(pageable);
	
	List<CategoryDTO> categoryDTOs = categoryService.search();
	List<SizeDTO> sizeSTOs = sizeService.search();
	List<TrademarkDTO> trademarkDTOs = trademarkService.search();
	
	request.setAttribute("trademarkList", trademarkDTOs);
	request.setAttribute("sizeList", sizeSTOs);
	request.setAttribute("categoryList", categoryDTOs);
	request.setAttribute("productList", productDTOs);
	request.setAttribute("page", p+1);
	
	
		return "client/product";
	}
	
	@GetMapping("/search/products")
	public String searchProducts(HttpServletRequest request,@RequestParam(name = "name",required = false) String name) {
		name= name == null ? name="":name;
	int p =0;
	List<ProductDTO> productDTOs = productService.searchbyProduct(name, p);
	
	
	List<CategoryDTO> categoryDTOs = categoryService.search();
	List<SizeDTO> sizeSTOs = sizeService.search();
	List<TrademarkDTO> trademarkDTOs = trademarkService.search();
	
	request.setAttribute("trademarkList", trademarkDTOs);
	request.setAttribute("sizeList", sizeSTOs);
	request.setAttribute("categoryList", categoryDTOs);
	request.setAttribute("productList", productDTOs);
	request.setAttribute("page", p+1);
	
	
		return "client/search-product";
	}
	
	@GetMapping("/category/products")
	public String findCategoryByProducts(HttpServletRequest request, @RequestParam(name = "name")String name,
			@RequestParam(value = "pageable", required = false) Pageable pageable) {
	pageable= pageable == null ? PageRequest.of(0, 8): pageable;
	int p=0;
	List<ProductDTO> productDTOs = productService.searchByCategory(name, pageable);
	List<CategoryDTO> categoryDTOs = categoryService.search();
	List<SizeDTO> sizeSTOs = sizeService.search();
	List<TrademarkDTO> trademarkDTOs = trademarkService.search();
	
	
	request.setAttribute("trademarkList", trademarkDTOs);
	request.setAttribute("sizeList", sizeSTOs);
	request.setAttribute("categoryList", categoryDTOs);
	request.setAttribute("productList", productDTOs);
	request.setAttribute("page", p+1);
		return "client/product-by-category";
	}
	
	@GetMapping("/product")
	public String oneProduct(HttpServletRequest request,@RequestParam(name = "id")Long id,
			@RequestParam(value = "pageable", required = false) Pageable pageable) {
		pageable= pageable == null ? PageRequest.of(0, 4): pageable;
		ProductDTO productDTO = productService.get(id);
		List<CommentDTO> commentDTOs = commentService.searchByProduct(id);
		List<ReviewDTO> reviewDTOs = reviewService.find(id);
		List<SizeDTO> sizeDTOs = sizeService.search();
		
		float sum=0;
		for(ReviewDTO reviewDTO:reviewDTOs) {
			int star=reviewDTO.getStarNumber();
			sum=sum+star;
		}
		int dem=reviewDTOs.size();
		float totalStar= sum/dem;
		request.setAttribute("dem", dem);
		request.setAttribute("totalStar", totalStar);
		List<ProductDTO> productDTOs = productService.search1(pageable);
		request.setAttribute("product", productDTO);
		request.setAttribute("listProduct", productDTOs);
		request.setAttribute("listSize", sizeDTOs);
		request.setAttribute("commentProduct", commentDTOs);
		request.setAttribute("reviews", reviewDTOs);
		return"client/product-details";
	}
	
	@GetMapping(value = "/cart")
	public String Cart(HttpSession session) {
		Object object2 = session.getAttribute("cart");
		System.out.println(object2);
		Map<Long, BillProductDTO> map2 = (Map<Long, BillProductDTO>) object2;
		if(object2!=null) {
			
			Long sum=(long) 0;
			for(java.util.Map.Entry<Long, BillProductDTO> entry: map2.entrySet()) {
				sum = sum + entry.getValue().getQuantity()*entry.getValue().getUnitPrice();
			}
			session.setAttribute("total", sum);
			System.out.println(sum);
			}
		return"client/cart";
	}
	
	@GetMapping("/add-to-cart")
	public String AddtoCart(@RequestParam(name = "id")Long id,
			//@RequestParam(name = "quantity")int quantity,
			HttpSession session){
		ProductDTO productDTO = productService.get(id);
		Object obj = session.getAttribute("cart");//lấy ss
		if(obj==null) {
			BillProductDTO billProductDTO = new BillProductDTO();
			billProductDTO.setProductDTO(productDTO);
			billProductDTO.setQuantity(1);
			billProductDTO.setUnitPrice(productDTO.getPrice());
			
			Map<Long, BillProductDTO> map = new HashMap<>();
			map.put(productDTO.getId(), billProductDTO);
			session.setAttribute("cart", map);
		}else {
			Map<Long, BillProductDTO> map = (Map<Long, BillProductDTO>) obj;
			BillProductDTO billProductDTO = map.get(productDTO.getId());
			if(billProductDTO==null) {
				 billProductDTO = new BillProductDTO();
				 billProductDTO.setProductDTO(productDTO);
				 billProductDTO.setQuantity(1);
				 billProductDTO.setUnitPrice(productDTO.getPrice());
				 
				 map.put(productDTO.getId(), billProductDTO);
				 
			}else {
				billProductDTO.setQuantity(billProductDTO.getQuantity()+1);
			}
			session.setAttribute("cart",map);
		}
		return "redirect:/cart";
	}
	@GetMapping("/remove-cart")
	public String removeCart(@RequestParam(name = "id")Long id,HttpSession session) {
		Object obj = session.getAttribute("cart");
		if(obj!=null) {
			Map<Long, BillProductDTO> map = (Map<Long, BillProductDTO>) obj;
			map.remove(id);
			session.setAttribute("cart", map);
		}
		return "redirect:/cart";
		
	}
	@GetMapping("/update-cart")
	public String updateCart(@RequestParam(name = "id")Long id,
			@RequestParam(name = "quantity") int quantity,
			HttpSession session) {
		
		Object obj = session.getAttribute("cart");
		
		if(obj!=null) {
			Map<Long, BillProductDTO> map = (Map<Long, BillProductDTO>) obj;
			
			BillProductDTO billProductDTO = map.get(id);
			if(billProductDTO!=null) {
				billProductDTO.setQuantity(quantity);
			}
			session.setAttribute("cart", map);
		}
		return "redirect:/cart";
		
	}
}
