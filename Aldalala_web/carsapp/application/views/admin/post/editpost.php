<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Admin | Dashboard</title>
    <?php  $this->load->view("admin/common/common_css"); ?>
    <script src="<?php echo base_url($this->config->item("theme_admin")."/ckeditor/ckeditor.js"); ?>"></script>
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
                        Update Post
                        <small>Preview</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Post</a></li>
                        <li class="active">Update Post</li>
                    </ol>
                </section>
                <!-- ================== Post Add New Code =================================  -->
                
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <?php  if(isset($error)){ echo $error; }
                                    echo $this->session->flashdata('success_req'); ?>
                            <!-- general form elements -->
                            <div class="box box-primary">
                                <div class="box-header">
                                    <h3>Detail Post</h3>
                                </div>
                                
                                <!-- form start -->
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-md-5">
                                                <table  class="table">
                                                    <tbody>
                                                        <tr>
                                                            <th>Post User :-</th>
                                                            <td> <?php echo $setpost->user_fullname; ?></td>
                                                        </tr>
                                                        <tr>
                                                            <th>User Email :-</th>
                                                            <td><?php echo $setpost->user_email; ?></td>
                                                        </tr>
                                                        <tr>
                                                            <th>User City :-</th>
                                                            <td><?php echo $setpost->city_name; ?></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Post Title :-</th>
                                                            <td><?php echo $setpost->post_title; ?></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Post Price :-</th>
                                                            <td><?php echo $setpost->post_price; ?></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Post Year :-</th>
                                                            <td><?php echo $setpost->post_year; ?></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Post KM :-</th>
                                                            <td><?php echo $setpost->post_km; ?></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Post Hand :-</th>
                                                            <td><?php echo $setpost->post_hand; ?></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Post Status :-</th>
                                                            <td><?php if($setpost->post_status == "1"){ ?><span class="label label-danger">In-Complete</span><?php } else if($setpost->post_status == "2") { ?><span class="label label-danger">In-Complete</span><?php } else{ ?><span class="label label-success">Complete</span> <?php } ?></td>
                                                        </tr>
                                                        <?php
                                                            foreach($postattribute as $poattri){
                                                        ?>
                                                        <tr>
                                                            <th><?php echo $poattri->attr_group_name; ?></th>
                                                            <td><?php echo $poattri->attr_rolle; ?></td>
                                                        </tr>
                                                         <?php } ?>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="col-md-7">
                                                <div class="row">
                                                    <div class="bus-product">   
                                                        <?php
                                                            $proicon = base_url($this->config->item('theme_folder')."/images/generic-profile.png");
                                                            if($get_image->image_path != "")
                                                            {
                                                                $proicon = base_url("uploads/image/".$get_image->image_path); 
                                                            } 
                                                        ?>                                 
                                                        <img src="<?php echo $proicon; ?>" style="width: 300px;"/>
                                                    </div>
                                                   
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <table class="table">
                                                    <tbody>
                                                        <tr>
                                                            <th style="width: 17%;">Post Description :-</th>
                                                            <td><?php echo $setpost->post_description; ?></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                
                            </div>
                        </div>
                    </div>
                </section>
                
                
                
                <!---  =============== Post Add New End ===================================  -->
                
                
                
            </aside><!-- /.right-side -->
        </div><!-- /.content-wrapper -->
      
      <?php  $this->load->view("admin/common/common_footer"); ?>  

      
      <!-- Add the sidebar's background. This div must be placed
           immediately after the control sidebar -->
      <div class="control-sidebar-bg"></div>
    </div><!-- ./wrapper -->

    <?php  $this->load->view("admin/common/common_js"); ?>
    <script>
    
    var loc = window.location.pathname;
var current_dir = loc.substring(0, loc.lastIndexOf('/'));
var current_dir = document.location.origin + current_dir;
// Enable local "bootstrapTabs" plugin from /ckeditorPlugins/bootstrapTabs/ folder.

CKEDITOR.plugins.addExternal( 'bootstrapTabs', 'plugins/bootstrapTabs/', 'plugin.js' );

CKEDITOR.config.imageUploadUrl = 'plugins/uploadimage/imgupload.php';

CKEDITOR.replace( 'page_media', {
  extraPlugins: 'imageuploader,layoutmanager,basewidget,bootstrapTabs,embedbase,embed',
  removePlugins: 'elementspath,save,showblocks,smiley,templates,iframe,pagebreak,preview,flash,language,print,newpage,find,selectall,maximize,about',
  contentsCss: [ 'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css' ],
  on: {
    instanceReady: loadBootstrap,
    mode: loadBootstrap,
    focus: loadBootstrap
  }
});
// Add the necessary jQuery and Bootstrap JS source so that tabs are clickable.
// If you're already using Bootstrap JS with your editor instances, then this is not necessary.
function loadBootstrap(event) {
    
    if (event.name == 'mode' && event.editor.mode == 'source')
        return; // Skip loading jQuery and Bootstrap when switching to source mode.

    var jQueryScriptTag = document.createElement('script');
    var bootstrapScriptTag = document.createElement('script');

    jQueryScriptTag.src = 'https://code.jquery.com/jquery-1.11.3.min.js';
    bootstrapScriptTag.src = 'assets/global/plugins/bootstrap/js/bootstrap.min_3.6.js';

    var editorHead = event.editor.document.$.head;

    editorHead.appendChild(jQueryScriptTag);
    jQueryScriptTag.onload = function() {
      editorHead.appendChild(bootstrapScriptTag);
    };
}
    </script>
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
  </body>
</html>
