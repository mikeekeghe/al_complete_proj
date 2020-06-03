<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
  <head>
    <!-- Basic Page Needs
    ================================================== -->
    <meta charset="utf-8">
    <!--[if IE]><meta http-equiv="x-ua-compatible" content="IE=9" /><![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>HairKat - Digital Agency One Page Template</title>
    <meta name="description" content="HairKat is a Digital agency one page template built based on bootstrap framework. This template is design by Robert Berki and coded by Jenn Pereira. It is simple, mobile responsive, perfect for portfolio and agency websites. Get this for free exclusively at ThemeForces.com">
    <meta name="keywords" content="bootstrap theme, portfolio template, digital agency, onepage, mobile responsive, spirit8, free website, free theme, themeforces themes, themeforces wordpress themes, themeforces bootstrap theme">
    <meta name="author" content="ThemeForces.com">
    
    <!-- Favicons
    ================================================== -->
    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon">
    <link rel="apple-touch-icon" href="img/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="img/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="img/apple-touch-icon-114x114.png">

    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css"  href="<?php echo base_url($this->config->item("default_theme")); ?>/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="<?php echo base_url($this->config->item("default_theme")); ?>/fonts/font-awesome/css/font-awesome.css">


    <!-- Stylesheet
    ================================================== -->
    <link rel="stylesheet" type="text/css"  href="<?php echo base_url($this->config->item("default_theme")); ?>/css/style.css">
    <link rel="stylesheet" type="text/css" href="<?php echo base_url($this->config->item("default_theme")); ?>/css/responsive.css">

    <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,100italic,300italic,400italic,700italic,900italic' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,700,300,600,800,400' rel='stylesheet' type='text/css'>

    <script type="text/javascript" src="<?php echo base_url($this->config->item("default_theme")); ?>/js/modernizr.custom.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <!-- Navigation
    ==========================================-->
    <nav id="tf-menu" class="navbar navbar-default  on">
      <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="index.html">HairKat</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" >
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#tf-home" class="page-scroll">Home</a></li>
            <li><a href="#tf-about" class="page-scroll">About</a></li>
            <li><a href="#tf-team" class="page-scroll">Team</a></li>
            <li><a href="#tf-services" class="page-scroll">Services</a></li>
            <li><a href="#tf-works" class="page-scroll">Portfolio</a></li>
            <li><a href="#tf-testimonials" class="page-scroll">Testimonials</a></li>
            <li><a href="#tf-contact" class="page-scroll">Contact</a></li>
            <li><button class="btn tf-btn btn-default" data-toggle="modal" data-target="#myModal">SALON HOST</button></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Welcome to HairKat</h4>
      </div>
      <div class="modal-body">
            <div class="row">
                <div class="col-md-7"> 
                        <div class="">
                            <div class="card card-container">
                                <h3>Login with HairKat</h3>
                                <form class="form-signin">
                                    <span id="reauth-email" class="reauth-email"></span>
                                    <input type="email" id="inputEmail" class="form-control" placeholder="User Name" required autofocus>
                                    <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
                                    <div id="remember" class="checkbox">
                                        <label>
                                            <input type="checkbox" value="remember-me"> I'm Awesome, Remember Me!
                                        </label>
                                    </div>
                                    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Let's Go!</button>
                                </form><!-- /form -->
                                <a href="#" class="forgot-password">
                                    Forgot your password?
                                </a>
                            </div><!-- /card-container -->
                        </div><!-- /container -->
                </div>
                <div class="col-md-5">
                <div class="register-box">
                        <a href="#" class="btn tf-btn register btn-default">Register New</a>
                        <p>If you are authorized salon parlar then you can send us your registration request.</p>
                </div>
                </div>
            </div>
      </div>
      
    </div>
  </div>
</div>
<div class="container">
<div class="row">
    <div class="col-xs-12 col-sm-8 col-md-8 col-sm-offset-2 col-md-offset-2">
		
  </div>
</div>
</div>    
    <nav id="footer" class="navbar-fixed-bottom">
        <div class="container">
            <div class="pull-left fnav">
                <p>ALL RIGHTS RESERVED. COPYRIGHT © 2014. Designed by <a href="https://dribbble.com/shots/1817781--FREEBIE-HairKat-Digital-agency-one-page-template">Robert Berki</a> and Coded by <a href="https://dribbble.com/jennpereira">Jenn Pereira</a></p>
            </div>
            <div class="pull-right fnav">
                <ul class="footer-social">
                    <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                    <li><a href="#"><i class="fa fa-dribbble"></i></a></li>
                    <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                    <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                </ul>
            </div>
        </div>
    </nav>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>-->
    <script type="text/javascript" src="<?php echo base_url($this->config->item("default_theme")); ?>/js/jquery.1.11.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script type="text/javascript" src="<?php echo base_url($this->config->item("default_theme")); ?>/js/bootstrap.js"></script>
    
    
  </body>
</html>