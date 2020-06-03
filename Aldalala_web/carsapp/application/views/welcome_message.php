<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
  <head>
        <?php $this->load->view("common/head"); ?>
  </head>
  <body>
    <?php $this->load->view("common/header"); ?>
    <?php $this->load->view("common/search_top"); ?>
    <div id="tf-home" class="text-center" style="margin-top: 60px; " >
        <div class="overlay">
            <div class="content">
                <div class="container">
                    
                    <div class="hk_category">
                        <div class="hk_heading">
                            <h2 class="hk_header">Services</h2>
                        </div>
                    <?php foreach($categories as $category){
                        ?>
                        <div class="hk_ca_block">
                            <div>
                                <a href="<?php echo site_url("search/".$category->slug); ?>"><img src="<?php echo base_url("uploads/admin/category/".$category->image); ?>" /></a>
                            </div>
                            <span><?php echo $category->title; ?></span>
                        </div>
                        <?php
                    } ?>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <?php $this->load->view("common/footer_bottom"); ?>
    <?php $this->load->view("common/common_js"); ?>

  </body>
</html>