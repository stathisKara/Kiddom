package kiddom.model;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Arianna on 26/5/2017.
 */

@Entity
@Table(name = "parent", schema = "mydb")
public class ParentEntity {

    /*----------------------------Fields----------------------------*/
    @EmbeddedId
    private ParentPK pk = new ParentPK("");
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
    @Column(name = "photo")
    private String photo;
    @Column(name = "avail_points")
    private int availPoints;
    @Column(name = "restr_points")
    private int restrPoints;
    @Column(name = "total_points")
    private int totalPoints;
    @Column(name = "longitude")
    private Float longitude;
    @Column(name = "latitude")
    private Float latitude;

    /*----------------------Constructor----------------------*/
    //ParentEntity() {
        //Default constructor
    //}

    public ParentEntity() {
        this.area="";
        this.town="";
        this.surname="";
        this.name="";
        this.telephone="";
        this.email="";
        this.setAvailPoints(0);
        this.setRestrPoints(0);
        this.setTotalPoints(0);
    }

    /*--------------Relations with other tables--------------*/

    /*--------------Many to Many relation with parent and program, for the reservations-inverse mapping--------------*/
    @OneToMany(mappedBy = "parent")
    private Set<ReservationsEntity> reservations = new HashSet<ReservationsEntity>(0);

    public Set<ReservationsEntity> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ReservationsEntity> reservations) {
        this.reservations = reservations;
    }

    /*--------------One to Many relation with parent and event, for the comments-inverse mapping--------------*/
    @OneToMany(mappedBy = "parent_username",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<CommentsEntity> comment_event = new HashSet<CommentsEntity>(0);

    public Set<CommentsEntity> getComment_event() {
        return comment_event;
    }

    public void setComment_event(Set<CommentsEntity> comment_event) {
        this.comment_event = comment_event;
    }


    /*--------------One to Many relation with parent and reports--------------*/
    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<ParentReportsEntity> parentReports = new HashSet<ParentReportsEntity>(0);

    public Set<ParentReportsEntity> getParentReports() {
        return parentReports;
    }

    public void setParentReports(Set<ParentReportsEntity> parentReports) {
        this.parentReports = parentReports;
    }

   /*--------------Getters - Setters for table fields--------------*/

    @Transient
    public void setUser(UserEntity user){ this.pk.setUser(user);}

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

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getAvailPoints() {
        return availPoints;
    }
    public void setAvailPoints(int availPoints) {
        this.availPoints = availPoints;
    }

    public int getRestrPoints() {
        return restrPoints;
    }
    public void setRestrPoints(int restrPoints) {
        this.restrPoints = restrPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public ParentPK getPk() { return pk;}
    public void setPk(ParentPK pk) {this.pk = pk;}

    @Transient
    public String getParentUsername(){ return getPk().getUser().getUsername();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentEntity that = (ParentEntity) o;

        //if (username != that.username) return false;
        if (availPoints != that.availPoints) return false;
        if (restrPoints != that.restrPoints) return false;
        if (totalPoints != that.totalPoints) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (town != null ? !town.equals(that.town) : that.town != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;

        return true;
    }

}
