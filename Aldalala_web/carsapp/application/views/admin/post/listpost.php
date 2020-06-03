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
                        All Post
                        <small>Preview</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Post</a></li>
                        <li class="active">All Post</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <?php  if(isset($error)){ echo $error; }
                                    echo $this->session->flashdata('success_req'); ?>
                            <div class="box box-primary">
                                <div class="box-header">
                                    <h3 class="box-title">All Post</h3>                                    
                                </div><!-- /.box-header -->
                                <div class="box-body table-responsive">
                                    <table id="example2" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th class="text-center">Post</th>
                                                <th>Date</th>
                                                <th>Title</th>
                                                <th>User Name</th>
                                                <th style="width: 25%;">Description</th>
                                                <th>Post Status</th>
                                                <th>Year</th>
                                                <th>Post Km</th>
                                                <th>Post Hand</th>
                                                <!--<th>Image</th>-->
                                                <th>Status</th>
                                                <th class="text-center" style="width: 100px;">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                           <?php
                                            $co = 1;
                                            foreach($post as $pos){ ?>
                                            <tr>
                                                <td class="text-center"><?php echo $co; ?></td>
                                                <td><?php echo $pos->post_date; ?></td>
                                                <td><?php echo $pos->post_title; ?></td>
                                                <td><?php echo $pos->user_fullname; ?></td>
                                                <td><?php echo substr($pos->post_description,0,100); ?></td>
                                                <td><?php if($pos->post_status == "1"){ ?><span class="label label-danger">In-Complete</span><?php } else if($pos->post_status == "2") { ?><span class="label label-danger">In-Complete</span><?php } else{ ?><span class="label label-success">Complete</span> <?php } ?></td>
                                                <td><?php echo $pos->post_year; ?></td>
                                                <td><?php echo $pos->post_km; ?></td>
                                                <td><?php echo $pos->post_hand; ?></td>
                                                <!--<td><?php if($pos->post_image!=""){ ?><div class="cat-img" style="width: 50px; height: 50px;"><img width="100%" height="100%" src="<?php echo base_url('/uploads/post/'.$pos->post_image); ?>" /></div> <?php } ?></td>-->
                                                <td><input class='tgl tgl-ios tgl_checkbox' data-table="tbl_posts" data-status="status" data-idfield="post_id"  data-id="<?php echo $pos->post_id; ?>" id='cb_<?php echo $pos->post_id; ?>' type='checkbox' <?php echo ($pos->status==1)? "checked" : ""; ?> />
    <label class='tgl-btn' for='cb_<?php echo $pos->post_id; ?>'></label></td>
                                                <td class="text-center"><div class="btn-group">
                                                        <?php echo anchor('admin/editpost/'.$pos->	post_id, '<i class="fa fa-edit"></i>', array("class"=>"btn btn-success")); ?>
                                                        <?php echo anchor('admin/deletepost/'.$pos->post_id, '<i class="fa fa-times"></i>', array("class"=>"btn btn-danger", "onclick"=>"return confirm('Are you sure delete?')")); ?>
                                                        
                                                    </div>
                                                </td>
                                            </tr>
                                            <?php $co++; } ?>
                                        </tbody>
                                    </table>
                                </div><!-- /.box-body -->
                            </div><!-- /.box -->
                        </div>
                    </div>
                    <!-- Main row -->
                </section><!-- /.content -->
            </aside><!-- /.right-side -->
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
