package model;

/**
 * Created by Rajesh on 2017-08-24.
 */

public class Review_model {

    String review_id;
    String user_id;
    String post_id;
    String comment;
    String rating;
    String on_date;
    String status;
    String user_fullname;
    String user_image;

    public String getReview_id() {
        return review_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public String getComment() {
        return comment;
    }

    public String getRating() {
        return rating;
    }

    public String getOn_date() {
        return on_date;
    }

    public String getStatus() {
        return status;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public String getUser_image() {
        return user_image;
    }
}
