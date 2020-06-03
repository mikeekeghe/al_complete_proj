<tr>
                        <td><?php echo $list->user_fullname; ?></td>
                        <td><?php echo $list->appointment_date; ?></td>
                        <td><?php echo  date("H:i", strtotime($list->start_time)) ;
                        echo "  - " ;
                        if($list->time_token == "1"){
                            echo "Morning";
                        }else if($list->time_token == "2"){
                            echo "After Noon";
                        }else if($list->time_token == "3"){
                            echo "Evening";
                        }
                         ?></td>
                         <td><a href="<?php echo site_url("business/appointment_service/".$list->id); ?>" class="btn btn-warning btn-sm">View</a></td>
                        <td>
  <input type="checkbox" class="toggle tgl_checkbox"  data-table="business_appointment" data-status="status" data-idfield="id"  data-id="<?php echo $list->id; ?>" id='cb_<?php echo $list->id; ?>'  <?php echo ($list->status==1)? "checked" : ""; ?>  />
  <label for="cb_<?php echo $list->id; ?>"></label>
</td>
                        <td><label class='tgl-btn' for='cb_<?php echo $list->id; ?>'></label><?php echo ($list->status==1)? "Complete" : "Pending"; ?></td>                     
                        <td>
                            
                            <a href="<?php echo site_url("business/delete_business_appointment/".$list->id); ?>" onclick="return confirm('Are you sure to delete..?')" class="btn btn-danger btn-sm"><i class="fa fa-remove"></i></a>
                        </td>
                    </tr>