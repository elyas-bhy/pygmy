<?php
session_start();
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" > 
   <head> 
       <title>Pygmy</title> 
	   <meta http-equiv="content-language" content="fr" /> 
	   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" /> 
	   <meta name="description" content="" /> 
	   <meta name="keyword" content=""/> 
	   <link rel="shortcut icon" href="../Images/ic_launcher.png" /> 
	   <link rel="stylesheet" media="screen" type="text/css" title="Pages" href="Stylesheet/Pygmy.css" /> 
   </head> 
<body>
	<div id="content">

		<?php include '../headerConnect.php' ?>
		
		<div id="corps">
			
					<h2><?php
					$host = "dbserver"; 
					$user = "njouanla"; 
					$password = "pygmyproject";
					$conn = mysql_connect($host,$user,$password) or die ("Error. You are not registered");
					mysql_select_db("njouanla") or die ("DB PB");
	
					$username = $_GET['username'];
					// Remove developer account on the database (codes uploaded are conserved on the server)
					$query = "DELETE FROM developer WHERE username = '$username'";
					mysql_query($query);
					include '../scripts/database_backup.php';
					?>
					<img src="../Images/loading.gif" align="center" width=80/>
					<?php
					session_destroy();
					echo '<meta http-equiv="refresh" content="1; URL=../index.php"> <br/>';	
					?>

					</h2>
			</div>

		</div>
		
		<?php 
			include("../feet.html"); 
		?> 

		
	</div>

</body>
</html>
