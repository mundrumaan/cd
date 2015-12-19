<?php
	error_reporting(E_ALL ^ E_DEPRECATED);

	$response = array();
	require_once __DIR__.'/db_connect.php';
	$db = new DB_CONNECT();
	
	//check for post data
	if(isset($_POST['uid']) && isset($_POST['vid'])){
		$uid = $_POST['uid'];
		$vid = $_POST['vid'];
		
		$result = mysql_query("SELECT * FROM user WHERE vid = $vid");

		//echo($result);
		
		if(!empty($result)){
			if(mysql_num_rows($result) > 0){
				$result = mysql_fetch_array($result);
				//echo "vishal";
				$user = array();
				if($uid == $result["uid"] && $result["partych"] == null){
					$response["success"] = 1;
					$response["message"] = "Successfully logged In";
				}else if($uid == $result["uid"] && $result["partych"] != null){
					$response["success"] = 0;
					$response["message"] = "Already Voted with this Voter Card";
				}else{
					$response["success"] = 0;
					$response["message"] = "Aadhar UID and Voter ID didn't match";
				}
			}else{
				$response["success"] = 0;
				$response["message"] = "Voter ID not found";
			}
		}else{
			$response["success"] = 0;
			$response["message"] = "Voter ID not found";
		}
	}else{
		$response["success"] = 0;
		$response["message"] = "Enter Valid UID and VID";
	}
	echo json_encode($response);
?>