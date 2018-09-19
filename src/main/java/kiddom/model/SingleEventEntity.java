package kiddom.model;

/**
 * Created by eleni on 02-Jun-17.
**/

//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.security.access.method.P;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "single_event", schema = "mydb")
public class SingleEventEntity implements Serializable {

    /*----------------------------Fields----------------------------*/
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="event_id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "photos")
    private String photos;
    @Column(name = "price")
    private int price;
    @Column(name = "category")
    private String category;
    @Column(name = "sub1")
    private String sub1;
    @Column(name = "sub2")
    private String sub2;
    @Column(name = "sub3")
    private String sub3;
    @Column(name = "area")
    private String town;
    @Column (name = "town")
    private String area;
    @Column(name = "address")
    private String address;
    @Column(name = "number")
    private int number;
    @Column(name = "postcode")
    private int postcode;
    @Column(name = "ratings_sum")
    private float ratings_sum;
    @Column(name = "ratings_number")
    private float ratings_number;
    @Column(name = "date")
    private String date;
    @Column(name = "canceled")
    private int canceled;
    @Column(name = "longitude")
    private Float longitude;
    @Column(name = "latitude")
    private Float latitude;
    @Transient
    private int numOfSlots;
    @Transient
    private int slotDuration;

    private String owner;

    @Transient
    private int availability;
    public int getAvailability(){
        int max=0;
        for(ProgramEntity pro:program)
        {
            if(pro.getAvailability()>max)
                max=pro.getAvailability();
        }
        return max;
    }

    /*----------------------Constructor----------------------*/
    //SingleEventEntity() {
    //  Default constructor
    //}

    public SingleEventEntity() {
        this.canceled = 0;
    }

     /*--------------Relations with other tables--------------*/

    /*--------------Many to One relation from provider, to get username--------------*/
    @ManyToOne
    @JoinColumn(name="provider")
    private ProviderEntity provider;

    public ProviderEntity getProviders() {
        return provider;
    }

    public void setProviders(ProviderEntity provider) {
        this.provider = provider;
    }

    public String getOwner(){return new String (provider.getName()+" "+ provider.getSurname());}

    /*--------------One to Many relation for event_id from event to the program--------------*/
    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name="event_id")
    private Set<ProgramEntity> program = new HashSet<ProgramEntity>(0);

    public Set<ProgramEntity> getProgram() {
        return program;
    }

    public void setProgram(HashSet<ProgramEntity> program) {
        this.program = program;
    }


    /*--------------One to Many relation with parent->username and event->event_id, for the comments--------------*/
    @OneToMany(mappedBy = "event_id",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<CommentsEntity> comment_parent = new HashSet<CommentsEntity>(0);

    public Set<CommentsEntity> getComment_parent() {
        return comment_parent;
    }

    public void setComment_parent(Set<CommentsEntity> comment_parent) {
        this.comment_parent = comment_parent;
    }

    /*--------------Getters - Setters for table fields--------------*/

    public void setId(Integer id) { this.id = id; }
    public Integer getId() { return id; }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public String getPhotos() {
        return photos;
    }
    public void setPhotos(String photos) {
        this.photos = photos;
    }


    public int getPrice() { return price; }
    public void setPrice(int price) {
        this.price = price;
    }


    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }


    public String getSub1() {
        return sub1;
    }
    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }


    public String getSub2() {
        return sub2;
    }
    public void setSub2(String sub2) {
        this.sub2 = sub2;
    }


    public String getSub3() {
        return sub3;
    }
    public void setSub3(String sub3) {
        this.sub3 = sub3;
    }


    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }


    public String getTown() {
        return town;
    }
    public void setTown(String town) {
        this.town = town;
    }


    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }


    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }


    public int getPostcode() {
        return postcode;
    }
    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getDate(){return date;}
    public void setDate(String date){this.date=date;}


    public float getRatings_sum(){return this.ratings_sum;}
    public void setRatings_sum(float sum){this.ratings_sum=sum;}

    public float getRatings_num(){return this.ratings_number;}
    public void setRatings_num(float number){this.ratings_number=number;}

    public float getRating() {
        return ratings_number;
    }
    public void setRating(float rating) {
        this.ratings_number = rating;
    }

    public int getCanceled() { return canceled; }
    public void setCanceled(int canceled) { this.canceled = canceled; }

    public int getNumOfSlots() { return  numOfSlots; }
    public void setNumOfSlots(int numOfSlots) { this.numOfSlots = numOfSlots; }

    public int getSlotDuration() { return slotDuration; }
    public void setSlotDuration(int slotDuration) { this.slotDuration = slotDuration; }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleEventEntity that = (SingleEventEntity) o;

        if (price != that.price) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (photos != null ? !photos.equals(that.photos) : that.photos != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (sub1 != null ? !sub1.equals(that.sub1) : that.sub1 != null) return false;
        if (sub2 != null ? !sub2.equals(that.sub2) : that.sub2 != null) return false;
        if (sub3 != null ? !sub3.equals(that.sub3) : that.sub3 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (sub1 != null ? sub1.hashCode() : 0);
        result = 31 * result + (sub2 != null ? sub2.hashCode() : 0);
        result = 31 * result + (sub3 != null ? sub3.hashCode() : 0);
        return result;
    }
}
