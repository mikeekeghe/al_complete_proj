<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
  <head>
        <?php $this->load->view("common/head"); ?>
        <link rel="stylesheet" type="text/css"  href="<?php echo base_url($this->config->item("default_theme")); ?>/css/lightbox.css" />
        <link rel="stylesheet" type="text/css"  href="<?php echo base_url($this->config->item("default_theme")); ?>/css/rating.css" />
  </head>
  <body>
    <?php $this->load->view("common/header"); ?>
	      
      <div class=" nopading">
        <div class="container"> 
        
                
                   <div class="row">
                        <div id="signupbox" style=" margin-top:50px" class="col-md-12">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="panel-title">Appointments</div>
                                            
                                        </div>  
                                        <div class="panel-body" >
                                            <?php foreach($appointments as $app){
                                               ?>
                                               <div class="panel panel-default">
                                                    <div class="panel-body">
                                                       <div class="row">
                                                            <div class="col-md-4">
                                                                <strong><?php echo $app->bus_title; ?></strong>
                                                                <div><span>Date : <?php echo date("d M, Y", strtotime($app->appointment_date)); ?></span></div>
                                                                <div><span>Time : <?php echo date("H:i A", strtotime($app->start_time)); ?></span></div>
                                                                <div><span>Fees : <?php echo $app->payment_amount." ".$app->currency; ?></span></div>
                                                                <a href="" class="btn btn-default" data-toggle="modal" data-target="#mapModal" data-lat='<?php echo $app->bus_latitude; ?>' data-lon='<?php echo $app->bus_longitude; ?>' data-desc='<?php echo $app->bus_google_street; ?>' ><i class="glyphicon glyphicon-map-marker"></i> Map Location</a>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <strong>Visit At :</strong> <br />
                                                                <p><?php echo $app->bus_google_street; ?></p>
                                                                <strong>Contact :</strong> <br />
                                                                <p><?php echo $app->bus_contact; ?></p>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <div class="status pull-right">
                                                                    <?php if($app->status == 1){
                                                                        ?>
                                                                        <a href="<?php echo site_url("users/appointments/".$app->id."/cancel"); ?>" class="btn btn-default"  onclick="return confirm('Are you sure you want to cancle?');" >Do cancle</a>
                                                                        <?php
                                                                    }else if($app->status == 0){
                                                                        ?>
                                                                        <a href="#" class="btn btn-danger">Cancled by you</a>
                                                                        <?php
                                                                    }else if($app->status == 2){
                                                                        ?>
                                                                        <a href="#" class="btn btn-danger">Cancled</a>
                                                                        <?php
                                                                    }else if($app->status == 3){
                                                                        ?>
                                                                        <a href="#" class="btn btn-success">Visited</a>
                                                                        <?php
                                                                    } ?>
                                                                </div>
                                                            </div>
                                                       </div>
                                                    </div>
                                               </div>
                                               <?php 
                                            }?>
                                        </div>
                                    </div>
                         </div> 
                   </div>
                
    </div>
    </div>
    
<div class="modal fade" id="mapModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Welcome to HairKat</h4>
      </div>
      <div class="modal-body">
            <div id="map" style="width: 100%; height: 400px;"></div>
      </div>
      
    </div>
  </div>
</div>



    <?php $this->load->view("common/footer_bottom"); ?>
    <?php $this->load->view("common/common_js"); ?>
    

    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=<?php echo $this->config->item("google_place_api_key"); ?>&callback=initMap">
    </script>
    <script>
      
      function initMap(lat,lon, detailsmessage) {
        var uluru = {lat: lat, lng: lon};
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 15,
          center: uluru
        });
        var marker = new google.maps.Marker({
          position: uluru,
          map: map
        });
        //attachSecretMessage(marker,detailsmessage );
      }
      function attachSecretMessage(marker, secretMessage) {
          var infowindow = new google.maps.InfoWindow({
            content: secretMessage
          });
        
          marker.addListener('click', function() {
            infowindow.open(marker.get('map'), marker);
          });
      }
      $(document).ready(function(){
                        $('#mapModal').on('show.bs.modal', function (event) {
                
              var button = $(event.relatedTarget) // Button that triggered the modal
              var lat = button.data('lat'); // Extract info from data-* attributes
              var lon = button.data('lon');
              var desc = button.data('desc');
              
              var modal = $(this);
              initMap(lat,lon,desc);
        });
      });
    </script>
</body>
</html>