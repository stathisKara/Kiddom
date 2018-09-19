package kiddom.repository;

import kiddom.model.CookiesEntity;
import kiddom.model.ParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arianna on 3/6/2017.
 */
@Repository("cookieRepository")
public  interface CookieRepository extends JpaRepository<CookiesEntity, Long> {
    // ParentEntity findByUsername(String username);
    // void save(String user, ParentEntity parent)
}
