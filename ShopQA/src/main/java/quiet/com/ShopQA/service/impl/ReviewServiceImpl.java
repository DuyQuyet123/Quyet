package quiet.com.ShopQA.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quiet.com.ShopQA.DTO.ProductDTO;
import quiet.com.ShopQA.DTO.ReviewDTO;
import quiet.com.ShopQA.DTO.UserDTO;
import quiet.com.ShopQA.Entity.ProductEntity;
import quiet.com.ShopQA.Entity.ReviewEntity;
import quiet.com.ShopQA.Entity.UserEntity;
import quiet.com.ShopQA.Repostory.ReviewRepository;
import quiet.com.ShopQA.service.ReviewService;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public void add(ReviewDTO reviewDTO) {
		ReviewEntity reviewEntity = new ReviewEntity();
		reviewEntity.setReviewDate(new Date());
		reviewEntity.setStarNumBer(reviewDTO.getStarNumber());
		ProductEntity productEntity = new ProductEntity();
		productEntity.setId(reviewDTO.getProductDTO().getId());
		reviewEntity.setProductEntity(productEntity);
		UserEntity userEntity = new UserEntity();
		userEntity.setId(reviewDTO.getUserDTO().getId());
		reviewEntity.setUserEntity(userEntity);
		reviewRepository.save(reviewEntity);

	}

	@Override
	public List<ReviewDTO> find(Long id) {
		List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();
		List<ReviewEntity> reviewEntities = reviewRepository.search(id);
		for (ReviewEntity reviewEntity : reviewEntities) {
			reviewDTOs.add(convert(reviewEntity));
		}
		return reviewDTOs;
	}

	private ReviewDTO convert(ReviewEntity reviewEntity) {
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setId(reviewEntity.getId());
		reviewDTO.setReviewDate(String.valueOf(reviewEntity.getReviewDate()));
		reviewDTO.setStarNumber(reviewEntity.getStarNumBer());
		UserDTO userDTO = new UserDTO();
		userDTO.setId(reviewEntity.getUserEntity().getId());
		userDTO.setName(reviewEntity.getUserEntity().getName());
		reviewDTO.setUserDTO(userDTO);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(reviewEntity.getProductEntity().getId());
		reviewDTO.setProductDTO(productDTO);
		return reviewDTO;
	}
}
