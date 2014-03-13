<?php
mysql_connect("dbserver","njouanla","pygmyproject");
mysql_select_db("njouanla");
$sql=mysql_query("SELECT report FROM report WHERE name = '$name'");

$result = mysql_num_rows($sql);

if($result > 5)
	$output='false';
else
	$output='true';
print(json_encode($output));
mysql_close();
?>
