package kiddom.model;

/**
 * Created by eleni on 02-Jun-17.
**/

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reservations", schema = "mydb")
public class ReservationsEntity implements Serializable {

    /*----------------------------Fields----------------------------*/
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "reservation_id", nullable = false)
    private Integer reservation_id;

    @Column(name="slots")
    private Integer slots;

    @ManyToOne
    @JoinColumn(name="timeslot_id")
    private ProgramEntity timeslot_id;

    public ProgramEntity getTimeslot_id() {
        return timeslot_id;
    }

    public void setTimeslot_id(ProgramEntity timeslot_id) {
        this.timeslot_id = timeslot_id;
    }

    @ManyToOne
    @JoinColumn(name="parent")
    private ParentEntity parent;

    public ParentEntity getParent() {
        return parent;
    }

    public void setParent(ParentEntity parent) {
        this.parent = parent;
    }

    /*--------------Getters - Setters for table fields--------------*/
    public Integer getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Integer reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Integer getSlots() {
        return slots;
    }

    public void setSlots(Integer slots) {
        this.slots = slots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationsEntity)) return false;

        ReservationsEntity that = (ReservationsEntity) o;

        if (getReservation_id() != null ? !getReservation_id().equals(that.getReservation_id()) : that.getReservation_id() != null)
            return false;
        if (getSlots() != null ? !getSlots().equals(that.getSlots()) : that.getSlots() != null) return false;
        if (getTimeslot_id() != null ? !getTimeslot_id().equals(that.getTimeslot_id()) : that.getTimeslot_id() != null)
            return false;
        return getParent().equals(that.getParent());
    }

    @Override
    public int hashCode() {
        int result = getReservation_id() != null ? getReservation_id().hashCode() : 0;
        result = 31 * result + (getSlots() != null ? getSlots().hashCode() : 0);
        result = 31 * result + (getTimeslot_id() != null ? getTimeslot_id().hashCode() : 0);
        result = 31 * result + getParent().hashCode();
        return result;
    }
}
