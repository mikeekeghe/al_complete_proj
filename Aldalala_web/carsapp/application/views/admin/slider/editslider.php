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
              Slider <?php echo $this->lang->line("");?> 
          </h1>
          
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
                                    <h3 class="box-title">Edit Slider <?php echo $this->lang->line("");?> </h3>
                                </div><!-- /.box-header -->
                                <!-- form start -->
                                <form action="" method="post" enctype="multipart/form-data">
                                    <div class="box-body">
                                    <div class="col-md-6">
                                     <div class="form-group">
                                            <label class="">Slider Title<?php echo $this->lang->line("");?>  : <span class="text-danger">*</span></label>
                                            <input type="text" name="slider_title" class="form-control" placeholder="Slider Title" value="<?php echo $slider->slider_title; ?>"/>
                                        </div>
                                        </div>
                                   <div class="col-md-6">
                                     <div class="form-group">
                                            <label class="">Slider Url <?php echo $this->lang->line("");?>  : </label>
                                            <input type="text" name="slider_url" class="form-control" placeholder="Slider Url/Link" value="<?php echo $slider->slider_url;?>" />
                                        </div>
                                  </div>
                                   
                                         
                                        <div class="col-md-6">
                                     <div class="cat-img" style="width: 300px; height: 100px;"><img width="100%" height="100%" src="<?php echo $this->config->item('base_url').'uploads/admin/sliders/'.$slider->slider_image ?>" /></div>
                                        <div class="form-group">
                                        
                                            <label><?php echo $this->lang->line("Image");?> : <span class="text-danger">*</span> </label>
                                            <input type="file" name="slider_img" />
                                        </div>
                                        </div>
                               
                                                 <div class="col-md-6">
                                        <div class="form-group">
                                            <label class=""><?php echo $this->lang->line("Status");?> : <span class="text-danger">*</span></label>
                                            <select class="text-input form-control" name="slider_status">
                                                <option value="1" <?php if($slider->slider_status=="1"){echo "selected";} ?>> <?php echo $this->lang->line("Active");?>  </option>
                                                <option value="0" <?php if($slider->slider_status=="0"){echo "selected";} ?>> <?php echo $this->lang->line("DeActive");?>  </option>
                                                      
                                            </select>
                                        </div>
                                        </div>
                                    </div><!-- /.box-body -->

                                    <div class="box-footer">
                                        <input type="submit" class="btn btn-primary" name="saveslider" value="Add Slider" />
                                       
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
  </body>
</html>
