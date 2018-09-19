package kiddom.service;

import kiddom.model.*;
import kiddom.repository.*;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OneToOne;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Arianna on 1/7/2017.
 */
@Service("eventService")
public class EventServiceImpl implements EventService {

    @Qualifier("programRepository")
    @Autowired
    private ProgramRepository programRepository;

    @Qualifier("commentRepository")
    @Autowired
    private CommentRepository commentRepository;

    @Qualifier("activityRepository")
    @Autowired
    private ActivityRepository activityRepository;

    @Qualifier("providerRepository")
    @Autowired
    private ProviderRepository providerRepository;

    @Qualifier("parentRepository")
    @Autowired
    private ParentRepository parentRepository;

    @Override
    public SingleEventEntity findSingleEventById(int eventID) {
        return  activityRepository.findSingleEventById(eventID);
    }

    @Override
    public SingleEventEntity findSingleEvent(SingleEventEntity singleEventEntity) {
        return activityRepository.findSingleEventById(1/*singleEventEntity.getId()*/);
    }

    @Override
    @Transactional
    public Iterable<SingleEventEntity> findAllByID(Iterable<Integer> list,Pageable pageable){
        return activityRepository.findAll(list);
    }


    @Override
    public void addComment(ParentEntity parentEntity, CommentsEntity commentsEntity, SingleEventEntity singleEventEntity)
    {
         /*Save the comment*/
        commentsEntity.setParent_username(parentEntity);
        commentsEntity.setEvent_id(singleEventEntity);
        commentRepository.save(commentsEntity);

        /*
        /*Update Comment of Parents at event
       Set<ParentEntity> parents=singleEventEntity.getComment_parent();
       parents.add(parentEntity);
       singleEventEntity.setComment_parent(parents);
       activityRepository.save(singleEventEntity);

       /*Update Comment  of events to Parents
       Set<SingleEventEntity> events =parentEntity.getComment_event();
       events.add(singleEventEntity);
       parentEntity.setComment_event(events);
       parentRepository.save(parentEntity);*/
        System.out.println("Paw n kanw save t comment "+commentsEntity.getComment());
    }

    @Override
    public Set<ProgramEntity> findProgram(int eventID)
    {
        return findSingleEventById(eventID).getProgram();
    }

