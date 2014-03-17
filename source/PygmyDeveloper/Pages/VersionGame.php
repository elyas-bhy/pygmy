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
   </head> 
<body>
	<div id="content">

		<?php include '../header.php' ?>
		
		<div id="corps">
			
					<h2><?php
					$host = "dbserver"; 
					$user = "njouanla"; 
					$password = "pygmyproject";
					$conn = mysql_connect($host,$user,$password) or die ("Error. You are not registered");
					mysql_select_db("njouanla") or die ("DB PB");
	
					$title = $_GET['game'];
					$vers = $_REQUEST["Version"];
					$filename= "game.jar";
					$user = $_SESSION['Login'];
					$min_player_v = $_REQUEST["min"];
					$max_player_v = $_REQUEST["max"];
					
					$check = "SELECT min_player, max_player FROM game WHERE name = '$title'";
					$check_title = mysql_query($check);
					
					
					if (mysql_num_rows($check_title) != 0) 
					{	
					  while($row = mysql_fetch_array($check_title)){
						  
					  $min = $row['min_player'];
					  $max = $row['max_player'];  
					
					  $target = "../files/$title/";
					  $path = "http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/files/$title/";

					  if(is_dir($target) == false)
					  @mkdir ($target, 0777, true); 
	
					  $target = $target . basename( $_FILES['code']['name']);
					  $path = $path . basename( $_FILES['code']['name']);
					
					  $extensions = array('.jar');
					  $extension = strrchr($_FILES['code']['name'], '.'); 
					  
					  $error = False;
					
					  if(!in_array($extension, $extensions)){ 
						echo 'You need to upload a .jar file.<br>';
					  }
					  else {

					    if(move_uploaded_file($_FILES['code']['tmp_name'], $target)){
							if(isset($min_player_v) && $min_player_v != '' && isset($max_player_v) && $max_player_v != '' && $max_player_v >= $min_player_v){
							}
							else if(isset($min_player_v) && $min_player_v != '' && $min_player_v <= $max){ 
								$max_player_v = $max;						
							}
							else if(isset($max_player_v) && $max_player_v != '' && $max_player_v >= $min){
								$min_player_v = $min;
							}
							else if(isset($min_player_v) && $min_player_v != '' && isset($max_player_v) && $max_player_v != '' && $max_player_v < $min_player_v){
								$error = True;
								$min_player_v = $min;
								$max_player_v = $max;
						}
						else{
								$min_player_v = $min;
								$max_player_v = $max;
							}
							
							if($error == False){
								echo "The file ". basename( $_FILES['code']['name']). " has been uploaded good";	
					
								$query = "UPDATE game SET min_player = '$min_player_v', max_player = '$max_player_v', version = '$vers' WHERE name = '$title'";
								mysql_query($query);
							}
							else{
								echo 'Min > Max impossible';
								echo '<meta http-equiv="refresh" content="1; URL=PygmyLog_c.php"> <br/>';
							}
							
						}
					  }	
	
						echo '<meta http-equiv="refresh" content="1; URL=PygmyLog_c.php"> <br/>';
					}
					}
					
					else if(mysql_num_rows($check_title) == 0)
					{
					    echo 'Error in your upload.';
					    echo '<meta http-equiv="refresh" content="1; URL=Version.php">';
					}
					
				
				
					?>

					</h2>
			</div>

		</div>
		
		<?php 
			include("../feet.html"); 
		?> 

		
	</div>

</body>
</html>
