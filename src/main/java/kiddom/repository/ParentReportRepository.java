package kiddom.repository;

import kiddom.model.ParentEntity;
import kiddom.model.ParentReportsEntity;
import kiddom.model.ParentReportsEntity;
import kiddom.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stathis on 7/5/2017.
 *
 */
@Repository("parentReportsRepo")
public interface ParentReportRepository extends JpaRepository<ParentReportsEntity, Integer> {
    public List<ParentReportsEntity> getParentReportsEntityByParent(ParentEntity parent);

}
