
<?php
	
	$db_host = "localhost";
	$db_user = "root";
	$db_passwd = "1130";
	$db_name = "cambook";
	
	
	$conn = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);
	
	mysqli_set_charset($con,"utf8mb4");
	
	mysqli_select_db($conn,$db_name);
	
	$id = $_POST['id'];
	$pw = $_POST['pw'];

	$result = mysqli_query($conn, "delete from user where id='$id' and pw='$pw'");
	
	mysqli_close($conn);

?>
