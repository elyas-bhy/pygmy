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
   </head> 
<body>
	<?php include '../headerConnect.php' ?>		
				
		<div id="corps">
		
		<?php 
				if(isset($_SESSION['islogged']) && $_SESSION['islogged'] == true)
					{
		?>
					
		<img src="../Images/download.png"  align="center" width=80/>
		<div id="tabDownload">
		<TABLE BORDER="1" id="dwld"> 
			<TR> 
			<TH> Pygmy Lib </TH> 
			<TD> <a href="../download/pygmy-lib.jar">PYGMY LIB(.jar)</a> </TD>
			</TR>
			<TR>
			<TH> Pygmy Client Project </TH> 
			<TD> <a href="../download/pygmy-client.zip">PYGMY CLIENT PROJECT(.zip)</a> </TD>
			</TR> 
		</TABLE> 	
		<h1>
		<?php
			}
				else
					{
					echo '<h3>Access denied ! You need to log in. </h3>';
					echo '<meta http-equiv="refresh" content="1; URL=ConnexionSite.php"> <br/>';
					}
			?>
			</h1>
		</div>
		</div>

<?php 
include("../feet.html"); 
?> 
		

</body>
</html>
