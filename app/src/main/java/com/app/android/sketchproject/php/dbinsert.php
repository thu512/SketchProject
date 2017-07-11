
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
	$name = $_POST['name'];
	$email = $_POST['email'];

	$result = mysqli_query($conn, "insert into user values ('$id','$pw','$name','$email')");
	
	
	
	mysqli_close($conn);

?>
