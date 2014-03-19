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
	   <link rel="stylesheet" media="screen" type="text/css" title="Design" href="Stylesheet/Pygmy.css" />
	    <!-- IMPORT Javascript -->
		<script type="text/javascript" src="../js/query-1.2.6.js"></script>
		<script type="text/javascript" src="../js/jquery.formvalidation.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
		$("#formulaire").formValidation({
			alias		: "name",
			required	: "accept",
			err_list	: true
		}); 
               
	});
	</script>
   </head> 
<body>
	<div id="content">

		<?php include '../header.php' ?>

		
		<div id="corps">
		<img src="../Images/log.png" align="center" width=80/>	
			<div id="connect">
					<h1>Log In</h1>
					<form id="formulaire" method="post" action="Connexion.php">
						<table id="connect">
						<tr><td><th class="left"><label>Login : </label></th><th><input required="true" type="text" size="20" name="Login" id="Login"/></th></td></tr>
						<tr><td><th class="left"><label>Password : </label></th><th><input required="true" type="password" size="20" name="Password" id="Password"/></th></td></tr>
						</table>
						<p><input type="submit" value="SUBMIT"/>
						<input type="reset" value="RESET"/></p>
					</form>

					<a href="Register.php">Create another user</a>
			</div>

		</div>
		
		<?php 
			include("../feet.html"); 
		?> 

		
	</div>

</body>
</html>
