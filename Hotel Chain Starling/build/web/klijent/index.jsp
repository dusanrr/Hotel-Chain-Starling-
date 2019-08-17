<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="beans.Klijent"%>
<%@page import = "java.util.*" session="true"%>
<%@page import="java.io.*" %>
<%@page import="java.sql.*" %>
<%@page import="javax.servlet.*" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.Timer"%>
<%@page import="java.time.LocalDateTime"%>
<%
    HttpSession sesija = request.getSession();
    Klijent user = (Klijent)sesija.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<title>Starling Hotels</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/klijent/images/favicon.png" type="image/x-icon"/>
<link href='${pageContext.request.contextPath}/admin/fonts/fonts/fonts.css' rel='stylesheet' type='text/css'>  
<link href="${pageContext.request.contextPath}/klijent/css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/admin/fonts/css/all.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/klijent/css/animate.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/klijent/js/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/klijent/css/fwslider.css" type="text/css" media="all">
<script type="text/javascript" src="${pageContext.request.contextPath}/klijent/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/klijent/js/css3-mediaqueries.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/klijent/js/fwslider.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/klijent/js/slick.js"></script>
<script src="${pageContext.request.contextPath}/klijent/js/sweetalert.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/klijent/css/sweetalert.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/klijent/css/jquery-ui.css" />
<script src="${pageContext.request.contextPath}/klijent/js/jquery-ui.js"></script>
		  <script>
				  $(function() {
				    $( "#datepicker,#datepicker1" ).datepicker();
				  });
		  </script>
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
//SEARCH VALIDACIJA
function formValidation()  
{  
    var city = document.search.lokacija; 
    var cidate = document.search.datumDolaska; 
    var codate = document.search.datumOdlaska; 
    
    if(aphabet(city))
    {
    if(validatecidate(cidate))
    {
    if(validatecodate(codate))  
    {
        document.search.submit();
    }  
    }   
    }  
    return false;  
} 
function aphabet(city)
{
    var letters = /^[a-zA-Z\s]+$/;
    if(city.value.length == 0)
    {
        swal ("Warning" ,  "Polje grad mora biti popunjeno." ,  "warning" );
        city.focus();   
        return false;
    }
    else if(!city.value.match(letters))
    {
        swal ("Warning" ,  "Polje grad može sadržati samo slova." ,  "warning" );
        city.focus();
        return false;
    }
    return true;
}
function validatecidate(cidate)
{
    var allowBlank = true;
    var minYear = 2018;
    var maxYear = (new Date()).getFullYear();

    var errorMsg = "";

    //regular expression
    re = /^(\d{1,2})\.(\d{1,2})\.(\d{4})$/;

    if(cidate.value != '') 
    {
      if(regs = cidate.value.match(re)) 
      {
        if(regs[2] < 1 || regs[2] > 31) 
        {
          errorMsg = "Dan nije u validnom formatu: " + regs[2];
        } else if(regs[1] < 1 || regs[1] > 12) 
        {
          errorMsg = "Mesec nije u validnom formatu: " + regs[1];
        } else if(regs[3] < minYear || regs[3] > maxYear) 
        {
          errorMsg = "Godina nije u validnom formatu: " + regs[3] + " - mora biti između " + minYear + " i " + maxYear;
        }
      } 
      else 
      {
        errorMsg = "Polje datum dolaska nije u validnom formatu: " + cidate.value;
      }
    } 
    else
    {
      errorMsg = "Polje datum dolaska mora biti popunjeno!";
    }

    if(errorMsg != "") 
    {
      swal ("Warning" ,  ""+errorMsg ,  "warning" );
      cidate.focus();
      return false;
    }
    return true;
}
function validatecodate(codate)
{
    var allowBlank = true;
    var minYear = 1902;
    var maxYear = (new Date()).getFullYear();

    var errorMsg = "";

    //regular expression
    re = /^(\d{1,2})\.(\d{1,2})\.(\d{4})$/;

    if(codate.value != '') 
    {
      if(regss = codate.value.match(re)) 
      {
        if(regss[2] < 1 || regss[2] > 31) 
        {
          errorMsg = "Dan nije u validnom formatu: " + regss[2];
        } else if(regss[1] < 1 || regss[1] > 12) 
        {
          errorMsg = "Mesec nije u validnom formatu: " + regss[1];
        } else if(regss[3] < minYear || regss[3] > maxYear) 
        {
          errorMsg = "Godina nije u validnom formatu: " + regss[3] + " - mora biti između " + minYear + " i " + maxYear;
        }
      } 
      else 
      {
        errorMsg = "Polje datum odlaska nije u validnom formatu: " + codate.value;
      }
    } 
    else
    {
      errorMsg = "Polje datum odlaska mora biti popunjeno!";
    }

    if(errorMsg != "") 
    {
      swal ("Warning" ,  ""+errorMsg ,  "warning" );
      codate.focus();
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
 <%
    int initialDelay = 1000; // aktivira se nakon 1 sec
    int period = 3600000;        // ponavlja se svakih 60 sec
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
    public void run() 
    {
        int dan = LocalDateTime.now().getDayOfMonth();
        int mesec = LocalDateTime.now().getMonthValue();
        int godina = LocalDateTime.now().getYear();
        int sat = LocalDateTime.now().getHour();
        int minut = LocalDateTime.now().getMinute();
        
        String mesecc="", dann="";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        PreparedStatement pst = null;
        Statement stmt = null;
        ResultSet rss = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            if(mesec < 10 && dan > 9) //mesec manji dan veci
            {
                mesecc = ""+0+mesec+"";
                dann = ""+dan+"";
            }
            else if(mesec < 10 && dan < 10) //mesec i dan manji
            {
                dann = ""+0+dan+"";
                mesecc = ""+0+mesec+"";
            }
            else if(dan > 9 && mesec > 9) //mesec i dan veci
            {
                dann = ""+dan+"";
                mesecc = ""+mesec+"";
            }
            else if(dan < 10 && mesec > 9) //mesec veci dan manji
            {
                dann = ""+0+dan+"";
                mesecc = ""+mesec+"";
            }
            
            stmt= con.createStatement();
            String upit = "select * from rezervacije where "+mesecc+" >= SUBSTRING(`datum2`, 1, 2) and "+dann+" >= SUBSTRING(`datum2`, 4, 2)";
            rss = stmt.executeQuery(upit);
            
            if(rss.first())
            {
                while(rss.next())
                {
                    String sql = "update sobe set klijentID='-1', status='Prazna', datum1='Nema',datum2='Nema', vreme1='Nema', vreme2='Nema' where datum2='"+rss.getString("datum2")+"' and "+sat+" >= SUBSTRING(`vreme2`, 2, 1) and status='Zauzeta'";
                    pstmt = con.prepareStatement(sql); 
                    pstmt.executeUpdate();

                    String query = "delete from rezervacije where datum2='"+rss.getString("datum2")+"' and "+sat+" >= SUBSTRING(`vreme2`, 2, 1)";
                    pst = con.prepareStatement(query); 
                    pst.executeUpdate();

                }
            }
            rss.close();
            stmt.close();
            pstmt.close();
            pst.close();
            con.close();
        }
        catch(SQLException ex)
        {
            String errormsg = ex.getMessage();
            if(con!=null)
                try
                {
                    con.close();
                }
                catch(Exception exc)
                {
                    errormsg = errormsg+exc.getMessage();
                }
            
        }
        catch(ClassNotFoundException cnf) {}
    }
    };
     timer.scheduleAtFixedRate(task, initialDelay, period);
     %>
