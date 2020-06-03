<?php
class Notification_model extends CI_Model{
   
     
          public function get_notification(){
       //  $q = $this->db->query("select * from notification ");
        
         $q = $this->db->query("Select ".TBL_NOTIFICATION.".*,".TBL_IMAGES.".image_path from ".TBL_NOTIFICATION."
        left outer join ".TBL_IMAGES." on ".TBL_IMAGES.".type_id = ".TBL_NOTIFICATION.".not_id");
        
        return $q->result();
    }
    

}
?>