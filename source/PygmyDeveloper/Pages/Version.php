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
		if(isset($_GET['game'])){

			echo ' '.$_GET['game'].' <br>';

					$host = "dbserver"; 
					$user = "njouanla";  
					$password = "pygmyproject";
					$conn = mysql_connect($host,$user,$password) or die ("Error. You are not registered");
					mysql_select_db("njouanla") or die ("DB PB");

					$query = "SELECT version, min_player, max_player FROM game WHERE name = '".$_GET['game']."';";
					$result = mysql_query($query);
					while ($row = mysql_fetch_array($result)) {

					$version = $row['version'];
					$min = $row['min_player'];
					$max = $row['max_player'];
					echo 'Version : '.$version.' <br>';
					echo ' Players min : '.$min.' | Players max : '.$max.' <br>';
			}
}
		?>
	
		
		<form id="formulaire" method="post" enctype="multipart/form-data" action="VersionGame.php?game=<?php echo $_GET['game']; ?>">
						<table id="upload">
						<tr><td><th class="left"><label>Version (write the number) : </label></th><th><input required="true" type="text" size="20" name="Version"/></th></td></tr>
						<tr><td><th class="left"><label>Min player : </label></th><th><input type="text" size="10" name="min"</th></td></tr>
						<tr><td><th class="left"><label>Max player : </label></th><th><input type="text" size="10" name="max"</th></td></tr>
						</table>
						<p> /!\ Filename must be game.jar /!\ .</p>
						<p><input type="file" name="code"></p>
						<p><input type="submit" value="UPLOAD"/>
						<input type="reset" value="RESET"/></p>
					</form>
					
		</div>

<?php 
include("../feet.html"); 
?> 
		
	</div>

</body>
</html>
