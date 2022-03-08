package quiet.com.ShopQA.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quiet.com.ShopQA.DTO.CategoryDTO;
import quiet.com.ShopQA.Entity.CategoryEntity;
import quiet.com.ShopQA.Repostory.CategoryRepository;
import quiet.com.ShopQA.Service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void insert(CategoryDTO categoryDTO) {
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setName(categoryDTO.getName());
		categoryRepository.save(categoryEntity);

	}

	@Override
	public void update(CategoryDTO categoryDTO) {
		CategoryEntity categoryEntity = categoryRepository.getOne(categoryDTO.getId());
		if (categoryEntity != null) {
			categoryEntity.setName(categoryDTO.getName());
			categoryRepository.save(categoryEntity);
		}
	}

	@Override
	public void delete(Long id) {
		CategoryEntity categoryEntity = categoryRepository.getOne(id);
		if (categoryEntity != null) {
			categoryRepository.delete(categoryEntity);
		}

	}

	@Override
	public CategoryDTO get(Long id) {
		CategoryEntity categoryEntity = categoryRepository.getOne(id);

		return convert(categoryEntity);
	}

	public CategoryDTO convert(CategoryEntity categoryEntity) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(categoryEntity.getId());
		categoryDTO.setName(categoryEntity.getName());
		return categoryDTO;
	}

	@Override
	public List<CategoryDTO> search() {
		List<CategoryEntity> categoryEntitys = categoryRepository.findAll();
		List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();
		for (CategoryEntity categoryEntity : categoryEntitys) {
			categoryDTOs.add(convert(categoryEntity));

		}
		return categoryDTOs;
	}
}
