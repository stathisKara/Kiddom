package kiddom.repository;

import kiddom.model.ProgramEntity;
import kiddom.model.ReservationsEntity;
import kiddom.model.SingleEventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Arianna on 3/6/2017.
 */
@Repository("activityRepository")
public interface ActivityRepository extends PagingAndSortingRepository<SingleEventEntity, Integer> {
    Page<SingleEventEntity> findByIdIn(List<Integer> list ,Pageable pageable);
    SingleEventEntity findSingleEventById(Integer Id);
    //Page<SingleEventEntity> findAllByApproved(Integer approved, Pageable pageable);
  //  Page<SingleEventEntity> findAllEvents(Pageable pageable);
    Page<SingleEventEntity> findByTownOrAreaAndDate(String Town,String Area,String Date,Pageable pageable);
    Page<SingleEventEntity> findByTownOrAreaAndAvailability(String Town,String Area,Integer max,Pageable pageable);
    Page<SingleEventEntity> findByDateAndAvailability(String Date,Integer max,Pageable pageable);
    Page<SingleEventEntity> findAllByDateContaining(String Date,Pageable pageable);
    Page<SingleEventEntity> findByTownOrArea(String Town, String Area, Pageable pageable);
    Page<SingleEventEntity> findByAvailability(Integer max,Pageable pageable);
    Page<SingleEventEntity> findByCategoryAndSub1OrSub2OrSub3(String cat,String subcat,String subcat2,String subcat3,Pageable pageable);
    Page<SingleEventEntity> findByCategory(String cat,Pageable pageable);
    Page<SingleEventEntity> findBySub1OrSub2OrSub3(String subcat,String subcat2,String subcat3,Pageable pageable);
    Page<SingleEventEntity> findByTownOrAreaAndDateAndAvailability(String Town,String Area,String Date,Integer max,Pageable pageable);
//    Page<SingleEventEntity> findAllByDateContainingOrAddressContainingOrAreaContainingOrCategoryContainingOrDescriptionContainingOrComment_parentContainingOrNameContainingOrProviderContaining(String text,Pageable pageable);
}
