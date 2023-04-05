package quiet.com.ShopQA.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import quiet.com.ShopQA.Entity.UserEntity;
import quiet.com.ShopQA.Repostory.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import quiet.com.ShopQA.DTO.UserDTO;

import quiet.com.ShopQA.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepostory;

	@Override
	public void insert(UserDTO userDTO) {
		UserEntity userEntity = new UserEntity();
		userEntity.setName(userDTO.getName());
		userEntity.setEnabled(userDTO.getEnabled());
		userEntity.setUsername(userDTO.getUsername());
		userEntity.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setGioiTinh(userDTO.getGioiTinh());
		userEntity.setRole("ROLE_MEMBER");
		userEntity.setPhone(userDTO.getPhone());
		userEntity.setAddress(userDTO.getAddress());
		userRepostory.save(userEntity);
	}

	@Override
	public void update(UserDTO userDTO) {
		UserEntity userEntity = userRepostory.getOne(userDTO.getId());
		if (userEntity != null) {
			userEntity.setName(userDTO.getName());
			userEntity.setUsername(userDTO.getUsername());
			userEntity.setEnabled(userDTO.getEnabled());
			userEntity.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
			userEntity.setEmail(userDTO.getEmail());
			userEntity.setGioiTinh(userDTO.getGioiTinh());
			userEntity.setRole("ROLE_MEMBER");
			userEntity.setPhone(userDTO.getPhone());
			userEntity.setAddress(userDTO.getAddress());
			userRepostory.save(userEntity);
		}

	}

	@Override
	public void delete(Long id) {
		UserEntity userEntity = userRepostory.getOne(id);
		if (userEntity != null) {
			userRepostory.delete(userEntity);
		}

	}

	@Override
	public UserDTO get(Long id) {
		UserEntity userEntity = userRepostory.getOne(id);

		return convertEntity(userEntity);
	}

	private UserDTO convertEntity(UserEntity userEntity) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(userEntity.getId());
		userDTO.setName(userEntity.getName());
		userDTO.setEnabled(userEntity.getEnabled());
		userDTO.setUsername(userEntity.getUsername());
		userDTO.setPassword(userEntity.getPassword());
		userDTO.setGioiTinh(userEntity.getGioiTinh());
		userDTO.setEmail(userEntity.getEmail());
		userDTO.setRole(userEntity.getRole());
		userDTO.setPhone(userEntity.getPhone());
		userDTO.setAddress(userEntity.getAddress());
		return userDTO;
	}

	@Override
	public UserDTO getByUserName(String userName) {
		UserEntity userEntity = userRepostory.search(userName);
		return convertEntity(userEntity);
	}

	@Override
	public List<UserDTO> search(String name, Pageable pageable) {
		List<UserEntity> users = userRepostory.search2(name, pageable);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for (UserEntity uEntity : users) {
			userDTOs.add(convertEntity(uEntity));
		}
		return userDTOs;
	}

	@Override
	public List<UserDTO> search1() {
		List<UserEntity> users = userRepostory.findAll();
		List<UserDTO> userDTO = new ArrayList<UserDTO>();
		for (UserEntity uEntity : users) {
			userDTO.add(convertEntity(uEntity));
		}
		return userDTO;
	}

	@Override
	public List<UserDTO> search3(Pageable pageable) {
		List<UserEntity> users = userRepostory.search3(pageable);
		List<UserDTO> userDTO = new ArrayList<UserDTO>();
		for (UserEntity uEntity : users) {
			userDTO.add(convertEntity(uEntity));
		}
		return userDTO;
	}

}
