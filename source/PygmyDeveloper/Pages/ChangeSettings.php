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

		<?php include '../headerConnect.php' ?>
		
		<div id="corps">
			
					<?php
					error_reporting(E_ALL ^ E_NOTICE);
					$host = "dbserver"; //dbserver
					$user = "njouanla";  //njouanla
					$password = "pygmyproject"; //pygmyproject
					$conn = mysql_connect($host,$user,$password) or die ("Error. You are not registered");
					mysql_select_db("njouanla") or die ("DB PB");
	
					$pass = $_POST["Password"];
					$mail = $_POST["Email"];
				if($_POST["Password"] == '' && $_POST["Email"] == '' ){
					echo 'Fields not renseigned';
					echo '<meta http-equiv="refresh" content="1; URL=Settings.php"> <br/>';
				}
				else{
						if(isset($_POST["Password"]) && $_POST["Password"] != '') {
							$query = "Update developer set password = '".md5($pass)."' where username='".htmlspecialchars(addslashes($_SESSION['Login']))."';";
							echo '<h1>LOADING...</h1>';
						}
						else if (isset($_POST["Email"]) && $_POST["Email"] != '') {
							$query = "Update developer set email = '".$mail."' where username='".htmlspecialchars(addslashes($_SESSION['Login']))."';";
						}
					
						mysql_query($query);
						echo '<meta http-equiv="refresh" content="1; URL=PygmyLog_c.php"> <br/>';
					}
					mysql_close($conn);

					?>

			</div>

		</div>
		
		<?php 
			include("../feet.html"); 
		?> 

		
	</div>

</body>
</html>
