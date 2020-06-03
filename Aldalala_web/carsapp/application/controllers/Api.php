<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Api extends CI_Controller
{

    /**
     * Index Page for this controller.
     *
     * Maps to the following URL
     * 		http://example.com/index.php/api
     *	- or -
     * 		http://example.com/index.php/api/index
     *	- or -
     * Since this controller is set as the default controller in
     * config/routes.php, it's displayed at http://example.com/
     *
     * So any other public methods not prefixed with an underscore will
     * map to /index.php/api/<method_name>
     * @see http://codeigniter.com/user_guide/general/urls.html
     */
    public function __construct()
    {
        parent::__construct();
        // Your own constructor code
        $this->load->database();
        header('Content-type: text/json');

        $this->db->query("SET sql_mode = '' ");
        if ($this->input->post("user_id")) {
            $this->session->set_userdata(array("user_id"=>$this->input->post("user_id")));
        }
    }

    /**
     * BASE_URL + "/index.php/api/get_categories"
     * Display services of "Categories"
     * */
    public function get_categories()
    {
        $this->load->model("category_model");
        $categories = $this->category_model->get_categories_short(0, 0, $this) ;
        $data["responce"] = true;
        $data["data"] = $categories;
        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/get_sliders"
     * Display Photos of slider.
     * */
    public function get_sliders()
    {
        $q = $this->db->query("Select * from slider");
        $data["responce"] = true;
        $data["data"] = $q->result();
        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/get_rent_post"
     * Written by Mike.
     * */
    public function get_rent_post()
    {
        $page = $this->input->post("page");
        if (!empty($page)) {
            $page = $this->input->post("page");
        }
        $this->load->model("post_model");
        $postlist = $this->post_model->get_rent_postlist($page);
        $data["responce"] = true;
        $data["data"] = $postlist;
        echo json_encode($data);
    }
    /**
     * BASE_URL + "/index.php/api/get_post"
     * Display Post.
     * */
    public function get_post()
    {
        $page = $this->input->post("page");
        if (!empty($page)) {
            $page = $this->input->post("page");
        }
        $this->load->model("post_model");
        $postlist = $this->post_model->get_postlist($page);
        $data["responce"] = true;
        $data["data"] = $postlist;
        echo json_encode($data);
    }
    /**
    * BASE_URL + "/index.php/api/login"
    * Login User.
    * */
    public function login()
    {
        $username = $this->input->post("email");
        $password = $this->input->post("password");

        $q = $this->db->query("Select * from users where (
                              (user_email='".$username."'
                              and user_password='".md5($password)."')
                              OR   (user_phone='".$username."'
                              and user_password='".md5($password)."')
                              ) Limit 1 ");
        if ($q->num_rows() > 0) {
            $row = $q->row();
            if ($row->user_status == "0") {
                $data["responce"] = false;
                $data["error"] = 'Your account currently inactive.';
            } else {
                $data["responce"] = true;
                $data["data"] = $row;
            }
        } else {
            $data["responce"] = false;
            $data["error"] = 'User Not Found.';
        }
        echo json_encode($data);
    }
    /**
     * BASE_URL + "/index.php/api/login"
     * Login User.
     * */
    public function registration()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('full_name', 'Full Name', 'trim|required');
        $this->form_validation->set_rules('email', 'Email', 'trim|required|valid_email');
        $this->form_validation->set_rules('phone', 'Phone Number', 'trim|required');
        $this->form_validation->set_rules('password', 'Password', 'trim|required|min_length[4]');
        $this->form_validation->set_rules('cpassword', 'Confirm Password', 'required|matches[password]');
        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'field is required';
        } else {
            $q = $this->db->query("Select * from users where user_email='".$this->input->post("email")."' Limit 1 ");

            if ($q->num_rows() > 0) {
                $data["responce"] = false;
                $data["error"] = 'User already register.';
            } else {
                $addcat = array(
                                                 "user_fullname"=>$this->input->post("full_name"),
                                                "user_email"=>$this->input->post("email"),
                                                "user_phone"=>$this->input->post("phone"),
                                                "user_password"=>md5($this->input->post("password")),
                                                "user_city"=>$this->input->post("city"),
                                                "user_status"=>1,
                                                "user_type_id"=>1
                                                );

                $this->db->insert("users", $addcat);
                $data["responce"] = true;
                $data["data"] = 'Your registration successfully...';
            }
        }
        echo json_encode($data);
    }
    /**
     * BASE_URL + "/index.php/api/add_post"
     * Display Post.
     * */
    public function add_post()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('user_id', 'User ID', 'trim|required');
        $this->form_validation->set_rules('post_title', 'Post Title', 'trim|required');
        $this->form_validation->set_rules('post_price', 'Post Price', 'trim|required');
        $this->form_validation->set_rules('post_description', 'Post Description', 'trim|required');
        $this->form_validation->set_rules('post_year', 'Post Year', 'trim|required');
        $this->form_validation->set_rules('post_km', 'Post Km', 'trim|required');
        $this->form_validation->set_rules('post_hand', 'Post Hand', 'trim|required');
        $this->form_validation->set_rules('city_id', 'Post City', 'trim|required');


        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'field is required';
        } else {
            $post_date=date("Y-m-d m:i:s");
            $tbl_posts = array("user_id"=>$this->input->post("user_id"),
                                            "post_title"=>$this->input->post("post_title"),
                                            "post_price"=>$this->input->post("post_price"),
                                            "post_description"=>$this->input->post("post_description"),
                                            "post_year"=>$this->input->post("post_year"),
                                             "post_km"=>$this->input->post("post_km"),
                                            "post_hand"=>$this->input->post("post_hand"),
                                             "city_id"=>$this->input->post("city_id"),
                                            "post_date"=>$post_date,
                                            "post_status"=>"1");

            $this->db->insert("tbl_posts", $tbl_posts);

            $insertid = $this->db->insert_id();
            if ($insertid) {
                $data["responce"] = true;
                $data["data"] = $insertid;
            } else {
                $data["responce"] = false;
                $data["data"] = 'Opps! Your Post Not Add Successfully...';
            }
        }

        echo json_encode($data);
    }


    /**
     * BASE_URL + "/index.php/api/add_post_images"
     * Save All Post Images.
     * */
    public function add_post_images()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('post_id', 'post ID', 'trim|required');
        $this->form_validation->set_rules('post_face', 'post Face', 'trim|required');


        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'field is required';
        } else {
            $post_id = $this->input->post("post_id");
            $post_face = $this->input->post("post_face");

            $file_name = "";
            if (isset($_FILES["image"]) && $_FILES['image']['size'] > 0) {
                $path = './uploads/image';

                if (!file_exists($path)) {
                    mkdir($path);
                }
                $this->load->library("imagecomponent");

                $file_name_temp = md5(uniqid())."_".$_FILES['image']['name'];
                $file_name = $this->imagecomponent->upload_image_and_thumbnail('image', 680, 200, $path, 'crop', false, $file_name_temp);

                $tbl_posts = array("image_path"=>$file_name,"type"=>"post","post_face"=>$post_face,"type_id"=>$post_id);
                $this->load->model("common_model");
                $image_id = $this->common_model->data_insert(" ".TBL_IMAGES." ", $tbl_posts);

                //$this->db->insert("tbl_posts",$tbl_posts);
                if ($image_id != null) {
                    $data["responce"] = true;
                    $data["data"] = $file_name;

                    $this->load->model("common_model");
                    $this->common_model->data_update("tbl_posts", array(
                                            "post_status"=>"2"
                                            ), array("post_id"=>$this->input->post("post_id")));
                } else {
                    $data["responce"] = false;
                    $data["data"] = 'Opps! Your Car Picture Save Not Successfully...';
                }
            }
        }

        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/City"
     * Result : get all city.
     * */
    public function get_city()
    {
        $q = $this->db->query("Select ".TBL_CITY.".*, ".TBL_COUNTRY.".currency from ".TBL_CITY." inner join ".TBL_COUNTRY." on ".TBL_COUNTRY.".country_id = ".TBL_CITY.".country_id");
        $city = $q->result();
        $data["responce"] = true;
        $data["data"] = $city;
        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/update_profile_pic"
     * Result : get alUser Profile Picture Update.
     * */
    public function update_profile_pic()
    {
        $data = array();
        $this->load->library('form_validation');
        /* add users table validation */
        $this->form_validation->set_rules('user_id', 'User ID', 'trim|required');

        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'Warning! : '.strip_tags($this->form_validation->error_string());
        } else {
            $file_name = "";
            if (isset($_FILES["image"]) && $_FILES['image']['size'] > 0) {
                $path = './uploads/admin/profile';

                if (!file_exists($path)) {
                    mkdir($path);
                }
                $this->load->library("imagecomponent");

                $file_name_temp = md5(uniqid())."_".$_FILES['image']['name'];
                $file_name = $this->imagecomponent->upload_image_and_thumbnail('image', 680, 200, $path, 'crop', false, $file_name_temp);


                $this->load->model("common_model");
                $this->common_model->data_update("users", array(
                                            "user_image"=>$file_name
                                            ), array("user_id"=>$this->input->post("user_id")));


                $data["responce"] = true;
                $data["data"] = $file_name;
            } else {
                $data["responce"] = false;
                $data["data"] = 'Opps! Your Car Picture Save Not Successfully...';
            }
        }
        echo json_encode($data);
    }


    /**
     * BASE_URL + "/index.php/api/update_user_profile"
     * Result : Return Edit Profile Profile.
     * */

    public function update_user_profile()
    {
        $data = array();
        $this->load->library('form_validation');
        /* add users table validation */
        $this->form_validation->set_rules('user_id', 'User ID', 'trim|required');
        $this->form_validation->set_rules('user_fullname', 'Full Name', 'trim|required');
        $this->form_validation->set_rules('user_city', 'City', 'trim|required');
        $this->form_validation->set_rules('user_phone', 'Phone Number', 'trim|required');


        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'Warning! : '.strip_tags($this->form_validation->error_string());
        } else {
            $insert_array=  array(
                                            "user_fullname"=>$this->input->post("user_fullname"),
                                            "user_city"=>$this->input->post("user_city"),
                                            "user_phone"=>$this->input->post("user_phone")
                                            );

            $this->load->model("common_model");

            $this->common_model->data_update("users", $insert_array, array("user_id"=>$this->input->post("user_id")));

            $q = $this->db->query("Select * from `users` where(user_id='".$this->input->post('user_id')."' ) Limit 1");
            $row = $q->row();
            $data["responce"] = true;
            $data["data"] = array("user_id"=>$row->user_id,"user_fullname"=>$row->user_fullname,"user_email"=>$row->user_email,"user_phone"=>$row->user_phone,"user_image"=>$row->user_image,"user_city"=>$row->user_city,"user_country"=>$row->user_country,"user_state"=>$row->user_state) ;
        }

        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/change_password"
     * Result : User Change Password.
     * */

    public function change_password()
    {
        $data = array();
        $this->load->library('form_validation');
        /* add users table validation */
        $this->form_validation->set_rules('user_id', 'User ID', 'trim|required');
        $this->form_validation->set_rules('current_password', 'Current Password', 'trim|required');
        $this->form_validation->set_rules('new_password', 'New Password', 'trim|required');

        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = strip_tags($this->form_validation->error_string());
        } else {
            $this->load->model("common_model");
            $q = $this->db->query("select * from users where user_id = '".$this->input->post("user_id")."' and  user_password = '".md5($this->input->post("current_password"))."' limit 1");
            $user = $q->row();

            if (!empty($user)) {
                $this->common_model->data_update("users", array(
                                            "user_password"=>md5($this->input->post("new_password"))
                                            ), array("user_id"=>$user->user_id));

                $data["responce"] = true;
                $data["data"] = 'Your password change successfully...';
            } else {
                $data["responce"] = false;
                $data["error"] = 'Current password do not match';
            }
        }

        echo json_encode($data);
    }


    /**
     * BASE_URL + "/index.php/api/register_fcm"
     * Result : FCM Register.
     * */
    public function register_fcm()
    {
        $data = array();
        $this->load->library('form_validation');
        $this->form_validation->set_rules('user_id', 'User ID', 'trim|required');
        $this->form_validation->set_rules('token', 'Token', 'trim|required');
        $this->form_validation->set_rules('device', 'Device', 'trim|required');
        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = $this->form_validation->error_string();
        } else {
            $device = $this->input->post("device");
            $token = $this->input->post("token");
            $user_id = $this->input->post("user_id");

            $field = "";
            if ($device=="android") {
                $field = "user_gcm_code";
            } elseif ($device=="ios") {
                $field = "user_ios_token";
            }
            if ($field!="") {
                $this->db->query("update users set ".$field." = '".$token."' where user_id = '".$user_id."'");
                $data["responce"] = true;
            } else {
                $data["responce"] = false;
                $data["error"] = "Device type is not set";
            }
        }
        echo json_encode($data);
    }

    /**
    * BASE_URL + "/index.php/api/get_attribute"
    * Save All Post Images.
    * */
    public function get_attribute()
    {
        $data = array();
        $this->load->model("attribute_model");
        $attribute = $this->attribute_model->get_attribute_with_group();

        $data["responce"] = true;
        $data["data"] = $attribute;

        echo json_encode($data);
    }

    /**
    * BASE_URL + "/index.php/api/get_post_by_city"
    * Display Post user location in app Home page bottom.
    * */
    public function get_post_by_city()
    {
        $city_id = $this->input->post("city_id");
        $this->load->model("post_model");
        $postlistcity = $this->post_model->get_postlist_by_city($city_id, 6);
        $data["responce"] = true;
        $data["data"] = $postlistcity;

        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/get_post_by_user_id"
     *  Display Get Post by user in app.
     * */
    public function get_post_by_user_id()
    {
        $data = array();
        $this->load->library('form_validation');
        /* add users table validation */
        $this->form_validation->set_rules('user_id', 'User ID', 'trim|required');


        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = strip_tags($this->form_validation->error_string());
        } else {
            $this->load->model("post_model");
            $postlistcity = $this->post_model->get_post_by_user_id($this->input->post("user_id"));
            $data["responce"] = true;
            $data["data"] = $postlistcity;
        }
        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/delete_post_by_id"
     * Display Delete post by ID.
     * */
    public function delete_post_by_id()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('post_id', 'User ID', 'trim|required');

        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'field is required';
        } else {
            $this->db->delete("tbl_posts", array("post_id"=>$this->input->post("post_id")));

            $data["responce"] = true;
            $data["data"] = 'Your Post deleted successfully...';
        }
        echo json_encode($data);
    }
    /**
    * BASE_URL + "/index.php/api/edit_add_post_description"
    * Display edit add post description.
    * */
    public function edit_add_post_description()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('post_id', 'Post ID', 'trim|required');
        $this->form_validation->set_rules('post_title', 'Post Title', 'trim|required');
        $this->form_validation->set_rules('post_price', 'Post Price', 'trim|required');
        $this->form_validation->set_rules('post_description', 'Post Description', 'trim|required');
        $this->form_validation->set_rules('post_year', 'Post Year', 'trim|required');
        $this->form_validation->set_rules('post_km', 'Post Km', 'trim|required');
        $this->form_validation->set_rules('post_hand', 'Post Hand', 'trim|required');
        $this->form_validation->set_rules('city_id', 'City ID', 'trim|required');

        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'field is required';
        } else {
            $tbl_posts = array("post_title"=>$this->input->post("post_title"),
                                            "post_price"=>$this->input->post("post_price"),
                                            "post_description"=>$this->input->post("post_description"),
                                            "post_year"=>$this->input->post("post_year"),
                                             "post_km"=>$this->input->post("post_km"),
                                            "post_hand"=>$this->input->post("post_hand"),
                                              "city_id"=>$this->input->post("city_id") );


            $this->db->update("tbl_posts", $tbl_posts, array("post_id"=>$this->input->post("post_id")));
            $data["responce"] = true;
            $data["data"] = 'Your Post Description Edit Successfully...';
        }

        echo json_encode($data);
    }

    /**
    * BASE_URL + "/index.php/api/update_post_image"
    * Display Update add post image.
    * */
    public function update_post_image()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('post_id', 'Post ID', 'trim|required');
        $this->form_validation->set_rules('post_face', 'Post Face', 'trim|required');

        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'Warning! : '.strip_tags($this->form_validation->error_string());
        } else {
            $file_name = "";
            if (isset($_FILES["image"]) && $_FILES['image']['size'] > 0) {
                $path = './uploads/image';

                if (!file_exists($path)) {
                    mkdir($path);
                }
                $this->load->library("imagecomponent");

                $file_name_temp = md5(uniqid())."_".$_FILES['image']['name'];
                $file_name = $this->imagecomponent->upload_image_and_thumbnail('image', 680, 200, $path, 'crop', false, $file_name_temp);

                $insert_array=  array("image_path"=>$file_name
                                            );
                $this->load->model("common_model");
                $this->common_model->data_update("tbl_images", $insert_array, array("type_id"=>$this->input->post("post_id"),"post_face"=>$this->input->post("post_face")));

                $data["responce"] = true;
                $data["data"] = $file_name;
            } else {
                $data["responce"] = false;
                $data["data"] = 'Opps! Your Car Picture Save Not Successfully...';
            }
        }
        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/get_image_by_post_id"
     * Display Post user location in app Home page bottom.
     * */
    public function get_image_by_post_id()
    {
        $post_id = $this->input->post("post_id");
        $this->load->model("post_model");
        $postlistcity = $this->post_model->get_all_image_by_post_id($post_id);
        $data["responce"] = true;
        $data["data"] = $postlistcity;

        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/set_post_attribute"
     * insert add Post attribute.
     * */
    public function set_post_attribute()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('post_id', 'Post ID', 'trim|required');
        $this->form_validation->set_rules('data', 'data', 'trim|required');
        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'Warning! : '.strip_tags($this->form_validation->error_string());
        } else {
            $post_id = $this->input->post("post_id");
            $data_post = $this->input->post("data");
            $data_array = json_decode($data_post);

            foreach ($data_array as $dt) {
                $array = array("post_id"=>$post_id,
                        "attr_group_id"=>$dt->attr_group_id,
                        "attr_id"=>$dt->attr_id
                        );
                $this->load->model("common_model");
                $this->common_model->data_insert("tbl_post_attributes", $array);
            }

            $this->common_model->data_update("tbl_posts", array(
                                            "post_status"=>"3"
                                            ), array("post_id"=>$this->input->post("post_id")));


            $data["responce"] = true;
            $data["data"] = "Your car attribute save successfully";
        }
        echo json_encode($data);
    }
    /**
     * BASE_URL + "/index.php/api/set_post_attribute"
     * insert add Post attribute.
     * */
    public function get_attribute_by_post()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('post_id', 'Post ID', 'trim|required');

        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'Warning! : '.strip_tags($this->form_validation->error_string());
        } else {
            $this->load->model("post_model");
            $postlistcity = $this->post_model->get_post_by_attribute($this->input->post("post_id"));
            $data["responce"] = true;
            $data["data"] = $postlistcity;
        }
        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/update_post_attribute"
     * Update add Post attribute.
     * */
    public function update_post_attribute()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('post_id', 'Post ID', 'trim|required');
        $this->form_validation->set_rules('data', 'data', 'trim|required');
        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'Warning! : '.strip_tags($this->form_validation->error_string());
        } else {
            $post_id = $this->input->post("post_id");

            $this->db->delete("tbl_post_attributes", array("post_id"=>$post_id));

            $data_post = $this->input->post("data");
            $data_array = json_decode($data_post);
            $this->load->model("common_model");
            foreach ($data_array as $dt) {
                $array = array("post_id"=>$post_id,
                        "attr_group_id"=>$dt->attr_group_id,
                        "attr_id"=>$dt->attr_id
                        );

                $this->common_model->data_insert("tbl_post_attributes", $array);
            }
            $this->common_model->data_update("tbl_posts", array(
                                            "post_status"=>"3"
                                            ), array("post_id"=>$this->input->post("post_id")));
            $data["responce"] = true;
            $data["data"] = "Your car attribute Update successfully";
        }
        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/get_review"
     * Get Review.
     * */
    public function get_review()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('post_id', 'post_id', 'trim|required');

        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'field ID is required';
        } else {
            $this->load->model("common_model");
            $q = $this->db->query("Select review.*, users.user_fullname, users.user_image from review inner join users on users.user_id = review.user_id where review.post_id= '".$this->input->post("post_id")."' order by review.on_date desc");

            $data["responce"] = true;
            $data["data"] = $q->result();
        }
        echo json_encode($data);
    }
    /* end list reviwe */

    /**
      * BASE_URL + "/index.php/api/add_review"
      * Add Review.
      * */
    public function add_review()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('comment', 'comment', 'trim|required');
        $this->form_validation->set_rules('rating', 'Rating', 'trim|required');
        $this->form_validation->set_rules('user_id', 'User ID', 'trim|required');
        $this->form_validation->set_rules('post_id', 'Post ID', 'trim|required');
        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'field is required';
        } else {
            $add = array("comment"=>strip_tags($this->input->post("comment")),"rating"=>$this->input->post("rating"),"user_id"=>$this->input->post('user_id'),"post_id"=>$this->input->post("post_id"),"status"=>1);

            $this->load->model("common_model");
            $this->common_model->data_insert("review", $add);


            $data["responce"] = true;
            $data["data"] = 'Your Rating Send Successfully...';
        }
        echo json_encode($data);
    }
    /**
    * BASE_URL + "/index.php/api/delete_post_by_id"
    * Display Delete post by ID.
    * */
    public function delete_review()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('review_id', 'Review ID', 'trim|required');

        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'field is required';
        } else {
            $this->db->delete("review", array("review_id"=>$this->input->post("review_id")));

            $data["responce"] = true;
            $data["data"] = 'Your Review deleted successfully...';
        }
        echo json_encode($data);
    }

    /*  end add review */
    /**
    * BASE_URL + "/index.php/api/get_join_user"
    * Get Join User
    * */
    public function get_join_user()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('post_user_id', 'post_user_id', 'trim|required');
        $this->form_validation->set_rules('login_user_id', 'login_user_id', 'trim|required');
        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'field is required';
        } else {
            $this->load->model("common_model");
            $q = $this->db->query("Select * from join_chat where
                      (user_1 = '".$this->input->post("login_user_id")."' and user_2 = '".$this->input->post("post_user_id")."') or
                      (user_1 = '".$this->input->post("post_user_id")."' and user_2 = '".$this->input->post("login_user_id")."')");
            //$row = $q->row();
            $row = $q->result();
            if (count($row) > 0) {
                $data["responce"] = true;
                //$data["data"] = $row->join_id;
                $data["data"] = $row[0]->join_id;
            } else {
                $add_chat = array(
                                   "user_1"=>$this->input->post("post_user_id"),
                                   "user_2"=>$this->input->post("login_user_id")
                                   );

                $this->db->insert("join_chat", $add_chat);

                $insertid = $this->db->insert_id();
                if ($insertid) {
                    $data["responce"] = true;
                    $data["data"] = $insertid;
                }
            }
        }
        echo json_encode($data);
    }
    /**
     * BASE_URL + "/index.php/api/get_review"
     * Get Review.
     * */
    public function get_join_data()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('join_id', 'join_id', 'trim|required');

        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'join id is required';
        } else {
            $this->load->model("common_model");
            $q = $this->db->query("Select * from chat where join_id= '".$this->input->post("join_id")."' ORDER BY created_date ASC");

            $data["responce"] = true;
            $data["data"] = $q->result();
        }
        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/send_chat_data"
     * Send Chat Data.
     * */
    public function send_chat_data()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('join_id', 'join_id', 'trim|required');
        $this->form_validation->set_rules('sender_id', 'sender_id', 'trim|required');
        $this->form_validation->set_rules('message', 'message', 'trim|required');

        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'join id is required';
        } else {
            $post_date=date("Y-m-d m:i:s");
            $send_chat = array(
                                   "join_id"=>$this->input->post("join_id"),
                                   "sender_id"=>$this->input->post("sender_id"),
                                   "message"=>$this->input->post("message"),
                                   "created_date"=>$post_date
                                   );

            $this->db->insert("chat", $send_chat);
            $insertid = $this->db->insert_id();

            $q = $this->db->query("Select * from join_chat where join_id= '".$this->input->post("join_id")."' limit 1");
            $recever_id = 0;
            $row = $q->row();
            if ($row->user_1 == $this->input->post("sender_id")) {
                $recever_id = $row->user_2;
            } else {
                $recever_id = $row->user_1;
            }
            $q2 = $this->db->query("Select * from chat where chat_id= '".$insertid."' limit 1");


            $chat = $q2->row();
            $data["responce"] = true;
            $data["data"] = $chat;



            $q_fcm = $this->db->query("Select * from users where user_id= '".$recever_id."' limit 1");
            $row_fcm = $q_fcm->row();

            $registatoin_ids =$row_fcm->user_gcm_code;


            $message["title"] = $this->config->item('app_name');
            $message["message"] = $this->input->post("message");
            $message["image"] = "";
            $message["created_at"] = date("Y-m-d h:i:s");
            $message["obj"] = $chat;

            $this->load->helper('gcm_helper');
            $gcm = new GCM();
            //$result = $gcm->send_topics("/topics/rabbitapp",$message ,"ios");

            $result = $gcm->send_notification(array($registatoin_ids), $message, "android");
        }
        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/get_chat_data_by_user"
     *Get Chat Data By User ID
     * */
    public function get_chat_data_by_user()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('user_id', 'user_id', 'trim|required');

        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'user id is required';
        } else {
            $this->load->model("common_model");
            $q = $this->db->query("Select join_chat.*, u1.user_fullname as user_1_fullname, u1.user_image as user_1_image, u2.user_fullname as user_2_fullname, u2.user_image as user_2_image from join_chat
                 inner join users as u1 on u1.user_id = join_chat.user_1
                 inner join users as u2 on u2.user_id = join_chat.user_2 where
                      (user_1 = '".$this->input->post("user_id")."' or user_2 = '".$this->input->post("user_id")."')");

            $data["responce"] = true;
            $data["data"] = $q->result();
        }
        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/set_bookmark"
     *Set Bookmark
     * */

    public function set_bookmark()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_rules('user_id', 'user_id', 'trim|required');
        $this->form_validation->set_rules('post_id', 'post_id', 'trim|required');
        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = $this->form_validation->error_string();
        } else {
            $is_like = false;
            $q = $this->db->query("Select * from user_bookmarks where user_id = '".$this->input->post("user_id")."' and post_id = '".$this->input->post("post_id")."' limit 1");
            $row = $q->row();
            if ($row) {
                $this->db->query("Delete from user_bookmarks where user_id = '".$this->input->post("user_id")."' and post_id = '".$this->input->post("post_id")."' ");
                $is_like = false;
            } else {
                $this->db->query("Insert into user_bookmarks values('".$this->input->post("user_id")."','".$this->input->post("post_id")."') ON DUPLICATE KEY UPDATE user_id = '".$this->input->post("user_id")."'");
                $is_like = true;
            }


            $data["responce"] = $is_like;
        }
        //$data["error"] = $_POST;
        echo json_encode($data);
    }

    /**
     * BASE_URL + "/index.php/api/get_bookmarklist"
     *Get Book Mark List
     * */
    public function get_bookmarklist()
    {
        $post_id = $this->input->post("post_id");
        // bus_ids = 5,87,5,7,10
        //$bus_ids = explode(",",$bus_ids);
        //$bus_ids = implode(",",$bus_ids);
        /**  echo "Select tbl_posts.*,users.user_email,users.user_fullname,area_city.city_name,post_img.image_path
 FROM tbl_posts
 inner join users on users.user_id = tbl_posts.user_id
 inner join area_city on area_city.city_id = tbl_posts.city_id
 left outer join (select image_path,type_id from  ".TBL_IMAGES." where ".TBL_IMAGES.".type = 'post' limit 1) as post_img on post_img.type_id = `tbl_posts`.`post_id`
 where tbl_posts.post_id in (".$post_id.")"; **/



        $q = $this->db->query("Select tbl_posts.*,area_country.currency,users.user_email,users.user_fullname,area_city.city_name,post_img.image_path
 FROM tbl_posts
 inner join users on users.user_id = tbl_posts.user_id
 inner join area_city on area_city.city_id = tbl_posts.city_id
  inner join area_country on area_country.country_id = area_city.country_id
 left outer join (select image_path,type_id from  ".TBL_IMAGES." where ".TBL_IMAGES.".type = 'post' group by type_id) as post_img on post_img.type_id = `tbl_posts`.`post_id`
 where tbl_posts.post_id in (".$post_id.")");

        $data["responce"] = true;
        $data["data"] = $q->result();
        echo json_encode($data);
    }

    public function test_fcm()
    {
        $message["title"] = "test";
        $message["message"] = "Design No id added as new";
        $message["image"] = "";
        $message["created_at"] = date("Y-m-d h:i:s");

        $this->load->helper('gcm_helper');
        $gcm = new GCM();
        $result = $gcm->send_notification(array("e2Em1PIZ6rU:APA91bHeepLr4Um6Gm3s7zrlCahzc7cZZl-6raQPKqJGU8PXa6oe1C2Kiuy1s0WwIbX0O6RAFpGPQFXjUpDluGOnE7V3EVMwCnWUXuQ75pXV-k3pmhwJYdSx0nW-mJpl8ZofKDjSPVKR"), $message, "android");
        //  $result = $gcm->send_topics("/topics/rabbitapp",$message ,"ios");
        print_r($result);
    }

    /* about us */
    public function aboutus()
    {
        $q = $this->db->query("select * from `pageapp` where id=1");


        $data["responce"] = true;
        $data['data'] = $q->result();


        echo json_encode($data);
    }
    /* end about us */
    /* Terms And Condition*/
    public function terms()
    {
        $q = $this->db->query("select * from `pageapp` where id=2");


        $data["responce"] = true;
        $data['data'] = $q->result();


        echo json_encode($data);
    }
    /* end  Terms And Condition */
    /**
      * BASE_URL + "/index.php/api/get_post_search"
      * Get Post Search
      * */
    public function get_post_search()
    {
        $searchtext= $this->input->post("search");
        $city_id = $this->input->post("city_id");

        $this->load->model("post_model");
        $post_list = $this->post_model->get_post_by_search($searchtext, $city_id) ;

        $data["responce"] = true;
        $data["data"] = $post_list;
        echo json_encode($data);
    }

    public function get_post_search_brand()
    {
        $searchtext= $this->input->post("search");
        $city_id = $this->input->post("city_id");

        $this->load->model("post_model");
        $post_list = $this->post_model->get_post_by_search_brand($searchtext, $city_id) ;

        $data["responce"] = true;
        $data["data"] = $post_list;
        echo json_encode($data);
    }

    public function get_post_search_year()
    {
        $searchtext= $this->input->post("search");
        $city_id = $this->input->post("city_id");

        $this->load->model("post_model");
        $post_list = $this->post_model->get_post_by_search_year($searchtext, $city_id) ;

        $data["responce"] = true;
        $data["data"] = $post_list;
        echo json_encode($data);
    }

    /**
    * BASE_URL + "/index.php/api/forgot_password"
    * Forgot Password
    * */
    public function forgot_password()
    {
        $data = array();
        $this->load->library('form_validation');
        $this->form_validation->set_rules('email', 'Email', 'trim|required');
        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'Warning! : '.strip_tags($this->form_validation->error_string());
        } else {
            $request = $this->db->query("Select * from users where user_email = '".$this->input->post("email")."' limit 1");
            if ($request->num_rows() > 0) {
                $user = $request->row();
                $token = uniqid(uniqid());
                $this->db->update("users", array("varified_token"=>$token), array("user_id"=>$user->user_id));
                $this->load->library('email');
                // $this->email->from($this->config->item('default_email'), $this->config->item('email_host'));

                $email = $user->user_email;
                $name = $user->user_fullname;
                $return = $this->send_email_verified_mail($email, $token, $name);



                if (!$return) {
                    $data["responce"] = false;
                    $data["data"] = 'Warning! : Something is wrong with system to send mail.';
                } else {
                    $data["responce"] = true;
                    $data["data"] = 'Success! : Recovery mail sent to your email address please verify link.';
                }
            } else {
                $data["responce"] = false;
                $data["data"] = 'Warning! : No user found with this email.';
            }
        }
        echo json_encode($data);
    }


    public function send_email_verified_mail($email, $token, $name)
    {
        //$message = $this->load->view('emails/email_verify',array("name"=>$name,"active_link"=>site_url("users/verify_email?email=".$email."&token=".$token)),TRUE);



        $this->email->from("aldalalatechgmail.com", "Aldalala Tech");
        $list = array($email);
        $this->email->to($list);
        $this->email->reply_to("aldalalatechgmail.com", "Car On Deal");
        $this->email->subject('Forgot password request');
        $this->email->message("Hi ".$name." \n Your password forgot request is accepted plase visit following link to change your password. \n
                                ".site_url("users/modify_password/".$token)."
                                ");
        return $this->email->send();
    }
    /* End Forgot Password */
    public function get_notificationlist()
    {
        $this->load->library('form_validation');

        $this->form_validation->set_rules('user_id', 'User ID', 'trim|required');
        if ($this->form_validation->run() == false) {
            $data["responce"] = false;
            $data["error"] = 'User ID field is required';
        } else {
            $q = $this->db->query("Select notification.*,post_img.image_path from notification
         left outer join (select image_path,type_id from tbl_images where tbl_images.type = 'notification' group by type_id) as post_img on post_img.type_id = `notification`.`not_id`");
            $allnoti = $q->result();

            $data["responce"] = true;
            $data["data"] = $allnoti;
        }
        echo json_encode($data);
    }
}
