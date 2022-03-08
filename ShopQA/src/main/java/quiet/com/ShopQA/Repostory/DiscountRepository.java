package quiet.com.ShopQA.Repostory;

import org.springframework.data.jpa.repository.JpaRepository;

import quiet.com.ShopQA.Entity.DiscountEntity;

public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {

}
