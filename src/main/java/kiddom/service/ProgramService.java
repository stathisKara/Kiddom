package kiddom.service;

import kiddom.model.ParentEntity;
import kiddom.model.ProgramEntity;
import kiddom.model.ProviderEntity;
import kiddom.model.ReservationsEntity;
import org.springframework.stereotype.Service;

/**
 * Created by Arianna on 4/7/2017.
 */
@Service("programService")
public interface ProgramService {
    public ProgramEntity getProgramById(Integer id);
    public void makeReservation(ReservationsEntity reservation, ProviderEntity provider, ParentEntity parent, ProgramEntity program);
    public void cancelReservation(ProviderEntity provider, ParentEntity parent, ReservationsEntity reservation, ProgramEntity program);
}
