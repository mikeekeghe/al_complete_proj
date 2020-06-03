<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Admin | Dashboard</title>
    <?php  $this->load->view("admin/common/common_css"); ?>
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper">

      <?php  $this->load->view("admin/common/common_header"); ?>
      <!-- Left side column. contains the logo and sidebar -->
      <?php  $this->load->view("admin/common/common_sidebar"); ?>

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Area
            <small>Cities</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Admin</a></li>
            <li class="active">Area Cities</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-5">
                    <div class="box">
                        <div class="box-header">
                            Add new City
                        </div>
                        <div class="box-body">
                            <?php echo $this->session->flashdata("message"); ?>
                            <form role="form" action="" method="post">
                              <div class="box-body">
                                <div class="form-group">
                                    <label for="country_id">Country</label>
                                    <select class="form-control select2" name="country_id" id="country_id" style="width: 100%;">
                                      <?php foreach($countries as $country){
                                        ?>
                                        <option value="<?php echo $country->country_id; ?>" <?php if($country->country_id == $city->country_id) { echo "selected"; } ?> ><?php echo $country->country_name; ?></option>
                                        <?php
                                      } ?>
                                    </select>
                                </div>
                                <div class="form-group">
                                  <label for="city_name">City Name</label>
                                  <input type="text" class="form-control" id="city_name" name="city_name" placeholder="Enter City Name" value="<?php echo $city->city_name; ?>" />
                                </div>
                                
                                <div class="form-group">
                                    <div class="checkbox">
                                      <label for="status">
                                        <input type="checkbox" id="status" name="status" <?php if($city->status == 1){ echo "checked"; } ?> /> Status
                                      </label>
                                    </div>
                                </div>
                              </div><!-- /.box-body -->
            
                              <div class="box-footer">
                                <button type="submit" class="btn btn-primary">Submit</button>
                              </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-7">
                
                
                </div>
                
            </div>
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
      <?php  $this->load->view("admin/common/common_footer"); ?>  
      <?php  $this->load->view("admin/common/common_js"); ?>
      <script src="<?php echo base_url()."/".$this->config->item('default_theme'); ?>/js/chosen.jquery.min.js"></script> 

    <script>
    $(function(){
       $(".select2").chosen();
    });
    </script>
    
  </body>
</html>
