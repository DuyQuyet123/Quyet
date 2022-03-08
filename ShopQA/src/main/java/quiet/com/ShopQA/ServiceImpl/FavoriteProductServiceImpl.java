package quiet.com.ShopQA.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quiet.com.ShopQA.DTO.CategoryDTO;
import quiet.com.ShopQA.DTO.FavoriteProductDTO;
import quiet.com.ShopQA.DTO.ProductDTO;
import quiet.com.ShopQA.DTO.TrademarkDTO;
import quiet.com.ShopQA.DTO.UserDTO;
import quiet.com.ShopQA.Entity.FavoriteProductEntity;
import quiet.com.ShopQA.Entity.ProductEntity;
import quiet.com.ShopQA.Entity.UserEntity;
import quiet.com.ShopQA.Repostory.FavoriteProductRepository;
import quiet.com.ShopQA.Service.FavoriteProductService;

@Service
@Transactional
public class FavoriteProductServiceImpl implements FavoriteProductService {
	@Autowired
	private FavoriteProductRepository favoriteProductRepository;

	@Override
	public void insert(FavoriteProductDTO favoriteProductDTO) {
		// TODO Auto-generated method stub
		FavoriteProductEntity favoriteProductEntity = new FavoriteProductEntity();
		favoriteProductEntity.setFavoriteDate(new Date());
		UserEntity userEntity = new UserEntity();
		userEntity.setId(favoriteProductDTO.getUserDTO().getId());
		favoriteProductEntity.setUserEntity(userEntity);
		ProductEntity productEntity = new ProductEntity();
		productEntity.setId(favoriteProductDTO.getProductDTO().getId());
		favoriteProductEntity.setProductEntity(productEntity);

		favoriteProductRepository.save(favoriteProductEntity);

	}

	@Override
	public void delete(Long id) {
		FavoriteProductEntity favoriteProductEntity = favoriteProductRepository.getOne(id);
		if (favoriteProductEntity != null) {
			favoriteProductRepository.deleteById(id);
		}

	}

	@Override
	public List<FavoriteProductDTO> searchbyProduct(Long uId) {
		List<FavoriteProductEntity> favoriteProductEntities = favoriteProductRepository.search(uId);
		List<FavoriteProductDTO> favoriteProductDTOs = new ArrayList<FavoriteProductDTO>();
		for (FavoriteProductEntity favoriteProductEntity : favoriteProductEntities) {
			favoriteProductDTOs.add(convert(favoriteProductEntity));
		}
		return favoriteProductDTOs;

	}

	public FavoriteProductDTO convert(FavoriteProductEntity favoriteProductEntity) {
		FavoriteProductDTO favoriteProductDTO = new FavoriteProductDTO();
		favoriteProductDTO.setId(favoriteProductEntity.getId());
		favoriteProductDTO.setDate(String.valueOf(favoriteProductEntity.getFavoriteDate()));

		UserDTO userDTO = new UserDTO();
		userDTO.setId(favoriteProductEntity.getUserEntity().getId());
		favoriteProductDTO.setUserDTO(userDTO);

		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(favoriteProductEntity.getProductEntity().getId());
		productDTO.setName(favoriteProductEntity.getProductEntity().getName());
		productDTO.setImage(favoriteProductEntity.getProductEntity().getImage());
		productDTO.setPrice(favoriteProductEntity.getProductEntity().getPrice());
		productDTO.setQuantity(favoriteProductEntity.getProductEntity().getQuantity());
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setName(favoriteProductEntity.getProductEntity().getCategoryEntity().getName());
		productDTO.setCategoryDTO(categoryDTO);
		TrademarkDTO trademarkDTO = new TrademarkDTO();
		trademarkDTO.setName(favoriteProductEntity.getProductEntity().getTrademarkEntity().getName());
		productDTO.setTrademarkDTO(trademarkDTO);
		favoriteProductDTO.setProductDTO(productDTO);

		return favoriteProductDTO;
	}
}
