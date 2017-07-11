
<?php
	
	$db_host = "localhost";
	$db_user = "root";
	$db_passwd = "1130";
	$db_name = "cambook";	
	
	$conn = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);
	
	mysqli_set_charset($con,"utf8mb4");
	
	mysqli_select_db($conn,$db_name);
	
	$id = $_POuST['id'];

	$result = mysqli_query($conn, "select count(*) from user where id='$id'");
	
	$row = mysqli_fetch_array($result);
	$data = $row[0];
	
	if($data){
	echo $data;
	}else{
	echo '0';
	}
	
	mysqli_close($conn);

?>
