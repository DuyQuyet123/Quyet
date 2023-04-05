package quiet.com.ShopQA.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quiet.com.ShopQA.DTO.CommentDTO;
import quiet.com.ShopQA.DTO.ProductDTO;
import quiet.com.ShopQA.DTO.UserDTO;
import quiet.com.ShopQA.Entity.CommentEntity;
import quiet.com.ShopQA.Entity.ProductEntity;
import quiet.com.ShopQA.Entity.UserEntity;
import quiet.com.ShopQA.Repostory.CommentRepository;
import quiet.com.ShopQA.service.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public void insert(CommentDTO commentDTO) {
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setComment(commentDTO.getComment());
		commentEntity.setCommentDate(new Date());

		UserEntity userEntity = new UserEntity();
		userEntity.setId(commentDTO.getUserDTO().getId());
		commentEntity.setUserEntity(userEntity);

		ProductEntity productEntity = new ProductEntity();
		productEntity.setId(commentDTO.getProductDTO().getId());
		commentEntity.setProductEntity(productEntity);
		commentRepository.save(commentEntity);

	}

	@Override
	public void update(CommentDTO commentDTO) {
		// TODO Auto-generated method stub
		CommentEntity commentEntity = commentRepository.getOne(commentDTO.getId());
		if (commentEntity != null) {
			commentEntity.setComment(commentDTO.getComment());
			commentEntity.setCommentDate(new Date());
			commentRepository.save(commentEntity);
		}

	}

	@Override
	public void delete(Long id) {
		CommentEntity comment = commentRepository.getOne(id);
		if (comment != null) {
			commentRepository.deleteById(id);
		}

	}

	@Override
	public List<CommentDTO> searchByProduct(Long id) {
		List<CommentEntity> listComments = commentRepository.searchByProduct(id);
		List<CommentDTO> commentDTOs = new ArrayList<>();
		for (CommentEntity comment : listComments) {
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setId(comment.getId());
			commentDTO.setComment(comment.getComment());
			commentDTO.setCommentDate(String.valueOf(comment.getCommentDate()));

			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(comment.getProductEntity().getId());
			commentDTO.setProductDTO(productDTO);

			UserDTO userDTO = new UserDTO();
			userDTO.setName(comment.getUserEntity().getName());
			commentDTO.setUserDTO(userDTO);

			commentDTOs.add(commentDTO);
		}
		return commentDTOs;
	}

	@Override
	public CommentDTO searchComment(Long id) {
		CommentEntity listComments = commentRepository.getOne(id);
		return convert(listComments);
	}

	public CommentDTO convert(CommentEntity commentEntity) {
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setId(commentEntity.getId());
		commentDTO.setComment(commentEntity.getComment());
		commentDTO.setCommentDate(String.valueOf(commentEntity.getCommentDate()));

		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(commentEntity.getProductEntity().getId());
		productDTO.setName(commentEntity.getProductEntity().getName());
		commentDTO.setProductDTO(productDTO);

		UserDTO userDTO = new UserDTO();
		userDTO.setId(commentEntity.getUserEntity().getId());
		userDTO.setName(commentEntity.getUserEntity().getName());
		commentDTO.setUserDTO(userDTO);
		return commentDTO;
	}

	@Override
	public List<CommentDTO> searchCommentByUser(Long id) {
		List<CommentDTO> commentDTOs = new ArrayList<CommentDTO>();
		List<CommentEntity> commentEntities = commentRepository.searchCommentByUser(id);
		for (CommentEntity commentEntity : commentEntities) {
			commentDTOs.add(convert(commentEntity));
		}
		return commentDTOs;
	}
}