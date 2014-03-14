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
		
				<div id="menu">
			<ul>
				<li><a href="../index.php" class="current">ACCUEIL</a></li>
				<li><a href="Download_c.php" class="current">DOWNLOADS</a></li>
				<li><a href="Upload_c.php">UPLOAD YOUR CODE</a></li>
				<li><a href="PygmyLog_c.php">MY SETTINGS</a></li>
			</ul>
		</div>
		
		<div id="corps">
		<?php
		if(isset($_SESSION['islogged']) && $_SESSION['islogged'] == true){
		?>
		<form name="formulaire" method="post" enctype="multipart/form-data" action="UploadGame.php">
			<table id="upload">
			<tr><td><th class="left"><label>Title (no spaces)* : </label></th><th><input type="text" size="20" name="Title"/></th></td></tr>
			<tr><td><th class="left"><label>Description (200 characters) : </label></th><th><input type="text" size="200" name="Resume"</th></td></tr>
			<tr><td><th class="left"><label>Min player (default value 1) : </label></th><th><input type="text" size="10" name="min"</th></td></tr>
			<tr><td><th class="left"><label>Max player (default value 1) : </label></th><th><input type="text" size="10" name="max"</th></td></tr>
			</table>
			<p>Please upload a .jar file. /!\ Filename must be game.jar /!\ .</p>
			<p><input type="file" name="code"></p>
			<p>Select an image for your game. (200ko max)</p>
			<p><input type="file" name="image"></p>
			<p><input type="submit" value="UPLOAD"/></p>
			<input type="reset" value="RESET"/></p>
					</form>
					
		</div>
		<?php
		}
		else
					{
					echo 'You need to log in.';
					echo '<meta http-equiv="refresh" content="1; URL=ConnexionSite.php"> <br/>';
					}
		?>

<?php 
include("../feet.html"); 
?> 
		
	</div>

</body>
</html>
