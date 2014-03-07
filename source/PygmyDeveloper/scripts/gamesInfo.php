<?php
mysql_connect("dbserver","njouanla","pygmyproject");
mysql_select_db("njouanla");

$name = $_POST['name'] ;

$sql=mysql_query("SELECT name, resume FROM game WHERE name = '$name'");
while($row=mysql_fetch_assoc($sql))
$output[]=$row;
print(json_encode($output));
mysql_close();
?>

