package model;

/**
 * Created by Rajesh on 2017-09-02.
 */

public class Notification_model {

    String not_id;
    String title;
    String message;
    String created_at;
    String image_path;

    public String getNot_id() {
        return not_id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getImage_path() {
        return image_path;
    }
}
