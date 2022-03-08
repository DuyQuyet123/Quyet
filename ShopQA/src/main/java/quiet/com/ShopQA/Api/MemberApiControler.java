package quiet.com.ShopQA.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quiet.com.ShopQA.DTO.CommentDTO;
import quiet.com.ShopQA.DTO.FavoriteProductDTO;
import quiet.com.ShopQA.DTO.UserPrincipal;
import quiet.com.ShopQA.Service.CommentService;
import quiet.com.ShopQA.Service.FavoriteProductService;

@RestController
@RequestMapping("/api")
public class MemberApiControler {

	@Autowired
	private CommentService commentService;

	@Autowired
	private FavoriteProductService favoriteProductService;

	@GetMapping(value = "/comment/searchByProduct/{id}")
	public List<CommentDTO> searchComment(@PathVariable(name = "id") Long id) {
		return commentService.searchByProduct(id);
	}

	@GetMapping(value = "/comment/searchCommentByUser/{id}")
	public List<CommentDTO> searchCommentUser(@PathVariable(name = "id") Long id) {
		return commentService.searchCommentByUser(id);
	}

	@GetMapping(value = "/favoriteProduct/search")
	public List<FavoriteProductDTO> searchFavoriteProduct(@RequestParam(name = "uId") Long uId) {
		return favoriteProductService.searchbyProduct(uId);

	}
	
	
}
