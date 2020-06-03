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
    <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
		<form method="post" enctype="multipart/form-data" action="">
			<div class="form-header text-center">
            <h2 class="text-center">Sign Up</h2>
            <small>It's free and always will be.</small>
            <?php echo $this->session->flashdata("message"); ?>
            </div>
			<hr class="colorgraph" />
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12">
					<div class="form-group">
                        <input type="text" name="fullname" class="form-control" data-validation="required" placeholder="Full Name *" tabindex="1" required="" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12">
					<div class="form-group">
						<input type="email" name="email" class="form-control" data-validation="required email server"  data-validation-url="<?php echo site_url("register/is_email_exist"); ?>" placeholder="Email *" tabindex="6" required="" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-md-6">
					<div class="form-group">
						<input type="password" name="password" class="form-control" placeholder="Password *" data-validation="required length" data-validation-length="min8" tabindex="8" required="" />
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-6">
					<div class="form-group">
						<input type="password" name="password_confirm" class="form-control" placeholder="Confirm Password *" data-validation="required confirmation length" data-validation-length="min8" data-validation-confirm="password" tabindex="9" required="" />
					</div>
				</div>
                <div class="col-xs-12 col-sm-6 col-md-6">
					<div class="form-group">
						<input type="tel" name="phone" class="form-control" placeholder="Mobile Number" tabindex="10" required="" data-validation="required" maxlength="13" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12">
					<span class="button-checkbox">
						<label><input type="checkbox" name="accept_terms" id="accept_terms" data-validation="required" /> By clicking <strong class="label label-primary">Register</strong>, you agree to the <a href="#" data-toggle="modal" data-target="#t_and_c_m">Terms and Conditions</a> set out by this site, including our Cookie Use.</label>
					</span>
				</div>
				
			</div>
			<button type="submit" value="Register" name="add" class="btn tf-btn btn-default" tabindex="12">Submit</button>
		</form>
  </div>
</div>
</div>    

    <?php $this->load->view("common/footer_bottom"); ?>
    <?php $this->load->view("common/common_js"); ?>

  </body>
</html>