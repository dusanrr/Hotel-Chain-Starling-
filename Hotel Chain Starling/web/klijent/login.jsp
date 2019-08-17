<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="beans.Klijent"%>
<%@page import = "java.util.*" session="true"%>
<%@page import="java.io.*" %>
<%@page import="java.sql.*" %>
<%@page import="javax.servlet.*" %>
<%@page import="javax.servlet.http.*" %>
<%
    HttpSession sesija = request.getSession();
    Klijent user = (Klijent)sesija.getAttribute("user");
    try
    {
        if(user!= null && user.getPower().equals("Admin") || user!= null && user.getPower().equals("Menadžer hotela") || user!= null && user.getPower().equals("Klijent"))
        {
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Pristup odbijen!");
            RequestDispatcher rd = request.getRequestDispatcher("../klijent/index.jsp");
            rd.forward(request, response);  
            return;
        }
    }
    catch(NullPointerException npe)
    {
        npe.printStackTrace();
    }
%>
<!DOCTYPE html>
<html>
<head>
<title>Starling Hotels</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link href='${pageContext.request.contextPath}/admin/fonts/fonts/fonts.css' rel='stylesheet' type='text/css'>  
<link href="${pageContext.request.contextPath}/klijent/css/style.css" rel="stylesheet" type="text/css" media="all" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/klijent/images/favicon.png" type="image/x-icon"/>
<script src="${pageContext.request.contextPath}/klijent/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/klijent/js/sweetalert.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/klijent/css/sweetalert.css">
<style>
p{
  font-size: 10px;
  font-weight: 400;
  text-align: center;
}

a{
  color: #4A8DCA;
  text-decoration: none;
}

a:hover{
  text-decoration: underline;
}

.container{
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'Open Sans', 'Helvetica', sans-serif; 
  background-color: #f9f9f9;
  border-radius: 5px;
  padding: 40px 50px 40px 50px;
}

form h1{
  font-size: 24px;
  font-weight: 300;
  margin-bottom: 30px;
  text-align: center;
  text-transform: none;
}

form h2{
  color: #444;
  font-size: 10px;
  font-weight: 700;
  margin-bottom: 10px;
}

form input{
  border: 1px solid #ddd;
  border-radius: 3px;
  color: #888;
  display: block;
  font-size: 12px;
  margin-bottom: 30px;
  padding: 0px 10px;
  height: 35px;
  width: 250px;
}

form input:focus{
  outline: none;
  border-color: #ccc;
}

::-webkit-input-placeholder {
  color: #bbb;
  font-size: 10px;
  line-height: 16px;
}

form label{
  display: inline-block;
}

form input[type="submit"]{
  background-color: #29967D;
  border: none;
  border-radius: 3px;
  color: #fff;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: .5px;
  height: 50px;
  width: 270px;
  margin-bottom: 20px;
  -webkit-transition: all 400ms ease;
          transition: all 400ms ease;
}

form input[type="submit"]:focus{
  outline: none;
}

form input[type="submit"]:hover{
  background-color: #67A84F;
}
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/klijent/css/jquery-ui.css" />
<script src="${pageContext.request.contextPath}/klijent/js/jquery-ui.js"></script>
		  <script>
				  $(function() {
				    $( "#datepicker,#datepicker1" ).datepicker();
				  });
		  </script>
<!---/End-date-piker---->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/klijent/css/JFGrid.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/klijent/css/JFFormStyle-1.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/klijent/js/JFCore.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/klijent/js/JFForms.js"></script>
<script>
		$(function() {
			var pull 		= $('#pull');
				menu 		= $('nav ul');
				menuHeight	= menu.height();

			$(pull).on('click', function(e) {
				e.preventDefault();
				menu.slideToggle();
			});

			$(window).resize(function(){
        		var w = $(window).width();
        		if(w > 320 && menu.is(':hidden')) {
        			menu.removeAttr('style');
        		}
    		});
		});
</script>
<script>
//LOGIN VALIDACIJA
function formValidation()  
{  
    var username = document.login.username; 
    var password = document.login.password;  
   
    if(alphanumeric(username))  
    { 
    if(pass_validation(password,7,12))  
    { 
        document.login.submit();
    }  
    }
    return false;  
} 
function pass_validation(password,mx,my)  
{  
    var password_len = password.value.length;  
    if(password_len == 0)
    {
        swal ("Warning" ,  "Polje lozinka mora biti popunjeno." ,  "warning" );
        password.focus();   
        return false;
    }
    else if (password_len >= my || password_len < mx)  
    {  
        swal ("Warning" ,  "Duzina lozinke mora biti\n izmedju 7 i 12 karaktera." ,  "warning" );
        password.focus();  
        return false;  
    }  
    return true;  
}
function alphanumeric(username)  
{   
    var letters = /^[0-9a-zA-Z]+$/;  
    if(username.value.length == 0)
    {
        swal ("Warning" ,  "Polje korisničko ime mora biti popunjeno." ,  "warning" );
        username.focus();
        return false;
    }
    else if(!username.value.match(letters))  
    {  
        swal ("Warning" ,  "Polje korisničko ime može da sadrži samo slova i brojeve." ,  "warning" );
        username.focus();  
        return false;  
    } 
    return true;
} 
function error()  
{    
    swal ("Error" ,  "${poruka}",  "error" );    
}
function success()  
{   
    swal ("Success" ,  "${poruka}",  "success" );  
}
////////////////////////////////////////////////////////////////////////////////
</script>  
</head>
<%
        if(request.getAttribute("poruka") != null)
        {
            if(Integer.parseInt(request.getAttribute("popupid").toString()) == 2)   
            {
            %>
<body onLoad="error()">
            <%
            }
                else if(Integer.parseInt(request.getAttribute("popupid").toString()) == 3)
                {
                %>
                    <body onLoad="success()">
                <%}
        }
        else
        {
        %>
<body>
    <%
        }
        %>