<!-- start header -->
 <!--<div id="main-wrapper">
	<main class="main" role="main" id="main-content">-->
		
            <%
                Connection con = null;
                Statement stmt = null;
                ResultSet rs = null;
                try
                {

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmt = con.createStatement();
                    String upit = "select * from klijenti where kime='"+user.getUsername()+"'";
                    rs = stmt.executeQuery(upit);
                    %>

                    <%  if(rs.first())
                    { %>
                    <div id="main-top-bar">
			<a href="https://rolecalljobs.com" class="main-logo"></a>
                <div class="user-menu">
                  <div class="user-menu-item">
                    <div class="user-image-wrapper">
                      <span class="user-image"><img src="${pageContext.request.contextPath}/klijent/images/shc-avatar.png"></span>
                    </div>
                    <div class="user-name-wrapper">
                    <%= rs.getString("ime")%> <i class="fa fa-caret-down"></i>
                  </div>
                </div>
                <div class="user-dropdown">
                  <a href="#" class="dropdown-item"><span class="mr-3"><i class="fa fa-underline"></i></span><%= rs.getString("kime")%></a>
                  <a href="#" class="dropdown-item"><span class="mr-3"><i class="fab fa-superpowers"></i></span><%= rs.getString("vrsta")%></a>
                  <a href="#" class="dropdown-item"><span class="mr-3"><i class="fa fa-envelope"></i></span><%= rs.getString("email")%></a>
                  <a href="#" class="dropdown-item"><span class="mr-3"><i class="fab fa-pinterest-p"></i></span><%= rs.getInt("poeni")%></a>
                  <a href="${pageContext.request.contextPath}/odjavljivanje" class="dropdown-item"><span class="mr-3"><i class="fa fa-power-off"></i></span> Odjavi se</a>
                </div>
                 </div>
                <div class="shape"></div>
		</div>
                    <%}else{} %>        
            <%
                    rs.close();
                    stmt.close();
                    con.close();
                }
                catch(NullPointerException npe)
                {
                    npe.printStackTrace();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                

            %>
     
<!--	</main>
</div>-->
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
				<li class="active"><a href="${pageContext.request.contextPath}/klijent/index.jsp">Početna</a></li> 
				<li><a href="${pageContext.request.contextPath}/klijent/rezervacije.jsp">rezervacije</a></li> 
                                <li><a href="${pageContext.request.contextPath}/klijent/login.jsp">Login</a></li>
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

<div class="Modern-Slider">
  <!-- Item -->
  <div class="item">
    <div class="img-fill">
      <img src="${pageContext.request.contextPath}/klijent/images/slider-bg.jpg" alt="">
      <div class="info">
        <div>
            <h3>Starling Hotel Chain</h3>
          <h5>Hotels in over 35 countries around the world</h5>
        </div>
      </div>
    </div>
  </div>
  <!-- // Item -->
  <!-- Item -->
  <div class="item">
    <div class="img-fill">
      <img src="${pageContext.request.contextPath}/klijent/images/slider3.jpg" alt="">
      <div class="info">
        <div>
          <h3>Starling Hotel Chain</h3>
          <h5>Reservations are available in over 45 cities around the world</h5>
        </div>
      </div>
    </div>
  </div>
  <!-- // Item -->
  <!-- Item -->
  <div class="item">
    <div class="img-fill">
      <img src="${pageContext.request.contextPath}/klijent/images/slider4.jpg" alt="">
      <div class="info">
        <div>
          <h3>One of the best hotel chains in the world</h3>
          <h5>We are one of the best in our business.</h5>
        </div>
      </div>
    </div>
  </div>
  <!-- // Item -->
  <!-- Item -->
  <div class="item">
    <div class="img-fill">
      <img src="${pageContext.request.contextPath}/klijent/images/slider5.jpg" alt="">
      <div class="info">
        <div>
          <h3>Starling Hotel Chain</h3>
          <h5>Choose hotel and a room according to your wishes.</h5>
        </div>
      </div>
    </div>
  </div>
  <!-- // Item -->
</div>
<!--start main -->
<div class="main_bg">
<div class="wrap">
	<div class="online_reservation">
	<div class="b_room">
		<div class="booking_room">
			<h4>book a room online</h4>
			<p>Find hotel rooms</p>
		</div>

		<div class="reservation">
                    <form method="GET" action="${pageContext.request.contextPath}/PretraziHotele" name="search">
			<ul>
                                <li  class="span1_of_1 left">
					<h5>Grad</h5>
					<div class="book_date">
                                            
				<input list="city" type="text" name="lokacija" required placeholder="Pretraga po gradu" oninvalid="this.setCustomValidity('Enter city here')" oninput="this.setCustomValidity('')">
                                <%    
                                            try
                                            {
                                                Class.forName("com.mysql.cj.jdbc.Driver");
                                                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                                                stmt = con.createStatement();
                                                String upit = "select DISTINCT grad from hoteli";
                                                rs = stmt.executeQuery(upit);
                                                %>
                                             <datalist id="city">
                                                <%  while(rs.next())
                                                { %>
                                                <option value="<%= rs.getString("grad") %>">
                                                <%}
                                                    rs.close();
                                                    stmt.close();
                                                    con.close();
                                                }
                                                catch(Exception e)
                                                {
                                                    e.printStackTrace();
                                                }  
                                            %>
                                            </datalist>
					</div>					
				</li>
                                <li class="span1_of_1">
					<h5>Sobe:</h5>
					<div class="section_room">
                                            <select name="sobe" required oninvalid="this.setCustomValidity('Select number of rooms here.')" oninput="this.setCustomValidity('')">
                                                <option value="1">1</option>         
                                                <option value="2">2</option>
                                            </select>
					</div>	
				</li>
				<li  class="span1_of_1 left">
					<h5>Datum dolaska:</h5>
					<div class="book_date">
                                            <input class="date" name="datumDolaska" id="datepicker" type="text" required <!--oninvalid="this.setCustomValidity('Choose check in date here.')" oninput="this.setCustomValidity('')"-->
					</div>					
				</li>
				<li  class="span1_of_1 left">
					<h5>Datum odlaska:</h5>
					<div class="book_date">
                                            <input class="date" name="datumOdlaska" id="datepicker1" type="text" required <!--oninvalid="this.setCustomValidity('Choose check out date here.')" oninput="this.setCustomValidity('')"-->
					</div>		
				</li>
                                <li class="span1_of_3">
					<h5>Paketi</h5>
					<div class="section_room">
                                            <select name="paket" required>
                                                <option value="paket1">Member rate</option>
                                                <option value="paket2">Standard rate</option>
                                                <option value="paket3">Bed and breakfast</option>
                                                <option value="paket4">Double your points</option>
                                                <option value="paket5">Explore package</option>
                                            </select>
					</div>	
				</li>
				<li class="span1_of_2 left">
					<h5>Odrasli:</h5>
					<!----------start section_room----------->
					<div class="section_room">
						<select name="odrasli" required class="frm-field required" oninvalid="this.setCustomValidity('Select number of adults here.')" oninput="this.setCustomValidity('')">
							<option value="1">1</option>
                                                        <option value="2">2</option>         
                                                        <option value="3">3</option>
							<option value="4">4</option>
                                                        <option value="5">5</option>
		        		</select>
					</div>					
				</li>
                                <li class="span1_of_2 left">
					<h5>Deca:</h5>
					<!----------start section_room----------->
					<div class="section_room">
						<select name="deca" required class="frm-field required" oninvalid="this.setCustomValidity('Select number of kids here.')" oninput="this.setCustomValidity('')">
							<option value="0">0</option>
                                                        <option value="1">1</option>
                                                        <option value="2">2</option>         
                                                        <option value="3">3</option>
							<option value="4">4</option>
                                                        <option value="5">5</option>
		        		</select>
					</div>					
				</li>
                               
				<li class="span1_of_3">
					<div class="date_btn">
                                             <input type="submit" value="PRONAĐI HOTELE" onclick="return formValidation()" />
					</div>
				</li>
				<div class="clear"></div>
			</ul>
                    </form>
		</div>
		<div class="clear"></div>
		</div>
	</div>
    </div>
</div>	

<div class="travel">
    <h1>Travel The <em>World</em></i></h1>
</div>
<div class="hotels">
<div id="shadow"></div>
<div id="containeric">
  <div id="mainimage">
      <%    
        PreparedStatement psmt = null;
        ResultSet rst = null; 
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            String upit = "select * from hoteli where grad='Paris'";
            String sqlPagination="SELECT SQL_CALC_FOUND_ROWS * FROM hoteli where grad='Paris'";
            
            psmt=con.prepareStatement(sqlPagination);
            rst= psmt.executeQuery();
    
            //racuna ukupan broj redova
            String sqlRowCnt="SELECT FOUND_ROWS() as cnt";
            psmt=con.prepareStatement(sqlRowCnt);
            rst=psmt.executeQuery();
            int broj = 0;
            while(rst.next())
            {
                broj = rst.getInt("cnt");
                request.setAttribute("paris", broj);
            }

            rs = stmt.executeQuery(upit);

     
            if(rs.next())
            { 
                byte[] imgData = rs.getBytes("slika");
                String encode = Base64.getEncoder().encodeToString(imgData);
                request.setAttribute("image", encode);
            }
            rs.close();
            rst.close();
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }    
    %>         
    <div id="heart"><span class="entypo-heart"></span></div>
    <span class="likes">${paris}</span>
    <div id="slides"><img src="data:image/jpeg;base64,${image}" title="image" /></div>
  </div>
  
  <div id="sidepanel">
    <div id="next"><span class="entypo-right-open-big"></span></div>
    <div id="previous"><span class="entypo-left-open-big"></span></div>
  </div>
  <div id="ratingbox"><span class="counter">${paris}</span>
  </div>
  <div id="infopanel">
    <h1 class="h1h2 h1">Paris</span
    </h1>
    <div id="stars">
      <span class="entypo-star-empty"></span>
      <span class="entypo-star-empty"></span>
      <span class="entypo-star-empty"></span>
      <span class="entypo-star-empty"></span>
    </div>
    <h2 class="h1h2 h2">FRA</h2>
    <a class="htlbtn" href="${pageContext.request.contextPath}/klijent/ParisHotels.jsp">POGLEDAJ HOTELE</a>
  </div>
