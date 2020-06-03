<!DOCTYPE html>
<html>
  <head>
    
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
                        Add Post
                        <small>Preview</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Post</a></li>
                        <li class="active">Add Post</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <?php  echo $this->session->flashdata("message"); ?>
                            <!-- general form elements -->
                            <div class="box box-primary">
                                <div class="box-header">
                                    <h3 class="box-title">Add Post</h3>
                                </div><!-- /.box-header -->
                                <!-- form start -->
                                <form action="" method="post" enctype="multipart/form-data">
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="bus_title">Post Admin</label>
                                                    <select class="choosen form-control" name="user_id">
                                                        <option value="">Choose Clinic Admin</option>
                                                    <?php foreach($users as $user){
                                                        ?>
                                                        <option value="<?php echo $user->user_id ?>"><?php echo $user->user_fullname.", ". $user->user_email; ?></option>
                                                        <?php
                                                    }
                                                    ?>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="">Post Title : <span class="text-danger">*</span></label>
                                                    <input type="text" name="post_title" class="form-control" placeholder="Post Title"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="">Post Description :<span class="text-danger">*</span></label>
                                                    <textarea class="ckeditor form-control" name="post_description" id="page_media" rows="6" data-error-container="#editor2_error"><?php if(isset($_REQUEST["post_description"])){echo $_REQUEST["post_description"];} ?></textarea>
                                					<div id="editor2_error">
                                					</div>
                                            
                                                </div>
                                                <!--<div class="form-group">
                                                    <label class="">Post Description :</label>
                                                    <textarea name="post_description" class="textarea" placeholder="Place some text here" style="width: 100%; height: 120px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;"></textarea>
                                                </div>-->
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="">Post Visible : <span class="text-danger">*</span></label>
                                                    <input type="text" name="visible_limit" class="form-control" placeholder="Post Visible"/>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="">Post Km : <span class="text-danger">*</span></label>
                                                    <input type="text" name="post_km" class="form-control" placeholder="Post Km"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="">Post Hand : <span class="text-danger">*</span></label>
                                                    <input type="text" name="post_hand" class="form-control" placeholder="Post Hand"/>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="">Post Year : <span class="text-danger">*</span></label>
                                                    <input type="text" name="post_year" class="form-control" placeholder="Post Year"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                        <div class="form-group">
                                            <div class="col-sm-2">
                                                <div class="bus-product thumbnail">   
                                                            <?php
                                                            $proicon = base_url($this->config->item('theme_folder')."/images/generic-profile.png");
                                                            ?>                                 
                                                        <div class="pro-icon" style="background-image: url('<?php echo $proicon; ?>'); height: 100px; width: 100%;"></div>
                                                    </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="form-group">
                                                <label class="">Attribute Image</label>
                                                <input type="file" name="image_path" onchange="readURL(this)" multiple=""  />
                                                </div>
                                            </div>
                                        </div>
                                        </div>
                                        <div class="form-group"> 
                                            <div class="radio-inline">
                                                <label>
                                                    <input type="radio" name="post_status" id="optionsRadios1" value="1" checked/>
                                                    Actvie
                                                </label>
                                            </div>
                                            <div class="radio-inline">
                                                <label>
                                                    <input type="radio" name="post_status" id="optionsRadios2" value="0"/>
                                                    Deactive
                                                </label>
                                            </div>
                                            <p class="help-block">Set Post Status.</p>
                                        </div>
                                    </div><!-- /.box-body -->

                                    <div class="box-footer">
                                        <input type="submit" class="btn btn-primary" name="addpost" value="Add Post" />
                                       
                                    </div>
                                </form>
                            </div><!-- /.box -->
                        </div>
                    </div>
                    <!-- Main row -->
                </section><!-- /.content -->
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
   function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    jQuery('.pro-icon').css("background-image","url("+e.target.result+")");
                };

                reader.readAsDataURL(input.files[0]);
            }
        }
   </script> 
       <style>
       .pro-icon { background-position: center; background-size: cover; background-repeat: no-repeat; }
       </style>   
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
