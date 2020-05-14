<?php

class DbConnect{
    private $con=null;
    function _construct(){

    }
    function connect(){
        include_once dirname(__FILE__).'/constants.php';
        $this->con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        if(mysqli_connect_error()){
            echo "failed to connect to database".mysqli_connect_error();
        }else{
            return $this->con;
        }
    }
}