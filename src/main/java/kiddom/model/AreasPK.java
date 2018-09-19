package kiddom.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Stathis on 6/11/2017.
 */

@Embeddable
public class AreasPK implements Serializable {

    /*--------------Primary key: area--------------*/
    @Column
    private String area;

    /*--------------Primary foreign key: town_name, from towns table--------------*/
    @ManyToOne
    @JoinColumn(name="town_name")
    private TownsEntity town_name;

    /*--------------Getters - Setters for table fields--------------*/

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public TownsEntity getTownName() { return town_name; }
    public void setTownName(TownsEntity town_name) { this.town_name = town_name; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreasPK that = (AreasPK) o;

        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (town_name != null ? !town_name.equals(that.town_name) : that.town_name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = area != null ? area.hashCode() : 0;
        result = 31 * result + (town_name != null ? town_name.hashCode() : 0);
        return result;
    }
}
