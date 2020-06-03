<?php 

    class Review_model extends CI_Model
    {         
           public function get_review_by_id($id){
            $q = $this->db->query("select * from review where review_id ='".$id."' limit 0,1");
            return $q->row();
        }
        public function get_review($id){
                $q = $this->db->query("Select review.*, users.user_fullname from review inner join users on users.user_id = review.user_id where review.business_id= '".$id."'");
                return $q->result();   
        }

    }
?>