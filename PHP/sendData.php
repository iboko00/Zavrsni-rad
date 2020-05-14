<?php
require '../includes/DbOperations.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['time']) and isset($_POST['device'])and isset($_POST['sensor'])and isset($_POST['x'])and isset($_POST['y'])and isset($_POST['z'])){
        $db= new DbOperations();
        if($db->sendreading($_POST['time'],$_POST['device'],$_POST['sensor'],$_POST['x'],$_POST['y'],$_POST['z'])){
            $response['error']=false;
            $response['message']="Data sent successfully";
        }else{
            $response['error']=true;
            $response['message']="Some error occurred. Please try again.";
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

