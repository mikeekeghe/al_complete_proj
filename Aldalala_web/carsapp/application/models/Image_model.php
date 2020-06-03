<?php
class Image_model extends CI_Model{
    public function get_image_by_id($image_id){
          $q = $this->db->query("select * from ".TBL_IMAGES." where  image_id = '".$image_id."' limit 1");
        return $q->row();
    }
     public function set_image($id){
            $q = $this->db->query("select * from `".TBL_IMAGES."` WHERE ".TBL_IMAGES.".image_id =".$id);
            return $q->row();
        }
         public function get_images(){
        $query = $this->db->get(" ".TBL_IMAGES." ");
        return $query->result();
    }
}
?>
