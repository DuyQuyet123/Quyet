package quiet.com.ShopQA.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quiet.com.ShopQA.DTO.SizeDTO;
import quiet.com.ShopQA.Entity.SizeEntity;
import quiet.com.ShopQA.Repostory.SizeRepository;
import quiet.com.ShopQA.Service.SizeService;

@Service
@Transactional
public class SizeServiceImpl implements SizeService {
	@Autowired
	private SizeRepository sizeRepostory;

	@Override
	public void insert(SizeDTO sizeDTO) {
		SizeEntity sizeEntity = new SizeEntity();
		sizeEntity.setName(sizeDTO.getName());
		sizeRepostory.save(sizeEntity);
	}

	@Override
	public void update(SizeDTO sizeDTO) {
		SizeEntity sizeEntity = sizeRepostory.getOne(sizeDTO.getId());
		if (sizeEntity != null) {
			sizeEntity.setName(sizeDTO.getName());
			sizeRepostory.save(sizeEntity);
		}

	}

	@Override
	public void delete(Long id) {
		SizeEntity sizeEntity = sizeRepostory.getOne(id);
		if (sizeEntity != null) {
			sizeRepostory.delete(sizeEntity);
		}
	}

	@Override
	public SizeDTO get(Long id) {
		SizeEntity sizeEntity = sizeRepostory.getOne(id);

		return convert(sizeEntity);
	}

	private SizeDTO convert(SizeEntity sizeEntity) {
		SizeDTO sizeDTO = new SizeDTO();
		sizeDTO.setId(sizeEntity.getId());
		sizeDTO.setName(sizeEntity.getName());
		return sizeDTO;

	}

	@Override
	public List<SizeDTO> search() {
		List<SizeEntity> sizeEntity = sizeRepostory.findAll();
		List<SizeDTO> sizeDTOs = new ArrayList<SizeDTO>();
		for (SizeEntity sEntity : sizeEntity) {
			sizeDTOs.add(convert(sEntity));
		}
		return sizeDTOs;
	}
}
