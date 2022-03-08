package quiet.com.ShopQA.Repostory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import quiet.com.ShopQA.Entity.BillProductEntity;

public interface BillProductRepository extends JpaRepository<BillProductEntity, Long>{
	
	@Query("select bp from BillProductEntity bp join bp.productEntity p join bp.billEntity b where b.id =:id")
	List<BillProductEntity> searchbyBill(@Param("id") Long id);
}
