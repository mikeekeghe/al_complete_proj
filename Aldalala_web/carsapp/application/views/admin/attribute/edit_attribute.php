<!DOCTYPE html>
<html>
  <head>
    
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
            Users
            <small>Manage Users</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Admin</a></li>
            <li class="active">Add Users</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-6">
                    <div class="box">
                        <div class="box-header">
                           
                        </div>
                        <div class="box-body">
                        
                            <form role="form" action="" method="post" enctype="multipart/form-data">
                              <div class="box-body">
                              <?php 
                                echo $this->session->flashdata("message");
                               ?>
                                
                                    <div class="row">
                                        <div class="form-group">
                                            <label for="user_fullname">Attribute Roll</label>
                                            <input type="text" class="form-control" id="user_fullname" value="<?php echo $attribute->attr_rolle; ?>" name="attr_rolle" placeholder="User Full Name" />
                                        </div>
                                    </div>
                                    <div class="row">
                                                <div class="form-group">
                                                    <label for="country_id">Attribute Group Name</label>
                                                    <select class="form-control select2" name="attr_group_id" id="country_id" style="width: 100%;">
                                                      <option value="">-- Choose a Attribute Group --</option>
                                                      <?php foreach($attribute_group as $categ){ ?>
                                                                <option value="<?php echo $categ->attr_group_id; ?>" <?php if($attribute->attr_group_id == $categ->attr_group_id){ echo 'selected=""';}  ?>><?php echo $categ->attr_group_name; ?></option>
                                                            <?php } ?>
                                                    </select>
                                                </div>
                                            </div>
                                  
                                        
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <div class="bus-product">   
                                                            <?php
                                                            $proicon = base_url($this->config->item('theme_folder')."/images/generic-profile.png");
                                                            if($get_image->image_path != ""){
                                                             $proicon = base_url("uploads/image/".$get_image->image_path); } ?>                                 
                                                        <img src="<?php echo $proicon; ?>" style="height: 100px; width: 100px;"/>
                                                       
                                                    </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="form-group">
                                                <label class="">Attribute Image</label>
                                                <input type="file" name="image_path" onchange="readURL(this)" multiple="" value="<?php $get_image->image_path; ?>"  />
                                                
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <!----------------------------->
                               
                              </div><!-- /.box-body -->
            
                              <div class="box-footer">
                                <button type="submit" class="btn btn-primary">Update</button>
                                
                              </div>
                            </form>
                        </div>
                    </div>
                </div>
               
                
            </div>
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
      <?php  $this->load->view("admin/common/common_footer"); ?>  

      
      <!-- Add the sidebar's background. This div must be placed
           immediately after the control sidebar -->
      <div class="control-sidebar-bg"></div>
    </div><!-- ./wrapper -->

    <?php  $this->load->view("admin/common/common_js"); ?>
    <script>
   function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    jQuery('.pro-icon').css("background-image","url("+e.target.result+")");
                };

                reader.readAsDataURL(input.files[0]);
            }
        }
   </script> 
       <style>
       .pro-icon { background-position: center; background-size: cover; background-repeat: no-repeat; }
       </style>  
    <script>
      $(function () {
        
        $('#example2').DataTable({
          "paging": true,
          "lengthChange": false,
          "searching": true,
          "ordering": true,
          "info": true,
          "autoWidth": false
        });

      });
    </script>
    <script>
    $(function(){
       $(".select2").select2();
    });
    </script>
    
  </body>
</html>
