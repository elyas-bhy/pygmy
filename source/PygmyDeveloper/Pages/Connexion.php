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
	   <link rel="stylesheet" media="screen" type="text/css" title="Design" href="Pygmy.css" /> 
   </head> 
<body>
	<div id="content">

		<div id="banner">

				<div id="login">
					<p><?php
						if(isset($_SESSION['islogged']) && $_SESSION['islogged'] == true){
							echo '<p>WELCOME ' .$_SESSION['Login'].' ! | <a href="Deconnexion.php">LOG OUT</a>';
						}
						else{
							echo '<p><a href="ConnexionSite.php">LOG IN</a> | <a href="Register.php">REGISTER</a></p>';
						}
					?></p>				
				</div>
		</div>

		
		<div id="corps">
			<div class="block">
					<h1>Connexion</h1>
					<p> 
					<?php
					
					error_reporting(E_ALL ^ E_NOTICE);
		$host = "dbserver"; //dbserver
		$user = "njouanla";  //njouanla
		$password = "pygmyproject"; //pygmyproject
		$conn = mysql_connect($host, $user, $password) or die (mysql_error());
		mysql_select_db("njouanla");
		$islogged = false;
		$_SESSION['islogged'] = $islogged;

		if(isset($_POST['Login']) 
		&& isset($_POST['Password']) 
		&& $_POST['Login'] != ''
		&& $_POST['Password'] != '')
			{	
			$query = "SELECT username, password, email FROM developer WHERE username = '".htmlspecialchars(addslashes($_POST['Login']))."' AND password = '".htmlspecialchars(addslashes($_POST['Password']))."';";
			
			$result = mysql_query($query);
			
			while ($row = mysql_fetch_array($result)) {

			$login = $row['username'];
			$password = $row['password'];
			$mail = $row['email'];
			}

			if($_POST['Login'] == $login && $_POST['Password'] == $password)
				{
					$islogged = true;

					$_SESSION['Login'] = $login;
					$_SESSION['Password'] = $password;
					$_SESSION['Email'] = $mail;
					$_SESSION['islogged'] = $islogged;
					echo 'Redirection';
					echo '<meta http-equiv="refresh" content="1; URL=PygmyLog_c.php"> <br/>';
					echo '<a href="PygmyLog.php" class="lien"> (Or click here.)</a></p>';
				}
			else
				{
					echo '<p id="error"> ! Wrong informations. Your are not logged. <br /><br/>';
					echo 'Redirection...';
					echo '<meta http-equiv="refresh" content="1; URL=ConnexionSite.php"> <br/>';
					echo '<a href="ConnexionSite.php" class="lien"> (Or click here.)</a></p>';
				}
			}	
			else
				{
				echo '<p id="error"> Fields not renseigned. !<br /><br/>';
				echo 'Redirection...';
				echo '<meta http-equiv="refresh" content="1; URL=ConnexionSite.php"> <br/>';
				echo '<a href="ConnexionSite.php" class="lien"> (Or click here.)</a></p>';
				}
	 
		?>
					</p>
			</div>

		</div>
		
		<?php 
			include("../feet.html"); 
		?> 

		
	</div>

</body>
</html>
