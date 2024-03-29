package quiet.com.ShopQA.service;

import java.util.List;

import quiet.com.ShopQA.DTO.FavoriteProductDTO;

public interface FavoriteProductService {
	
	void insert(FavoriteProductDTO favoriteProductDTO);
	
	void delete(Long id);
	
	List<FavoriteProductDTO> searchbyProduct(Long uId);
}