</div>
<div id="shadow"></div>
<div id="containeric">
  <div id="mainimage">
      <%      
        try
        {           
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            String upit = "select * from hoteli where grad='Dubai'";
            String sqlPagination="SELECT SQL_CALC_FOUND_ROWS * FROM hoteli where grad='Dubai'";
            
            psmt=con.prepareStatement(sqlPagination);
            rst= psmt.executeQuery();
    
            //racuna ukupan broj redova
            String sqlRowCnt="SELECT FOUND_ROWS() as cnt";
            psmt=con.prepareStatement(sqlRowCnt);
            rst=psmt.executeQuery();
            int broj = 0;
            while(rst.next())
            {
                broj = rst.getInt("cnt");
                request.setAttribute("dubai", broj);
            }
            rs = stmt.executeQuery(upit);
            
            if(rs.next())
            { 
                byte[] imgData = rs.getBytes("slika"); 
                String encode = Base64.getEncoder().encodeToString(imgData);
                request.setAttribute("image", encode);
            }
            rs.close();
            rst.close();
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    %>         
    <div id="heart"><span class="entypo-heart"></span></div>
    <span class="likes">${dubai}</span>
    <div id="slides"><img src="data:image/jpeg;base64,${image}" title="image" /></div>
  </div>
  <div id="sidepanel">
    <div id="next"><span class="entypo-right-open-big"></span></div>
    <div id="previous"><span class="entypo-left-open-big"></span></div>
  </div>
  <div id="ratingbox"><span class="counter">${dubai}</span>
  </div>
  <div id="infopanel">
    <h1 class="h1h2 h1">Dubai</span>
    </h1>
    <div id="stars">
      <span class="entypo-star-empty"></span>
      <span class="entypo-star-empty"></span>
      <span class="entypo-star-empty"></span>
      <span class="entypo-star-empty"></span>
    </div>
    <h2 class="h1h2 h2">UAE</h2>
    <a class="htlbtn" href="${pageContext.request.contextPath}/klijent/DubaiHotels.jsp">POGLEDAJ HOTELE</a>
  </div>
</div>
<div id="shadow"></div>
<div id="containeric">
  <div id="mainimage">
      <%      
        try
        {            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            String upit = "select * from hoteli where grad='New York'";
            String sqlPagination="SELECT SQL_CALC_FOUND_ROWS * FROM hoteli where grad='New York'";
            
            psmt=con.prepareStatement(sqlPagination);
            rst= psmt.executeQuery();
    
            //racuna ukupan broj redova
            String sqlRowCnt="SELECT FOUND_ROWS() as cnt";
            psmt=con.prepareStatement(sqlRowCnt);
            rst=psmt.executeQuery();
            int broj = 0;
            while(rst.next())
            {
                broj = rst.getInt("cnt");
                request.setAttribute("newyork", broj);
            }

            rs = stmt.executeQuery(upit);

            if(rs.next())
            { 
                byte[] imgData = rs.getBytes("slika"); 
                String encode = Base64.getEncoder().encodeToString(imgData);
                request.setAttribute("image", encode);
            }
            rs.close();
            rst.close();
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    %>         
    <div id="heart"><span class="entypo-heart"></span></div>
    <span class="likes">${newyork}</span>
    <div id="slides"><img src="data:image/jpeg;base64,${image}" title="image" /></div>
  </div>
  <div id="sidepanel">
    <div id="next"><span class="entypo-right-open-big"></span></div>
    <div id="previous"><span class="entypo-left-open-big"></span></div>
  </div>
  <div id="ratingbox"><span class="counter">${newyork}</span>
  </div>
  <div id="infopanel">
    <h1 class="h1h2 h1">New York City</span
    </h1>
    <div id="stars">
      <span class="entypo-star-empty"></span>
      <span class="entypo-star-empty"></span>
      <span class="entypo-star-empty"></span>
      <span class="entypo-star-empty"></span>
    </div>
    <h2 class="h1h2 h2">SAD</h2>
    <a class="htlbtn" href="${pageContext.request.contextPath}/klijent/NewYorkHotels.jsp">POGLEDAJ HOTELE</a>
  </div>
</div>
</div>
   
<div class="main_bg">
<div class="wrap">
        <section>
        <div class="page1">
            <div class="page1-center">
                <div class="text">
                    <h1 class="headone">ABOUT STARLING</h1>
                    <p>With more than 550 locations across six continents, Starling Hotels & Resorts provide an authentic and contemporary experience for guests worldwide.</p>
                </div>
                <button class="button">
                    <a href="${pageContext.request.contextPath}/klijent/registracija.jsp">JOIN NOW</a>
                </button>
            </div>
        </div>
        </section>
	
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
                                <script type="text/javascript">
                                    $(".user-menu").click(function() {
  $(this).toggleClass("show");
});

                                </script>
</body>
</html>
