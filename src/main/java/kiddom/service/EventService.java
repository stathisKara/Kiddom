package kiddom.service;

import kiddom.controller.Views;
import kiddom.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Arianna on 1/7/2017.
 */
@Service("eventService")
public interface EventService {
   // public Set<CommentsEntity> findAllCommentsByEvent(Integer eventID_);
    public Page<SingleEventEntity> findAllByID(List<Integer> list,Pageable pageable);
   public Iterable<SingleEventEntity> findAllByID(Iterable<Integer> list,Pageable pageable);
   public void addComment(ParentEntity parentEntity,CommentsEntity commentsEntity,SingleEventEntity singleEventEntity);
    public Set<ProgramEntity> findProgram(int eventID);
    public SingleEventEntity findSingleEvent(SingleEventEntity singleEventEntity);
    public SingleEventEntity findSingleEventById(int eventID);
   // public List<SingleEventEntity> findALLEvents(List<Integer> ids);
    //public Page<SingleEventEntity> getAllEvents(Pageable pageable);
    public void saveActivity(UserEntity user, ProviderEntity provider, SingleEventEntity event, HashSet<ProgramEntity> program,String[] photos);
    public void updateSingleEvent(ProviderEntity provider, SingleEventEntity event, SingleEventEntity eventEdit);

    public void cancelSingleEvent(ProviderEntity provider, SingleEventEntity eventEdit);
    public void cancelSingleEvent(ProviderEntity provider, SingleEventEntity eventEdit, List<ReservationsEntity> eventReservations);

    public Page<SingleEventEntity> findAllPageable(Pageable pageable);
    public Page<SingleEventEntity> findByTownOrAreaAndDate(String Town,String Area,String Date,Pageable pageable);
    public Page<SingleEventEntity> findByDate(String Date,Pageable pageable);
    public Page<SingleEventEntity> findByTownOrArea(String Town, String Area, Pageable pageable);
    public Page<SingleEventEntity> findByAvailability(Integer max,Pageable pageable);
    public Page<SingleEventEntity> findByTownOrAreaAndDateAndAvailability(String Town,String Area,String Date,Integer max,Pageable pageable);
    public Page<SingleEventEntity> findByTownOrAreaAndAvailability(String Town,String Area,Integer max,Pageable pageable);
    public Page<SingleEventEntity> findByDateAndAvailability(String Date,Integer max,Pageable pageable);
 public Page<SingleEventEntity> findByCategoryAndSub1OrSub2OrSub3(String cat,String subcat,String subcat2,String subcat3,Pageable pageable);
 public Page<SingleEventEntity> findByCategory(String cat,Pageable pageable);
 public Page<SingleEventEntity> findBySub1OrSub2OrSub3(String subcat,String subcat2,String subcat3,Pageable pageable);
//  public Page<SingleEventEntity> freetext(String text,Pageable pageable);
}
