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
                         Notification 
                    </h1> 
                </section> 
                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-4">
                            <div class="box box-primary"> 
                                <!-- form start -->
                                  <form action="" method="post" enctype="multipart/form-data">
                                       
                                        <div class="box-header">
                                          <i class="fa fa-bell"></i>
                            
                                          <h3 class="box-title">Quick Notification</h3>
                                          
                                        </div>
                                        <div class="box-body">
                                          
                                            
                                            <div class="form-group">
                                              <input type="text" class="form-control" name="subject" placeholder="Subject*" required="">
                                            </div>
                                            <div class="form-group">
                                                <span>Notification Banner :</span>
                                              <input type="file" class="form-control" name="image_path" placeholder="Attachment Image">
                                            </div>
                                            <div>
                                              <textarea class="textarea" name="message" placeholder="Message*" required="" style="width: 100%; height: 125px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;"></textarea>
                                            </div>
                                            <input type="hidden" name="type" value="notification" />
                                        </div>
                                        <div class="box-footer clearfix">
                                          <button type="submit" class="pull-right btn btn-default" id="sendEmail">Send
                                            <i class="fa fa-arrow-circle-right"></i></button>
                                        </div>
                                       
                                    </form>
                            </div><!-- /.box -->
                        </div>
                        <div class="col-xs-8">
                            <?php  if(isset($error)){ echo $error; }
                                    echo $this->session->flashdata('message'); ?>
                            <div class="box box-primary">
                                <div class="box-header">
                                    <h3 class="box-title">Notification List</h3>                                    
                                </div><!-- /.box-header -->
                                <div class="box-body table-responsive">
                                     <table id="example2" class="table table-bordered table-hover">
                                        <thead>
                                          <tr>
                                            <th>ID</th>
                                            <th>Notification Title</th>
                                            <th>Notification Description</th>
                                            <th>Notification image</th>
                                            <th>Date</th> 
                                            <th width="30">Action</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                    <?php  foreach($notification as $noticelist){
                                        ?>
                                        <tr>
                                            
                                            <td><?php echo $noticelist->not_id; ?></td>
                                            <td><?php echo $noticelist->title; ?></td>
                                            <td><?php echo $noticelist->message; ?></td> 
                                             <td><?php if($noticelist->image_path!=""){ ?><div class="cat-img" style="width: 50px; height: 50px;"><img width="100%" height="100%" src="<?php echo $this->config->item('base_url').'/uploads/image/'.$noticelist->image_path; ?>" /></div> <?php } ?></td>
                                            <td><?php echo $noticelist->created_at; ?></td> 
                                            <td> 
                                                <a href="<?php echo site_url("admin/delete_notification/".$noticelist->not_id); ?>" onclick="return confirm('are you sure to delete?')" class="btn btn-danger"><i class="fa fa-remove"></i></a>
                                                
                                            </td>
                                        </tr>
                                        <?php
                                    } ?>
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
              //  alert(msg);
              }); 
        });
      });
    </script>
      <!-- Add the sidebar's background. This div must be placed
           immediately after the control sidebar -->
      <div class="control-sidebar-bg"></div>
    </div><!-- ./wrapper --> 
  </body>
</html>
