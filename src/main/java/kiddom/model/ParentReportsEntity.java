package kiddom.model;

/**
 * Created by eleni on 02-Jun-17.
**/

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="parent_reports")
public class ParentReportsEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "report_id", nullable = false)
    private int report_id;
    @Column(name="date", nullable=false)
    private String date;
    @Column(name="boughtPoints", nullable = false)
    private int boughtPoints;
    @Column(name="cost", nullable = false)
    private int cost;

    @ManyToOne
    @JoinColumn(name="parent")
    private ParentEntity parent;

    public ParentReportsEntity(){}

    public ParentReportsEntity(String date, Integer total, Integer points, ParentEntity parent){
        this.date=date;
        this.cost=total;
        this.boughtPoints=points;
        this.parent = parent;
    }


    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBoughtPoints() {
        return boughtPoints;
    }

    public void setBoughtPoints(int boughtPoints) {
        this.boughtPoints = boughtPoints;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ParentEntity getParent_username() {
        return parent;
    }

    public void setParent_username(ParentEntity parent_username) {
        this.parent = parent_username;
    }



    /*--------------Getters - Setters for table fields--------------*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentReportsEntity)) return false;

        ParentReportsEntity that = (ParentReportsEntity) o;

        if (getReport_id() != that.getReport_id()) return false;
        if (getBoughtPoints() != that.getBoughtPoints()) return false;
        if (getCost() != that.getCost()) return false;
        if (getDate() != null ? !getDate().equals(that.getDate()) : that.getDate() != null) return false;
        return getParent_username() != null ? getParent_username().equals(that.getParent_username()) : that.getParent_username() == null;
    }

    @Override
    public int hashCode() {
        int result = getReport_id();
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + getBoughtPoints();
        result = 31 * result + getCost();
        result = 31 * result + (getParent_username() != null ? getParent_username().hashCode() : 0);
        return result;
    }
}
