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
            <?php echo $this->lang->line(" App Page");?>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i>  <?php echo $this->lang->line(" Home");?></a></li>
            <li class="active"> <?php echo $this->lang->line(" App Page");?></li>
          </ol>
        </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-md-6">
                            <?php  if(isset($error)){ echo $error; }
                                    echo $this->session->flashdata('success_req'); ?>
                            <!-- general form elements -->
                            <div class="box box-primary">
                                 <div class="box-header">
                                    <h3 class="box-title"> <?php echo $this->lang->line("Edit Page :");?> <strong><?php echo $onepage->pg_title; ?></strong></h3>
                                </div><!-- /.box-header -->
                                <!-- form start -->
                                <form action="" method="post" enctype="multipart/form-data">
                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class=""> <?php echo $this->lang->line("Page Title :");?> <span class="text-danger">*</span></label>
                                           <input type="text" name="page_title" class="form-control" placeholder="Page Title" value="<?php echo $onepage->pg_title; ?>"/>
                                            <input type="hidden" name="page_id" class="form-control" placeholder="Page id" value="<?php echo $onepage->id; ?>"/>
                                        </div>
                                      
                                        <div class="form-group">
                                        <label class=""> <?php echo $this->lang->line("Page Description.");?> <span class="text-danger">*</span></label>
                                            <textarea id="editor1" name="page_descri" rows="10" cols="80" placeholder="Place some text here">
                                           <?php echo $onepage->pg_descri; ?> </textarea>
                                            
                                        </div>
                                          
                                    </div><!-- /.box-body -->

                                    <div class="box-footer">
                                        <input type="submit" class="btn btn-primary" name="savepageapp" value="Save Page" />
                                       
                                    </div>
                                </form>
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
  </body>
</html>

