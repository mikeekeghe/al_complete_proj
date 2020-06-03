<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Login extends MY_Controller {
    public function __construct()
    {
                parent::__construct();
                // Your own constructor code        
    }
    
    public function index(){
    }
    
    public function is_user_login(){
        
        $data = _is_frontend_user_login($this);
        echo $data;
    }
    function confirm(){
            //http://localhost/freeticket/index.php/login/confirm/?code=56e512236aec4&email=balajiinfo@gmail.com
                $data = array();
               
              
                $this->load->library('form_validation');
                 $this->form_validation->set_data(array(
                    "code" => $this->input->get("code"),
                    "email" => $this->input->get("email")));
               
                $this->form_validation->set_rules('code', 'Code', 'trim|required');
                $this->form_validation->set_rules('email', 'Email Id', 'trim|required|valid_email');
                if ($this->form_validation->run() == FALSE) 
        		{
        		  
        			$data["error"] = '<div class="alert alert-warning alert-dismissible" role="alert">
                                  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                  <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                </div>';
                    
        		}else
                {
                     $code = $this->input->get("code");
                    $email = $this->input->get("email");
                    $q = $this->db->query("Select * from users where varified_token = '".$code."' and user_email = '".$email."' limit 1");
                    $user = $q->row();
                    if($user){
                        $this->db->update("users",array("is_email_varified"=>1,"user_status"=>1,"varified_token"=>""),array("user_id"=>$user->user_id));
                        $data["success"] = true;
                    }
                }
                $this->load->view('login_varified_email',$data);
    }
}
