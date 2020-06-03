package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Rajesh on 2017-08-11.
 */

public class Attribute_model {

    String attr_group_id;
    String attr_group_name;
    String attr_group_type;
    String attr_group_choosen;

    @SerializedName("attributes")
    ArrayList<Attribute_sub_model> attribute_sub_models;

    public String getAttr_group_id() {
        return attr_group_id;
    }

    public String getAttr_group_name() {
        return attr_group_name;
    }

    public String getAttr_group_type() {
        return attr_group_type;
    }

    public String getAttr_group_choosen() {
        return attr_group_choosen;
    }

    public ArrayList<Attribute_sub_model> getAttribute_sub_models() {
        return attribute_sub_models;
    }
}
