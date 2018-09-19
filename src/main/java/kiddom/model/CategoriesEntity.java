package kiddom.model;

/**
 * Created by eleni on 02-Jun-17.
**/

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "categories", schema = "mydb")
public class CategoriesEntity implements Serializable{

    /*----------------------------Fields----------------------------*/
    @Id
    @Column(name = "name")
    private String name;

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
        CategoriesEntity that = (CategoriesEntity) o;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return true;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.category_name", cascade= CascadeType.ALL)
    private Set<SubcategoriesEntity> subcategoriesByCatId = new HashSet<SubcategoriesEntity>(0);

    public Set<SubcategoriesEntity> getSubcategoriesByCatId() {
        return this.subcategoriesByCatId;
    }

    public void setSubcategoriesByCatId(Set<SubcategoriesEntity> subcategoriesByCatId) {
        this.subcategoriesByCatId = subcategoriesByCatId;
    }
}
