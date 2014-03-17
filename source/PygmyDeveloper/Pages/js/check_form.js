function goodmail(mailtest)
{
var reg = new RegExp('^[a-z0-9]+([_|\.|-]{1}[a-z0-9]+)*@[a-z0-9]+([_|\.|-]{1}[a-z0-9]+)*[\.]{1}[a-z]{2,6}$', 'i');

return(reg.test(mailtest));
}

function check_form(){
	if(document.formulaire.Login.value == ""){
		alert("Login missing");
		document.formulaire.Login.style.backgroundColor = "#cccacb";
		document.formulaire.Login.focus();
		return false;
	}
		
	if(document.formulaire.Password.value == ""){
		alert("Password missing");
		document.formulaire.Password.style.backgroundColor = "#cccacb";
		document.formulaire.Password.focus();
		return false;
	}
}

function check_form_register(){
	if(document.formulaire.Login.value == ""){
		alert("Login missing");
		document.formulaire.Login.style.backgroundColor = "#cccacb";
		document.formulaire.Login.focus();
		return false;
	}
		
	if(document.formulaire.Password.value == ""){
		alert("Password missing");
		document.formulaire.Password.style.backgroundColor = "#cccacb";
		document.formulaire.Password.focus();
		return false;
	}
	
	if(document.formulaire.Email.value == ""){
		alert("Email missing");
		document.formulaire.Email.style.backgroundColor = "#cccacb";
		document.formulaire.Email.focus();
		return false;
	}
	
	if(!goodmail(document.formulaire.Email.value)) {
		alert("Email incorrect");
		document.formulaire.Email.style.backgroundColor = "#cccacb";
		document.formulaire.Email.focus();
		return false;
	}
}












function check_form_a()
{
if(document.formulaire.pseudo.value == "")  {
alert("Veuillez saisir votre pseudo");
document.formulaire.pseudo.style.backgroundColor = "#cccacb";
document.formulaire.pseudo.focus();
return false;
}
 
if(document.formulaire.mdp.value == "")  {
alert("Veuillez saisir votre mot de passe");
document.formulaire.mdp.style.backgroundColor = "#cccacb";
document.formulaire.mdp.focus();
return false;
}

if(document.formulaire.mdp2.value == "")  {
alert("Veuillez saisir votre mot de passe");
document.formulaire.mdp2.style.backgroundColor = "#cccacb";
document.formulaire.mdp2.focus();
return false;
}

if (document.formulaire.mdp.value != document.formulaire.mdp2.value)
{
alert("Les mots de passe renseign�s ne sont pas identiques");
document.formulaire.mdp.style.backgroundColor = "#cccacb";
document.formulaire.mdp2.style.backgroundColor = "#cccacb";
document.formulaire.mdp2.focus();
return false;
}

if(document.formulaire.nom.value == "")  {
alert("Veuillez indiquer votre nom");
document.formulaire.nom.style.backgroundColor = "#cccacb";
document.formulaire.nom.focus();
return false;
}

if(document.formulaire.prenom.value == "")  {
alert("Veuillez indiquer votre prenom");
document.formulaire.prenom.style.backgroundColor = "#cccacb";
document.formulaire.prenom.focus();
return false;
}

if(document.formulaire.cp.value == "")  {
alert("Veuillez indiquer votre code postal");
document.formulaire.cp.style.backgroundColor = "#cccacb";
document.formulaire.cp.focus();
return false;
}

if(document.formulaire.ville.value == "")  {
alert("Veuillez indiquer votre ville");
document.formulaire.ville.style.backgroundColor = "#cccacb";
document.formulaire.ville.focus();
return false;
}

if(document.formulaire.mail.value == "")  {
alert("Veuillez indiquer votre mail");
document.formulaire.mail.style.backgroundColor = "#cccacb";
document.formulaire.mail.focus();
return false;
}

if(!bonmail(document.formulaire.mail.value)) {
alert("Veuillez indiquer un mail correct");
document.formulaire.mail.style.backgroundColor = "#cccacb";
document.formulaire.mail.focus();
return false;
}
}