    @Override
    public void saveActivity(UserEntity user, ProviderEntity provider, SingleEventEntity event, HashSet<ProgramEntity> program,String[] photos)
    {
        event.setProviders(provider);
        System.out.println("Event id is " + event.getId());
        System.out.println("Event by " + provider.getPk().getUser().getUsername());
        activityRepository.save(event);
        for (ProgramEntity daily_program : program) {
            daily_program.setEvent(event);
            programRepository.save(daily_program);
        }
        String photo = "";
        for (String file : photos) {
            photo = photo + file +";";
        }
        String postcode=Integer.toString(event.getPostcode());
        try
        {
            String pos[] =LatLng.getLatLongPositions(postcode);
            System.out.print(pos[0]+ pos[1]);
            event.setLatitude(Float.parseFloat(pos[0]));
            event.setLongitude(Float.parseFloat(pos[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        event.setPhotos(photo);
        event.setProgram(program);
        activityRepository.save(event);
        System.out.println("Done.");

        Elastic elastic = new Elastic();
        RestClient client = elastic.getRestClient();
        elastic.indexer(client);
        elastic.create(client, event.getId().toString(), event.getName(), event.getDate() ,event.getDescription(), event.getCategory(), event.getSub1(),
                event.getSub2(), event.getSub3(), event.getTown(), event.getArea(), event.getAddress(), event.getNumber(),
                Integer.toString(event.getPostcode()),event.getLatitude(),event.getLongitude());
    }

    @Override
    public void updateSingleEvent(ProviderEntity provider, SingleEventEntity event, SingleEventEntity eventEdit) {
        if (!event.getName().replaceAll(" ", "").equals("")) {
            eventEdit.setName(event.getName());
        }
        if (!event.getDescription().replaceAll(" ", "").equals("")) {
            eventEdit.setDescription(event.getDescription());
        }
        Elastic elastic = new Elastic();
        RestClient client = elastic.getRestClient();
        elastic.indexer(client);
        elastic.create(client, eventEdit.getId().toString(), eventEdit.getName(), eventEdit.getDate() ,eventEdit.getDescription(), eventEdit.getCategory(), eventEdit.getSub1(),
                eventEdit.getSub2(), eventEdit.getSub3(), eventEdit.getTown(), eventEdit.getArea(), eventEdit.getAddress(), eventEdit.getNumber(),
                Integer.toString(eventEdit.getPostcode()),eventEdit.getLatitude(),eventEdit.getLongitude());
        activityRepository.save(eventEdit);
    }

    @Override
    public void cancelSingleEvent(ProviderEntity provider, SingleEventEntity eventEdit) {
        eventEdit.setCanceled(1);
        Set<ProgramEntity> program = eventEdit.getProgram(); //new HashSet<ProgramEntity>(eventEdit.getProgram());
        for (ProgramEntity p : program) {
            System.out.println("program event id " + p.getEvent().getId());
            p.setCanceled(1);
            programRepository.save(p);
        }
        //HashSet<ProgramEntity> newProgram = new HashSet<>();
       /* for (ProgramEntity p : program) {
            p.setEvent(eventEdit);
            newProgram.add(p);
        }*/
        //eventEdit.setProgram(newProgram);
        System.out.println("Event id is " + eventEdit.getId());
       // activityRepository.save(eventEdit);
    }

    @Override
    public void cancelSingleEvent(ProviderEntity provider, SingleEventEntity eventEdit, List<ReservationsEntity> eventReservations) {
        eventEdit.setCanceled(1);
        Set<ProgramEntity> program = eventEdit.getProgram();
        for (ProgramEntity p : program) {
            System.out.println("program event id " + p.getEvent().getId());
            p.setCanceled(1);
            programRepository.save(p);
        }
        for (ReservationsEntity r : eventReservations) {
            ParentEntity parent = r.getParent();
            parent.setAvailPoints(parent.getAvailPoints() + eventEdit.getPrice());
            parentRepository.saveAndFlush(parent);
        }
        int providerPoints = (eventReservations.size())*eventEdit.getPrice();
        provider.setOwedPoints(provider.getOwedPoints() - providerPoints);
        providerRepository.save(provider);
    }

    @Transactional
    @Override
    public Page<SingleEventEntity> findAllPageable(Pageable pageable)
    {
        return activityRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Page<SingleEventEntity> findByTownOrAreaAndDate(String Town,String Area,String Date,Pageable pageable)
    {
        return activityRepository.findByTownOrAreaAndDate(Town,Area,Date,pageable);
    }


    /*@Transactional
    @Override
    public Page<SingleEventEntity> freetext(String text,Pageable pageable)
    {
        return activityRepository.findAllByDateContainingOrAddressContainingOrAreaContainingOrCategoryContainingOrDescriptionContainingOrComment_parentContainingOrNameContainingOrProviderContaining(text,pageable);
    }*/


    @Transactional
    @Override
    public Page<SingleEventEntity> findByDate(String Date,Pageable pageable)
    {
        return activityRepository.findAllByDateContaining(Date,pageable);
    }
    @Transactional
    @Override
    public Page<SingleEventEntity> findByTownOrArea(String Town, String Area, Pageable pageable)
    {
        return activityRepository.findByTownOrArea(Town,Area,pageable);
    }
    @Transactional
    @Override
    public Page<SingleEventEntity> findByAvailability(Integer max,Pageable pageable){
        return activityRepository.findByAvailability(max,pageable);
    }

    @Transactional
    @Override
    public Page<SingleEventEntity> findByTownOrAreaAndDateAndAvailability(String Town,String Area,String Date,Integer max,Pageable pageable){
        return activityRepository.findByTownOrAreaAndDateAndAvailability(Town,Area,Date,max,pageable);
    }

    @Transactional
    @Override
    public Page<SingleEventEntity> findByTownOrAreaAndAvailability(String Town,String Area,Integer max,Pageable pageable){
        return activityRepository.findByTownOrAreaAndAvailability(Town,Area,max,pageable);
    }

    @Transactional
    @Override
    public Page<SingleEventEntity> findByDateAndAvailability(String Date,Integer max,Pageable pageable){
        return activityRepository.findByDateAndAvailability(Date,max,pageable);
    }

    @Transactional
    @Override
    public Page<SingleEventEntity> findByCategoryAndSub1OrSub2OrSub3(String cat,String subcat,String subcat2,String subcat3,Pageable pageable){
        return activityRepository.findByCategoryAndSub1OrSub2OrSub3(cat,subcat,subcat2,subcat3,pageable);
    }
    @Transactional
    @Override
    public Page<SingleEventEntity> findByCategory(String cat,Pageable pageable){
        return activityRepository.findByCategory(cat,pageable);
    }
    @Transactional
    @Override
    public Page<SingleEventEntity> findBySub1OrSub2OrSub3(String subcat,String subcat2,String subcat3,Pageable pageable){
        return activityRepository.findBySub1OrSub2OrSub3(subcat,subcat2,subcat3,pageable);
    }

    @Transactional
    @Override
    public Page<SingleEventEntity> findAllByID(List<Integer> list,Pageable pageable)
    {
       return activityRepository.findByIdIn(list,pageable);
    }

}
