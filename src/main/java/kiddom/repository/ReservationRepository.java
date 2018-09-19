package kiddom.repository;

import kiddom.model.ReservationsEntity;
import kiddom.model.SingleEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Arianna on 4/7/2017.
 */

@Repository("reservationRepository")
public interface ReservationRepository  extends JpaRepository<ReservationsEntity,Integer>{

}
