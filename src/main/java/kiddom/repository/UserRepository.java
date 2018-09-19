package kiddom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kiddom.model.UserEntity;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<UserEntity, String> {
	 UserEntity findByUsername(String username);

//	void saveUser(UserEntity user, ParentEntity parent);
}
