<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
  <head>
        <?php $this->load->view("common/head"); ?>
  </head>
  <body>
    <?php $this->load->view("common/header"); ?>
<div class="container">
<div class="row">
    <div class="col-md-8 col-md-offset-2 margin-top-20">
    <?php echo $this->session->flashdata("message"); ?>
                <div class="col-md-6"> 
                        <div class="">
                            <div class="card card-container">
                                <div class="panel panel-default">
                                    <div class="panel-body padding-20">
                                <h3>Login</h3>
                                <form class="form-signin" method="post" action="">
                                    <span id="reauth-email" class="reauth-email"></span>
                                    <input type="email" name="email" id="inputEmail" class="form-control" placeholder="User Name" required autofocus>
                                    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
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
                                    </div>
                                </div>
                            </div><!-- /card-container -->
                        </div><!-- /container -->
                </div>
                <div class="col-md-6">
                <div class="register-box">
                        <a href="<?php echo site_url("register"); ?>" class="btn tf-btn register btn-default">Register New</a>
                        <p>If you are authorized salon parlar then you can send us your registration request.</p>
                </div>
                </div>
            </div>
    </div>
</div>    

    <?php $this->load->view("common/footer_bottom"); ?>
    <?php $this->load->view("common/common_js"); ?>

  </body>
</html>            