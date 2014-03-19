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
	   <link rel="stylesheet" media="screen" type="text/css" title="Pages" href="Pages/Stylesheet/Pygmy.css" /> 	
		<!-- IMPORT Javascript -->
	<script type="text/javascript" src="js/jquery-1.2.6.js"></script>
	<script type="text/javascript" src="js/jquery.formvalidation.js"></script>
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
				<li><a href="index.php" class="current">HOME</a></li>
				<li><a href="Pages/Download_c.php" class="current">DOWNLOADS</a></li>
				<li><a href="Pages/Upload_c.php">UPLOAD YOUR CODE</a></li>
				<li><a href="Pages/PygmyLog_c.php">MY SETTINGS</a></li>
			</ul>
		</div>

		
		<div id="corps">
				

		<div id="id_block">
			
			<div class="package log">
				<h1>Log In <img src="Images/log.png" align="center" width=30/></h1>
					<form id="formulaire" method="post" action="Pages/Connexion.php">
						<table id="connect">
						<tr><td><th class="left"><label>Login : </label></th><th><input required="true" type="text" size="20" name="Login" id="Login"/></th></td></tr>
						<tr><td><th class="left"><label>Password : </label></th><th><input required="true" type="password" size="20" name="Password" id="Password"/></th></td></tr>
						</table>
						<p><input type="submit" value="SUBMIT"/>
						<input type="reset" value="RESET"/></p>
					</form>
			</div>
			
			<div class="package register">
				<h1>Register <img src="Images/add.png" align="center" width=30/></h1>
					<form method="post" id="formulaire" action="Pages/RegisterDev.php">
						<table id="connect">
						<tr><td><th class="left"><label>Login : </label></th><th><input required="true" type="text" size="20" name="Login" id="Login"/></th></td></tr>
						<tr><td><th class="left"><label>Password : </label></th><th><input  required="true" type="password" size="20" name="Password" id="Password"/></th></td></tr>
						<tr><td><th class="left"><label>Email : </label></th><th><input mask="email" required="true" type="email" size="20" name="Email" id="Email"/></th></td></tr>
						</table>
						<p><input type="submit" value="SUBMIT"/>
						<input type="reset" value="RESET"/></p>
					</form>
			</div>
			<div style="clear: both;"></div>
			
		</div>
		
		<div id="how_it_works">
			<div class="step1">
			<img src="Images/add.png" align="center" width=55 style="padding:10px;"/> 
			<img src="Images/log.png" align="center" width=40 style="padding:10px;"/> 
			<h3>Step 1</h3>
			<p style="font-weight:bold;">Register or Log In</p>
			<p>Create a new account or log in as a Pygmy developer</p>
			</div>
			<div class="step2">
			<img src="Images/download.png" align="center" width=55 style="padding:10px;"/>
			<h3>Step 2</h3>
			<p style="font-weight:bold;">Download our framework</p>
			<p>Download our existing framework and create your own games</p>
			</div>
			<div class="step3">
			<img src="Images/upload.png" align="center" width=55 style="padding:10px;"/>
			<h3>Step 3</h3>
			<p style="font-weight:bold;">Upload your code</p>
			<p>Upload your games and enjoy them on your Pygmy Application</p>
			</div>
		</div>
		</div>
	
	
		
		<?php 
			include("feet.html"); 
		?> 

		
	

</body>
</html>

