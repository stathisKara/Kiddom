package kiddom.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/*
 * Created by eleni on 01-Jun-17.
*/

@Entity
@Table(name = "provider", schema = "mydb")
public class ProviderEntity {

    /*----------------------------Fields----------------------------*/
    @EmbeddedId
    ProviderPK pk = new ProviderPK("");
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "town")
    private String town;
    @Column(name = "area")
    private String area;
    @Column(name = "TR")
    private String tr;
    @Column(name = "owed_points")
    private int owedPoints;
    @Column(name = "gotten_points")
    private int gottenPoints;
    @Column(name = "total_points")
    private int totalPoints;
    @Column(name = "approved")
    private int approved;

    private String username;
    /*--------------Constructor------------------------------*/
    //ProviderEntity() {
    //Default constructor
    //}

    public ProviderEntity () {
        this.email="";
        this.tr="";
        this.area="";
        this.town="";
        this.surname="";
        this.name="";
        this.telephone="";
        this.setApproved(0);
        this.setGottenPoints(0);
        this.setOwedPoints(0);
        this.setTotalPoints(0);
    }

    /*--------------Relations with other tables--------------*/

    /*--------------One to Many relation from provider to single_event--------------*/
    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name="provider")
    private Set<SingleEventEntity> events = new HashSet<SingleEventEntity>(0);

    public Set<SingleEventEntity> getEvents() {
        return events;
    }

    public void setEvents(Set<SingleEventEntity> events) {
        this.events = events;
    }


    /*--------------Getters - Setters for table fields--------------*/
    @Transient
    public void setUser(UserEntity user){ this.pk.setUser(user);}

    public void setPk(ProviderPK pk) {
        this.pk = pk;
    }
    public ProviderPK getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTown() {
        return town;
    }
    public void setTown(String town) {
        this.town = town;
    }

    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTr() {
        return tr;
    }
    public void setTr(String tr) {
        this.tr = tr;
    }

    public int getOwedPoints() {
        return owedPoints;
    }
    public void setOwedPoints(int owedPoints) {
        this.owedPoints = owedPoints;
    }

    public int getGottenPoints() {
        return gottenPoints;
    }
    public void setGottenPoints(int gottenPoints) {
        this.gottenPoints = gottenPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getApproved() {
        return approved;
    }
    public void setApproved(int approved) {
        this.approved = approved;
    }

    public String getUsername(){ return pk.getUser().getUsername();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProviderEntity that = (ProviderEntity) o;

       // if (!username.equals(that.username)) return false;
        if (owedPoints != that.owedPoints) return false;
        if (gottenPoints != that.gottenPoints) return false;
        if (totalPoints != that.totalPoints) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (tr != null ? !tr.equals(that.tr) : that.tr != null) return false;
        if (town != null ? !town.equals(that.town) : that.town != null) return false;
        if (area!= null ? !area.equals(that.area) : that.area != null) return false;
        return true;
    }

}
