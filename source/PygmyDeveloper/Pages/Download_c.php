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
						<li><a href="Download.php" class="current">DOWNLOADS</a></li>
						<li><a href="Upload.php">UPLOAD YOUR CODE</a></li>
						<li><a href="PygmyLog.php">MY SETTINGS</a></li>
					</ul>
				</div>
		
		<div id="tab">
		<TABLE BORDER="1"> 
			<TR> 
			<TH> Pygmy </TH> 
			<TD> <a href="../download/jeu.zip">PYGMY (Download me)</a> </TD> 
			</TR> 
		</TABLE> 	
		</div>	

<?php 
include("../feet.html"); 
?> 
		

</body>
</html>
