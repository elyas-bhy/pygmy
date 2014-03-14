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
   </head> 
<body>
	<div id="content">

		<div id="banner">

				<div id="login">
					<p><?php
						if(isset($_SESSION['islogged']) && $_SESSION['islogged'] == true){
							echo '<p>WELCOME ' .$_SESSION['Login'].' ! | <a href="Deconnexion.php">LOG OUT</a></p>';
						}
						else{
							echo '<p><a href="ConnexionSite.php">LOG IN</a> | <a href="Register.php">REGISTER</a></p>';
						}
					?></p>				
				</div>
		</div>
		
				<div id="menu">
			<ul>
				<li><a href="../index.php" class="current">ACCUEIL</a></li>
				<li><a href="Download_c.php" class="current">DOWNLOADS</a></li>
				<li><a href="Upload_c.php">UPLOAD YOUR CODE</a></li>
				<li><a href="PygmyLog_c.php">MY SETTINGS</a></li>
			</ul>
		</div>
		
		<?php
			if(isset($_SESSION['islogged']) && $_SESSION['islogged'] == true){
		?>
		<div id="settings">
		<p>
			<?php
			error_reporting(E_ALL ^ E_NOTICE);
					$host = "dbserver"; //dbserver
					$user = "njouanla";  //njouanla
					$password = "pygmyproject"; //pygmyproject
					$conn = mysql_connect($host,$user,$password) or die ("Error. You are not registered");
					mysql_select_db("njouanla") or die ("DB PB");
					
					$query = "SELECT email FROM developer WHERE username = '".htmlspecialchars(addslashes($_SESSION['Login']))."';";
					$result = mysql_query($query);
					while ($row = mysql_fetch_array($result)) {

					$mail = $row['email'];
			}
			
			echo 'USERNAME : '.$_SESSION['Login'].' <br>';
			echo 'EMAIL : '.$mail.' <br>';
		?>
		<form method="post" action="Settings.php">
		<input type="submit" name="submit" value="Modify">
		</form>
		</p>
		</div>		
		
		<div id="games">
		<h3>My Games</h3><br>
		
		<?php
					error_reporting(E_ALL ^ E_NOTICE);
					$conn = mysql_connect($host,$user,$password) or die ("Error. You are not registered");
					mysql_select_db("njouanla") or die ("DB PB");
					
					$query = "SELECT name, version, min_player, max_player FROM game WHERE username = '".htmlspecialchars(addslashes($_SESSION['Login']))."';";
					$result = mysql_query($query);
					while ($row = mysql_fetch_array($result)) {

					$title = $row['name'];
					$version = $row['version'];
					//$min = $row['min_player'];
					//$max = $row['max_player'];
					echo ' '.$title.'  |  Version : '.$version.' <br>';
					//echo ' Players min : '.$min.' | Players max : '.$max.' <br>';
					?>
					<form method="post" action="Version.php?game=<?php echo $row['name']; ?>"><br>
					<input type="submit" name="submit" value="UPDATE">
					</form>
				
					<?php
					echo '<br>';
			}
					
					
					mysql_close($conn);
		}
		
		else
					{
					echo 'You need to log in.';
					echo '<meta http-equiv="refresh" content="1; URL=ConnexionSite.php"> <br/>';
					}
		?>
		
		</div>
<?php 
include("../feet.html"); 
?> 
		
	</div>

</body>
</html>
