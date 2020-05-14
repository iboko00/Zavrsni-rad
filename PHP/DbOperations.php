<?php
class DbOperations{
    private $con;
    function __construct(){
        require dirname(__FILE__).'/DbConnect.php';
        $db=new DbConnect();
        $this->con=$db->connect();
    }
    //createSensorID
    function createsensor($type, $name, $vendor,$version,$resolution,$range,$mindelay,$maxdelay,$power,$deviceid){
        $stmt=$this->con->prepare("INSERT INTO `specs` (`Type`, `Name`, `Vendor`, `Version`, `Resolution`, `Sensor range`, `Min delay`, `Max delay`, `Power`, `DeviceID`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
        $stmt->bind_param("isssddddds",$type,$name,$vendor,$version,$resolution,$range,$mindelay,$maxdelay,$power,$deviceid);
        if($stmt->execute()){
            return true;
        }else{
            return false;
        }


    }

    //send reading from the sensor
    function sendreading($time, $device, $sensor, $x, $y, $z){
        $stmt=$this->con->prepare("INSERT INTO `data` (`Time`, `DeviceID`, `Sensor`, `x`, `y`, `z`) VALUES (?, ?, ?, ?, ?, ?);");
        $stmt->bind_param("sssddd",$time,$device,$sensor,$x,$y,$z);
        if($stmt->execute()){
            return true;
        }else{
            return false;
        }


    }
}
