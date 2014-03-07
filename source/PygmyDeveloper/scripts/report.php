<?php
mysql_connect("dbserver","njouanla","pygmyproject");
mysql_select_db("njouanla");

$report = $_POST['report'];
$name = $_POST['name'];

if(!empty($_POST['report']) AND !empty($_POST['name'])){

$sql=mysql_query("INSERT INTO report (report, name) VALUES ('$report', '$name')");
}

mysql_close();
?>
