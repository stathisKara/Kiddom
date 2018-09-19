package kiddom.model;

/**
  Created by eleni on 02-Jun-17.
**/

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "areas", schema = "mydb")
public class AreasEntity {

    /*----------------------------Fields----------------------------*/
    @EmbeddedId
    AreasPK pk = new AreasPK();

    /*--------------Getters - Setters for table fields--------------*/
    public AreasPK getPk() { return pk; }

    public void setPk(AreasPK pk) { this.pk = pk; }


    public String getName() { return pk.getArea(); }
    public void setName(String name) { pk.setArea(name); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreasEntity that = (AreasEntity) o;
        String name = pk.getArea();
        if (name != null ? !name.equals(that.pk.getArea()) : that.pk.getArea() != null) return false;

        return true;
    }

}
