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

		<?php include '../headerConnect.php' ?>
		<div id="corps">
		
		<div class="block settings">
					<h1>Modify your informations</h1>
					<div id="changement">
					<form method="post" action="ChangeSettings.php">
						<p> New Password : <input type="password" size="20" name="Password"/></p>
						<p><input type="submit" value="CONFIRM"/>
						<input type="reset" value="RESET"/></p>
					</form>
					
					<form method="post" action="ChangeSettings.php">
						<p> New Email : <input type="email" size="50" name="Email"/></p>
						<p><input type="submit" value="CONFIRM"/>
						<input type="reset" value="RESET"/></p>
					</form>
					</div>
					
					<form action="PygmyLog_c.php"> 
					<input type="submit" value="BACK"></form>
					
			</div>
			</div>
		

<?php 
include("../feet.html"); 
?> 
		
	</div>

</body>
</html>
