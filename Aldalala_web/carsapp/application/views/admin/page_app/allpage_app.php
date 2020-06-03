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
             <?php echo $this->lang->line("App Page");?>
            
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i>  <?php echo $this->lang->line("Home");?></a></li>
            <li class="active"> <?php echo $this->lang->line("App Page");?></li>
          </ol>
        </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <?php  if(isset($error)){ echo $error; }
                                    echo $this->session->flashdata('success_req'); ?>
                            <!-- general form elements -->
                           <div class="box box-primary">
                                <div class="box-header">
                                    <h3 class="box-title"> <?php echo $this->lang->line("All Pages");?></h3>                                    
                                </div><!-- /.box-header -->
                                <div class="box-body table-responsive">
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th style="width: 30px;"><?php echo $this->lang->line("ID ");?></th>
                                                <th style="width: 15%;"><?php echo $this->lang->line("Title ");?></th>
                                                <th style="width: 15%;"> <?php echo $this->lang->line("Page Slug");?></th>
                                                <th style="width: 40%;"> <?php echo $this->lang->line("Description");?></th>
                                                 
                                                
                                                <th class="text-center" style="width: 100px;"> <?php echo $this->lang->line("Action");?></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <?php foreach($allpages as $allpage){ ?>
                                            <tr>
                                                <td><?php echo $allpage->id; ?></td>
                                                <td><?php echo $allpage->pg_title; ?></td>
                                                <td><?php echo $allpage->pg_slug; ?></td>
                                                <td><?php echo $allpage->pg_descri; ?></td>
                                                
                                                
                                                <td class="text-center"><div class="btn-group">
                                                        <?php echo anchor('admin/editpage_app/'.$allpage->id, '<i class="fa fa-edit"></i>', array("class"=>"btn btn-success")); ?>
                                                       <!--<?php echo anchor('admin/deletepage/'.$allpage->id, '<i class="fa fa-times"></i>', array("class"=>"btn btn-danger", "onclick"=>"return confirm('Are you sure delete?')")); ?>--> 
                                                        
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
           
        </div><!-- ./wrapper -->

        <?php  $this->load->view("admin/common/common_footer"); ?>  

      
      <!-- Add the sidebar's background. This div must be placed
           immediately after the control sidebar -->
      <div class="control-sidebar-bg"></div>
    </div><!-- ./wrapper -->

    <?php  $this->load->view("admin/common/common_js"); ?>
    <script>
      $(function () {
        
        $('#example1').DataTable({
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
