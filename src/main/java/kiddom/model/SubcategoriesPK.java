package kiddom.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Stathis on 6/11/2017.
 */

@Embeddable
public class SubcategoriesPK implements Serializable {

    /*--------------Primary key: subname--------------*/
    @Column
    private String subname;

    /*--------------Primary foreign key: category_name, from categories table--------------*/
    @ManyToOne
    @JoinColumn(name="category_name")
    private CategoriesEntity category_name;

    /*--------------Getters - Setters for table fields--------------*/

    public String getSubname() {
        return subname;
    }
    public void setSubname(String subname) {
        this.subname = subname;
    }

    public CategoriesEntity getCategoryName() {
        return category_name;
    }
    public void setCategoryName(CategoriesEntity category_name) {
        this.category_name = category_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubcategoriesPK that = (SubcategoriesPK) o;

        if (subname != null ? !subname.equals(that.subname) : that.subname != null) return false;
        if (category_name != null ? !category_name.equals(that.category_name) : that.category_name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = subname != null ? subname.hashCode() : 0;
        result = 31 * result + (category_name != null ? category_name.hashCode() : 0);
        return result;
    }
}
