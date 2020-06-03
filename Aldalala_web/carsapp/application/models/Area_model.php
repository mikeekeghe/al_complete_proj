<?php
class Area_model extends CI_Model{
    public function get_countries($status=""){
        $filter = "";
        if($status!=""){
            $filter .=" and status = '".$status."'";
        }
        $q = $this->db->query("Select * from area_country where 1 ".$filter);
        return $q->result();
    }
    public function get_country_id($id){
        $q = $this->db->query("Select * from area_country where country_id = '".$id."' limit 1");
        return $q->row();
    }
    
    public function get_cities($status="",$country_id=""){
        $filter = "";
        if($status!=""){
            $filter .=" and area_city.status = '".$status."' ";
        }
        if($country_id!=""){
            $filter .=" and area_city.country_id = '".$country_id."' ";
        }
        $q = $this->db->query("Select area_city.*,area_country.country_name from area_city 
        inner join area_country on area_country.country_id = area_city.country_id
        where 1 ".$filter);
        return $q->result();
    }
    public function get_city_id($id){
        $q = $this->db->query("Select * from area_city 
        inner join area_country on area_country.country_id = area_city.country_id
        where city_id = '".$id."' limit 1");
        return $q->row();
    } 
}
?>