<?php
 error_reporting(E_ALL ^ E_DEPRECATED);

	define('DB_USER',"root");
	define('DB_PASSWORD',"");
	define('DB_DATABASE',"voting");
	define('DB_SERVER',"localhost");
	$response = array();
	$connect = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD);
	$db = mysql_select_db(DB_DATABASE);
	
	$res = mysql_query("SELECT * from user");
	$response["users"] = array();
	
	while($row = mysql_fetch_array($res)){
			$users = array();
			$users["uid"] = $row["uid"];
			$users["vid"] = $row["vid"];
			
			array_push($response["users"], $users);
		}
		
		echo json_encode($response);
	
	

?>