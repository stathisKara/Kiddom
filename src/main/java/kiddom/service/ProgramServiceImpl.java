package kiddom.service;

import kiddom.model.*;
import kiddom.repository.ParentRepository;
import kiddom.repository.ProgramRepository;
import kiddom.repository.ProviderRepository;
import kiddom.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by Arianna on 4/7/2017.
 */
@Service("programService")
public class ProgramServiceImpl implements ProgramService {

    @Qualifier("programRepository")
    @Autowired
    private ProgramRepository programRepository;

    @Qualifier("reservationRepository")
    @Autowired
    private ReservationRepository reservationRepository;

    @Qualifier("parentRepository")
    @Autowired
    private ParentRepository parentRepository;

    @Qualifier("providerRepository")
    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public ProgramEntity getProgramById(Integer id)
    {
        return programRepository.findById(id);
    }


    @Override
    public void cancelReservation(ProviderEntity provider, ParentEntity parent, ReservationsEntity reservation, ProgramEntity program) {
        int moneyFromProv = program.getPrice();
        provider.setOwedPoints(provider.getOwedPoints() - moneyFromProv);
        providerRepository.save(provider);
        parent.setAvailPoints(parent.getAvailPoints() + moneyFromProv);
        Set<ReservationsEntity> res = parent.getReservations();
        ReservationsEntity toRemove = null;
        for (ReservationsEntity r : res) {
            if (r.getReservation_id() == reservation.getReservation_id()) {
                toRemove = r;
                break;
            }
        }
        if (toRemove != null) {
            int slots = reservation.getSlots();
            program.setAvailability(program.getAvailability() + slots);
            res.remove(toRemove);
        }
        parent.setReservations(res);
        parentRepository.saveAndFlush(parent);
        programRepository.save(program);
    }

    @Override
    public void makeReservation(ReservationsEntity reservation, ProviderEntity provider, ParentEntity parent, ProgramEntity program){
        reservationRepository.save(reservation);

        int price=program.getPrice()*reservation.getSlots();
        parent.setTotalPoints(parent.getTotalPoints()+price);//increase restrain points +price
        parent.setAvailPoints(parent.getAvailPoints()-price);
        parentRepository.saveAndFlush(parent);

        provider.setOwedPoints(provider.getOwedPoints()+price);
        providerRepository.save(provider);

        program.setAvailability(program.getAvailability()-reservation.getSlots());
        programRepository.save(program);


        //int parent_restr=parent.getRestrPoints();
        //int parent_avail=parentEntity.getAvailPoints();
        /* Changes on parent */
//        parentEntity.setAvailPoints(parent_avail-price); //decrease avail -price
//        Set<ProgramEntity> events =parentEntity.getEvents(); //get reservations
//        events.add(programEntity); //add extra
//        parentEntity.setEvents(events);  //set reservations
//        parentRepository.save(parentEntity);
//
//        /*Changes on Program */
//        int prog_avail=programEntity.getAvailability();
//        programEntity.setAvailability(prog_avail -num); //decrease availability
//        Set<ParentEntity> parents =programEntity.getParents();
//        parents.add(parentEntity);
//        programEntity.setParents(parents);
//        programRepository.save(programEntity);

       /* ReservationsEntity reservationsEntity=
        reservationRepository.save(reservationsEntity);*/
        /*Changes on Provider */
//        int prov_owned=providerEntity.getOwedPoints();
//        providerEntity.setOwedPoints(prov_owned+price);//increase providers owned_points
//        providerRepository.save(providerEntity);
    }

}
