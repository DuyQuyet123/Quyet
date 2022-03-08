package quiet.com.ShopQA.Repostory;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import quiet.com.ShopQA.Entity.ProductEntity;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	//select * from product order by id DESC
	
	@Query("select p from ProductEntity p order by p.id DESC")
	List<ProductEntity> search1(Pageable pageable);
	
	@Query("select p from ProductEntity p")
	List<ProductEntity> search2(Pageable pageable);
	
	@Query("select p from ProductEntity p where p.quantity <5")
	List<ProductEntity> searchP();
	
	@Query("select p from ProductEntity p join p.categoryEntity c where c.name like :name")
	List<ProductEntity> searchByCategory(@Param("name") String name,Pageable pageable);
	
	@Query("select p from ProductEntity p where p.name like :name")
	List<ProductEntity> searchByProduct(@Param("name") String name,Pageable pageable);
}
