<?php
class Post_model extends CI_Model
{
    public function get_postlist($page="")
    {
        $limit = "";
        if ($page != "") {
            $from = $page * 5;
            $limit .=" limit ".$from.", 5";
        }


        $q = $this->db->query("Select ".TBL_POSTS.".*,".TBL_COUNTRY.".currency,".TBL_USERS.".user_email,".TBL_USERS.".user_fullname,".TBL_USERS.".user_phone,post_img.image_path,ifnull(".TBL_POSTS.".post_image,post_img.image_path) as post_image,".TBL_CITY.".city_name from ".TBL_POSTS."
           inner join ".TBL_CITY." on ".TBL_CITY.".city_id = ".TBL_POSTS.".city_id
             inner join ".TBL_COUNTRY." on ".TBL_COUNTRY.".country_id = ".TBL_CITY.".country_id
            inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id
           left outer join (select image_path,type_id from  ".TBL_IMAGES." where ".TBL_IMAGES.".type = 'post' group by type_id) as post_img on post_img.type_id = `tbl_posts`.`post_id`
           where  (".TBL_POSTS." .post_title <> 'Rent' ) AND (".TBL_POSTS." .post_title <> 'تأجير' ) order by ".TBL_POSTS.".post_id DESC $limit");
        return $q->result();
    }

    public function get_rent_postlist($page="")
    {
        $limit = "";
        if ($page != "") {
            $from = $page * 5;
            $limit .=" limit ".$from.", 5";
        }

        $q = $this->db->query("Select ".TBL_POSTS.".*,".TBL_COUNTRY.".currency,".TBL_USERS.".user_email,".TBL_USERS.".user_fullname,".TBL_USERS.".user_phone,post_img.image_path,ifnull(".TBL_POSTS.".post_image,post_img.image_path) as post_image,".TBL_CITY.".city_name from ".TBL_POSTS."
           inner join ".TBL_CITY." on ".TBL_CITY.".city_id = ".TBL_POSTS.".city_id
             inner join ".TBL_COUNTRY." on ".TBL_COUNTRY.".country_id = ".TBL_CITY.".country_id
            inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id
           left outer join (select image_path,type_id from  ".TBL_IMAGES." where ".TBL_IMAGES.".type = 'post' group by type_id) as post_img on post_img.type_id = `tbl_posts`.`post_id`
           where  ((".TBL_POSTS.".post_status = '3' and ".TBL_POSTS." .post_title = 'Rent' ) OR (".TBL_POSTS.".post_status = '3' and ".TBL_POSTS." .post_title = 'تأجير' )) order by ".TBL_POSTS.".post_id DESC $limit");
        return $q->result();
    }

    public function get_postlist_by_city($city_id="", $number_row="")
    {
        $limit="";
        $filter = "";

        if ($city_id!="") {
            $filter.=" and ".TBL_USERS.".user_city = '".$city_id."' ";
        }
        if ($number_row!="") {
            $limit .=" limit 0,".$number_row;
        }

        $q = $this->db->query("Select ".TBL_POSTS.".*,".TBL_COUNTRY.".currency,".TBL_USERS.".user_email,".TBL_USERS.".user_fullname,".TBL_USERS.".user_phone,post_img.image_path,ifnull(".TBL_POSTS.".post_image,post_img.image_path) as post_image,".TBL_USERS.".user_city,".TBL_CITY.".city_name from ".TBL_POSTS."
          inner join ".TBL_CITY." on ".TBL_CITY.".city_id = ".TBL_POSTS.".city_id
          inner join ".TBL_COUNTRY." on ".TBL_COUNTRY.".country_id = ".TBL_CITY.".country_id
        left outer join (select image_path,type_id from  ".TBL_IMAGES." where ".TBL_IMAGES.".type = 'post' group by type_id) as post_img on post_img.type_id = `tbl_posts`.`post_id`
        inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id where  ((".TBL_POSTS.".post_status = '3' and ".TBL_POSTS." .post_title <> 'Rent' ) AND (".TBL_POSTS.".post_status = '3' and ".TBL_POSTS." .post_title <> 'تأجير' ))"
        .$filter." ".$limit);
        $city_data = $q->result();
        if (empty($city_data)) {
            $q = $this->db->query("Select ".TBL_POSTS.".*,".TBL_COUNTRY.".currency,".TBL_USERS.".user_email,".TBL_USERS.".user_fullname,".TBL_USERS.".user_phone,post_img.image_path,ifnull(".TBL_POSTS.".post_image,post_img.image_path) as post_image,".TBL_USERS.".user_city,".TBL_CITY.".city_name from ".TBL_POSTS."
          inner join ".TBL_CITY." on ".TBL_CITY.".city_id = ".TBL_POSTS.".city_id
          inner join ".TBL_COUNTRY." on ".TBL_COUNTRY.".country_id = ".TBL_CITY.".country_id
        left outer join (select image_path,type_id from  ".TBL_IMAGES." where ".TBL_IMAGES.".type = 'post' group by type_id) as post_img on post_img.type_id = `tbl_posts`.`post_id`
        inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id where  (".TBL_POSTS.".post_status = '3' and ".TBL_POSTS." .post_title <> 'Rent' ) AND (".TBL_POSTS.".post_status = '3' and ".TBL_POSTS." .post_title <> 'تأجير' ) order by ".TBL_POSTS.".post_id DESC".$limit);
            $city_data = $q->result();
        }
        return $city_data;
    }
    
    public function get_post_by_search($searchtext="", $city_id="")
    {
        $filter="";

        if ($city_id!="") {
            $filter.=" and tbl_posts.city_id = ".$city_id;
        }

        if ($searchtext!="") {
            $filter .=" and (tbl_posts.post_title like '%".$searchtext."%'
            or tbl_posts.post_description like '%".$searchtext."%'
            or tbl_posts.post_year like '%".$searchtext."%'
            ) ";
        }

        echo "";

        $q = $this->db->query("Select ".TBL_POSTS.".*,".TBL_COUNTRY.".currency,".TBL_USERS.".user_email,".TBL_USERS.".user_fullname,".TBL_USERS.".user_phone,post_img.image_path,".TBL_CITY.".city_name from ".TBL_POSTS."
            inner join ".TBL_CITY." on ".TBL_CITY.".city_id = ".TBL_POSTS.".city_id
            inner join ".TBL_COUNTRY." on ".TBL_COUNTRY.".country_id = ".TBL_CITY.".country_id
            inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id
            left outer join (select image_path,type_id from  ".TBL_IMAGES." where ".TBL_IMAGES.".type = 'post' group by type_id) as post_img on post_img.type_id = `tbl_posts`.`post_id`
            where ".TBL_POSTS.".post_status = '3'".$filter);

        return $q->result();
    }

    
    public function get_post_by_search_year($searchtext="", $city_id="")
    {
        $filter="";

        if ($city_id!="") {
            $filter.=" and tbl_posts.city_id = ".$city_id;
        }

        if ($searchtext!="") {
            $filter .=" and (tbl_posts.post_title like '%".$searchtext."%'
            or tbl_posts.post_year like '%".$searchtext."%'
            ) ";
        }

        echo "";

        $q = $this->db->query("Select ".TBL_POSTS.".*,".TBL_COUNTRY.".currency,".TBL_USERS.".user_email,".TBL_USERS.".user_fullname,".TBL_USERS.".user_phone,post_img.image_path,".TBL_CITY.".city_name from ".TBL_POSTS."
            inner join ".TBL_CITY." on ".TBL_CITY.".city_id = ".TBL_POSTS.".city_id
            inner join ".TBL_COUNTRY." on ".TBL_COUNTRY.".country_id = ".TBL_CITY.".country_id
            inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id
            left outer join (select image_path,type_id from  ".TBL_IMAGES." where ".TBL_IMAGES.".type = 'post' group by type_id) as post_img on post_img.type_id = `tbl_posts`.`post_id`
            where ".TBL_POSTS.".post_status = '3'".$filter);

        return $q->result();
    }

 
    
    public function get_post_by_search_brand($searchtext="", $city_id="")
    {
        $filter="";

        if ($city_id!="") {
            $filter.=" and tbl_posts.city_id = ".$city_id;
        }

        if ($searchtext!="") {
            $filter .=" and (tbl_attributes.attr_rolle like '%".$searchtext."%'
            and tbl_attributes.attr_group_id = '22'
            or tbl_posts.post_year like '%".$searchtext."%'
            or tbl_posts.post_title like '%".$searchtext."%'
            or tbl_posts.post_description like '%".$searchtext."%'
            ) ";
        }

        echo "";

        $q = $this->db->query("Select ".TBL_POSTS.".*,".TBL_COUNTRY.".currency,".TBL_USERS.".user_email,".TBL_USERS.".user_fullname,".TBL_USERS.".user_phone,post_img.image_path,".TBL_CITY.".city_name from ".TBL_POSTS."
            inner join ".TBL_CITY." on ".TBL_CITY.".city_id = ".TBL_POSTS.".city_id
            inner join ".TBL_COUNTRY." on ".TBL_COUNTRY.".country_id = ".TBL_CITY.".country_id
            inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id
            inner join ".TBL_POST_ATTRIBUTES ." on ". TBL_POST_ATTRIBUTES ." .post_id = ".TBL_POSTS.".post_id
            inner join ".TBL_ATTRIBUTES ." on ". TBL_ATTRIBUTES ." .attr_id = ".TBL_POST_ATTRIBUTES.".attr_id
            left outer join (select image_path,type_id from  ".TBL_IMAGES." where ".TBL_IMAGES.".type = 'post' group by type_id) as post_img on post_img.type_id = `tbl_posts`.`post_id`
            where ".TBL_POSTS.".post_status = '3'".$filter);

        return $q->result();
    }

    public function get_post($userid)
    {
        if ($userid==0) {
            $q = $this->db->query("Select ".TBL_POSTS.".*, ".TBL_USERS.".user_fullname from ".TBL_POSTS." inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id");
            return $q->result();
        }
        if ($userid==3) {
            $sql = "Select ".TBL_POSTS.".*, ".TBL_USERS.".user_fullname from ".TBL_POSTS." inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id where ".TBL_POSTS.".user_id="._get_current_user_id($this);
            $q = $this->db->query($sql);
            return $q->result();
        }
    }
    
    
    public function get_post_rent($userid)
    {
        if ($userid==0) {
             $q = $this->db->query("Select ".TBL_POSTS.".*, ".TBL_USERS.".user_fullname from ".TBL_POSTS." inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id  WHERE ".TBL_POSTS.".post_title = 'Rent'  OR ".TBL_POSTS." .post_title = 'تأجير'");
            //echo $this->db->query("Select ".TBL_POSTS.".*, ".TBL_USERS.".user_fullname from ".TBL_POSTS." WHERE (".TBL_POSTS.".post_title = 'Rent'  OR ".TBL_POSTS." .post_title = 'تأجير' ) inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id");
            //exit();
            return $q->result();
        }
        if ($userid==3) {

            $sql = "Select ".TBL_POSTS.".*, ".TBL_USERS.".user_fullname from ".TBL_POSTS." inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id where ".TBL_POSTS.".user_id="._get_current_user_id($this). " AND ".TBL_POSTS.".post_title = 'Rent'  OR ".TBL_POSTS." .post_title = 'تأجير'";
            $q = $this->db->query($sql);
            return $q->result();
      
        }
    }
    
    
    public function get_post_by_id($post_id)
    {
        $q = $this->db->query("select * from ".TBL_POSTS." where  post_id = '".$post_id."' limit 1");
        return $q->row();
    }
    public function set_post($post_id)
    {
        $q = $this->db->query("select * from `".TBL_POSTS."` WHERE ".TBL_POSTS.".post_id =".$post_id);
        return $q->row();
    }
    public function get_post_by_image($image_id)
    {
        $q = $this->db->query("Select ".TBL_POSTS.".*,".TBL_IMAGES.".image_path from ".TBL_POSTS."
        inner join ".TBL_IMAGES." on ".TBL_IMAGES.".type_id = ".TBL_POSTS.".post_id where post_id = '".$image_id."' limit 1");
        return $q->row();
    }

    public function get_post_by_user_id($user_id)
    {
        $sql = "Select ".TBL_POSTS.".*,".TBL_COUNTRY.".currency, ".TBL_USERS.".user_fullname,".TBL_CITY.".city_name,post_img.image_path,ifnull(".TBL_POSTS.".post_image,post_img.image_path) as post_image from ".TBL_POSTS."
            inner join ".TBL_CITY." on ".TBL_CITY.".city_id = ".TBL_POSTS.".city_id
               inner join ".TBL_COUNTRY." on ".TBL_COUNTRY.".country_id = ".TBL_CITY.".country_id
          inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id
           left outer join (select image_path,type_id from  ".TBL_IMAGES." where ".TBL_IMAGES.".type = 'post' group by type_id) as post_img on post_img.type_id = ".TBL_POSTS.".`post_id` where ".TBL_POSTS.".user_id=".$user_id;
        $q = $this->db->query($sql);
        return $q->result();
    }
    public function get_post_counts($post_id="", $user_id="")
    {
        $filter = "";
        if ($post_id!="") {
            $filter.=" and ".TBL_POSTS.".post_id = '".$post_id."' ";
        }
        $join = "";
        if ($user_id!="") {
            $join.=" inner join (select post_id from ".TBL_POSTS." where user_id = '".$user_id."' ) as ".TBL_POSTS." on ".TBL_POSTS.".post_id";
        }
        $sql = "Select count(*) as count_review  from ".TBL_POSTS." $join where 1 ".$filter;

        $q = $this->db->query($sql);
        $row = $q->row();
        return $row->count_review;
    }
    public function get_Attribute_count()
    {
        $typeid = _get_current_user_type_id($this);
        if ($typeid == 3 || $typeid == "3") {
            $user_id = _get_current_user_id($this);
            $q = $this->db->query("Select count(*) as total_count from ".TBL_ATTRIBUTES." where attr_id in (select attr_id from ".TBL_ATTRIBUTES." where user_id = '".$user_id."')");
            $row = $q->row();
            return $row->total_count;
        } else {
            $q = $this->db->query("Select count(*) as total_count from ".TBL_ATTRIBUTES." ");
            $row = $q->row();
            return $row->total_count;
        }
    }
    public function get_pos_by_id($post_id)
    {
        $q = $this->db->query("select * from ".TBL_POSTS." where  post_id = '".$post_id."' limit 1");
        return $q->row();
    }
    /*  =========================================================== */
    public function get_post_today()
    {
        /*$q = $this->db->query("SELECT * FROM ".TBL_POSTS." WHERE (`post_date` > DATE_SUB(now(), INTERVAL 1 DAY))");*/
        $q = $this->db->query("Select ".TBL_POSTS.".*, ".TBL_USERS.".user_fullname from ".TBL_POSTS."
        inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id WHERE (`post_date` > DATE_SUB(now(), INTERVAL 1 DAY)) ");
        return $q->result();
    }

    public function get_all_image_by_post_id($post_id)
    {
        $q =  $this->db->query("select * from `".TBL_IMAGES."` where ".TBL_IMAGES.".type_id ='".$post_id."' AND ".TBL_IMAGES.".type = 'post'");
        return $q->result();
    }

    public function get_post_by_attribute($post_id)
    {
        $q = $this->db->query("Select * from ".TBL_POST_ATTRIBUTES."
        where post_id = '".$post_id."'");
        return $q->result();
    }
    /* ===========================================================  */
    public function get_post_of_user_by_id($post_id)
    {
        $sql = "Select ".TBL_POSTS.".*, ".TBL_USERS.".user_fullname, ".TBL_USERS.".user_email, ".TBL_CITY.".city_name from ".TBL_POSTS."
        inner join ".TBL_USERS." on ".TBL_USERS.".user_id = ".TBL_POSTS.".user_id
        inner join ".TBL_CITY." on ".TBL_CITY.".city_id = ".TBL_POSTS.".city_id where ".TBL_POSTS.".post_id =".$post_id;
        $q = $this->db->query($sql);
        return $q->row();
    }


    /*  ========================================  */
    public function get_post_of_user_by_id_attribute($post_id)
    {
        $sql = "select ".TBL_ATTRIBUTES.".attr_rolle, ".TBL_ATTRIBUTE_GROUP.".attr_group_name from ".TBL_POST_ATTRIBUTES."
         inner join ".TBL_ATTRIBUTES." on ".TBL_ATTRIBUTES.".attr_id = ".TBL_POST_ATTRIBUTES.".attr_id
         inner join ".TBL_ATTRIBUTE_GROUP." on ".TBL_ATTRIBUTE_GROUP.".attr_group_id = ".TBL_POST_ATTRIBUTES.".attr_group_id
         where ".TBL_POST_ATTRIBUTES.".post_id = ".$post_id;
        $q = $this->db->query($sql);
        return $q->result();
    }

    /*  ======================================   */
}
