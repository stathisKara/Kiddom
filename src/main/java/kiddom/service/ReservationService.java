package kiddom.service;

import org.springframework.stereotype.Service;
import kiddom.model.ParentEntity;
import kiddom.model.ProgramEntity;
import kiddom.model.ProviderEntity;
import kiddom.model.ReservationsEntity;

/**
 * Created by eleni on 06-Jul-17.
 */

@Service("reservationService")
public interface ReservationService{
    public ReservationsEntity getReservationsEntityById(Integer id);
}
