package quiet.com.ShopQA.Controler;


import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import quiet.com.ShopQA.DTO.UserPrincipal;

import quiet.com.ShopQA.Service.BillProductService;
import quiet.com.ShopQA.Service.BillService;
import quiet.com.ShopQA.Service.CommentService;
import quiet.com.ShopQA.Service.EmailService;
import quiet.com.ShopQA.Service.FavoriteProductService;
import quiet.com.ShopQA.Service.ProductService;
import quiet.com.ShopQA.Service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quiet.com.ShopQA.DTO.BillDTO;
import quiet.com.ShopQA.DTO.BillProductDTO;
import quiet.com.ShopQA.DTO.CommentDTO;
import quiet.com.ShopQA.DTO.FavoriteProductDTO;
import quiet.com.ShopQA.DTO.ProductDTO;
import quiet.com.ShopQA.DTO.ReviewDTO;
import quiet.com.ShopQA.DTO.UserDTO;

@Controller
public class MemberClientControler {

	@Autowired
	private ProductService productService;
	@Autowired
	private BillProductService billProductService;
	@Autowired
	private BillService billService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private FavoriteProductService favoriteProductService;
	@Autowired
	private ReviewService reviewService;

	@GetMapping("/member/add-bill")
	public String addBill(HttpSession session, Model model) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		UserDTO userDTO = new UserDTO();
		userDTO.setId(principal.getId());

		Object obj = session.getAttribute("cart");

