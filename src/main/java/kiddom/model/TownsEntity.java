package kiddom.model;

/**
 * Created by eleni on 02-Jun-17.
**/

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "towns", schema = "mydb")
public class TownsEntity {

    /*----------------------------Fields----------------------------*/
    @Id
    @Column(name = "name")
    private String name;

    /*--------------One to Many relation of name from town to area--------------*/
    @OneToMany(mappedBy = "pk.town_name")
    Set<AreasEntity> areas = new HashSet<AreasEntity>(0);

    /*--------------Getters - Setters for table fields--------------*/
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TownsEntity that = (TownsEntity) o;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return true;
    }


}
