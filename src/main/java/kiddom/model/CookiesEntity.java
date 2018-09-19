package kiddom.model;

/**
 * Created by eleni on 02-Jun-17.
 **/


import javax.persistence.*;

@Entity
@Table(name = "cookies", schema = "mydb")
public class CookiesEntity {

    private String category;
    private String subcat1;
    private String subcat2;
    private String subcat3;
    private String price;
    //private ParentEntity parentByParentId;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;



    @Basic
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "subcat1")
    public String getSubcat1() {
        return subcat1;
    }

    public void setSubcat1(String subcat1) {
        this.subcat1 = subcat1;
    }

    @Basic
    @Column(name = "subcat2")
    public String getSubcat2() {
        return subcat2;
    }

    public void setSubcat2(String subcat2) {
        this.subcat2 = subcat2;
    }

    @Basic
    @Column(name = "subcat3")
    public String getSubcat3() {
        return subcat3;
    }

    public void setSubcat3(String subcat3) {
        this.subcat3 = subcat3;
    }

    @Basic
    @Column(name = "price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CookiesEntity that = (CookiesEntity) o;

       // if (!parent_username.equals(that.parent_username)) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (subcat1 != null ? !subcat1.equals(that.subcat1) : that.subcat1 != null) return false;
        if (subcat2 != null ? !subcat2.equals(that.subcat2) : that.subcat2 != null) return false;
        if (subcat3 != null ? !subcat3.equals(that.subcat3) : that.subcat3 != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 35;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (subcat1 != null ? subcat1.hashCode() : 0);
        result = 31 * result + (subcat2 != null ? subcat2.hashCode() : 0);
        result = 31 * result + (subcat3 != null ? subcat3.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }


}
