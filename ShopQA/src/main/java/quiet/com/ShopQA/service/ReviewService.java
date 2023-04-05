package quiet.com.ShopQA.service;

import java.util.List;

import quiet.com.ShopQA.DTO.ReviewDTO;

public interface ReviewService {
	
	void add(ReviewDTO reviewDTO);
	
	List<ReviewDTO> find(Long id);
}
