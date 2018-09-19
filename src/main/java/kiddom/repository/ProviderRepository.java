package kiddom.repository;

import kiddom.model.ProviderEntity;
import kiddom.model.ProviderPK;
import kiddom.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arianna on 2/6/2017.
 */
@Repository("providerRepository")
public interface ProviderRepository extends PagingAndSortingRepository<ProviderEntity, ProviderPK> {
    @Query("select p from ProviderEntity p where p.approved=?1 order by p.name")
    Page<ProviderEntity> findAllByApproved(Integer approved, Pageable pageable);
   // ProviderEntity findByUsername(String username);
}
