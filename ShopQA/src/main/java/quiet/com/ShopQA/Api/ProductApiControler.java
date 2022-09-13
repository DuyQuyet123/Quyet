package quiet.com.ShopQA.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quiet.com.ShopQA.DTO.ProductDTO;
import quiet.com.ShopQA.Service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductApiControler {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> searchProduct(HttpServletRequest request) {

        List<ProductDTO> productDTOs = productService.search();

        return productDTOs;
    }

    @GetMapping("{id}")
    public ProductDTO get(@PathVariable(name = "id") Long id) {
        return productService.get(id);
    }

}
