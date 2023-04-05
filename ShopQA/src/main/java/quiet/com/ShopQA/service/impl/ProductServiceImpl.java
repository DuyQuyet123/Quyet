package quiet.com.ShopQA.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import quiet.com.ShopQA.DTO.CategoryDTO;
import quiet.com.ShopQA.DTO.ProductDTO;
import quiet.com.ShopQA.DTO.SizeDTO;
import quiet.com.ShopQA.DTO.TrademarkDTO;
import quiet.com.ShopQA.Entity.CategoryEntity;
import quiet.com.ShopQA.Entity.ProductEntity;
import quiet.com.ShopQA.Entity.SizeEntity;
import quiet.com.ShopQA.Entity.TrademarkEntity;
import quiet.com.ShopQA.Repostory.ProductRepository;
import quiet.com.ShopQA.service.ProductService;
import quiet.com.ShopQA.service.QRCodeService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	QRCodeService qrCodeService;


	@Override
	public void insert(ProductDTO productDTO) {
		String productUUID = UUID.randomUUID().toString();
		ProductEntity productEntity = new ProductEntity();
		productEntity.setName(productDTO.getName());
		productEntity.setPrice(productDTO.getPrice());
		productEntity.setDescription(productDTO.getDescription());
		productEntity.setQuantity(productDTO.getQuantity());
		productEntity.setImage(productDTO.getImage());
		productEntity.setProductUUID(productUUID);
		productEntity.setQrCode(qrCodeService.generateQRCodeForMerchantInfo(productUUID));

		SizeEntity sizeEntity = new SizeEntity();
		sizeEntity.setId(productDTO.getSizeDTO().getId());
		sizeEntity.setName(productDTO.getSizeDTO().getName());
		productEntity.setSizeEntity(sizeEntity);

		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setId(productDTO.getCategoryDTO().getId());
		categoryEntity.setName(productDTO.getCategoryDTO().getName());
		productEntity.setCategoryEntity(categoryEntity);

		TrademarkEntity trademarkEntity = new TrademarkEntity();
		trademarkEntity.setId(productDTO.getTrademarkDTO().getId());
		trademarkEntity.setName(productDTO.getTrademarkDTO().getName());
		productEntity.setTrademarkEntity(trademarkEntity);
		productRepository.save(productEntity);
	}

	@Override
	public void update(ProductDTO productDTO) {
		ProductEntity productEntity = productRepository.getOne(productDTO.getId());
		if (productEntity != null) {
			productEntity.setName(productDTO.getName());
			productEntity.setPrice(productDTO.getPrice());
			productEntity.setDescription(productDTO.getDescription());
			productEntity.setQuantity(productDTO.getQuantity());
			productEntity.setImage(productDTO.getImage());

			SizeEntity sizeEntity = new SizeEntity();
			sizeEntity.setId(productDTO.getSizeDTO().getId());
			sizeEntity.setName(productDTO.getSizeDTO().getName());
			productEntity.setSizeEntity(sizeEntity);

			CategoryEntity categoryEntity = new CategoryEntity();
			categoryEntity.setId(productDTO.getCategoryDTO().getId());
			categoryEntity.setName(productDTO.getCategoryDTO().getName());
			productEntity.setCategoryEntity(categoryEntity);

			TrademarkEntity trademarkEntity = new TrademarkEntity();
			trademarkEntity.setId(productDTO.getTrademarkDTO().getId());
			trademarkEntity.setName(productDTO.getTrademarkDTO().getName());
			productEntity.setTrademarkEntity(trademarkEntity);
			productRepository.save(productEntity);
		}
	}

	@Override
	public void delete(Long id) {
		ProductEntity productEntity = productRepository.getOne(id);
		if (productEntity != null) {
			productRepository.delete(productEntity);
		}

	}

	@Override
	public ProductDTO get(Long id) {
		ProductEntity productEntity = productRepository.getOne(id);

		return convert(productEntity);
	}

	@Override
	public List<ProductDTO> search() {
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		List<ProductEntity> listProductEntities = productRepository.findAll();
		for (ProductEntity productEntity : listProductEntities) {
			productDTOs.add(convert(productEntity));

		}
		return productDTOs;
	}

	public ProductDTO convert(ProductEntity productEntity) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(productEntity.getId());
		productDTO.setName(productEntity.getName());
		productDTO.setPrice(productEntity.getPrice());
		productDTO.setDescription(productEntity.getDescription());
		productDTO.setImage(productEntity.getImage());
		productDTO.setQuantity(productEntity.getQuantity());

		SizeDTO sizeDTO = new SizeDTO();
		sizeDTO.setId(productEntity.getSizeEntity().getId());
		sizeDTO.setName(productEntity.getSizeEntity().getName());
		productDTO.setSizeDTO(sizeDTO);

		TrademarkDTO trademarkDTO = new TrademarkDTO();
		trademarkDTO.setId(productEntity.getTrademarkEntity().getId());
		trademarkDTO.setName(productEntity.getTrademarkEntity().getName());
		productDTO.setTrademarkDTO(trademarkDTO);

		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(productEntity.getCategoryEntity().getId());
		categoryDTO.setName(productEntity.getCategoryEntity().getName());
		productDTO.setCategoryDTO(categoryDTO);
		return productDTO;
	}

	@Override
	public List<ProductDTO> search1(Pageable pageable) {
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		List<ProductEntity> listProductEntities = productRepository.search1(pageable);
		for (ProductEntity productEntity : listProductEntities) {
			productDTOs.add(convert(productEntity));
		}

		return productDTOs;
	}

	@Override
	public List<ProductDTO> search2(int pageable) {
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		List<ProductEntity> listProductEntities = productRepository.search2(PageRequest.of(pageable, 8));
		for (ProductEntity productEntity : listProductEntities) {
			productDTOs.add(convert(productEntity));
		}

		return productDTOs;
	}

	@Override
	public List<ProductDTO> searchP() {
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		List<ProductEntity> listProductEntities = productRepository.searchP();
		for (ProductEntity productEntity : listProductEntities) {
			productDTOs.add(convert(productEntity));
		}

		return productDTOs;
	}

	@Override
	public List<ProductDTO> searchByCategory(String name, Pageable pageable) {
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		List<ProductEntity> listProductEntities = productRepository.searchByCategory(name, pageable);
		for (ProductEntity productEntity : listProductEntities) {
			productDTOs.add(convert(productEntity));
		}

		return productDTOs;
	}

	@Override
	public List<ProductDTO> searchbyProduct(String name, int pageable) {
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		List<ProductEntity> listProductEntities = productRepository.searchByProduct(name + "%",
				PageRequest.of(pageable, 8));
		for (ProductEntity productEntity : listProductEntities) {
			productDTOs.add(convert(productEntity));
		}

		return productDTOs;
	}
}