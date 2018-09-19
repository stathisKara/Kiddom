package kiddom.service;

import kiddom.model.ProgramEntity;
import kiddom.model.ReservationsEntity;
import kiddom.model.SingleEventEntity;
import kiddom.repository.ActivityRepository;
import kiddom.repository.ProgramRepository;
import kiddom.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eleni on 06-Jul-17.
 */

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
    @Qualifier("reservationRepository")
    @Autowired
    private ReservationRepository reservationRepository;

    @Qualifier("programRepository")
    @Autowired
    private ProgramRepository programRepository;

    @Qualifier("activityRepository")
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<ReservationsEntity> getReservationsByEventId(SingleEventEntity event) {
        List<ReservationsEntity> myReservations = new ArrayList<>();
        List<ReservationsEntity> allReservations = reservationRepository.findAll();
        int eventID = event.getId();
        System.out.println("-----------ME KALESATE");
        for (ReservationsEntity res : allReservations) {
            System.out.println(res);
            if (res.getReservation_id() == eventID)
                myReservations.add(res);
        }
        return myReservations;
    }
}
