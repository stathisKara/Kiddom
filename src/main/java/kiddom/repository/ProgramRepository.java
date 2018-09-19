package kiddom.repository;

/**
 * Created by eleni on 01-Jul-17.
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kiddom.model.ProgramEntity;

@Repository("programRepository")
public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {
    public ProgramEntity findById(Integer id);
}
