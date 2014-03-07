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
	   <link rel="shortcut icon" href="Images/ic_launcher.png" /> 
	   <link rel="stylesheet" media="screen" type="text/css" title="Pages" href="Pages/Pygmy.css" /> 
   </head> 
<body>
	<div id="content">

		<div id="banner">

				<div id="login">
					<p><?php
						if(isset($_SESSION['islogged']) && $_SESSION['islogged'] == true){
							echo '<p>WELCOME ' .$_SESSION['Login'].' ! | <a href="Pages/Deconnexion.php">LOG OUT</a>';
						}
						else{
							echo '<p><a href="Pages/ConnexionSite.php">LOG IN</a> | <a href="Pages/Register.php">REGISTER</a></p>';
						}
					?></p>				
				</div>
		</div>
		
				<div id="menu">
			<ul>
				<li><a href="index.php" class="current">ACCUEIL</a></li>
				<li><a href="Pages/Download.php" class="current">DOWNLOADS</a></li>
				<li><a href="Pages/Upload.php">UPLOAD YOUR CODE</a></li>
				<li><a href="Pages/PygmyLog.php">MY SETTINGS</a></li>
			</ul>
		</div>

		
		<div id="corps">
				

		<div id="id_block">
			
			<div class="package log">
				<h1>Log In</h1>
					<form method="post" action="Pages/Connexion.php">
					<p> Login : <input name="Login" type="text" /> </p>
					<p> Password : <input name="Password" type="password" /> </p> 
					<input name="submit" type="submit" value="LOG IN"/>
					<input name="reset" type="reset" value="RESET"/></form>
			</div>
			
			<div class="package register">
				<h1>Sign In</h1>
					<form method="post" action="Pages/RegisterDev.php">
						<p> Login : <input type="text" size="20" name="Login"/></p>
						<p> Password : <input type="password" size="20" name="Password"/></p>
						<p> Email : <input type="email" size="50" name="Email"/></p>
						<p><input type="submit" value="SUBMIT"/>
						<input type="reset" value="RESET"/></p>
					</form>
			</div>
			<div style="clear: both;"></div>
			
		</div>
		</div>
		
	</div>
		
		<?php 
			include("feet.html"); 
		?> 

		
	

</body>
</html>

