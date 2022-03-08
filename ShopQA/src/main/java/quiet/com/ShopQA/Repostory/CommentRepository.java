package quiet.com.ShopQA.Repostory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import quiet.com.ShopQA.Entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	@Query("SELECT c FROM CommentEntity c join c.productEntity p join c.userEntity u where p.id =:id ")
	List<CommentEntity> searchByProduct(@Param("id") Long id);
	
//	@Query("SELECT c FROM CommentEntity c where c.id =:id ")
//	CommentEntity searchComment(@Param("id") Long id);
	
	@Query("SELECT c FROM CommentEntity c join c.productEntity p join c.userEntity u where u.id =:id ")
	List<CommentEntity> searchCommentByUser(@Param("id") Long id);
}
