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

		<?php include '../header.php' ?>
		
		<div id="corps">
			
					<h1> Ajout d'un utilisateur</h1>
					<h2><?php
					$host = "dbserver"; //dbserver
					$user = "njouanla";  //njouanla
					$password = "pygmyproject"; //pygmyproject
					$conn = mysql_connect($host,$user,$password) or die ("Error. You are not registered");
					mysql_select_db("njouanla") or die ("DB PB");
	
					$login = $_REQUEST["Login"];
					$pass = $_REQUEST["Password"];
					$mail = $_REQUEST["Email"];
					
					if(!empty($_POST['Login']) AND !empty($_POST['Password']) AND !empty($_POST['Email']))
					{
						$check = "SELECT * FROM developer WHERE username = '$login' ";
						$check_log = mysql_query($check);
						
						if (mysql_num_rows($check_log) == 0) 
						{
							$query = 'INSERT INTO developer(username, password, email) VALUES ("'.$login.'","'.md5($pass).'","'.$mail.'")';
							mysql_query($query);
		
							mysql_close($conn);
	
							echo "Registration success<br>";
							echo '<meta http-equiv="refresh" content="1; URL=ConnexionSite.php">';
						}
						
						else
						{
							echo 'Login already exists.';
							echo '<meta http-equiv="refresh" content="1; URL=Register.php">';
						}
					}
					else
					{
						echo 'You need to renseign all the fields';
						echo '<meta http-equiv="refresh" content="1; URL=Register.php">';
					}

					?>
			</div>

		</div>
		
		<?php 
			include("../feet.html"); 
		?> 

		
	</div>

</body>
</html>
