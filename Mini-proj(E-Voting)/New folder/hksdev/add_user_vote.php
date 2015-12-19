<?php

error_reporting(E_ALL ^ E_DEPRECATED);
	$response = array();
	if(isset($_POST['pid']) && isset($_POST['uid'])){
		$pid = $_POST['pid'];
		$uid = $_POST['uid'];
		
		require_once __DIR__.'/db_connect.php';
		$db = new DB_CONNECT();
		$result = mysql_query("UPDATE user SET partych = '$pid' where uid = $uid");
		
		if($result){
			$response["success"] = 1;
			$response["message"] = "Your vote has submitted successfully";
		}else{
			$response["success"] = 0;
			$response["message"] = "Server Error. Try After sometime.";
		}	
	}else{
		$response["success"] = 0;
		$response["message"] = "Required fields missing";
	}
	echo json_encode($response);
?>