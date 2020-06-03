<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Users extends MY_Controller {
    public function __construct()
    {
                parent::__construct();
                                        
    }
    function appointments($app_id="",$action=""){
        if(_is_frontend_user_login($this)){
            $user_id = _get_current_user_id($this);
            if($app_id !="" && $action=="cancel"){
                $this->db->update("business_appointment",array("status"=>0),array("id"=>$app_id,"user_id"=>$user_id));
            }
            
            $this->load->model("business_model");
            $appointments = $this->business_model->get_user_appointment($user_id);
            $data["appointments"] = $appointments;
            $this->load->view("users/appointments",$data);
        }
    }
    function profile(){
        if(_is_frontend_user_login($this)){
                if($_POST){
                    $this->load->model("users_model");
                   
                        $this->load->library('form_validation');
                        
                        $this->form_validation->set_rules('fullname', 'Full Name', 'trim|required');
                        $this->form_validation->set_rules('b_month', 'Month', 'trim|required');
                        $this->form_validation->set_rules('b_day', 'Day', 'trim|required');
                        $this->form_validation->set_rules('b_year', 'Year', 'trim|required');
                        
                        if ($this->form_validation->run() == FALSE) 
                		{
                		  
                			$data["error"] = '<div class="alert alert-warning alert-dismissible" role="alert">
                                          <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                          <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                        </div>';
                            
                		}else
                        {
                                $user_fullname = $this->input->post("fullname");
                                $user_city = $this->input->post("city");
                                $user_country= $this->input->post("country_id");
                                
                                $b_month = $this->input->post("b_month");
                                $b_day = $this->input->post("b_day");
                                $b_year = $this->input->post("b_year");
                                $b_date = $b_year."-".$b_month."-".$b_day;
                                
                          
                              
        				  
        				  $update_array = array(
                                        "user_fullname"=>$user_fullname,
                                        "user_bdate"=>$b_date,
                                        "user_city"=>$user_city,
                                        "user_country"=>$user_country,
                                        "user_phone"=>$this->input->post("phone"));
        				        
                            
                                $config['upload_path'] = './uploads/profile/';
                        		$config['allowed_types'] = 'gif|jpg|png';
                        	
                        		$this->load->library('upload', $config);
                                
                                $file_name = "";
                        		if ( ! $this->upload->do_upload('user_image'))
                        		{
                        			
                        
                        			$data["error"] = '<div class="alert alert-warning alert-dismissible" role="alert">
                                          <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                          <strong>Warning!</strong> '.$this->upload->display_errors().'
                                        </div>';
                        		}
                        		else
                        		{
                        			
        							$file_data = $this->upload->data();
                                    $file_name = $file_data["file_name"];
                        			$update_array["user_image"]=$file_name;
                        		}
                               
        					    
                                    $this->load->model("common_model");
                                    $resid = $this->common_model->data_update("users", $update_array, array("user_id"=>_get_current_user_id($this)));
                                    $this->session->set_userdata("user_city",$user_city);     
            
                                    $this->session->set_flashdata("message", '<div class="alert alert-success alert-dismissible" role="alert">
                                          <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                          <strong>Success!</strong> User Added Successfully
                                        </div>');
                            
                        }
                        }
                        $this->load->model("users_model");
                        $user_data = $this->users_model->get_user_by_id(_get_current_user_id($this));
                        $data["user_data"] = $user_data;
                        $this->load->model("area_model");
                        $data["countries"] = $this->area_model->get_countries();
                        
                        $cities = $this->area_model->get_cities("1",$user_data->user_country);
                        $data["cities"] = $cities;
                        
                        
                        
                        $this->load->view("users/profile",$data);
        }
    }
	 
     function modify_password($token){
        $data = array();
        $q = $this->db->query("Select * from users where varified_token = '".$token."' limit 1");
        if($q->num_rows() > 0){
                        $data = array();
                        $this->load->library('form_validation');
                        $this->form_validation->set_rules('n_password', 'New password', 'trim|required');
                        $this->form_validation->set_rules('r_password', 'Re password', 'trim|required|matches[n_password]');
                        if ($this->form_validation->run() == FALSE) 
                  		{
                  		    if($this->form_validation->error_string()!=""){
                        		  
                                    $data["response"] = "error";
                        			$data["error"] = '<div class="alert alert-warning alert-dismissible" role="alert">
                                                  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                                  <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                                </div>';
                                                }
                                    
                  		}else
                        {
                                    $user = $q->row();
                                   $this->db->update("users",array("user_password"=>md5($this->input->post("n_password")),"varified_token"=>""),array("user_id"=>$user->user_id));
                                   $data["success"] = true;                             
                                                                   
                        }
                        $this->load->view("users/modify_password",$data);
        }else{
            echo "No access token found";
        }
    }

}
?>