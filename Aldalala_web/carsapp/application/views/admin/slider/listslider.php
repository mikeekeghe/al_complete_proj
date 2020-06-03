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
             Slider 
            <small>Manage Slides </small>
          </h1>
          
        </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <?php  if(isset($error)){ echo $error; }
                                    echo $this->session->flashdata('success_req'); ?>
                                    
                            <div class="box box-primary">
                                <div class="box-header">
                                    <h3 class="box-title"> All Slides </h3>                                    
                                </div><!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example2" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th class="text-center">Slider Title </th>                                                 
                                                <th> Slider Url </th>
                                                <th> Image </th>
                                                <th> Status</th>
                                                <th class="text-center" style="width: 100px;"> Action </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                           <?php foreach($allslider as $bus){ ?>
                                            <tr>
                                                
                                                <td><?php echo $bus->slider_title; ?></td>
                                                <td><?php echo $bus->slider_url; ?></td>
                                                <td><div class="cat-img" style="width: 500px; height: 150px;"><img width="100%" height="100%" src="<?php echo $this->config->item('base_url').'/uploads/admin/sliders/'.$bus->slider_image ?>" /></div></td>
                                              <td><?php if($bus->slider_status=="1"){echo "Active";}else{echo "DeActive";} ?></td>
                                           
                                        
                                                <td class="text-center"><div class="btn-group">
                                                        <?php echo anchor('admin/editslider/'.$bus->id, '<i class="fa fa-edit"></i>', array("class"=>"btn btn-success")); ?>
                                                        <?php echo anchor('admin/deleteslider/'.$bus->id, '<i class="fa fa-times"></i>', array("class"=>"btn btn-danger", "onclick"=>"return confirm('Are you sure delete?')")); ?>
                                                        
                                                    </div>
                                                </td>
                                            </tr>
                                            <?php } ?>
                                        </tbody>
                                    </table>
                                </div><!-- /.box-body -->
                            </div><!-- /.box -->
                        </div>
                    </div>
                    <!-- Main row -->
                </section><!-- /.content -->
            </aside><!-- /.right-side -->
        </div><!-- ./wrapper -->
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
      
      });
    </script>
    
  </body>
</html>
