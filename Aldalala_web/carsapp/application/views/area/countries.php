<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Admin | Dashboard</title>
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
            Area
            <small>Countries</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Admin</a></li>
            <li class="active">Area Countries</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-5">
                    <div class="box">
                        <div class="box-header">
                            Add new Country
                        </div>
                        <div class="box-body">
                            <?php echo $this->session->flashdata("message"); ?>
                            <form role="form" action="" method="post">
                              <div class="box-body">
                                <div class="form-group">
                                  <label for="country_name">Country Name</label>
                                  <input type="text" class="form-control" id="country_name" name="country_name" placeholder="Enter Country Name" />
                                </div>
                                
                                <div class="form-group">
                                    <label for="currency">Currency</label>
                                    <input type="text" maxlength="3" class="form-control" id="iso_3" name="currency" placeholder="IND" />
                                </div>
                                <div class="form-group">
                                    <div class="checkbox">
                                      <label for="status">
                                        <input type="checkbox" id="status" name="status"  /> Status
                                      </label>
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
                <div class="col-md-7">
                
                
            <div class="box">
                <div class="box-header">
                    List of Countries
                </div>
                <div class="box-body">
                <table id="example2" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th>Country Name</th>
                        <th>Currency</th>
                        <th>Status</th>
                        <th width="80">Action</th>
                      </tr>
                    </thead>
                    <tbody>
                <?php foreach($countries as $country){
                    ?>
                    <tr>
                        <td><?php echo $country->country_name; ?></td>
                        <td><?php echo $country->currency; ?></td>
                        <td><input class='tgl tgl-ios tgl_checkbox' data-table="area_country" data-status="status" data-idfield="country_id"  data-id="<?php echo $country->country_id; ?>" id='cb_<?php echo $country->country_id; ?>' type='checkbox' <?php echo ($country->status==1)? "checked" : ""; ?> />
    <label class='tgl-btn' for='cb_<?php echo $country->country_id; ?>'></label></td>
                        <td>    
                            <a href="<?php echo site_url("admin/country_edit/".$country->country_id); ?>" class="btn btn-success"><i class="fa fa-edit"></i></a>
                            <a href="<?php echo site_url("admin/country_delete/".$country->country_id); ?>" class="btn btn-danger"><i class="fa fa-remove"></i></a>
                        </td>
                    </tr>
                    <?php
                } ?>
                    </tbody>
                </table>
                </div>
            </div>
                </div>
                
            </div>
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
      <?php  $this->load->view("admin/common/common_footer"); ?>  
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
                alert(msg);
              }); 
        });
      });
    </script>
    <script>
    $(function(){
       $(".select2").select2();
       $(".select3").select2();
       $(".select4").select2();
       $("#country_id").change(function(){
        
            var country_id = $(this).val();
            $.ajax({
              method: "POST",
              url: '<?php echo site_url("admin/change_state"); ?>',
              data: { country_id: country_id }
            })
              .done(function( msg ) {
                
                    $("#state_id").html(msg);
                    $(".select3").select2();
              }); 
       }); 
       $("#state_id").change(function(){
        
            var state_id = $(this).val();
            var country_id = $("#country_id").val();     
            $.ajax({
              method: "POST",
              url: '<?php echo site_url("admin/change_city"); ?>',
              data: { state_id: state_id, country_id : country_id }
            })
              .done(function( msg ) {
               
                    $("#city_id").html(msg);
                    $(".select4").select2();
              }); 
       });
    });
    </script>
    
  </body>
</html>
