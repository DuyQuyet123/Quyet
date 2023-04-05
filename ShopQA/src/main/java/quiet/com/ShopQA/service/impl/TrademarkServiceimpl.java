package quiet.com.ShopQA.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quiet.com.ShopQA.DTO.TrademarkDTO;
import quiet.com.ShopQA.Entity.TrademarkEntity;
import quiet.com.ShopQA.Repostory.TrademarkRepository;
import quiet.com.ShopQA.service.TrademarkService;

@Service
@Transactional
public class TrademarkServiceimpl implements TrademarkService {
	@Autowired
	private TrademarkRepository trademarkRepostory;

	@Override
	public void insert(TrademarkDTO trademarkDTO) {
		TrademarkEntity trademarkEntity = new TrademarkEntity();
		trademarkEntity.setName(trademarkDTO.getName());
		trademarkRepostory.save(trademarkEntity);
	}

	@Override
	public void update(TrademarkDTO trademarkDTO) {
		TrademarkEntity trademarkEntity = trademarkRepostory.getOne(trademarkDTO.getId());
		if (trademarkEntity != null) {
			trademarkEntity.setName(trademarkDTO.getName());
			trademarkRepostory.save(trademarkEntity);
		}

	}

	@Override
	public void delete(Long id) {
		TrademarkEntity trademarkEntity = trademarkRepostory.getOne(id);
		if (trademarkEntity != null) {
			trademarkRepostory.delete(trademarkEntity);
		}
	}

	@Override
	public TrademarkDTO get(Long id) {
		TrademarkEntity trademarkEntity = trademarkRepostory.getOne(id);

		return convert(trademarkEntity);
	}

	private TrademarkDTO convert(TrademarkEntity trademarkEntity) {
		TrademarkDTO trademarkDTO = new TrademarkDTO();
		trademarkDTO.setId(trademarkEntity.getId());
		trademarkDTO.setName(trademarkEntity.getName());
		return trademarkDTO;
	}

	@Override
	public List<TrademarkDTO> search() {
		List<TrademarkEntity> liTrademarkEntities = trademarkRepostory.findAll();
		List<TrademarkDTO> list = new ArrayList<TrademarkDTO>();
		for (TrademarkEntity trademark : liTrademarkEntities) {
			list.add(convert(trademark));
		}
		return list;
	}
}
