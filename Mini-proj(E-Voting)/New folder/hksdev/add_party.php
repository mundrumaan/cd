<?php
	//this will create a new row to add new user to database
	$response = array();
	
	//check for required fields
	if(isset($_POST['pid']) && isset($_POST['pname']) && isset($_POST['cname'])){
		$pid = $_POST['pid'];
		$pname = $_POST['pname'];
		$cname = $_POST['cname'];
		
		require_once __DIR__.'/db_connect.php';
		//include db_connect class
		$db = new DB_CONNECT();
		//mysql inserting a new row
		$result = mysql_query("INSERT INTO party_details(party_id,party_name,candidate_name) VALUES('$pid','$pname','$cname')");
		if($result){
			//successfully inserted row
			$response["success"] = 1;
			$response["message"] = "New Party Details Successfully added";
			echo json_encode($response);
		}else{
			//failed to insert row
			$response["success"] = 0;
			$response["message"] = "Oops! An error occurred.";
			echo json_encode($response);
		}
	}else{
		//required fields missing
		$response["success"] = 0;
		$response["message"] = "Required fields(s) is missing";
		echo json_encode($response);
	
	}	

?>