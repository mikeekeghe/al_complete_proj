<?php
class Attribute_model extends CI_Model{
    public function get_attribute1(){
        $query = $this->db->get(" ".TBL_ATTRIBUTES." ");
        return $query->result();
    }
  /*  public function get_attribute(){
       
        $q = $this->db->query("Select TBL_ATTRIBUTES.*,TBL_ATTRIBUTE_GROUP.attr_group_name from TBL_ATTRIBUTES 
        inner join TBL_ATTRIBUTE_GROUP on TBL_ATTRIBUTE_GROUP.attr_group_id = TBL_ATTRIBUTES.attr_group_id
        where 1 ");
        return $q->result();
    }*/
      public function get_attribute(){
        $q = $this->db->query("select ".TBL_ATTRIBUTES.".*, ".TBL_ATTRIBUTE_GROUP.".attr_group_name from ".TBL_ATTRIBUTES." inner join ".TBL_ATTRIBUTE_GROUP." on ".TBL_ATTRIBUTE_GROUP.".attr_group_id = ".TBL_ATTRIBUTES.".attr_group_id ");
        return $q->result();
    }
    public function get_attribute_id($attr_id){
        $q = $this->db->query("Select * from ".TBL_ATTRIBUTES." 
        inner join ".TBL_ATTRIBUTE_GROUP." on ".TBL_ATTRIBUTE_GROUP.".attr_group_id = ".TBL_ATTRIBUTES.".attr_group_id
        where attr_id = '".$attr_id."' limit 1");
        return $q->row();
    }
    
     public function set_attribute($id){
            $q = $this->db->query("select * from `".TBL_ATTRIBUTES."` WHERE ".TBL_ATTRIBUTES.".attr_id =".$id);
            return $q->row();
        }
    public function get_attribute_by_id($attr_id){
        $q = $this->db->query("select * from ".TBL_ATTRIBUTES." where  attr_id = '".$attr_id."' limit 1");
        return $q->row();
    }
    public function get_attribute_group(){
        $query = $this->db->get(" ".TBL_ATTRIBUTE_GROUP." ");
        return $query->result();
    }
     public function get_attribute_group_by_id($attr_group_id){
        $q = $this->db->query("select * from ".TBL_ATTRIBUTE_GROUP." where  attr_group_id = '".$attr_group_id."' limit 1");
        return $q->row();
    }
    
   /* public function get_attribute_by_image($image_id){
           $q = $this->db->query("Select tbl_attributes.*,tbl_images.image_path from tbl_attributes
        inner join tbl_images on tbl_images.type_id = tbl_attributes.attr_id where attr_id = '".$image_id."' limit 1"); 
        return $q->row();
    }*/
    
    public function get_attribute_by_image($image_id){
           $q = $this->db->query("Select ".TBL_ATTRIBUTES.".*,".TBL_IMAGES.".image_path from ".TBL_ATTRIBUTES."
        inner join ".TBL_IMAGES." on ".TBL_IMAGES.".type_id = ".TBL_ATTRIBUTES.".attr_id where attr_id = '".$image_id."' limit 1"); 
        return $q->row();
    }
    public function del_attri_by_id($attr_id)
    {
        $q = $this->db->query("DELETE ".TBL_ATTRIBUTES.".*, ".TBL_IMAGES.".* FROM   ".TBL_ATTRIBUTES." 
         INNER JOIN ".TBL_IMAGES." ON ".TBL_IMAGES.".type_id =  WHERE ".TBL_ATTRIBUTES.".attr_id = '".$attr_id);
         return $q->row();
    }
    public function get_attribu_by_id($attr_id){
        $q = $this->db->query("select * from ".TBL_ATTRIBUTES." where  attr_id = '".$attr_id."' limit 1");
        return $q->row();
    }
    
      public function get_attribute_with_group(){
            $q = $this->db->query("select * from `".TBL_ATTRIBUTE_GROUP."`");
            $groups = $q->result();  
             foreach($groups as &$group){
                        $q = $this->db->query("select * from ".TBL_ATTRIBUTES." where  attr_group_id = '".$group->attr_group_id."'");
                         $group->attributes = $q->result();
            } 
            return $groups; 
        }
    
}
?>
