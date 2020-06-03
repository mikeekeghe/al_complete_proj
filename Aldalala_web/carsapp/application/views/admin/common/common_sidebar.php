<aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
          <!-- Sidebar user panel -->
          <div class="user-panel">
            <div class="pull-left image">
              <img src="<?php echo base_url($this->config->item("theme_admin")."/img/avatar.png"); ?>" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
              <p><?php _get_current_user_name($this); ?></p>
              <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
          </div>
          
          <!-- /.search form -->
          <!-- sidebar menu: : style can be found in sidebar.less -->
          <ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>
            <li class="active treeview">
              <a href="<?php echo site_url("admin/dashboard"); ?>">
                <i class="fa fa-dashboard"></i> <span>Dashboard</span>  
              </a>
            </li>
            
            <?php if(_get_current_user_type_id($this)==0){ ?>
            <li  class="<?php echo _is_active_menu($this,array(),array("area_country","area_city","area_locality")); ?>">
              <a href="#">
                <i class="fa fa-location-arrow"></i> <span>Area Management</span> <i class="fa fa-angle-left pull-right"></i> <small class="label pull-right bg-green"></small>
              </a>
              <ul class="treeview-menu">
                        <li  class="<?php echo _is_active_menu($this,array(),array("area_country")); ?>"><a href="<?php echo site_url("admin/area_country"); ?>"><i class="fa fa-circle-o"></i>Country</a></li>
                        <li class="<?php echo _is_active_menu($this,array(),array("area_city")); ?>"><a href="<?php echo site_url("admin/area_city"); ?>"><i class="fa fa-circle-o"></i>Cities</a></li>
                      <!--  <li class="<?php echo _is_active_menu($this,array(),array("area_locality")); ?>"><a href="<?php echo site_url("admin/area_locality"); ?>"><i class="fa fa-circle-o"></i>Locality</a></li>-->
                       
              </ul>
            </li>
 
            
            <li class="<?php echo _is_active_menu($this,array(),array("add_user","listuser")); ?>">
              <a href="#">
                <i class="fa fa-user"></i> <span>User Management</span> <i class="fa fa-angle-left pull-right"></i> <small class="label pull-right bg-green"></small>
              </a>
              <ul class="treeview-menu">
                        
                        <li class="<?php echo _is_active_menu($this,array(),array("listuser")); ?>"><a href="<?php echo site_url("admin/listappuser/1"); ?>"><i class="fa fa-circle-o"></i>List App Register</a></li>
                        
                        <li class="<?php echo _is_active_menu($this,array(),array("listuser")); ?>"><a href="<?php echo site_url("admin/listuser/0"); ?>"><i class="fa fa-circle-o"></i>List Admin</a></li>
                        
              </ul>
            </li>
             <?php } ?>
              <!------------------- Attribute Group   ---------------------------->
             
             <?php if(_get_current_user_type_id($this)==0){ ?>
            <li class="<?php echo _is_active_menu($this,array(),array("add_attribute_group","listattributegroup")); ?>">
              <a href="#">
                <i class="fa fa-list-alt "></i> <span>Attribute Group</span> <i class="fa fa-angle-left pull-right"></i> <small class="label pull-right bg-green"></small>
              </a>
              <ul class="treeview-menu">
                        <li class="<?php echo _is_active_menu($this,array(),array("add_attribute_group")); ?>"><a href="<?php echo site_url("admin/add_attribute_group"); ?>"><i class="fa fa-circle-o"></i>Add Atribute Group</a></li>
                        <li class="<?php echo _is_active_menu($this,array(),array("listattributegroup")); ?>"><a href="<?php echo site_url("admin/listattributegroup"); ?>"><i class="fa fa-circle-o"></i>List Attribute Group</a></li>
              </ul>
            </li>
             <?php } ?>
             
             <!----------------  Attribute Group End------------------------------->
             <!--------------------- Attribute -------------------------->
             
             <?php if(_get_current_user_type_id($this)==0){ ?>
            <li class="<?php echo _is_active_menu($this,array(),array("add_attribute","listattribute")); ?>">
              <a href="#">
                <i class="fa fa-star"></i> <span>Attribute</span> <i class="fa fa-angle-left pull-right"></i> <small class="label pull-right bg-green"></small>
              </a>
              <ul class="treeview-menu">
                        <li class="<?php echo _is_active_menu($this,array(),array("add_attribute")); ?>"><a href="<?php echo site_url("admin/add_attribute"); ?>"><i class="fa fa-circle-o"></i>Add Atribute</a></li>
                        <li class="<?php echo _is_active_menu($this,array(),array("listattribute")); ?>"><a href="<?php echo site_url("admin/listattribute"); ?>"><i class="fa fa-circle-o"></i>List Attribute</a></li>
              </ul>
            </li>
             <?php } ?>
             
             <!-------------------- Attributr End --------------------------->
             <!--------------------- Post Add -------------------------->
             
             <?php if(_get_current_user_type_id($this)==0){ ?>
            <li class="<?php echo _is_active_menu($this,array(),array("add_post","listpost")); ?>">
              <a href="#">
                <i class="fa fa-sticky-note "></i> <span>Post</span> <i class="fa fa-angle-left pull-right"></i> <small class="label pull-right bg-green"></small>
              </a>
              <ul class="treeview-menu">
                       <li class="<?php echo _is_active_menu($this,array(),array("add_post")); ?>"><a href="<?php echo site_url("admin/add_post"); ?>"><i class="fa fa-circle-o"></i>Add Post</a></li>
                       <li class=" <li class="<?php echo _is_active_menu($this,array(),array("listpost")); ?>"><a href="<?php echo site_url("admin/listpost"); ?>"><i class="fa fa-circle-o"></i>List Post</a></li>
                        <li class="<?php echo _is_active_menu($this,array(),array("listpost_rent")); ?>"><a href="<?php echo site_url("admin/listpost_rent"); ?>"><i class="fa fa-circle-o"></i>List Rent Post</a></li>
              </ul>
            </li>
             <?php } ?>
             
             <!-------------------- Post End --------------------------->
              <!--------------------- Image List -------------------------->
             
             <?php if(_get_current_user_type_id($this)==0){ ?>
            <li class="<?php echo _is_active_menu($this,array(),array("listimage")); ?>">
              <a href="<?php echo site_url("admin/listimage"); ?>">
                <i class="fa fa-picture-o"></i> <span>List Image </span> <i class="fa fa-angle-left pull-right"></i>  
              </a>
            </li>
             <?php } ?>
             
             <!-------------------- Image List End --------------------------->
 
              <!-------------------- Slider ------------------------------------->
               <?php if(_get_current_user_type_id($this)==0){ ?>
              <li class="<?php echo _is_active_menu($this,array(),array("admin","listslider")); ?>">
              <a href="#">
                <i class="fa fa-picture-o"></i>
                <span> Slider </span>
                <span class="label label-primary pull-right"></span><i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="<?php echo site_url("admin/listslider"); ?>"><i class="fa fa-circle-o"></i> List</a></li>
                <li><a href="<?php echo site_url("admin/addslider"); ?>"><i class="fa fa-circle-o"></i>  Add New  </a></li>
              
              </ul>
            </li>
              <?php } ?> 
             <!-------------------- End Slider --------------------------------->
             
             <!-------------------- Notification -------------------------------> 
               <?php if(_get_current_user_type_id($this)==0){ ?>
              <li  class="<?php echo _is_active_menu($this,array(),array("admin","manage_notification")); ?>">
              <a href="#">
                <i class="fa fa-bell"></i> Notification<i class="fa fa-angle-left pull-right "></i>
              </a>
              <ul class="treeview-menu">
                          
             <li>
              <a href="<?php echo site_url("admin/manage_notification"); ?>">
                <i class="fa fa-bell"></i> <span>List Notification</span> <small class="label pull-right bg-green"></small>
              </a>
            </li> 
                        
              </ul>
            </li> 
              <?php } ?>  
             <!-------------------- End Notification --------------------------->
          </ul>
        </section>
        <!-- /.sidebar -->
      </aside>