<?php
	class DB_CONNECT{
		//constructor
		function __construct(){
			$this->connect();
		}
		//destructor
		function __destruct(){
			$this->close();
		}
		
		//connect db
		function connect(){
			//import db_connect
			require_once __DIR__.'/db_config.php';
			//connecting to mysql database
			$con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());
 
			//selecting database
			$db = mysql_select_db(DB_DATABASE) or die(mysql_error());
			
			//return connection cursor
			return $con;
		}
		//function to close db
		function close(){
			mysql_close();
		}
	}
?>