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
		
		<div id="corps">
			
					<h2><?php
					$host = "dbserver"; //dbserver
					$user = "njouanla";  //njouanla
					$password = "pygmyproject"; //pygmyproject
					$conn = mysql_connect($host,$user,$password) or die ("Error. You are not registered");
					mysql_select_db("njouanla") or die ("DB PB");
	
					$title = $_REQUEST["Title"];
					$resume = $_REQUEST["Resume"];
					$filename= "game.jar";
					$user = $_SESSION['Login'];
					
					$check = "SELECT * FROM game WHERE name = '$title'";
					$check_title = mysql_query($check);
					
					if (mysql_num_rows($check_title) == 0) 
					{
					
					  $target = "../files/$title/";
					  $path = "http://nicolas.jouanlanne.emi.u-bordeaux1.fr/PygmyDeveloper/files/$title/";
					  if(is_dir($target) == false)
					  @mkdir ($target, 0777, true); 
					  $target = $target . basename( $_FILES['code']['name']);
					  $path = $path . basename( $_FILES['code']['name']);
					
					  $extensions = array('.jar');
					  $extension = strrchr($_FILES['code']['name'], '.'); 
					
					  if(!in_array($extension, $extensions)){ 
						echo 'You need to upload a .jar file.<br>';
					  }
					  else {

					    if(move_uploaded_file($_FILES['code']['tmp_name'], $target)){
						
					    echo "The file ". basename( $_FILES['code']['name']). " has been uploaded";	
					
					    $query = "Insert into game(name,username, resume, filename, path) values('$title','$user','$resume','$filename', '$path')";
					    mysql_query($query);
					    }
					  }	
	
					echo '<meta http-equiv="refresh" content="1; URL=PygmyLog_c.php"> <br/>';
					}
					
					else if(mysql_num_rows($check_filename) != 0)
					{
					    echo 'Please change the name of your file.';
					    echo '<meta http-equiv="refresh" content="1; URL=Upload_c.php">';
					}
					
					else{
					    echo 'This title is already taken.';
					    echo '<meta http-equiv="refresh" content="1; URL=Upload_c.php">';
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
