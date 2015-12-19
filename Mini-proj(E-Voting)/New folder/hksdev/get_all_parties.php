<?php

    error_reporting(E_ALL ^ E_DEPRECATED);

	$response = array();
	require_once __DIR__.'/db_connect.php';
	$db = new DB_CONNECT();
	
	$result = mysql_query("SELECT * FROM party_details") or die(mysql_error());
	if(mysql_num_rows($result) > 0){
		$response["parties"] = array();
		
		while($row = mysql_fetch_array($result)){
			$parties = array();
			$parties["pid"] = $row["party_id"];
			$parties["pname"] = $row["party_name"];
			$parties["cname"] = $row["candidate_name"];
			
			array_push($response["parties"], $parties);
		}
		$response["success"] = 1;
		echo json_encode($response);
	}else{
		$response["success"] = 0;
		$response["message"] = "No Parties Found";
		echo json_encode($response);
	}
?>