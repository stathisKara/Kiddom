package kiddom.service;

import kiddom.model.*;
import kiddom.repository.ParentRepository;
import kiddom.repository.ProgramRepository;
import kiddom.repository.ProviderRepository;
import kiddom.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by eleni on 06-Jul-17.
 */

@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {
    @Qualifier("reservationRepository")
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public ReservationsEntity getReservationsEntityById(Integer id) {
        return reservationRepository.findOne(id);
    }
}
