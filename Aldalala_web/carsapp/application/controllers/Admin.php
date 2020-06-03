<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Admin extends MY_Controller {
    public function __construct()
    {
                parent::__construct();
                // Your own constructor code
                $this->load->database();
                $this->load->helper('login_helper');
    }
        function signout(){
        $this->session->sess_destroy();
        redirect("admin");
    }
    public function change_status(){
        $table = $this->input->post("table");
        $id = $this->input->post("id");
        $on_off = $this->input->post("on_off");
        $id_field = $this->input->post("id_field");
        $status = $this->input->post("status");
        $post_status = $this->input->post("post_status");
        
        $this->db->update($table,array("$status"=>$on_off),array("$id_field"=>$id));
    }
	public function index()
	{
		if(_is_user_login($this)){
            redirect(_get_user_redirect($this));
        }else{
            
            $data = array("error"=>"");       
            if(isset($_POST))
            {
                
                $this->load->library('form_validation');
                
                $this->form_validation->set_rules('email', 'Email', 'trim|required');
                $this->form_validation->set_rules('password', 'Password', 'trim|required');
                if ($this->form_validation->run() == FALSE) 
        		{
        		  if($this->form_validation->error_string()!=""){
        			$data["error"] = '<div class="alert alert-warning alert-dismissible" role="alert">
                                  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                  <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                </div>';
                    }
                    
        		}else
                {
                   
                    $q = $this->db->query("Select * from `users` where (`user_email`='".$this->input->post("email")."') and user_password='".md5($this->input->post("password"))."'  Limit 1");
                    
                   // print_r($q) ; 
                    if ($q->num_rows() > 0)
                    {
                        $row = $q->row(); 
                        if($row->user_status == "0")
                        {
                            $data["error"] = '<div class="alert alert-danger alert-dismissible" role="alert">
                                  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                  <strong>Warning!</strong> Your account currently inactive.</div>';
                        }
                        else
                        {
                            $newdata = array(
                                                   'user_name'  => $row->user_fullname,
                                                   'user_email'     => $row->user_email,
                                                   'logged_in' => TRUE,
                                                   'user_id'=>$row->user_id,
                                                   'user_type_id'=>$row->user_type_id
                                                  );
                            $this->session->set_userdata($newdata);
                            redirect(_get_user_redirect($this));
                         
                        }
                    }
                    else
                    {
                        $data["error"] = '<div class="alert alert-danger alert-dismissible" role="alert">
                                  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                  <strong>Warning!</strong> Invalid User and password. </div>';
                    }
                   
                    
                }
            }
            $data["active"] = "login";
            
            $this->load->view("admin/login",$data);
        }
	}
        function dashboard(){
           if(_is_user_login($this))
        {
             
                
                $this->load->model("post_model");
                $data["gettoday"] = $this->post_model->get_post_today();
                $data["app_count"] =  $this->post_model->get_Attribute_count();
                $usertype = _get_current_user_type_id($this);
                $data["reviews_count"] = $this->post_model->get_post_counts();
                $this->load->model("users_model");
                $data["user_count"] =  $this->users_model->get_users_counts("1");
                $this->load->view("admin/dashboard",$data);
            
        }
    } 
/*----------- Users related functions , manage all users listings and process ------------*/
    public function add_user(){
        if(_is_user_login($this)){
            $data = array();
            $this->load->model("users_model");
            if($_POST){
                $this->load->library('form_validation');
                
                $this->form_validation->set_rules('user_fullname', 'Full Name', 'trim|required');
                $this->form_validation->set_rules('user_email', 'Email Id', 'trim|required');
                $this->form_validation->set_rules('user_password', 'Password', 'trim|required');
                $this->form_validation->set_rules('user_type', 'User Type', 'trim|required');
                if ($this->form_validation->run() == FALSE) 
        		{
        		  
        			$data["error"] = '<div class="alert alert-warning alert-dismissible" role="alert">
                                  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                  <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                </div>';
                    
        		}else
                {
                        $user_fullname = $this->input->post("user_fullname");
                        $user_email = $this->input->post("user_email");
                        $user_password = $this->input->post("user_password");
                        $user_type = $this->input->post("user_type");
                        
                        
                        $status = ($this->input->post("status")=="on")? 1 : 0;
                        
                            $this->load->model("common_model");
                            $this->common_model->data_insert("users",
                                array(
                                "user_fullname"=>$user_fullname,
                                "user_email"=>$user_email,
                                "user_password"=>md5($user_password),
                                "user_type_id"=>$user_type,
                                "user_status"=>$status));
                            $this->session->set_flashdata("message", '<div class="alert alert-success alert-dismissible" role="alert">
                                  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                  <strong>Success!</strong> User Added Successfully
                                </div>');
                                
                               redirect("admin/listuser/0");  
                        
                }
            }
            
            $data["user_types"] = $this->users_model->get_user_type();
            $this->load->view("admin/users/add_user",$data);
        }
    }
	 public function listuser($user_id){
        if(_is_user_login($this)){
            $data = array();
            $this->load->model("users_model");
            $data["users"] = $this->users_model->get_user($user_id);
            
            $this->load->view("admin/users/list",$data);
        }
    }
    public function listappuser($user_id){
        if(_is_user_login($this)){
            $data = array();
            $this->load->model("users_model");
            $data["users"] = $this->users_model->get_user($user_id);
            
            $this->load->view("admin/users/applist",$data);
        }
    }
    public function edit_user($user_id){
        if(_is_user_login($this)){
            $data = array();
            $this->load->model("users_model");
            $data["user_types"] = $this->users_model->get_user_type();
            $user = $this->users_model->get_user_by_id($user_id);
            $data["user"] = $user;
            if($_POST){
                $this->load->library('form_validation');
                
                $this->form_validation->set_rules('user_fullname', 'Full Name', 'trim|required');
                $this->form_validation->set_rules('user_password', 'Password', 'trim|required');
                $this->form_validation->set_rules('user_type', 'User Type', 'trim|required');
                if ($this->form_validation->run() == FALSE) 
        		{
        		  
        			$data["error"] = '<div class="alert alert-warning alert-dismissible" role="alert">
                                  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                  <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                </div>';
                    
        		}else
                {
                        $user_fullname = $this->input->post("user_fullname");
                        $user_type = $this->input->post("user_type");
                        $status = ($this->input->post("status")=="on")? 1 : 0;
                        
                        $update_array = array(
                                "user_fullname"=>$user_fullname,
                                "user_type_id"=>$user_type,
                                "user_status"=>$status);
                        $user_password = $this->input->post("user_password");
                        if($user->user_password != $user_password){
                            
                        $update_array["user_password"]= md5($user_password);
                        
                        }
                        
                            $this->load->model("common_model");
                            $this->common_model->data_update("users",$update_array,array("user_id"=>$user_id)
                                );
                            $this->session->set_flashdata("message", '<div class="alert alert-success alert-dismissible" role="alert">
                                  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                  <strong>Success!</strong> User Added Successfully
                                </div>');
                                redirect("admin/listuser/".$user->user_type_id);
                        
                }
            }
            
            
            $this->load->view("admin/users/edit_user",$data);
        }
    }
    function delete_user($user_id){
        if(_is_user_login($this)){
            $data = array();
            $this->load->model("users_model");
            $user  = $this->users_model->get_user_by_id($user_id);
            if($user){
                $this->db->query("Delete from users where user_id = '".$user->user_id."'");
                redirect("admin/listuser/".$user->user_type_id);
            }
        }
    }
/*------------END Users -----------------*/    

/*----------- Attrubute related functions ------------*/
     public function add_attribute()
     {
        if(_is_user_login($this))
        {
            $this->load->model("attribute_model");
            $data["attribute_group"] =  $this->attribute_model->get_attribute_group();
            $this->load->library('form_validation');
            $this->form_validation->set_rules('attr_rolle', 'Attribute Role', 'trim|required');
            $this->form_validation->set_rules('attr_group_id', 'Attribute Group', 'trim|required');
            if ($this->form_validation->run() == FALSE)
      		{
                if($this->form_validation->error_string()!="")
                {
                    $this->session->set_flashdata("message", '<div class="alert alert-warning alert-dismissible" role="alert">
                        <i class="fa fa-warning"></i>
                        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <strong>Warning!</strong> '.$this->form_validation->error_string().'
                        </div>');
                }
      		}
      		else
      		{
                 $saveattri = array(
                        "attr_rolle"=>$this->input->post("attr_rolle"),
                        "attr_group_id"=>$this->input->post("attr_group_id"),
                        );
                 $this->load->model("common_model");
                 $attr_id = $this->common_model->data_insert(" ".TBL_ATTRIBUTES." ",$saveattri);
    /* =================== Insert Image Table ====================================== */
                 $image_path = "";
                 if(isset( $_FILES["image_path"]) && $_FILES["image_path"]["size"] > 0)
                 {
                    $config['upload_path']          = './uploads/image/';
                    $config['allowed_types']        = 'gif|jpg|png|jpeg';
                    
                    if(!is_dir($config['upload_path']))
                    {
                        mkdir($config['upload_path']);
                    }
                    $this->load->library('upload', $config);
                    if ( ! $this->upload->do_upload('image_path'))
                    {
                        $error = array('error' => $this->upload->display_errors());
                    }
                    else
                    {
                        $img_data = $this->upload->data();
                        $image_path=$img_data['file_name'];
                    }
                 }
                 $image = array("image_path"=>$image_path,"type"=>"attribute","type_id"=>$attr_id);
                 $this->load->model("common_model");
                 $image_id = $this->common_model->data_insert(" ".TBL_IMAGES." ",$image);                
     /* ========================  Insert Image Table End  ======================================================= */
                $this->session->set_flashdata("message",'<div class="alert alert-success alert-dismissible" role="alert">
                                            <i class="fa fa-check"></i>
                                          <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                          <strong>Success!</strong> Your Attibute saved successfully...
                                        </div>');
            }
            $this->load->view('admin/attribute/add_attribute',$data);
        }
        else
        {
            redirect('admin');
        }
    }
    public function listattribute()
    {
        if(_is_user_login($this))
        {
            $data = array();
            $this->load->model("attribute_model");
            $data["attribute"] = $this->attribute_model->get_attribute();
            $this->load->view("admin/attribute/list",$data);
        }
    }    
    public function edit_attribute($attr_id = "", $image_id = "")
    {
        if(_is_user_login($this))
        {
            if($_POST)
            {
                $this->load->library('form_validation');
                $this->form_validation->set_rules('attr_rolle', 'Attribute Roll', 'trim|required');
                $this->form_validation->set_rules('attr_group_id', 'Attribute Group Name', 'trim|required');
                if ($this->form_validation->run() == FALSE)
        		{
  		            if($this->form_validation->error_string()!=""){
        			     $this->session->set_flashdata("message", '<div class="alert alert-warning alert-dismissible" role="alert">
                                        <i class="fa fa-warning"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                    </div>');
                    }
        		}
        		else
        		{  
  		            $array = array( "attr_rolle"=>$this->input->post("attr_rolle"),
                    "attr_group_id"=>$this->input->post("attr_group_id"),
                 );
                        $this->load->model("common_model");
                        $this->common_model->data_update(" ".TBL_ATTRIBUTES." ",$array,array("attr_id"=>$attr_id));
                        /* =================== Insert Image Table ====================================== */
                 $image_path = "";
                 if(isset( $_FILES["image_path"]) && $_FILES["image_path"]["size"] > 0)
                 {
                    $config['upload_path']          = './uploads/image/';
                    $config['allowed_types']        = 'gif|jpg|png|jpeg';
                    if(!is_dir($config['upload_path']))
                    {
                        mkdir($config['upload_path']);
                    }
                    $this->load->library('upload', $config);
                    if ( ! $this->upload->do_upload('image_path'))
                    {
                        $error = array('error' => $this->upload->display_errors());
                    }
                    else
                    {
                        $img_data = $this->upload->data();
                        $image_path=$img_data['file_name'];
                        $update_image = array("image_path"=>$image_path);
                
                        $this->load->model("common_model");
                        $this->common_model->data_update(TBL_IMAGES,$update_image,array("type_id"=>$attr_id,"type"=>"attribute"));   
                    }
                 }    
     /* ========================  Insert Image Table End  ======================================================= */ 
                        $this->session->set_flashdata("message",'<div class="alert alert-success alert-dismissible" role="alert">
                                        <i class="fa fa-check"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Success!</strong> Your Update Attibute saved successfully...
                                    </div>');
                      unset($_POST);  
                      redirect("admin/listattribute");
                }   
            } 
            $this->load->model("attribute_model");
            $data["setattribute"] = $this->attribute_model->set_attribute($attr_id);
            $data["attribute_group"] =  $this->attribute_model->get_attribute_group();
            $data["attribute"] = $this->attribute_model->get_attribute_by_id($attr_id);
            $data["get_image"] = $this->attribute_model->get_attribute_by_image($attr_id);
            $this->load->view("admin/attribute/edit_attribute",$data);
            
            }
        }
    
    /*******************************************/
    function delete_attribute($attr_id)
    {
        if(_is_user_login($this))
        {
            $data = array();
            $this->load->model("attribute_model");
            /*$attribu  = $this->attribute_model->get_attribute_by_id($attr_id);*/
            $attribu  = $this->attribute_model->get_attribu_by_id($attr_id);
            if($attribu)
            {
                
                $this->db->query("Delete from ".TBL_ATTRIBUTES."  where attr_id = '".$attribu->attr_id."'");
                    $this->db->query("Delete from ".TBL_IMAGES." where type_id = '".$attribu->attr_id."'");
                /*$this->db->query("Delete from TBL_ATTRIBUTES where attr_id = '".$attribu->attr_id."'");*/
               
                redirect("admin/listattribute",$data);
            }
        }
    }
/*------------END Attrubute -----------------*/    

/*----------- Attrubute Group related functions ------------*/
     public function add_attribute_group()
     {
        if(_is_user_login($this))
        {
            $this->load->library('form_validation');
            $this->form_validation->set_rules('attr_group_name', 'Attribute Group Name', 'trim|required');
            $this->form_validation->set_rules('attr_group_type', 'Attribute Group Type', 'trim|required');
            $this->form_validation->set_rules('attr_group_choosen', 'Attribute Group Choose', 'trim|required');
            if ($this->form_validation->run() == FALSE)
        	{
                if($this->form_validation->error_string()!="")
                {
 			        $this->session->set_flashdata("message", '<div class="alert alert-warning alert-dismissible" role="alert">
                                        <i class="fa fa-warning"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                    </div>');
                }
       		}
       		else
        	{
                $saveattri = array(
                            "attr_group_name"=>$this->input->post("attr_group_name"),
                            "attr_group_type"=>$this->input->post("attr_group_type"),
                             "attr_group_choosen"=>$this->input->post("attr_group_choosen"),
                            );
                $this->load->model("common_model");
                $attr_id = $this->common_model->data_insert(" ".TBL_ATTRIBUTE_GROUP." ",$saveattri);
                $this->session->set_flashdata("message",'<div class="alert alert-success alert-dismissible" role="alert">
                                        <i class="fa fa-check"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Success!</strong> Your Attibute Group saved successfully...
                                    </div>');
        	}
            $this->load->view('admin/attribute/add_attribute_group');
        }
        else
        {
            redirect('admin');
        }
    }
    public function listattributegroup()
    {
        if(_is_user_login($this)){
            $data = array();
            
            $q = $this->db->query("select * from ".TBL_ATTRIBUTE_GROUP." ");
            $data["getattributegroup"] = $q->result();
           /* $this->load->model("attribute_model");
            $data["attribute_group"] = $this->attribute_model->get_attribute_group();*/
            $this->load->view("admin/attribute/list_group",$data);
        }
    }
    public function edit_attribute_group($attr_group_id)
    {
        if(_is_user_login($this))
        {
            $data = array("error"=>"");  
            $q = $this->db->query("select * from `".TBL_ATTRIBUTE_GROUP."` WHERE attr_group_id=".$attr_group_id);
            $data["getattri"] = $q->row();
            $this->load->library('form_validation');
            $this->form_validation->set_rules('attr_group_name', 'Attribute Group Name', 'trim|required');
            $this->form_validation->set_rules('attr_group_type', 'Attribute Type', 'trim|required');
            $this->form_validation->set_rules('attr_group_choosen', 'Attribute Group Choose', 'trim|required');
            if ($this->form_validation->run() == FALSE)
        	{
  		        if($this->form_validation->error_string()!="")
                {
     		         $this->session->set_flashdata("message", '<div class="alert alert-warning alert-dismissible" role="alert">
                                        <i class="fa fa-warning"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                    </div>');
                }
       		}
        	else
        	{
      		    $saveattri = array(
                    "attr_group_name"=>$this->input->post("attr_group_name"),
                                "attr_group_type"=>$this->input->post("attr_group_type"),
                                "attr_group_choosen"=>$this->input->post("attr_group_choosen")
                                );
                       
                $this->db->update(" ".TBL_ATTRIBUTE_GROUP." ",$saveattri,array("attr_group_id"=>$attr_group_id)); 
                $this->session->set_flashdata("message",'<div class="alert alert-success alert-dismissible" role="alert">
                                            <i class="fa fa-check"></i>
                                          <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                          <strong>Success!</strong> Your Business Details Upadete successfully...
                                        </div>');
                redirect('admin/listattributegroup');
            }
            $this->load->model("attribute_model");
            $data["attribute_group"]  = $this->attribute_model->get_attribute_group($attr_group_id);
            $data["setattribute"] = $this->attribute_model->get_attribute_group_by_id($attr_group_id);
            $this->load->view('admin/attribute/edit_attribute_group',$data);
        }
        else
        {
            redirect('admin');
        }
    }
    function delete_attribute_group($attr_group_id)
    {
        if(_is_user_login($this))
        {
            $data = array();
            $this->load->model("attribute_model");
            $group  = $this->attribute_model->get_attribute_group_by_id($attr_group_id);
            if($group)
            {
                $this->db->query("Delete from ".TBL_ATTRIBUTE_GROUP." where attr_group_id = '".$group->attr_group_id."'");
                redirect("admin/listattributegroup");
            }
        }
    }
/*------------END Attrubute Group -----------------*/ 

/* =========  Post Related Function  ============= */
    public function add_post()
     {
        if(_is_user_login($this))
        {
            $this->load->library('form_validation');
            $this->form_validation->set_rules('post_title', 'Post Title', 'trim|required');
            $this->form_validation->set_rules('post_description', 'Post Description', 'trim|required');
            $this->form_validation->set_rules('user_id', 'Select Name', 'trim|required');
            $this->form_validation->set_rules('visible_limit', 'Limit Visible', 'trim|required');
            $this->form_validation->set_rules('post_title', 'Post Title', 'trim|required');
            if ($this->form_validation->run() == FALSE)
      		{
                if($this->form_validation->error_string()!="")
                {
                    $this->session->set_flashdata("message", '<div class="alert alert-warning alert-dismissible" role="alert">
                        <i class="fa fa-warning"></i>
                        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <strong>Warning!</strong> '.$this->form_validation->error_string().'
                        </div>');
                }
      		}
      		else
      		{
      		    $post_date=date("Y-m-d m:i:s");
                $savepost = array(
                    "post_title"=>$this->input->post("post_title"),
                    "post_description"=>$this->input->post("post_description"),
                    "user_id"=>$this->input->post("user_id"),
                    "visible_limit"=>$this->input->post("visible_limit"),
                    "post_km"=>$this->input->post("post_km"),
                    "post_hand"=>$this->input->post("post_hand"),
                    "post_year"=>$this->input->post("post_year"),
                    "post_date"=>$post_date,
                   /* "post_image"=>$post_image,*/
                    "post_status"=>"1"
                    );
                $this->load->model("common_model");
                $post_id = $this->common_model->data_insert(" ".TBL_POSTS." ",$savepost);
                
                /* ========================================================  */
                
                $image_path = "";
                if(isset( $_FILES["image_path"]) && $_FILES["image_path"]["size"] > 0)
                {
                    $config['upload_path']          = './uploads/image/';
                    $config['allowed_types']        = 'gif|jpg|png|jpeg';
                    if(!is_dir($config['upload_path']))
                    {
                        mkdir($config['upload_path']);
                    }
                    $this->load->library('upload', $config);
                    if ( ! $this->upload->do_upload('image_path'))
                    {
                        $error = array('error' => $this->upload->display_errors());
                    }
                    else
                    {
                        $img_data = $this->upload->data();
                        $image_path=$img_data['file_name'];
                    }
                }
                $image = array("image_path"=>$image_path,"type"=>"post","type_id"=>$post_id);
                $this->load->model("common_model");
                $image_id = $this->common_model->data_insert(" ".TBL_IMAGES." ",$image);
                
                /*  =======================================================  */
                $this->session->set_flashdata("message",'<div class="alert alert-success alert-dismissible" role="alert">
                                        <i class="fa fa-check"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Success!</strong> Your Post Add saved successfully...
                                    </div>');
   	        }
            $this->load->model("users_model");
            $data["users"] = $this->users_model->get_users(array("user_type"=>3));
            $this->load->view('admin/post/addpost',$data);
        }
        else
        {
            redirect('admin');
        }
    }
    
    public function listpost()
	{
	   if(_is_user_login($this)){
	       $data["error"] = "";
	       $data["active"] = " ".TBL_POSTS." ";
           if(_get_current_user_type_id($this)==0){
                $this->load->model("post_model");
                $data["post"] = $this->post_model->get_post($userid=0);
                $this->load->view('admin/post/listpost',$data);
           }
        }
        else
        {
            redirect('admin');
        }
    }
    
     
    public function listpost_rent()
	{
	   if(_is_user_login($this)){
	       $data["error"] = "";
	       $data["active"] = " ".TBL_POSTS." ";
           if(_get_current_user_type_id($this)==0){
                $this->load->model("post_model");
                $data["post"] = $this->post_model->get_post_rent($userid=0);
                $this->load->view('admin/post/listpost_rent',$data);
           }
        }
        else
        {
            redirect('admin');
        }
    }
    
    
     public function editpost($post_id)
     {
        if(_is_user_login($this))
        {
            $data = array("error"=>"");  
            $this->load->library('form_validation');
           /* $this->form_validation->set_rules('post_title', 'Post Title', 'trim|required');
            $this->form_validation->set_rules('post_description', 'Post Description', 'trim|required');
            $this->form_validation->set_rules('user_id', 'Select Name', 'trim|required');
            $this->form_validation->set_rules('visible_limit', 'Limit Visible', 'trim|required');
            $this->form_validation->set_rules('post_title', 'Post Title', 'trim|required');  */
            if ($this->form_validation->run() == FALSE)
        	{
                if($this->form_validation->error_string()!="")
                {
 			        $this->session->set_flashdata("message", '<div class="alert alert-warning alert-dismissible" role="alert">
                                        <i class="fa fa-warning"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                    </div>');
                }
       		}
        	else
        	{
                $post_date=date("Y-m-d m:i:s");
                $savepost = array(
                    "post_title"=>$this->input->post("post_title"),
                    "post_description"=>$this->input->post("post_description"),
                    "user_id"=>$this->input->post("user_id"),
                    "visible_limit"=>$this->input->post("visible_limit"),
                    "post_km"=>$this->input->post("post_km"),
                    "post_hand"=>$this->input->post("post_hand"),
                    "post_year"=>$this->input->post("post_year"),
                    "post_date"=>$post_date,
                    "post_image"=>$post_image,
                    "post_status"=>$this->input->post("post_status")
                    );
                    
                $this->load->model("common_model");       
                $this->db->update(" ".TBL_POSTS." ",$savepost,array("post_id"=>$post_id)); 
                
                 /* =================== Insert Image Table ====================================== */
                 $image_path = "";
                 if(isset( $_FILES["image_path"]) && $_FILES["image_path"]["size"] > 0)
                 {
                    $config['upload_path']          = './uploads/image/';
                    $config['allowed_types']        = 'gif|jpg|png|jpeg';
                    if(!is_dir($config['upload_path']))
                    {
                        mkdir($config['upload_path']);
                    }
                    $this->load->library('upload', $config);
                    if ( ! $this->upload->do_upload('image_path'))
                    {
                        $error = array('error' => $this->upload->display_errors());
                    }
                    else
                    {
                        $img_data = $this->upload->data();
                        $image_path=$img_data['file_name'];
                        $update_image = array("image_path"=>$image_path);
                
                        $this->load->model("common_model");
                        $this->common_model->data_update(" ".TBL_IMAGES." ",$update_image,array("type_id"=>$post_id,"type"=>"post"));   
                    }
                 }    
     /* ========================  Insert Image Table End  ======================================================= */ 
                $this->session->set_flashdata("message",'<div class="alert alert-success alert-dismissible" role="alert">
                                        <i class="fa fa-check"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Success!</strong> Your Business Details Upadete successfully...
                                    </div>');
                redirect('admin/listpost');
        	}
            $data["error"] = "";
            $this->load->model("post_model");
	        $data["setpost"] = $this->post_model->get_post_of_user_by_id($post_id);
            $data["postattribute"] = $this->post_model->get_post_of_user_by_id_attribute($post_id);
            $this->load->model("users_model");
            $data["users"] = $this->users_model->get_users(array("user_type"=>3));
            $this->load->model("post_model");
            $data["city"] = $this->post_model->get_post_by_user_id($post_id);
            $data["get_image"] = $this->post_model->get_post_by_image($post_id);
            $this->load->view('admin/post/editpost',$data);
        }
        else
        {
            redirect('admin');
        }
        
    }
    public function deletepost($post_id)
    {
        if(_is_user_login($this))
        {
            $data = array();
            $this->load->model("post_model");
            /*$post = $this->post_model->get_post_by_id($post_id);*/
            $post = $this->post_model->get_pos_by_id($post_id);
            if($post)
            {
                 $this->db->query("Delete from ".TBL_POSTS." where post_id = '".$post->post_id."'");
                    $this->db->query("Delete from ".TBL_IMAGES." where type_id = '".$post->post_id."'");
                /*$this->db->query("Delete from TBL_POSTS where 	post_id = '".$post->post_id."'");*/
                redirect("admin/listpost");
           }
        }
        else
        {
            redirect('admin');
        }
    }
/* ========= End Post Related Function  ============= */
/* ========= Image Related Function  ============= */
     public function listimage()
     {
        if(_is_user_login($this))
        {
            $data = array();
            $q = $this->db->query("select * from ".TBL_IMAGES." ");
            $data["getimage"] = $q->result();
            $this->load->view("admin/image/listimage",$data);
        }
    }
    function deleteimage($image_id)
    {
        if(_is_user_login($this))
        {
            $data = array();
            $this->load->model("image_model");
            $image  = $this->image_model->get_image_by_id($image_id);
            if($image)
            {
                unlink("./uploads/image/".$image->image_path);
                $this->db->query("Delete from ".TBL_IMAGES." where image_id = '".$image->image_id."'");
                redirect("admin/listimage");
            }
        }
    }   
/* ========= End Image Related Function  ============= */
/* ========== Area Management =============== */
        public function area_country(){
            if(_is_user_login($this)){
            if($_POST){
                $this->load->library('form_validation');
                $this->form_validation->set_rules('country_name', 'Country Name', 'trim|required');
                $this->form_validation->set_rules('currency', 'Currency', 'trim|required');
                
                
            
                if ($this->form_validation->run() == FALSE)
        		{
  		            if($this->form_validation->error_string()!=""){
        			     $this->session->set_flashdata("message", '<div class="alert alert-warning alert-dismissible" role="alert">
                                        <i class="fa fa-warning"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                    </div>');
                    }
        		}
        		else
        		{
  		            $array = array("country_name"=>$this->input->post("country_name"),
                      "currency"=>$this->input->post("currency"),
                      "status"=>"1");
                      $this->load->model("common_model");
                      $this->common_model->data_insert("area_country",$array); 
                      unset($_POST);  
                }   
            }
            
            $this->load->model("area_model");
            
            $data["countries"] = $this->area_model->get_countries();
            $this->load->view("area/countries",$data);
            }
        }
        public function country_edit($country_id){
            
            if(_is_user_login($this)){
            
            if($_POST){
                $this->load->library('form_validation');
                $this->form_validation->set_rules('country_name', 'Country Name', 'trim|required');
                $this->form_validation->set_rules('currency', 'Currency', 'trim|required');
                
                
            
                if ($this->form_validation->run() == FALSE)
        		{
  		            if($this->form_validation->error_string()!=""){
        			     $this->session->set_flashdata("message", '<div class="alert alert-warning alert-dismissible" role="alert">
                                        <i class="fa fa-warning"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                    </div>');
                    }
        		}
        		else
        		{
  		            $array = array("country_name"=>$this->input->post("country_name"),
                      "currency"=>$this->input->post("currency"),
                      "status"=>"1");
                      $this->load->model("common_model");
                      $this->common_model->data_update("area_country",$array,array("country_id"=>$country_id)); 
                      unset($_POST);  
                       redirect("admin/area_country");
                }   
            }
            
            $this->load->model("area_model");
            $this->load->model("area_model");
            $data["country"] = $this->area_model->get_country_id($country_id);
            
            $this->load->view("area/countries_edit",$data);
            
            }
        }
         public function country_delete($country_id){
            if(_is_user_login($this)){
                $q = $this->db->query("Delete from area_country where country_id = '".$country_id."'");
                redirect("admin/area_country");
            }
        }
        public function area_city(){
            
            if(_is_user_login($this)){
            
            if($_POST){
                $this->load->library('form_validation');
                $this->form_validation->set_rules('city_name', 'City Name', 'trim|required');
                $this->form_validation->set_rules('country_id', 'Country ID', 'trim|required');
                
                
                
            
                if ($this->form_validation->run() == FALSE)
        		{
  		            if($this->form_validation->error_string()!=""){
        			     $this->session->set_flashdata("message", '<div class="alert alert-warning alert-dismissible" role="alert">
                                        <i class="fa fa-warning"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                    </div>');
                    }
        		}
        		else
        		{
  		            $array = array("city_name"=>$this->input->post("city_name"),
                      "country_id"=>$this->input->post("country_id"),
                      
                      "status"=>"1");
                      $this->load->model("common_model");
                      $this->common_model->data_insert("area_city",$array); 
                      unset($_POST);  
                }   
            }
            
            $this->load->model("area_model");
            $data["countries"] = $this->area_model->get_countries();
            $data["cities"] = $this->area_model->get_cities();
            $this->load->view("area/cities",$data);
            
            }
        }
        public function city_edit($city_id){
            
            if(_is_user_login($this)){
            
            if($_POST){
                $this->load->library('form_validation');
                $this->form_validation->set_rules('city_name', 'City Name', 'trim|required');
                $this->form_validation->set_rules('country_id', 'Country ID', 'trim|required');
                
                
                
            
                if ($this->form_validation->run() == FALSE)
        		{
  		            if($this->form_validation->error_string()!=""){
        			     $this->session->set_flashdata("message", '<div class="alert alert-warning alert-dismissible" role="alert">
                                        <i class="fa fa-warning"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                    </div>');
                    }
        		}
        		else
        		{
  		            $array = array("city_name"=>$this->input->post("city_name"),
                      "country_id"=>$this->input->post("country_id"),
                      
                      "status"=>"1");
                      $this->load->model("common_model");
                      $this->common_model->data_update("area_city",$array,array("city_id"=>$city_id)); 
                      unset($_POST);  
                      redirect("admin/area_city");
                }   
            }
            
            $this->load->model("area_model");
            $data["countries"] = $this->area_model->get_countries();
            $data["city"] = $this->area_model->get_city_id($city_id);
            $this->load->view("area/city_edit",$data);
            
            }
        }
        public function city_delete($city_id){
            if(_is_user_login($this)){
                $q = $this->db->query("Delete from area_city where city_id = '".$city_id."'");
                redirect("admin/area_city");
            }
        }
        public function city_json(){
                      header('Content-type: text/json');
      
            $this->load->model("area_model");
            $result = $this->area_model->get_cities("1",$this->input->post("country_id")); 
            echo json_encode($result);
        }
        public function speciality_json(){
                     header('Content-type: text/json');
      
            $q = $this->db->query("Select DISTINCT service_title from business_services where service_title like '%".$this->input->get('term')."%'");
            $results = $q->result();
            $result = array();
            foreach($results as $row){
                $result[] = $row->service_title;
            }
            echo json_encode($result);
        }
/* ========== Area Management =============== */

/* =========  Slider Ares =================== */

 public function addslider()
	{
	   if(_is_user_login($this)){
	       
            $data["error"] = "";
            $data["active"] = "addslider";
            
            if(isset($_REQUEST["addslider"]))
            {
                $this->load->library('form_validation');
                $this->form_validation->set_rules('slider_title', 'Slider Title', 'trim|required');
                if (empty($_FILES['slider_img']['name']))
                {
                    $this->form_validation->set_rules('slider_img', 'Slider Image', 'required');
                }
                
                if ($this->form_validation->run() == FALSE)
        		{
        			  $data["error"] = '<div class="alert alert-warning alert-dismissible" role="alert">
                                        <i class="fa fa-warning"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                    </div>';
        		}
        		else
        		{
                    $add = array(
                                    "slider_title"=>$this->input->post("slider_title"),
                                    "slider_status"=>$this->input->post("slider_status"),
                                    "slider_url"=>$this->input->post("slider_url")
                                    );
                    
                        if($_FILES["slider_img"]["size"] > 0){
                            $config['upload_path']          = './uploads/admin/sliders/';
                            $config['allowed_types']        = 'gif|jpg|png|jpeg';
                            $this->load->library('upload', $config);
            
                            if ( ! $this->upload->do_upload('slider_img'))
                            {
                                    $error = array('error' => $this->upload->display_errors());
                            }
                            else
                            {
                                $img_data = $this->upload->data();
                                $add["slider_image"]=$img_data['file_name'];
                            }
                            
                       }
                       
                       $this->db->insert("slider",$add);
                    
                    $this->session->set_flashdata("success_req",'<div class="alert alert-success alert-dismissible" role="alert">
                                        <i class="fa fa-check"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Success!</strong> Your Slider added successfully...
                                    </div>');
                    redirect('admin/addslider');
               	}
            }
	   	$this->load->view('admin/slider/addslider',$data);
        }
        else
        {
            redirect('login');
        }
	}
 
     public function listslider()
	{
	   if(_is_user_login($this)){
	       $data["error"] = "";
	       $data["active"] = "listslider";
           $this->load->model("slider_model");
           $data["allslider"] = $this->slider_model->get_slider();
           $this->load->view('admin/slider/listslider',$data);
        }
        else
        {
            redirect('login');
        }
    }
     public function editslider($id)
	{
	   if(_is_user_login($this))
       {
            
            $this->load->model("slider_model");
           $data["slider"] = $this->slider_model->get_slider_by_id($id);
           
	        $data["error"] = "";
            $data["active"] = "listslider";
            if(isset($_REQUEST["saveslider"]))
            {
                $this->load->library('form_validation');
                $this->form_validation->set_rules('slider_title', 'Slider Title', 'trim|required');
               
                  if ($this->form_validation->run() == FALSE)
        		{
        			  $data["error"] = '<div class="alert alert-warning alert-dismissible" role="alert">
                                        <i class="fa fa-warning"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Warning!</strong> '.$this->form_validation->error_string().'
                                    </div>';
        		}
        		else
        		{
                    $this->load->model("slider_model");
                    $this->slider_model->edit_slider($id); 
                    $this->session->set_flashdata("success_req",'<div class="alert alert-success alert-dismissible" role="alert">
                                        <i class="fa fa-check"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Success!</strong> Your Slider saved successfully...
                                    </div>');
                    redirect('admin/listslider');
               	}
            }
	   	   $this->load->view('admin/slider/editslider',$data);
        }
        else
        {
            redirect('login');
        }
	}
     function deleteslider($id){
        $data = array();
            $this->load->model("slider_model");
            $slider  = $this->slider_model->get_slider_by_id($id);
           if($slider){
                $this->db->query("Delete from slider where id = '".$slider->id."'");
                unlink("uploads/sliders/".$slider->slider_image);
                redirect("admin/listslider");
           }
    }
 /*=================== End Slider =================== */
    /* Post review */
   public function listreview($id)
	{
	   if(_is_user_login($this)){
	       $data["error"] = "";
	       $data["active"] = "listcity";
           $this->load->model("review_model");
           $data["review"] = $this->review_model->get_review($id);
           $this->load->view('admin/review/listreview',$data);
        }
        else
        {
            redirect('login');
        }
    } 
    
     public function deletereview($id)
	{
	   if(_is_user_login($this)){
	        
            $this->db->delete("review",array("review_id"=>$id));
            $this->session->set_flashdata("success_req",'<div class="alert alert-success alert-dismissible" role="alert">
                                        <i class="fa fa-check"></i>
                                      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                      <strong>Success!</strong> Your item deleted successfully...
                                    </div>');
            redirect('admin/listbusiness');
        }
        else
        {
            redirect('login');
        }
    }
    
    public function manage_notification(){
        if(_is_user_login($this)){
             if(isset($_POST["type"]) && $_POST["type"] == "notification"){
                $this->load->library('form_validation');
                
                $this->form_validation->set_rules('subject', 'Subject', 'trim|required');
                $this->form_validation->set_rules('message', 'Message', 'trim|required');
                if ($this->form_validation->run() == FALSE) 
        		{
      		        if($this->form_validation->error_string()!="")
                    {
                        $this->session->set_flashdata("message", '<div class="alert alert-warning alert-dismissible" role="alert">
                            <i class="fa fa-warning"></i>
                            <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                            <strong>Warning!</strong> '.$this->form_validation->error_string().'
                            </div>');
                    }
        		}else
                {
                        $message = array("title"=>$this->input->post("subject"),
                        "message"=>$this->input->post("message"),"created_at"=>date("Y-m-d h:i:s"));
                         
                        $this->load->model("common_model");
                            $this->common_model->data_insert("notification",
                                array(
                                "title"=>$this->input->post("subject"),
                                "message"=>$this->input->post("message"), 
                                "created_at"=>date("Y-m-d h:i:s")));
                        $insertid = $this->db->insert_id();
                        
                         /* =================== Insert Image Table ====================================== */
                 $image_path = "";
                 if(isset( $_FILES["image_path"]) && $_FILES["image_path"]["size"] > 0)
                 {
                    $config['upload_path']          = './uploads/image/';
                    $config['allowed_types']        = 'gif|jpg|png|jpeg';
                    
                    if(!is_dir($config['upload_path']))
                    {
                        mkdir($config['upload_path']);
                    }
                    $this->load->library('upload', $config);
                    if ( ! $this->upload->do_upload('image_path'))
                    {
                        $error = array('error' => $this->upload->display_errors());
                    }
                    else
                    {
                        $img_data = $this->upload->data();
                        $image_path=$img_data['file_name'];
                        
                         $image = array("image_path"=>$image_path,"type"=>"notification","type_id"=>$insertid);
                          $message["image"]= $image_path;
                 $this->load->model("common_model");
                    $this->common_model->data_insert(" ".TBL_IMAGES." ",$image);  
                    }
                 }
                              
     /* ========================  Insert Image Table End  ======================================================= */
                        
                        $this->load->helper("gcm_helper");
                        $gcm = new GCM();
                            $result = $gcm->send_topics("/topics/carondeal",$message,"android");    
                   
                    $this->session->set_flashdata("message",'<div class="alert alert-success alert-dismissible" role="alert">
                                            <i class="fa fa-check"></i>
                                          <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                          <strong>Success!</strong> Your Attibute saved successfully...
                                        </div>');         
                }
                
            }
            
            
            
             $data["error"] = "";
            $this->load->model("notification_model");
            $data["notification"] = $this->notification_model->get_notification();
            
            $this->load->view("admin/notification/listnotification",$data);
        }
    }
     public function delete_notification($not_id){
            if(_is_user_login($this)){
                $this->db->query("Delete from notification where not_id = '".$not_id."'");
                redirect("admin/manage_notification");
                  }
    
    }    
}
