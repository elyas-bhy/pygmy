<?php
session_start();
session_destroy();
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
   </head> 
<body>
	<div id="content">

		<?php include '../header.php' ?>
		
		<div id="corps">
			<div class="block">
			<h1>LOADING...</h1>
			<img src="../Images/loading.gif" align="center" width=80/>	
			<meta http-equiv="refresh" content="1; URL=../index.php"> <br/>
			<p><a href="../index.php" class="lien"> (If you are not redirected, click here)</a></p>
			</div>
		</div>
		
		<?php 
			include("../feet.html"); 
		?> 

		
	</div>

</body>
</html>

