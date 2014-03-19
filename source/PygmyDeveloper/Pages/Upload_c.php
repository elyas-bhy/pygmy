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

		<?php include '../headerConnect.php' ?>
		
		<div id="corps">
		<?php
		if(isset($_SESSION['islogged']) && $_SESSION['islogged'] == true){
		?>
		<img src="../Images/upload.png" align="center" width=80/>
		<form id="formulaire" method="post" enctype="multipart/form-data" action="UploadGame.php">
			<table id="upload">
			<tr><td><th class="left"><label>Title (no spaces)* : </label></th><th><input required="true" type="text" size="20" name="Title"/></th></td></tr>
			<tr><td><th class="left"><label>Description (size max 200)* : </label></th><th><textarea required="true" type="text" size="200" name="Resume"></textarea></textarea></th></td></tr>
			<tr><td><th class="left"><label>Min player (default value 1) : </label></th><th><input type="text" size="10" name="min"</th></td></tr>
			<tr><td><th class="left"><label>Max player (default value 1) : </label></th><th><input type="text" size="10" name="max"</th></td></tr>
			</table>
			<p>Please upload a .jar file. /!\ Filename must be game.jar /!\ .</p>
			<p><input required="true" type="file" name="code"></p>
			<p>Select an image for your game. (200ko max)</p>
			<p><input type="file" name="image"></p>
			<p><input type="submit" value="UPLOAD"/></p>
			<input type="reset" value="RESET"/></p>
			<p>* You need to renseign this field.</p>
			</form>
					
		</div>
		<?php
		}
		else
					{
					echo '<h3>You need to log in.</h3>';
					echo '<meta http-equiv="refresh" content="1; URL=ConnexionSite.php"> <br/>';
					}
		?>

<?php 
include("../feet.html"); 
?> 
		
	</div>

</body>
</html>
