package quiet.com.ShopQA.Repostory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import quiet.com.ShopQA.Entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
	
	@Query("select r from ReviewEntity r join r.userEntity u join r.productEntity p where p.id =:id")
	List<ReviewEntity> search(@Param("id") Long id);
}
