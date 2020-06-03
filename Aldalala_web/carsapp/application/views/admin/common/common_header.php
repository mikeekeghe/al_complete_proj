<header class="main-header">
        <!-- Logo -->
        <a href="<?php echo site_url(_get_user_redirect($this)); ?>" class="logo">
          <!-- mini logo for sidebar mini 50x50 pixels -->
          <span class="logo-mini"><b><?php echo $this->config->item("app_name"); ?></b></span>
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><?php echo $this->config->item("app_name"); ?></span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
              <!-- User Account: style can be found in dropdown.less -->
              <li class="dropdown user user-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <img src="<?php echo base_url($this->config->item("theme_admin")."/img/avatar.png"); ?>" class="user-image" alt="User Image">
                  <span class="hidden-xs"><?php echo _get_current_user_name($this); ?></span>
                </a>
                <ul class="dropdown-menu">
                  <!-- User image -->
                  <li class="user-header">
                    <img src="<?php echo base_url($this->config->item("theme_admin")."/img/avatar.png"); ?>" class="img-circle" alt="User Image">
                    <p>
                      <?php echo _get_current_user_name($this); ?>
                       
                    </p>
                  </li>
                  <!-- Menu Body -->
                 
                  <!-- Menu Footer-->
                  <li class="user-footer">
                   
                    <div class="pull-right">
                      <a href="<?php echo site_url("admin/signout") ?>" class="btn btn-default btn-flat">Sign out</a>
                    </div>
                  </li>
                </ul>
              </li>
             
              
            </ul>
          </div>
        </nav>
      </header>