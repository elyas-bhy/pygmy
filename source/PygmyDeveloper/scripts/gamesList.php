<?php
mysql_connect("dbserver","njouanla","pygmyproject");
mysql_select_db("njouanla");
$sql=mysql_query("SELECT id_game, name, resume, filename, version FROM game");
while($row=mysql_fetch_assoc($sql))
$output[]=$row;
print(json_encode($output));
mysql_close();
?>
