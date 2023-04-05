package quiet.com.ShopQA.service;

import java.util.List;

import quiet.com.ShopQA.DTO.CommentDTO;

public interface CommentService {
	
	void insert(CommentDTO commentDTO);

	void update(CommentDTO commentDTO);

	void delete(Long id);

	CommentDTO searchComment(Long id);

	List<CommentDTO> searchByProduct(Long id);
	
	List<CommentDTO> searchCommentByUser(Long id);
}
