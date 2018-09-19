package kiddom.repository;

import kiddom.model.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arianna on 4/6/2017.
 */
@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<CategoriesEntity, String> {
    CategoriesEntity findByName(String name);
}
