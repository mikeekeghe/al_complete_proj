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
            <small>Cities</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Admin</a></li>
            <li class="active">Area Cities</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-5">
                    <div class="box">
                        <div class="box-header">
                            Add new City
                        </div>
                        <div class="box-body">
                            <?php echo $this->session->flashdata("message"); ?>
                            <form role="form" action="" method="post">
                              <div class="box-body">
                                <div class="form-group">
                                    <label for="country_id">Country</label>
                                    <select class="form-control select2" name="country_id" id="country_id" style="width: 100%;">
                                      <?php foreach($countries as $country){
                                        ?>
                                        <option value="<?php echo $country->country_id; ?>" ><?php echo $country->country_name; ?></option>
                                        <?php
                                      } ?>
                                    </select>
                                </div>
                                <div class="form-group">
                                  <label for="city_name">City Name</label>
                                  <input type="text" class="form-control" id="city_name" name="city_name" placeholder="Enter City Name" />
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
                    List of Cities
                </div>
                <div class="box-body">
                <table id="example2" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th>Country Name</th>
                        <th>City Name</th>
                        <th>Status</th>
                        <th width="80">Action</th>
                      </tr>
                    </thead>
                    <tbody>
                <?php foreach($cities as $city){
                    ?>
                    <tr>
                        <td><?php echo $city->country_name; ?></td>
                        <td><?php echo $city->city_name; ?></td>
                        <td><input class='tgl tgl-ios tgl_checkbox' data-table="area_city" data-status="status" data-idfield="city_id"  data-id="<?php echo $city->city_id; ?>" id='cb_<?php echo $city->city_id; ?>' type='checkbox' <?php echo ($city->status==1)? "checked" : ""; ?> />
    <label class='tgl-btn' for='cb_<?php echo $city->city_id; ?>'></label></td>
                        <td>    
                            <a href="<?php echo site_url("admin/city_edit/".$city->city_id); ?>" class="btn btn-success"><i class="fa fa-edit"></i></a>
                            <a href="<?php echo site_url("admin/city_delete/".$city->city_id); ?>" class="btn btn-danger"><i class="fa fa-remove"></i></a>
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
      <script src="<?php echo base_url()."/".$this->config->item('default_theme'); ?>/js/chosen.jquery.min.js"></script> 

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
       $(".select2").chosen();
    });
    </script>
    
  </body>
</html>
