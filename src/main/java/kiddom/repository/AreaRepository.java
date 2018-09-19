package kiddom.repository;

import kiddom.model.AreasEntity;
import kiddom.model.SubcategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Stathis on 7/6/2017.
 */
@Repository("areaRepository")
public interface AreaRepository extends JpaRepository<AreasEntity, String>{

}
