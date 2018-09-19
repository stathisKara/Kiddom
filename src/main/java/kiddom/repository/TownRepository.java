package kiddom.repository;

import kiddom.model.CategoriesEntity;
import kiddom.model.TownsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Stathis on 7/6/2017.
 */
@Repository("townRepository")
public interface TownRepository extends JpaRepository<TownsEntity, String> {
    TownsEntity findByName(String name);
}
