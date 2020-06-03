<!DOCTYPE html>
<html>
  <head>
     <?php  $this->load->view("admin/common/common_css"); ?>
      <link rel="stylesheet" href="<?php echo base_url($this->config->item("theme_admin")."/plugins/morris/morris.css"); ?>" >
    <!-- jvectormap -->
    <link rel="stylesheet" href="<?php echo base_url($this->config->item("theme_admin")."/plugins/jvectormap/jquery-jvectormap-1.2.2.css"); ?>" >
    <!-- daterange picker -->
    <link rel="stylesheet" href="<?php echo base_url($this->config->item("theme_admin")."/plugins/daterangepicker/daterangepicker-bs3.css"); ?>">
    
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
            Dashboard
            <small>Control panel</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
            <li class="active">Dashboard</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <!-- Small boxes (Stat box) -->
          <div class="row">
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-aqua">
                <div class="inner">
                  <h3><?php  echo $app_count;  ?> </h3>
                  <p>Attribute</p>
                </div>
                <div class="icon">
                  <i class="ion ion-bag"></i>
                </div>
                <a href="<?php echo site_url("admin/listattribute"); ?>" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
              </div>
            </div><!-- ./col -->
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-purple">
                <div class="inner">
                  <h3><?php  echo $reviews_count;  ?> </h3>
                  <p>Post</p>
                </div>
                <div class="icon">
                  <i class="fa fa-car"></i>
                </div>
                <a href="<?php echo site_url("admin/listpost"); ?>" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
              </div>
            </div><!-- ./col -->
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-yellow">
                <div class="inner">
                  <h3><?php echo $user_count; ?></h3>
                  <p>User Registrations</p>
                </div>
                <div class="icon">
                  <i class="ion ion-person-add"></i>
                </div>
                <a href="<?php echo site_url("admin/listuser/1"); ?>" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
              </div>
            </div><!-- ./col -->
            
          </div><!-- /.row -->
          
          <div class="row">
            <div class="col-md-12">
                <div class="panel">
                    <div class="panel-heading">

                <h3>Todays Post : </h3>
                <table id="example1" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th>Username</th>
                        <th>Post Name</th>
                        <th>Description</th>
                        <th>Post Time</th>
                        <th>Status</th>
                        <th>Status</th>
                        <th width="100">Action</th>
                      </tr>
                    </thead>
                    <tbody>
                <?php /*  foreach($appointments as $app){
                    $this->load->view("admin/common/buss_list_row",array("list"=>$app));
                }  */ ?>
                         <?php foreach($gettoday as $gpost){ ?>
                         <tr>
                            <td><?php echo $gpost->user_fullname; ?></td>
                            <td><?php echo $gpost->post_title; ?></td>
                            <td><?php echo substr($gpost->post_description,0,100); ?></td>
                            <td><?php echo $gpost->post_date; ?></td>
                            <td>
                                <input type="checkbox" class="toggle tgl_checkbox"  data-table="tbl_posts" data-status="post_status" data-idfield="post_id"  data-id="<?php echo $gpost->post_id; ?>" id='cb_<?php echo $gpost->post_id; ?>'  <?php echo ($gpost->post_status==1)? "checked" : ""; ?>  />
                                <label for="cb_<?php echo $gpost->post_id; ?>"></label>
                            </td>
                            <td><label class='tgl-btn' for='cb_<?php echo $gpost->post_id; ?>'></label><?php echo ($gpost->post_status==1)? "Complete" : "Pending"; ?></td>   
                           <!-- <td><?php if($gpost->post_status == "1"){ ?><span class="label label-success">Active</span><?php } else { ?><span class="label label-danger">Deactive</span><?php } ?></td>-->
                            <td class="text-center">
                                <div class="btn-group">
                                    <?php echo anchor('admin/editpost/'.$gpost->post_id, '<i class="fa fa-edit"></i>', array("class"=>"btn btn-success")); ?>
                                    <?php echo anchor('admin/deletepost/'.$gpost->post_id, '<i class="fa fa-times"></i>', array("class"=>"btn btn-danger", "onclick"=>"return confirm('Are you sure delete?')")); ?>
                                </div>
                            </td>
                         </tr>
                <?php } ?>
                    </tbody>
                </table>
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
      $(function () {
        
        $('#example2').DataTable({
          "paging": true,
          "lengthChange": false,
          "searching": true,
          "ordering": true,
          "info": true,
          "autoWidth": false
        });
        $("body").on("change",".tgl_checkbox",function(){
            var table = $(this).data("table");
            var status = $(this).data("status");
            var id = $(this).data("id");
            var id_field = $(this).data("idfield");
            var bin=0;
                                         if($(this).is(':checked')){
                                            bin = 1;
                                         }
            $.ajax({
              method: "POST",
              url: "<?php echo site_url("admin/change_status"); ?>",
              data: { table: table, status: status, id : id, id_field : id_field, on_off : bin }
            })
              .done(function( msg ) {
                
              }); 
        });
      });
    </script>

     

  </body>
</html>