		if (obj != null) {
			Map<String, BillProductDTO> map = (Map<String, BillProductDTO>) obj;
			BillDTO billDTO = new BillDTO();
			billDTO.setUserDTO(userDTO);
			billDTO.setStatus("NEW");
			billService.insert(billDTO);

			Long totalPrice = (long) 0;
			for (Entry<String, BillProductDTO> entry : map.entrySet()) {

				BillProductDTO billProduct = entry.getValue();
				billProduct.setBillDTO(billDTO);
				System.out.println(entry.toString());
				billProductService.insert(billProduct);

				totalPrice = totalPrice + billProduct.getQuantity() * billProduct.getUnitPrice();

				ProductDTO productDTO = productService.get(entry.getValue().getProductDTO().getId());
				productDTO.setQuantity(productDTO.getQuantity() - billProduct.getQuantity());
				productService.update(productDTO);
			}
			Long finalPrice = (long) 0;

			List<BillDTO> list = billService.searchByBuyerId(principal.getId());
			// mua lan dau
			if (list.size() == 1) {
				finalPrice = totalPrice - ((totalPrice * 5) / 100);
				billDTO.setPriceTotal(finalPrice);
				billDTO.setDiscountPercent(5);
				billDTO.setPay(String.valueOf(totalPrice));
				System.out.println("khuyen mai 5% cho lan thanh toan dau tien cua ban!!!!" + billDTO.getPriceTotal());
			} else {
				finalPrice = totalPrice;
				billDTO.setPriceTotal(finalPrice);
				billDTO.setDiscountPercent(0);
				billDTO.setPay(String.valueOf(totalPrice));
			}
			billService.update(billDTO);

			String content = "";

			for (BillProductDTO i : map.values()) {

				// "sản phẩm: " + i.getProductDTO().getName() + " Số tiền: " + i.getUnitPrice()
				// * i.getQuantity());
				content += "Sản phẩm: " + i.getProductDTO().getName() + ", Số tiền: "
						+ i.getUnitPrice() * i.getQuantity() + " ($); \n";
			}

			// gửi email sau khi thanh toán

			emailService.sendSimpleMessage(principal.getEmail(), "Hóa đơn thanh toán",
					"Bạn vừa thanh toán thành công đơn hàng trên hệ thống website QA!!! \n" + content + "\n Tổng tiền: "
							+ finalPrice + " ($)");
			System.out.println(principal.getEmail());
			// xóa cart khi đã thanh toán
			session.removeAttribute("cart");
			// return "redirect:/member/bills";
		}
		return "redirect:/products";
	}

	Long id;

	@GetMapping("/member/bills")
	public String Bills(HttpServletRequest request, @RequestParam(name = "id", required = false) Long id) {
		UserPrincipal uPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		id = uPrincipal.getId();
		List<BillDTO> list = billService.searchByBuyerId(id);

		request.setAttribute("list", list);

		request.setAttribute("id", id);

		return "member/bills";
	}

	@GetMapping(value = "/member/bill")
	public String billDetail(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {

		List<BillProductDTO> listBillProduct = billProductService.searchbyBill(id);// danh sach san
		// pham trong 1
		// bill
		request.setAttribute("billProducts", listBillProduct);
		request.setAttribute("id", id);
		return "member/bill";
	}

	@GetMapping(value = "/member/delete-bills")
	public String deleteBillsProduct(@RequestParam(name = "id", required = true) Long id) {
		BillDTO billDTO = billService.get(id);
		if (billDTO.getStatus().equals("NEW")) {
			billService.delete(id);
		} else {
			return "redirect:/member/bills";
		}

		return "redirect:/member/bills";
	}

	@GetMapping(value = "/member/delete-bill")
	public String deleteBillProduct(@RequestParam(name = "id", required = true) Long id,
			@RequestParam(name = "billId", required = true) Long billId) {
		// System.out.println(billId);

		// System.out.println();
		BillDTO billDTO = billService.get(billId);
		System.out.println(billDTO);
		if (billDTO.getStatus().equals("NEW")) {
			billProductService.delete(id);
		} else {
			return "redirect:/member/bill?id=" + billId;
		}

		Long priceTotal = (long) 0;
		List<BillProductDTO> billProductDTOs = billProductService.searchbyBill(billId);
		for (BillProductDTO billProductDTO : billProductDTOs) {
			priceTotal = priceTotal + billProductDTO.getUnitPrice();
		}
		billDTO.setPriceTotal(priceTotal * billDTO.getDiscountPercent());
		billDTO.setPay(String.valueOf(priceTotal));
		billService.update1(billDTO);
		return "redirect:/member/bill?id=" + billId;
	}

	@PostMapping(value = "/member/comment")
	public String comment(HttpServletRequest request, @RequestParam(name = "productId") Long productId,
			@RequestParam(name = "comment") String comment) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = principal.getId();
		UserDTO userDTO = new UserDTO();
		userDTO.setName(principal.getName());
		userDTO.setId(userId);
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setUserDTO(userDTO);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(productId);
		commentDTO.setProductDTO(productDTO);
		commentDTO.setComment(comment);
		commentService.insert(commentDTO);
		return "redirect:/product?id=" + commentDTO.getProductDTO().getId();
	}

	@GetMapping(value = "/member/delete/comment")
	public String deleteComment(HttpServletRequest request, @RequestParam(name = "productId") Long productId, Long id) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = principal.getId();
		CommentDTO commentDTO = commentService.searchComment(id);
		
		if (commentDTO.getUserDTO().getId().equals(userId)) {
			commentService.delete(id);
			System.out.println("thành công");
		} else {
			return "redirect:/product?id=" + productId;
		}
		return "redirect:/product?id=" + productId;
	}

	@PostMapping(value = "/member/update/comment")
	public String updateComment(HttpServletRequest request, @RequestParam(name = "productId") Long productId,
			@RequestParam(name = "comment") String comment, Long id) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = principal.getId();

		System.out.println(comment);

		CommentDTO commentDTO = commentService.searchComment(id);
		if (commentDTO.getUserDTO().getId().equals(userId)) {
			commentDTO.setComment(comment);
			commentService.update(commentDTO);
			System.out.println("Thành công");
		} else {
			return "redirect:/product?id=" + productId;
		}
		return "redirect:/product?id=" + productId;
	}

	@GetMapping(value = "/member/favoriteProduct/add")
	public String favoriteProduct(HttpServletRequest request, @RequestParam(name = "productId") Long productId) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = principal.getId();

		UserDTO userDTO = new UserDTO();
		userDTO.setId(userId);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(productId);
		FavoriteProductDTO favoriteProductDTO = new FavoriteProductDTO();
		favoriteProductDTO.setUserDTO(userDTO);
		favoriteProductDTO.setProductDTO(productDTO);
		List<FavoriteProductDTO> favoriteProductDTOs = favoriteProductService.searchbyProduct(userId);
		int count = 0;
		for (FavoriteProductDTO favoriteProductDTO2 : favoriteProductDTOs) {
			if (favoriteProductDTO2.getProductDTO().getId().equals(productId)) {
				count++;
			}
		}
		if (count == 0) {
			favoriteProductService.insert(favoriteProductDTO);
		} else {
			return "redirect:/member/favoriteProduct/search";
		}
		return "redirect:/member/favoriteProduct/search";
	}

	@GetMapping(value = "/member/favoriteProduct/search")
	public String searchFavoriteProduct(HttpServletRequest request,
			@RequestParam(name = "id", required = false) Long id) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = principal.getId();
		List<FavoriteProductDTO> favoriteProductDTOs = favoriteProductService.searchbyProduct(userId);
		request.setAttribute("favoriteProducts", favoriteProductDTOs);
		return "member/favorite-product";
	}

	@GetMapping(value = "/member/favoriteProduct/delete")
	public String deleteFavoriteProduct(HttpServletRequest request, @RequestParam(name = "id") Long id) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = principal.getId();
		List<FavoriteProductDTO> favoriteProductDTOs = favoriteProductService.searchbyProduct(userId);
		int count = 0;
		for (FavoriteProductDTO favoriteProductDTO2 : favoriteProductDTOs) {
			if (favoriteProductDTO2.getId() == id) {
				count++;
			}
		}
		if (count != 0) {
			favoriteProductService.delete(id);
		} else {
			return "redirect:/member/favoriteProduct/search";
		}
		return "redirect:/member/favoriteProduct/search";
	}

	@PostMapping(value = "/member/review")
	public String review(HttpServletRequest request, @ModelAttribute ReviewDTO reviewDTO,
			@RequestParam(name = "productId") Long productId, @RequestParam(name = "rating") int rating) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = principal.getId();
		// System.out.println(rating);
		int check = 0;
		List<ReviewDTO> reviewDTOs = reviewService.find(productId);
		for (ReviewDTO reviewDTO2 : reviewDTOs) {
			if (reviewDTO2.getUserDTO().getId().equals(userId)) {
				check++;
			}
		}
		if (check == 0) {
			UserDTO userDTO = new UserDTO();
			userDTO.setId(userId);
			reviewDTO.setUserDTO(userDTO);
			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(productId);
			reviewDTO.setProductDTO(productDTO);
			reviewDTO.setStarNumber(rating);
			reviewService.add(reviewDTO);
		} else {
			return "redirect:/product?id=" + productId;
		}

		return "redirect:/product?id=" + productId;
	}
}
