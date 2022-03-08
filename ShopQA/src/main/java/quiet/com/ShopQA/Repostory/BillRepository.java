package quiet.com.ShopQA.Repostory;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import quiet.com.ShopQA.Entity.BillEntity;



public interface BillRepository extends JpaRepository<BillEntity, Long> {
	@Query("select b from BillEntity b join b.buyer u where u.id =:id")
	List<BillEntity> search(@Param("id") Long id);
	
	//@Query("update BillEntity b set b.priceTotal =:price where b.id =:id")
	//BillEntity update(@Param("price")Long priceTotal,@Param("id")Long id);
	
	@Query("select b from BillEntity b order by b.id DESC")
	List<BillEntity> search2(Pageable pageable);
}
