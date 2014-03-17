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
	   <link rel="stylesheet" media="screen" type="text/css" title="Pages" href="Pygmy.css" /> 
	   <script type="text/javascript">
		<?php include 'js/chek_form.js'; ?>
	   </script> 
   </head> 
<body>
	<div id="content">

		<?php include '../header.php' ?>
		
		<div id="corps">
			<div class="block">
					<h1>Register</h1>
					<form name="formulaire" method="post" action="RegisterDev.php" onsubmit="return check_form_register()";>
						<table id="connect">
						<tr><td><th class="left"><label>Login : </label></th><th><input type="text" size="20" name="Login" id="Login"/></th></td></tr>
						<tr><td><th class="left"><label>Password : </label></th><th><input type="password" size="20" name="Password" id="Password"/></th></td></tr>
						<tr><td><th class="left"><label>Email : </label></th><th><input type="email" size="50" name="Email" id="Email"/></th></td></tr>
						</table>
						<p><input type="submit" value="Submit"/>
						<input type="reset" value="Reset"/></p>
					</form>
			</div>

		</div>
		
		<?php 
			include("../feet.html"); 
		?> 

		
	</div>

</body>
</html>
