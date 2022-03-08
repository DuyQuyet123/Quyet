package quiet.com.ShopQA.Repostory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import quiet.com.ShopQA.Entity.FavoriteProductEntity;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProductEntity, Long> {
	@Query("select f from FavoriteProductEntity f join f.userEntity u join f.productEntity p where u.id =:id")
	List<FavoriteProductEntity> search(@Param("id") Long id);
}
