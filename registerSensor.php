

<?php
require '../includes/DbOperations.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['type']) and isset($_POST['name'])and isset($_POST['vendor'])and isset($_POST['version'])and isset($_POST['resolution'])and isset($_POST['range'])and isset($_POST['mindelay'])and isset($_POST['maxdelay'])and isset($_POST['power'])and isset($_POST['deviceid'])){
    $db= new DbOperations();
        if($db->createsensor((int)$_POST['type'],$_POST['name'],$_POST['vendor'],$_POST['version'],$_POST['resolution'],$_POST['range'],$_POST['mindelay'],$_POST['maxdelay'],$_POST['power'],$_POST['deviceid'])){
            $response['error']=false;
            $response['message']="Sensors registered successfully";
        }else{
            $response['error']=true;
            $response['message']="Some error occurred, or your sensors are already registered. Please try again.";
        }
    }else{
        $response['error']=true;
        $response['message']="Required fields are missing";
    }
}else{
    $response['error']=true;
    $response['message']="Invalid request";
}
echo json_encode($response);

