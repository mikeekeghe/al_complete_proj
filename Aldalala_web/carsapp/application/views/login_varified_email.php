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
            <?php if(isset($error)){ echo $error; } ?>
                    <div class="row">    
                        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-12  col-sm-12">                    
                            <div class="panel panel-info" >
                                    <div class="panel-heading">
                                        <div class="panel-title">Email Confirmation</div>
                                    </div>     
                
                                    <div style="padding-top:30px" class="panel-body" >
                
                                        <?php
                                        if(isset($error)){ echo $error; }
                        if(isset($success)){
                            ?>
                            <h3 class="success text-success">Your email id is successfully verified please</h3>
                            <a href="<?php site_url("login"); ?>" class="button btn btn-success">Login Now</a>
                            <?
                        }
                        ?>    
                
                
                
                            </div>                     
                        </div>  
                </div>                
        </div>
</div>    

    <?php $this->load->view("common/footer_bottom"); ?>
    <?php $this->load->view("common/common_js"); ?>

  </body>
</html>