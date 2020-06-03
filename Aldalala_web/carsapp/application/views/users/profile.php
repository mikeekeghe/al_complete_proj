<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
  <head>
        <?php $this->load->view("common/head"); ?>
        <link rel="stylesheet" type="text/css"  href="<?php echo base_url($this->config->item("default_theme")); ?>/css/lightbox.css" />
        <link rel="stylesheet" type="text/css"  href="<?php echo base_url($this->config->item("default_theme")); ?>/css/rating.css" />
  </head>
  <body>
    <?php $this->load->view("common/header"); ?>
	      
      <div class=" nopading">
        <div class="container"> 
        
                
                   <div class="row">
                        <div id="signupbox" style=" margin-top:50px" class="mainbox col-md-6 col-sm-12 col-md-offset-3 ">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="panel-title">Profile</div>
                                            
                                        </div>  
                                        <div class="panel-body" >
                                            <form id="" class="form-horizontal"  enctype="multipart/form-data"    method="post" >
                                                
                                                <div class="form-group">
                                                    <label for="email" class="col-md-3 control-label">Email</label>
                                                    <div class="col-md-9">
                                                        <input type="email" class="form-control" disabled="" value="<?php echo $user_data->user_email; ?>" name="email" placeholder="Email Address"  required="">
                                                    </div>
                                                </div>
                                                    
                                                <div class="form-group">
                                                    <label for="firstname" class="col-md-3 control-label">Full Name</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" name="fullname" value="<?php echo $user_data->user_fullname; ?>" placeholder="Full Name"  required="">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="phone" class="col-md-3 control-label">Phone</label>
                                                    <div class="col-md-9">
                                                        <input type="tel" class="form-control" name="phone" value="<?php echo $user_data->user_phone; ?>" placeholder="0000000000" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="b_date" class="col-md-3 control-label">Birth Date</label>
                                                    <div class="col-md-3">
                                                    <?php $day = date("d",strtotime($user_data->user_bdate));
                                                    $month = date("m",strtotime($user_data->user_bdate));
                                                    $year = date("Y",strtotime($user_data->user_bdate)); ?>
                                                            <select class="form-control" name="b_month"  required="">
                                                                <option value="01" <?php echo ($month==1)? "selected" : ""; ?> >Jan</option>
                                                                <option value="02" <?php echo ($month==2)? "selected" : ""; ?> >Feb</option>
                                                                <option value="03" <?php echo ($month==3)? "selected" : ""; ?> >Mar</option>
                                                                <option value="04" <?php echo ($month==4)? "selected" : ""; ?> >Apr</option>
                                                                <option value="05" <?php echo ($month==5)? "selected" : ""; ?> >May</option>
                                                                <option value="06" <?php echo ($month==6)? "selected" : ""; ?> >Jun</option>
                                                                <option value="07" <?php echo ($month==7)? "selected" : ""; ?> >Jul</option>
                                                                <option value="08" <?php echo ($month==8)? "selected" : ""; ?> >Aug</option>
                                                                <option value="09" <?php echo ($month==9)? "selected" : ""; ?> >Sep</option>
                                                                <option value="10" <?php echo ($month==10)? "selected" : ""; ?> >Oct</option>
                                                                <option value="11" <?php echo ($month==11)? "selected" : ""; ?> >Nov</option>
                                                                <option value="12" <?php echo ($month==12)? "selected" : ""; ?> >Dec</option>
                                                            </select>
                                                    </div>
                                                    <div class="col-md-2">
                                                         <input type="text" class="form-control" name="b_day" value="<?php echo $day; ?>" max="2" required="" placeholder="DD" />   
                                                    </div>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="b_year"  value="<?php echo $year; ?>" max="4" required="" placeholder="YYYY" />  
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="password" class="col-md-3 control-label">Profile Image</label>
                                                    <div class="col-md-9">
                                                        <input type="file" name="user_image" class="form-control" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="country_id"  class="col-md-3 control-label">Country</label>
                                                    <div class="col-md-9">
                                                    <select class="form-control select2" name="country_id" id="country_id">
                                                    <option value="">--choose country--</option>
                                                      <?php foreach($countries as $country){
                                                        ?>
                                                        <option value="<?php echo $country->country_id; ?>" <?php if(!empty($user_data->user_country) && $user_data->user_country == $country->country_id){ echo "selected"; } ?> ><?php echo $country->country_name; ?></option>
                                                        <?php
                                                      } ?>
                                                    </select>
                                                    </div>
                                                </div>
                                                   <div class="form-group">
                                                    <label for="city_id" class="col-md-3 control-label">City</label>
                                                    <div class="col-md-9">
                                                        <select class="form-control select3" id="city_id" name="city">
                                                            <?php 
                                                            if(!empty($cities)){
                                                            foreach($cities as $city){
                                                        ?>
                                                        <option value="<?php echo $city->city_id; ?>" <?php if(!empty($user_data->user_city) && $user_data->user_city == $city->city_id){ echo "selected"; } ?> ><?php echo $city->city_name; ?></option>
                                                        <?php
                                                        } 
                                                      }?>
                                                        </select>
                                                    </div>
                                                </div>  
                                              
                
                                                <div class="form-group">
                                                    <!-- Button -->                                        
                                                    <div class="col-md-offset-3 col-md-9">
                                                        <input type="submit" name="register" value='Update Profile' id="btn-signup" type="button" class="btn btn-info" />
                                                        
                                                         
                                                    </div>
                                                </div>
                                                
                                               
                                                
                                                
                                            </form>
                                         </div>
                                    </div>
                
                               
                               
                                
                         </div> 
                         <?php if($user_data->user_image != ""){
                            ?>
                             <div id="signupbox" style=" margin-top:50px" class="mainbox col-md-6 col-sm-12 ">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="panel-title">Profile Image</div>
                                            <div style="float:right; font-size: 85%; position: relative; top:-10px"><a id="signinlink" href="#">Profile</a></div>
                                        </div>  
                                        <div class="panel-body" >
                                            <img src="<?php echo base_url("uploads/profile/".$user_data->user_image); ?>" class="thumbnail img-responsive" />
                                        </div>
                                    </div>
                             </div>
                            <?php
                            
                         } ?>
                   </div>
                
    </div>
    </div>
    <?php $this->load->view("common/footer_bottom"); ?>
    <?php $this->load->view("common/common_js"); ?>
        <script>
    $(function(){
       $("#country_id").chosen();
       $("#city_id").chosen();
       $("#country_id").change(function(){
            $('#city_id').html("");
            var country_id = $(this).val();
            
            $.ajax({
              method: "POST",
              url: '<?php echo site_url("admin/city_json"); ?>',
              data: { country_id: country_id }
            })
              .done(function( data ) {
                    
                     $.each(data, function(index, element) {
                                $('#city_id').append("<option value='"+element.city_id+"'>"+element.city_name+"</option>");
                            });
                            $("#city_id").trigger("chosen:updated");
            }); 
       }); 
    });
    </script>

</body>
</html>