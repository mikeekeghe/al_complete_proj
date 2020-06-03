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
            Atribute Group
            <small>Manage Attribute Group</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Admin</a></li>
            <li class="active">Add Attribute Group</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-6">
                    <div class="box">
                        <div class="box-header">
                           Add Attribute Group
                        </div>
                        <div class="box-body">
                        
                            <form role="form" action="" method="post">
                              <div class="box-body">
                              <?php 
                                echo $this->session->flashdata("message");
                               ?>
                                <div class="row">
                                    <div class="form-group">
                                        <label for="user_fullname">Attribute Group Name</label>
                                        <input type="text" class="form-control" id="attr_group_name" name="attr_group_name" placeholder="Attribute Group Name" />
                                   </div>
                                </div>
                                <div class="row">
                                    <div class="form-group">
                                        <label for="country_id">Attribute Group Type</label>
                                        <select class="form-control select2" name="attr_group_type" id="country_id" style="width: 100%;">
                                            <option value="">-- Choose a Attribute Group Type --</option>
                                            <option  value="image">Image</option>
                                            <option  value="text">Text</option></option>
                                            <option  value="checkbox">Checkbox</option>
                                            <option  value="radio">Radio</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group">
                                        <label for="country_id">Attribute Group Choose</label>
                                        <select class="form-control select2" name="attr_group_choosen" id="country_id" style="width: 100%;">
                                            <option value="">-- Choose a Attribute Group --</option>
                                            <option  value="single">Single</option>
                                            <option  value="multiple">Multiple</option></option>
                                        </select>
                                    </div>
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

      });
    </script>
    <script>
    $(function(){
       $(".select2").select2();
    });
    </script>
    
  </body>
</html>
