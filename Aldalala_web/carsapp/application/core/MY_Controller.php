<?php
Class MY_Controller Extends CI_Controller{

    public function __construct(){
        parent::__construct();
		$this->db->trans_strict(FALSE);
        $this->db->query("SET sql_mode = '' ");
    }
    public function get_business_list(){
        $this->load->model("business_model");
        return $this->business_model->get_businesses($userid=3);
    }
    public function common(){
        // code here
        $this->load->model("category_model");
        $data["categories"] = $this->category_model->get_categories();
        
        $this->load->model("area_model");
        $data["countries"] = $this->area_model->get_countries("1");
        $data["cities"] = $this->area_model->get_cities("1");
        return $data;
    }
}