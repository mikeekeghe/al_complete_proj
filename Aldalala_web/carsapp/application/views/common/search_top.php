<div class="top-search" style="background-color: #ffffff;">
        <div class="content">
        <div class="container text-center">
            <form class="form-inline home-search" action="<?php echo site_url("search"); ?>" method="get">
                      <div class="form-group">
                        <select class="form-control" id="city_id" name="city">
                        <?php foreach($countries as $country){
                            ?>
                            <optgroup label="<?php echo $country->country_name; ?>" style="background-image: url(<?php echo base_url($this->config->item("theme_admin"))."/img/flags/".strtolower($country->iso_code_2).".png"; ?>);">
                                <?php foreach($cities as $city){
                                    if($city->country_id == $country->country_id){
                                    ?>
                                    <option value="<?php echo $city->city_id; ?>"><?php echo $city->city_name; ?></option>
                                    <?php    
                                    }
                                } ?>
                                
                            </optgroup>
                            <?php
                        } ?>
                        </select>
                      </div>
                        
                      <div class="form-group">
                            <select class="form-control" id="locality_id" name="locality">
                            </select>
                      </div>
                      <div class="form-group">
                        <input type="text" class="form-control" id="speciality" name="speciality" placeholder="Type your speciality" />
                      </div>
                      
                      <button type="submit" class="btn btn-primary"><i class="glyphicon glyphicon-search"></i></button>
                    </form>
        </div>
        </div>
    </div>