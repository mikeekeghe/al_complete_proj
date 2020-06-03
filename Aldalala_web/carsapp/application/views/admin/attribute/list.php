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
            Attribute List
            <small>Manage Attribute</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Admin</a></li>
            <li class="active">Attribute</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header">List Attribute
                <table id="example2" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th>No</th>
                        <th>Roll</th>
                        <th>Attribute Group Name</th>
                       <!-- <th>Images</th>-->
                       
                        <th width="80">Action</th>
                      </tr>
                    </thead>
                    <tbody>
                <?php
                    $co = 1;
                 foreach($attribute as $attribu){
                    ?>
                    <tr>
                        <td><?php echo $co; ?></td>
                        <td><?php echo $attribu->attr_rolle; ?></td>
                        <td><?php echo $attribu->attr_group_name; ?></td>
                      <!--  <td><?php if($attribu->attr_image!=""){ ?><div class="cat-img" style="width: 50px; height: 50px;"><img width="100%" height="100%" src="<?php echo base_url('/uploads/attribute/'.$attribu->attr_image); ?>" /></div> <?php } ?></td>-->
                        <td>
                            <a href="<?php echo site_url("admin/edit_attribute/".$attribu->attr_id); ?>" class="btn btn-success"><i class="fa fa-edit"></i></a>
                            <a href="<?php echo site_url("admin/delete_attribute/".$attribu->attr_id); ?>" onclick="return confirm('Are you sure to delete..?')" class="btn btn-danger"><i class="fa fa-remove"></i></a>
                        </td>
                    </tr>
                    <?php
                    $co++;
                } ?>
                    </tbody>
                </table>
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
              //  alert(msg);
              }); 
        });
      });
    </script>
  </body>
</html>
