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
             <?php echo $this->lang->line("Dashboard");?>
            <small> <?php echo $this->lang->line("Control panel");?></small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i>  <?php echo $this->lang->line("Home");?></a></li>
            <li class="active">  <?php echo $this->lang->line("Dashboard");?></li>
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
                                    <h3 class="box-title">  <?php echo $this->lang->line("Add Page");?></h3>
                                </div><!-- /.box-header -->
                                <!-- form start -->
                                <form action="" method="post" enctype="multipart/form-data">
                                    <div class="box-body">
                                        <div class="form-group">
                                            <label class=""> <?php echo $this->lang->line("Page Title :");?> <span class="text-danger">*</span></label>
                                            <input type="text" name="page_title" class="form-control" placeholder="Page Title"/>
                                        </div>
                                      
                                        <div class="form-group">
                                            <textarea id="editor1" name="page_descri" rows="10" cols="80" placeholder="Place some text here">
                                            </textarea>
                                            <p class="help-block"> <?php echo $this->lang->line("Page Description.");?></p>  
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6"> 
                                                <div class="form-group"> 
                                                    <div class="radio">
                                                        <label class="text-success">
                                                            <input type="radio" name="page_status" id="optionsRadios1" value="1" checked=""/>
                                                            <strong> <?php echo $this->lang->line("Actvie");?></strong>
                                                        </label>
                                                    </div>
                                                    <div class="radio">
                                                        <label class="text-danger">
                                                            <input type="radio" name="page_status" id="optionsRadios2" value="0"/>
                                                            <strong> <?php echo $this->lang->line("Disactive");?></strong>
                                                        </label>
                                                    </div>
                                                    <p class="help-block"> <?php echo $this->lang->line("Set Page Status.");?></p>
                                                </div>        
                                            </div>
                                            <div class="col-md-6"> 
                                                <div class="form-group">
                                                    <label class="col-sm-5 control-label"> <?php echo $this->lang->line("Set Page Status.");?> </label>
                                                    <div class="col-sm-6">
                                                             <div class="form-group">
                                            <label class=""> <?php echo $this->lang->line("On Footer :");?> <span class="text-danger">*</span></label>
                                            <select class="text-input form-control" name="footer">
                                                <option value="0"> <?php echo $this->lang->line("No");?></option>
                                                <option value="1"> <?php echo $this->lang->line("Yes");?></option>
                                            </select>
                                        </div>
                                                    </div>
                                                    
                                                </div>
                                            </div>
                                        </div>
                                    </div><!-- /.box-body -->

                                    <div class="box-footer">
                                        <input type="submit" class="btn btn-primary" name="addpageapp" value="Add Page" />
                                       
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
