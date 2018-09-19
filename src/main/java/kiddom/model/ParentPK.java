package kiddom.model;

import org.apache.catalina.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Stathis on 6/11/2017.
 */
@Embeddable
public class ParentPK implements Serializable {

    /*--------------Primary foreign key: username, from user table--------------*/
    @OneToOne
    private UserEntity user;

    /*--------------Primary foreign key: username, from user table--------------*/
    public ParentPK  (String name){
        System.out.println("Eimai ston constructor (parent) me name " + name);
        user = new UserEntity(name);
    }
    public ParentPK(){}

    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentPK parentPK = (ParentPK) o;

        //if (username != parentPK.username) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 31;
        return result;
    }
}
