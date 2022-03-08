package quiet.com.ShopQA.Repostory;

import org.springframework.data.jpa.repository.JpaRepository;

import quiet.com.ShopQA.Entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
