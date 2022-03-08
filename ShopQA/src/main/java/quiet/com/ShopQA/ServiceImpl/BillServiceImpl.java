package quiet.com.ShopQA.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import quiet.com.ShopQA.DTO.BillDTO;
import quiet.com.ShopQA.DTO.UserDTO;
import quiet.com.ShopQA.Entity.BillEntity;
import quiet.com.ShopQA.Entity.UserEntity;
import quiet.com.ShopQA.Repostory.BillRepository;
import quiet.com.ShopQA.Service.BillService;
import quiet.com.ShopQA.Ultils.DateTimeUtils;

@Service
@Transactional
public class BillServiceImpl implements BillService {
	@Autowired
	private BillRepository billRepository;

	@Override
	public void insert(BillDTO billDTO) {
		BillEntity billEntity = new BillEntity();

		billEntity.setBuyDate(new Date());
		billEntity.setStatus(billDTO.getStatus());
		billEntity.setPay(billDTO.getPay());

		UserEntity userEntity = new UserEntity();
		userEntity.setId(billDTO.getUserDTO().getId());
		billEntity.setBuyer(userEntity);

		billRepository.save(billEntity);
		billDTO.setId(billEntity.getId());

	}

	@Override
	public void update(BillDTO billDTO) {
		BillEntity billEntity = billRepository.getOne(billDTO.getId());
		if (billEntity != null) {
			billEntity.setPriceTotal(billDTO.getPriceTotal());
			billEntity.setDiscountPercent(billDTO.getDiscountPercent());
			billEntity.setStatus(billDTO.getStatus());
			billEntity.setPay(billDTO.getPay());
			billRepository.save(billEntity);
		}
	}

	@Override
	public void delete(Long id) {
		BillEntity billEntity = billRepository.getOne(id);
		if (billEntity != null) {
			billRepository.delete(billEntity);
		}
	}

	@Override
	public BillDTO get(Long id) {
		BillEntity billEntity = billRepository.getOne(id);
		return convertDTO(billEntity);
	}

	@Override
	public List<BillDTO> search() {
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		List<BillEntity> billEntities = billRepository.findAll();
		for (BillEntity billEntity : billEntities) {
			billDTOs.add(convertDTO(billEntity));
		}
		return billDTOs;
	}

	private BillDTO convertDTO(BillEntity billEntity) {
		BillDTO billDTO = new BillDTO();
		billDTO.setId(billEntity.getId());
		billDTO.setBuyDate(DateTimeUtils.formatDate(billEntity.getBuyDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
		billDTO.setPriceTotal(billEntity.getPriceTotal());
		billDTO.setStatus(billEntity.getStatus());
		billDTO.setDiscountPercent(billEntity.getDiscountPercent());
		billDTO.setPay(billEntity.getPay());

		UserDTO userDTO = new UserDTO();
		userDTO.setId(billEntity.getBuyer().getId());
		userDTO.setAddress(billEntity.getBuyer().getAddress());
		userDTO.setName(billEntity.getBuyer().getName());
		userDTO.setPhone(billEntity.getBuyer().getPhone());
		billDTO.setUserDTO(userDTO);

		return billDTO;
	}

	@Override
	public List<BillDTO> searchByBuyerId(Long id) {
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		List<BillEntity> billEntities = (List<BillEntity>) billRepository.search(id);
		if (billEntities.isEmpty()) {
			return null;
		} else {
			for (BillEntity billEntity : billEntities) {
				billDTOs.add(convertDTO(billEntity));
			}
		}
		return billDTOs;
	}

	@Override
	public void update1(BillDTO billDTO) {
		BillEntity billEntity = billRepository.getOne(billDTO.getId());
		if (billEntity != null) {
			billEntity.setPriceTotal(billDTO.getPriceTotal());
			billEntity.setPay(billDTO.getPay());
		}
		billRepository.save(billEntity);
	}

	@Override
	public void update2(BillDTO billDTO) {
		BillEntity billEntity = billRepository.getOne(billDTO.getId());
		if (billEntity != null) {
			billEntity.setStatus(billDTO.getStatus());
		}
		billRepository.save(billEntity);
	}

	@Override
	public List<BillDTO> search2(Pageable pageable) {
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		List<BillEntity> billEntities = billRepository.search2(pageable);
		for (BillEntity billEntity : billEntities) {
			billDTOs.add(convertDTO(billEntity));
		}
		return billDTOs;
	}

}
