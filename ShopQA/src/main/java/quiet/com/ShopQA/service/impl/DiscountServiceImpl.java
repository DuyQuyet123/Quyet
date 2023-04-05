package quiet.com.ShopQA.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quiet.com.ShopQA.DTO.DiscountDTO;
import quiet.com.ShopQA.Entity.DiscountEntity;
import quiet.com.ShopQA.Repostory.DiscountRepository;
import quiet.com.ShopQA.service.DiscountService;

@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {
	@Autowired
	private DiscountRepository discountRepository;

	@Override
	public void insert(DiscountDTO discountDTO) {
		DiscountEntity discountEntity = new DiscountEntity();
		discountEntity.setName(discountDTO.getName());
		discountEntity.setDiscount(discountDTO.getDiscount());
		discountEntity.setDateCreate(LocalDateTime.now());
		discountRepository.save(discountEntity);
	}

	@Override
	public void update(DiscountDTO discountDTO) {
		// TODO Auto-generated method stub
		DiscountEntity discountEntity = discountRepository.getOne(discountDTO.getId());
		if (discountEntity != null) {
			discountEntity.setName(discountDTO.getName());
			discountEntity.setDiscount(discountDTO.getDiscount());
			discountEntity.setDateCreate(LocalDateTime.now());
			discountRepository.save(discountEntity);
		}
	}

	@Override
	public void delete(Long id) {
		DiscountEntity discountEntity = discountRepository.getOne(id);
		if (discountEntity != null) {
			discountRepository.delete(discountEntity);
		}
	}

	@Override
	public DiscountDTO get(Long id) {
		DiscountEntity discountEntity = discountRepository.getOne(id);

		return convert(discountEntity);
	}

	@Override
	public List<DiscountDTO> search() {
		List<DiscountEntity> discountEntities = discountRepository.findAll();
		List<DiscountDTO> discountDTOs = new ArrayList<DiscountDTO>();
		for (DiscountEntity discountEntity : discountEntities) {
			discountDTOs.add(convert(discountEntity));
		}
		return discountDTOs;
	}

	public DiscountDTO convert(DiscountEntity discountEntity) {
		DiscountDTO discountDTO = new DiscountDTO();
		discountDTO.setId(discountEntity.getId());
		discountDTO.setName(discountEntity.getName());
		discountDTO.setDiscount(discountEntity.getDiscount());
		discountDTO.setDayCreate(discountEntity.getDateCreate());
		return discountDTO;

	}
}
