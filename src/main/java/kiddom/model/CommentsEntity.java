package kiddom.model;

/**
 * Created by eleni on 02-Jun-17.
**/

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comments", schema = "mydb")
public class CommentsEntity implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int comment_id;
    @Column(name="comment")
    private String comment;
    @Column(name="reply")
    private String reply;
    @Column(name="rating")
    private float rating;

  //  private String username;
    public CommentsEntity() {
        this.comment="";
        this.reply="";
    }

    @ManyToOne
    @JoinColumn(name="parent_username")
    private ParentEntity parent_username;

    public ParentEntity getParent_username() {
        return parent_username;
    }

    public void setParent_username(ParentEntity parent_username) {
        this.parent_username = parent_username;
    }

    @ManyToOne
    @JoinColumn(name="event_id")
    private SingleEventEntity event_id;

    public SingleEventEntity getEvent_id() {
        return event_id;
    }

    public void setEvent_id(SingleEventEntity event_id) {
        this.event_id = event_id;
    }


 /*--------------Getters - Setters for table fields--------------*/

    public int getCommentId() {
        return comment_id;
    }

    public void setCommentId(int commentId) {
        this.comment_id = commentId;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "reply")
    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Column(name = "rating")
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentsEntity that = (CommentsEntity) o;

        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (reply != null ? !reply.equals(that.reply) : that.reply != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = comment_id;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (reply != null ? reply.hashCode() : 0);
        return result;
    }

}
