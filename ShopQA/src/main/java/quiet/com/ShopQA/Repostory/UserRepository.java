package quiet.com.ShopQA.Repostory;

import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import quiet.com.ShopQA.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	@Query("select u from UserEntity u where u.username = :username")
	UserEntity search(@Param("username") String username);
	
	@Query("select u from UserEntity u where u.name like :name")//like ?query.setParameter("name", "%" + findName + "%");
	List<UserEntity> search2(@Param("name") String name, Pageable pageable);//pageable..data/phân trang
	
	@Query("select u from UserEntity u")//like ?
	List<UserEntity> search1(Pageable pageable);
	
	@Query("select u from UserEntity u order by u.id DESC")
	List<UserEntity> search3(Pageable pageable);

}
