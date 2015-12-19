<?php
 error_reporting(E_ALL ^ E_DEPRECATED);
    
	define('DB_PASSWORD',"");
	define('DB_DATABASE',"voting");
	define('DB_SERVER',"localhost");
	$response = array();
	$connect = mysql_connect(DB_SERVER, DB_PASSWORD);
	$db = mysql_select_db(DB_DATABASE);

	$res2 = mysql_query("select count(partych) from user where partych in (Select party_id from party_details where party_name="Congress") ");
    echo "$res2";
    count(var)
    echo json_encode($response);

?>