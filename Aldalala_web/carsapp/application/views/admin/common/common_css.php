    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><?php echo $this->config->item("app_name"); ?></title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="<?php echo base_url($this->config->item("theme_admin")."/bootstrap/css/bootstrap.min.css"); ?>" />
    <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- DataTables -->
    <link rel="stylesheet" href="<?php echo base_url($this->config->item("theme_admin")."/plugins/datatables/dataTables.bootstrap.css"); ?>">
    <!-- Theme style -->
    <link rel="stylesheet" href="<?php echo base_url($this->config->item("theme_admin")."/dist/css/AdminLTE.css"); ?>">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="<?php echo base_url($this->config->item("theme_admin")."/dist/css/skins/skin-blue.css"); ?>">
    <?php echo link_tag( $this->config->item('theme_admin').'/plugins/chosen/chosen.min.css'); ?>
    <?php echo link_tag( $this->config->item('theme_admin').'/plugins/datepicker/jquery.timepicker.min.css'); ?>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
           <style type="text/css">
        .controls {
          margin-top: 10px;
          border: 1px solid transparent;
          border-radius: 2px 0 0 2px;
          box-sizing: border-box;
          -moz-box-sizing: border-box;
          height: 32px;
          outline: none;
          box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
        }
        
        #pac-input {
          background-color: #fff;
          font-family: Roboto;
          font-size: 15px;
          font-weight: 300;
          margin-left: 12px;
          padding: 0 11px 0 13px;
          text-overflow: ellipsis;
          width: 100%;
        }
        
        #pac-input:focus {
          border-color: #4d90fe;
        }
        .pac-container {
          font-family: Roboto;
        }
        
        #type-selector {
          color: #fff;
          background-color: #4d90fe;
          padding: 5px 11px 0px 11px;
        }
        
        #type-selector label {
          font-family: Roboto;
          font-size: 13px;
          font-weight: 300;
        }
        #target {
                width: 200px;
              }
        .mapbox{max-height: 450px; border: 1px solid #ccc; height: 250px;}
        #map{height: 100%; width: 100%; text-align: center; vertical-align: middle;}
        .gm-style{left: 100px; top: 100px;}
      
      </style>
