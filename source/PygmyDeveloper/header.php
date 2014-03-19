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
				<li><a href="../index.php" class="current">HOME</a></li>
				<li><a href="Download_c.php" class="current">DOWNLOADS</a></li>
				<li><a href="Upload_c.php">UPLOAD YOUR CODE</a></li>
				<li><a href="PygmyLog_c.php">MY SETTINGS</a></li>
			</ul>
		</div>
		</div>
</body>
