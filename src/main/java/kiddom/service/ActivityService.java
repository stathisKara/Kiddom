package kiddom.service;

import kiddom.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kiddom.model.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * Created by eleni on 06-Jul-17.
 */

@Service("activityService")
public interface ActivityService {
    public List<ReservationsEntity> getReservationsByEventId(SingleEventEntity event);
}
