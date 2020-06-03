package model;

/**
 * Created by Rajesh Dabhi on 19/7/2017.
 */

public class Category_model {

    String id;
    String title;
    String slug;
    String parent;
    String leval;
    String description;
    String image;
    String banner_image;
    String status;
    String Count;
    String PCount;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public String getSlug(){
        return slug;
    }

    public String getParent(){
        return parent;
    }

    public String getLeval(){
        return leval;
    }

    public String getDescription(){
        return description;
    }

    public String getImage(){
        return image;
    }

    public String getBanner_image(){
        return banner_image;
    }

    public String getStatus(){
        return status;
    }

    public String getCount(){
        return Count;
    }

    public String getPCount(){
        return PCount;
    }

}
