package quiet.com.ShopQA.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quiet.com.ShopQA.DTO.BillProductDTO;
import quiet.com.ShopQA.DTO.ProductDTO;
import quiet.com.ShopQA.Entity.BillEntity;
import quiet.com.ShopQA.Entity.BillProductEntity;
import quiet.com.ShopQA.Entity.ProductEntity;
import quiet.com.ShopQA.Repostory.BillProductRepository;
import quiet.com.ShopQA.Service.BillProductService;

@Service
@Transactional
public class BillProductServiceImpl implements BillProductService {
	@Autowired
	private BillProductRepository billProductRepository;

	@Override
	public void insert(BillProductDTO billProductDTO) {
		BillProductEntity billProductEntity = new BillProductEntity();
		billProductEntity.setQuantity(billProductDTO.getQuantity());
		billProductEntity.setUnitPrice(billProductDTO.getUnitPrice());

		BillEntity billEntity = new BillEntity();
		billEntity.setId(billProductDTO.getBillDTO().getId());
		billProductEntity.setBillEntity(billEntity);

		ProductEntity productEntity = new ProductEntity();
		productEntity.setId(billProductDTO.getProductDTO().getId());
		billProductEntity.setProductEntity(productEntity);

		billProductRepository.save(billProductEntity);
	}

	@Override
	public void update(BillProductDTO billProductDTO) {
		BillProductEntity billProductEntity = billProductRepository.getOne(billProductDTO.getId());
		if (billProductEntity != null) {
			billProductEntity.setQuantity(billProductDTO.getQuantity());
			billProductEntity.setUnitPrice(billProductDTO.getUnitPrice());

			BillEntity billEntity = new BillEntity();
			billEntity.setId(billProductDTO.getBillDTO().getId());
			billProductEntity.setBillEntity(billEntity);

			ProductEntity productEntity = new ProductEntity();
			productEntity.setId(billProductDTO.getProductDTO().getId());
			billProductEntity.setProductEntity(productEntity);

			billProductRepository.save(billProductEntity);
		}

	}

	@Override
	public void delete(Long id) {
		BillProductEntity billProductEntity = billProductRepository.getOne(id);
		if (billProductEntity != null) {
			billProductRepository.delete(billProductEntity);
		}

	}

	@Override
	public BillProductDTO get(Long id) {
		BillProductEntity billProductEntity = billProductRepository.getOne(id);
		return convertDTO(billProductEntity);
	}

	@Override
	public List<BillProductDTO> search() {
		List<BillProductDTO> billProductDTOs = new ArrayList<BillProductDTO>();
		List<BillProductEntity> billProductEntities = billProductRepository.findAll();
		for (BillProductEntity billProductEntity : billProductEntities) {
			billProductDTOs.add(convertDTO(billProductEntity));
		}
		return billProductDTOs;
	}

	private BillProductDTO convertDTO(BillProductEntity billProductEntity) {
		BillProductDTO billProductDTO = new BillProductDTO();
		billProductDTO.setId(billProductEntity.getId());
		billProductDTO.setQuantity(billProductEntity.getQuantity());
		billProductDTO.setUnitPrice(billProductEntity.getUnitPrice());

		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(billProductEntity.getProductEntity().getId());
		productDTO.setName(billProductEntity.getProductEntity().getName());
		productDTO.setImage(billProductEntity.getProductEntity().getImage());
		productDTO.setPrice(billProductEntity.getProductEntity().getPrice());
		billProductDTO.setProductDTO(productDTO);

		return billProductDTO;
	}

	@Override
	public List<BillProductDTO> searchbyBill(Long id) {
		List<BillProductDTO> billProductDTOs = new ArrayList<BillProductDTO>();
		List<BillProductEntity> billProductEntities = billProductRepository.searchbyBill(id);
		for (BillProductEntity billProductEntity : billProductEntities) {
			billProductDTOs.add(convertDTO(billProductEntity));
		}
		return billProductDTOs;
	}
}