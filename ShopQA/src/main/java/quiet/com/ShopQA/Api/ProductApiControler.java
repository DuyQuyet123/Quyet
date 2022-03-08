package quiet.com.ShopQA.Api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quiet.com.ShopQA.DTO.ProductDTO;
import quiet.com.ShopQA.Service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductApiControler {
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/product/search")
	public List<ProductDTO> searchProduct(HttpServletRequest request) {
		
		List<ProductDTO> productDTOs = productService.search();
		
		return productDTOs;
	}
	
	@GetMapping(value = "/product/{id}")
	public ProductDTO get(@PathVariable(name = "id") Long id) {
		return productService.get(id);
	}
	@GetMapping(value = "/product")
	public ProductDTO getId(@RequestParam(name = "id") Long id) {
		return productService.get(id);
	}
}
