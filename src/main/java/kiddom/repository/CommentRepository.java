package kiddom.repository;

import kiddom.model.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by Arianna on 4/7/2017.
 */
@Repository("commentRepository")
public interface CommentRepository extends PagingAndSortingRepository<CommentsEntity, Long> {

    /*@Query("select p from CommentsEntity p where p.event_id=?1" )
    public Set<CommentsEntity> findAllByEventId(Integer event_id);*/

}