<!-- start header -->
<div class="header_bg">
<div class="wrap">
	<div class="header">
		<div class="logo">
			<a href="${pageContext.request.contextPath}/klijent/index.jsp"><img src="${pageContext.request.contextPath}/klijent/images/logo.png" alt=""></a>
		</div>
		<div class="h_right">
			<!--start menu -->
                        <%if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
                        {%>
			<ul class="menu">
				<li class="active"><a href="${pageContext.request.contextPath}/klijent/index.jsp">Početna</a></li> 
				<li><a href="${pageContext.request.contextPath}/klijent/rezervacije.jsp">rezervacije</a></li> 
                                <li><a href="${pageContext.request.contextPath}/odjavljivanje">odjavi se</a></li>
				<div class="clear"></div>
			</ul>
                        <%
                         }
                        else
                        {
                        %>
                        <ul class="menu">
				<li><a href="${pageContext.request.contextPath}/klijent/index.jsp">Početna</a></li> 
				<li><a href="${pageContext.request.contextPath}/klijent/rezervacije.jsp">rezervacije</a></li> 
                                <li class="active"><a href="${pageContext.request.contextPath}/klijent/login.jsp">Login</a></li>
                                <li><a href="${pageContext.request.contextPath}/klijent/registracija.jsp">Registracija</a></li>
				<div class="clear"></div>
			</ul>
                                <%}%>
		</div>
		<div class="clear"></div>
		<div class="top-nav">
		<nav class="clearfix">
                     <%if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
                        {%>
				<ul>
				<li class="active"><a href="${pageContext.request.contextPath}/klijent/index.jsp">Početna</a></li> 
				<li><a href="${pageContext.request.contextPath}/klijent/rezervacije.jsp">rezervacije</a></li> 
				</ul>
				<a href="#" id="pull">Menu</a>
                                 <%
                         }
                        else
                        {
                        %>
                                <ul>
				<li class="active"><a href="${pageContext.request.contextPath}/klijent/index.jsp">Početna</a></li> 
				<li><a href="${pageContext.request.contextPath}/klijent/rezervacije.jsp">rezervacije</a></li> 
                                <li><a href="${pageContext.request.contextPath}/klijent/login.jsp">Login</a></li>
                                <li><a href="${pageContext.request.contextPath}/klijent/registracija.jsp">Registracija</a></li>
				</ul>
				<a href="#" id="pull">Menu</a>
                        
                         <%}%>
			</nav>
		</div>
	</div>
</div>
</div>

<!--start main -->
<div class="main_bg"">
<div class="wrap" >
	<div class="main">
            
             <div class="container" >
    <form method="POST" action="${pageContext.request.contextPath}/prijavljivanje" name="login">
        <img src="${pageContext.request.contextPath}/klijent/images/lele7.png" alt="" style="display: block; margin-left: auto; margin-right: auto;"/>
      <h1>Login</h1>
     
        <h2>Korisničko ime</h2>
        <input type="text" name="username" required placeholder="Korisničko ime" autofocus>
              <h2>Lozinka</h2>
        <input type="password" name="password" required placeholder="**********">
              <input type="submit" value="Login" onclick="return formValidation()" style="display: block; margin-left: auto; margin-right: auto;"/>
            </form>
        </div>
	</div>
</div>
</div>		
<!--start main -->
<!--<div class="footer_bg">-->
<!--<div class="wrap">-->
<div class="footer">
                        <div class="footer-logo">
                            <a href="#"><img src="https://i.imgur.com/zM3yItK.png" alt="scanfcode"></a>
                        </div>
			
			<div class="f_nav">
                            <%if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
                        {%>
				<ul>
					<li><a href="${pageContext.request.contextPath}/klijent/index.jsp">Početna</a></li>
					<li><a href="${pageContext.request.contextPath}/klijent/rezervacije.jsp">rezervacije</a></li>
				</ul>
                                 <%
                         }
                        else
                        {
                        %>
                        
                                        <li><a href="${pageContext.request.contextPath}/klijent/index.jsp">Početna</a></li>
					<li><a href="${pageContext.request.contextPath}/klijent/rezervacije.jsp">rezervacije</a></li>
                                        <li><a href="${pageContext.request.contextPath}/klijent/login.jsp">Login</a></li>
                                        <li><a href="${pageContext.request.contextPath}/klijent/registracija.jsp">Registracija</a></li>
                        
                         <%}%>
			</div>
			<div class="soc_icons">
				<ul>
					<li><a class="icon1" href="#"></a></li>
					<li><a class="icon2" href="#"></a></li>
					<li><a class="icon3" href="#"></a></li>
					<li><a class="icon4" href="#"></a></li>
					<li><a class="icon5" href="#"></a></li>
					<div class="clear"></div>
				</ul>	
			</div>
                        <div class="copy">
				<p class="link"><span>Copyright © 2018 - All rights reserved | <a href="#"> STARLING HOTEL CHAIN</a></span></p>
			</div>
			<div class="clear"></div>
<!--</div>-->
<!--</div>-->
</div>	
</body>
</html>
