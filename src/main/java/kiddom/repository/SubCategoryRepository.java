package kiddom.repository;

import kiddom.model.SubcategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arianna on 4/6/2017.
 */
@Repository("subcategoryRepository")
public interface SubCategoryRepository extends JpaRepository<SubcategoriesEntity, String> {

}
