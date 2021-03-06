package quiet.com.ShopQA.Controler;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import quiet.com.ShopQA.DTO.CategoryDTO;
import quiet.com.ShopQA.DTO.ProductDTO;
import quiet.com.ShopQA.DTO.SizeDTO;
import quiet.com.ShopQA.DTO.TrademarkDTO;
import quiet.com.ShopQA.Service.CategoryService;
import quiet.com.ShopQA.Service.ProductService;
import quiet.com.ShopQA.Service.SizeService;
import quiet.com.ShopQA.Service.TrademarkService;

@Controller
public class ProductControler {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SizeService sizeService;
	@Autowired
	private TrademarkService trademarkService;

	@GetMapping("/admin/add-product")
	public String addProductGet(HttpServletRequest request, Model model) {
		model.addAttribute("product", new ProductDTO());
		List<CategoryDTO> listCategoryDTOs = categoryService.search();
		List<SizeDTO> listSizeDTOs = sizeService.search();
		List<TrademarkDTO> trademarkDTOs = trademarkService.search();

		request.setAttribute("listTrademark", trademarkDTOs);
		request.setAttribute("listSize", listSizeDTOs);
		request.setAttribute("listCategory", listCategoryDTOs);
		return "admin/product/addproduct";
	}

	@PostMapping("/admin/add-product")
	public String addProductPost(@ModelAttribute(name = "addProduct") ProductDTO productDTO,
			@RequestParam(name = "imageFile") MultipartFile imagefile) {
		if (imagefile != null && !imagefile.isEmpty()) {
			String filename = imagefile.getOriginalFilename();
			try {
				String FOLDER_SAVE = "D:\\Images\\";
				File outputFile = new File(FOLDER_SAVE + filename);

				imagefile.transferTo(outputFile);
				// luu filename v??o database
				productDTO.setImage(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		productService.insert(productDTO);
		return "redirect:/admin/search-product";
	}

	@GetMapping("/admin/update-product")
	public String updateProductGet(HttpServletRequest request, Model model, @RequestParam(name = "id") Long id) {
		ProductDTO productDTO = productService.get(id);
		List<CategoryDTO> listCategoryDTOs = categoryService.search();
		List<SizeDTO> listSizeDTOs = sizeService.search();
		List<TrademarkDTO> trademarkDTOs = trademarkService.search();

		model.addAttribute("productDTO", productDTO);
		request.setAttribute("listTrademark", trademarkDTOs);
		request.setAttribute("listSize", listSizeDTOs);
		request.setAttribute("listCategory", listCategoryDTOs);
		return "admin/product/updateproduct";
	}

	@PostMapping("/admin/update-product")
	public String updateProductPost(@ModelAttribute(name = "product") ProductDTO productDTO,
			@RequestParam(name = "imageFile") MultipartFile imagefile) {
		if (imagefile != null && !imagefile.isEmpty()) {
			String filename = imagefile.getOriginalFilename();
			try {
				String FOLDER_SAVE = "D:\\Images\\";
				File outputFile = new File(FOLDER_SAVE + filename);

				imagefile.transferTo(outputFile);
				// luu filename v??o database
				productDTO.setImage(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		productService.update(productDTO);
		return "redirect:/admin/search-product";
	}

	@GetMapping("/admin/delete-product")
	public String deleteProduct(@RequestParam(name = "id") Long id) {
		productService.delete(id);
		return "redirect:/admin/search-product";
	}

	@GetMapping("/admin/search-product")
	public String searchProduct(HttpServletRequest request) {

		List<ProductDTO> productDTOs = productService.search();

		List<CategoryDTO> listCategoryDTOs = categoryService.search();
		List<SizeDTO> listSizeDTOs = sizeService.search();
		List<TrademarkDTO> trademarkDTOs = trademarkService.search();

		request.setAttribute("TrademarkDTOs", trademarkDTOs);
		request.setAttribute("SizeDTOs", listSizeDTOs);
		request.setAttribute("CategoryDTOs", listCategoryDTOs);
		request.setAttribute("ProductDTOs", productDTOs);
		return "admin/product/searchproduct";
	}

	// ManagerProduct
	@GetMapping("/manager/add-product")
	public String addProductGetManager(HttpServletRequest request, Model model) {
		model.addAttribute("product", new ProductDTO());
		List<CategoryDTO> listCategoryDTOs = categoryService.search();
		List<SizeDTO> listSizeDTOs = sizeService.search();
		List<TrademarkDTO> trademarkDTOs = trademarkService.search();

		request.setAttribute("listTrademark", trademarkDTOs);
		request.setAttribute("listSize", listSizeDTOs);
		request.setAttribute("listCategory", listCategoryDTOs);
		return "manager/product/addproduct";
	}

	@PostMapping("/manager/add-product")
	public String addProductPostManager(@ModelAttribute(name = "addProduct") ProductDTO productDTO,
			@RequestParam(name = "imageFile") MultipartFile imagefile) {
		if (imagefile != null && !imagefile.isEmpty()) {
			String filename = imagefile.getOriginalFilename();
			try {
				String FOLDER_SAVE = "D:\\Images\\";
				File outputFile = new File(FOLDER_SAVE + filename);

				imagefile.transferTo(outputFile);
				// luu filename v??o database
				productDTO.setImage(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		productService.insert(productDTO);
		return "redirect:/manager/search-product";
	}

	@GetMapping("/manager/update-product")
	public String updateProductGetManager(HttpServletRequest request, Model model, @RequestParam(name = "id") Long id) {
		ProductDTO productDTO = productService.get(id);
		List<CategoryDTO> listCategoryDTOs = categoryService.search();
		List<SizeDTO> listSizeDTOs = sizeService.search();
		List<TrademarkDTO> trademarkDTOs = trademarkService.search();

		model.addAttribute("productDTO", productDTO);
		request.setAttribute("listTrademark", trademarkDTOs);
		request.setAttribute("listSize", listSizeDTOs);
		request.setAttribute("listCategory", listCategoryDTOs);
		return "manager/product/updateproduct";
	}

	@PostMapping("/manager/update-product")
	public String updateProductPostManager(@ModelAttribute(name = "product") ProductDTO productDTO,
			@RequestParam(name = "imageFile") MultipartFile imagefile) {
		if (imagefile != null && !imagefile.isEmpty()) {
			String filename = imagefile.getOriginalFilename();
			try {
				String FOLDER_SAVE = "D:\\Images\\";
				File outputFile = new File(FOLDER_SAVE + filename);

				imagefile.transferTo(outputFile);
				// luu filename v??o database
				productDTO.setImage(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		productService.update(productDTO);
		return "redirect:/manager/search-product";
	}

	@GetMapping("/manager/delete-product")
	public String deleteProductManager(@RequestParam(name = "id") Long id) {
		productService.delete(id);
		return "redirect:/admin/search-product";
	}

	@GetMapping("/manager/search-product")
	public String searchProductManager(HttpServletRequest request) {

		List<ProductDTO> productDTOs = productService.search();

		List<CategoryDTO> listCategoryDTOs = categoryService.search();
		List<SizeDTO> listSizeDTOs = sizeService.search();
		List<TrademarkDTO> trademarkDTOs = trademarkService.search();

		request.setAttribute("TrademarkDTOs", trademarkDTOs);
		request.setAttribute("SizeDTOs", listSizeDTOs);
		request.setAttribute("CategoryDTOs", listCategoryDTOs);
		request.setAttribute("ProductDTOs", productDTOs);
		return "manager/product/searchproduct";
	}
}
