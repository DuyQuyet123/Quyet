package quiet.com.ShopQA.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import quiet.com.ShopQA.DTO.UserDTO;

public interface UserService {
	void insert(UserDTO userDTO);

	void update(UserDTO userDTO);

	void delete(Long id);

	UserDTO get(Long id);

	UserDTO getByUserName(String userName);

	List<UserDTO> search(String name, Pageable pageable);

	List<UserDTO> search1();

	List<UserDTO> search3(Pageable pageable);
}
