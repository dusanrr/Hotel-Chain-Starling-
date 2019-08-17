<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="beans.Klijent"%>
<%@page import = "java.util.*" session="true"%>
<%@page import="java.io.*" %>
<%@page import="java.sql.*" %>
<%@page import="javax.servlet.*" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.Timer"%>
<%@page import="java.time.LocalDateTime"%>
<%!
public int nullIntconv(String str)
{   
    int conv=0;
    if(str==null)
    {
        str="0";
    }
    else if((str.trim()).equals("null"))
    {
        str="0";
    }
    else if(str.equals(""))
    {
        str="0";
    }
    try{
        conv=Integer.parseInt(str);
    }
    catch(Exception e)
    {
    }
    return conv;
}
%>
<%

    Connection conn = null;
    Class.forName("com.mysql.cj.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");

    ResultSet rsPagination = null;
    ResultSet rsRowCnt = null;
    
    PreparedStatement psPagination=null;
    PreparedStatement psRowCnt=null;
    
    int iShowRows=5;  //prikaza po strani
    int iTotalSearchRecords=10;  //broj strana koji se prikazuje
    
    int iTotalRows=nullIntconv(request.getParameter("iTotalRows"));
    int iTotalPages=nullIntconv(request.getParameter("iTotalPages"));
    int iPageNo=nullIntconv(request.getParameter("iPageNo"));
    int cPageNo=nullIntconv(request.getParameter("cPageNo"));
    
    String poruka = "";
    
    int iStartResultNo=0;
    int iEndResultNo=0;
    
    if(iPageNo==0)
    {
        iPageNo=0;
    }
    else{
        iPageNo=Math.abs((iPageNo-1)*iShowRows);
    }
    
    String sqlPagination="SELECT SQL_CALC_FOUND_ROWS * FROM hoteli where grad='Dubai' limit "+iPageNo+","+iShowRows+"";

    psPagination=conn.prepareStatement(sqlPagination);
    rsPagination=psPagination.executeQuery();
    
    //racuna ukupan broj redova
    String sqlRowCnt="SELECT FOUND_ROWS() as cnt";
    psRowCnt=conn.prepareStatement(sqlRowCnt);
    rsRowCnt=psRowCnt.executeQuery();
     
    if(rsRowCnt.next())
    {
       iTotalRows=rsRowCnt.getInt("cnt");
    }
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
////////////////////////////////////////////////////////////////////////////////
</script>  
</head>
<body>
<!-- start header -->
<!--<div id="main-wrapper">
	<main class="main" role="main" id="main-content">-->
		
            <%
                Connection con = null;
                Statement stmt = null;
                ResultSet rs = null;
                HttpSession sesija = request.getSession();
                Klijent user = (Klijent)sesija.getAttribute("user");
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
                      <span class="user-image"><img src="${pageContext.request.contextPath}/admin/images/img_avatar.png"></span>
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
                    <%} 
                    rs.close();
                    stmt.close();
                    con.close();
                    %>        
            <%
                }
                catch(Exception e){}

            %>
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
				<li class="active"><a href="${pageContext.request.contextPath}/klijent/rezervacije.jsp">rezervacije</a></li> 
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

       <img src="${pageContext.request.contextPath}/klijent/images/dubaicity.jpg" alt="Dubai" style="height: 550px; width: 100%;"/>
<!--start main -->
<div class="main_bg">
    <div class="wrap">
        <div class="main">
                        <div class="online_reservation">
	<div class="b_room">
		<div class="booking_room">
			<h4>book a room online</h4>
			<p>Find hotel rooms</p>
		</div>

		<div class="reservation">
                    <form method="GET" action="${pageContext.request.contextPath}/PrikaziSobe" name="search">
			<ul>
                                <li  class="span1_of_1 left">
					<h5>Grad</h5>
					<div class="book_date">
                                            
				<input type="text" name="lokacija" required readonly="readonly" value="Dubai" oninvalid="this.setCustomValidity('Enter city here')" oninput="this.setCustomValidity('')">
                               
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
					<!--<div class="date_btn">
                                             <input type="submit" value="FIND HOTELS" onclick="return formValidation()" />
					</div>-->
				</li>
				<div class="clear"></div>
			</ul>
                    
		</div>
		<div class="clear"></div>
		</div>
	</div>
		<div class="res_online">
			<h4>Hotels in Dubai</h4>
			<p class="para">With a name that's equated with luxury, Dubai promises an ultrachic ambience on the shores of the Arabian Gulf. Enjoy the view from Burj Khalifa—the tallest skyscraper in the world—or explore Palm Jumeirah, a sprawling man-made island. Eclectic shopping, dining, and nightlife scenes all make Dubai a coveted United Arab Emirates destination; explore it all from your Starling hotel in Dubai.</p>
		</div>			
        </div>
    </div>
</div>	
<div class="wrapper">
     <%    
        try
        {
           
            %>

            <%  int i=1;
                while(rsPagination.next())
            { %>
              <div class="product">
            <div class="title">
              <%= rsPagination.getString("naziv")%>
            </div>

            <div class="text">
              <div class="code">Država: <%= rsPagination.getString("drzava")%></div>
              <div class="code">Grad: <%= rsPagination.getString("grad")%></div>
              <div class="code">Adresa: <%= rsPagination.getString("adresa")%></div>
              <div class="description">
                <%= rsPagination.getString("opis")%>
              </div>
              <div class="review">
                  <%
                  if(rsPagination.getInt("zvezdice") == 1)
                  {
                  %>
                    <span class="star-icon"></span>
                    <span class="star-icon star-disable"></span>
                    <span class="star-icon star-disable"></span>
                    <span class="star-icon star-disable"></span>
                    <span class="star-icon star-disable"></span>
                <%}
                  else if(rsPagination.getInt("zvezdice") == 2)
                  {
                %>
                    <span class="star-icon"></span>
                    <span class="star-icon"></span>
                    <span class="star-icon star-disable"></span>
                    <span class="star-icon star-disable"></span>
                    <span class="star-icon star-disable"></span>
                    
                   <%}
                  else if(rsPagination.getInt("zvezdice") == 3)
                  {
                %>
                    <span class="star-icon"></span>
                    <span class="star-icon"></span>
                    <span class="star-icon"></span>
                    <span class="star-icon star-disable"></span>
                    <span class="star-icon star-disable"></span>
                    
                   <%}
                  else if(rsPagination.getInt("zvezdice") == 4)
                  {
                %>
                    <span class="star-icon"></span>
                    <span class="star-icon"></span>
                    <span class="star-icon"></span>
                    <span class="star-icon"></span>
                    <span class="star-icon star-disable"></span>
                    
                   <%}
                  else if(rsPagination.getInt("zvezdice") == 5)
                  {
                %>
                    <span class="star-icon"></span>
                    <span class="star-icon"></span>
                    <span class="star-icon"></span>
                    <span class="star-icon"></span>
                    <span class="star-icon"></span>
                    
                    <%}%>
                
                <span class="star-reviews"><%= rsPagination.getInt("zvezdice")%></span>
              </div>    
              <%
                  Connection cons = null;
                  Statement stmts = null;
                  ResultSet rss = null;
                  try
                  {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    cons = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");


                    stmts = cons.createStatement();
                    String query = "SELECT AVG(paket1) AS prosek FROM `tipovisoba` WHERE hotelID='"+rsPagination.getInt("ID")+"'";
                    rss = stmts.executeQuery(query); 
                    if(rss.first())
                    {
                        %>
                        <div class="price">
                        $<%=rss.getInt("prosek")%>
                        </div>
                        <%
                    }
                    rss.close();
                    stmts.close();
                    cons.close();
                  }
                  catch(Exception e)
                  {
                    e.printStackTrace();
                  }
              %>
              
              <div class="code">AVG/Night from</div>
              <div class="shop-actions">    
                  <input type="text" name="hotelID" required value="<%=rsPagination.getInt("ID")%>" hidden="true">
                  <a class="product text shop-actions hot" onclick="return formValidation()">POGLEDAJ SOBE</a>
              </div>
            </div>

            <div class="preview">
              <svg class="svg" viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
                <circle class="circle" cx="100" cy="100" r="125"/>
                <%
                      byte[] imgData = rsPagination.getBytes("slika"); // blob field 
                      String encode = Base64.getEncoder().encodeToString(imgData);
                  %>
                  <image class="image" xlink:href="data:image/jpeg;base64,<%=encode%>" x="0" y="0" width="200px" height="180px"/>
              </svg>
            </div>

          </div>
              </form>
            
             <%}
            rsPagination.close();
            rsRowCnt.close();
            psPagination.close();
            psRowCnt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    %>
            <%
        //racuna next start i end
        try{
            if(iTotalRows<(iPageNo+iShowRows))
            {
                iEndResultNo=iTotalRows;
            }
            else
            {
                iEndResultNo=(iPageNo+iShowRows);
            }
           
            iStartResultNo=(iPageNo+1);
            iTotalPages=((int)(Math.ceil((double)iTotalRows/iShowRows)));
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

%>                     
        <div id="pagicontainer">
            <div class="pagination">
                                  <%
        //brojevi stranice
        int i=0;
        int cPage=0;
        if(iTotalRows!=0)
        {
        cPage=((int)(Math.ceil((double)iEndResultNo/(iTotalSearchRecords*iShowRows))));
        
        int prePageNo=(cPage*iTotalSearchRecords)-((iTotalSearchRecords-1)+iTotalSearchRecords);
        if((cPage*iTotalSearchRecords)-(iTotalSearchRecords)>0)
        {
         %>
          <a class='page gradient' href="${pageContext.request.contextPath}/klijent/DubaiHotels.jsp?iPageNo=<%=prePageNo%>&cPageNo=<%=prePageNo%>"> << Previous</a>
         <%
        }
        
        for(i=((cPage*iTotalSearchRecords)-(iTotalSearchRecords-1));i<=(cPage*iTotalSearchRecords);i++)
        {
          if(i==((iPageNo/iShowRows)+1))
          {
          %>
           <a class='page gradient' href="${pageContext.request.contextPath}/klijent/DubaiHotels.jsp?iPageNo=<%=i%>" style="color: #59d"><b><%=i%></b></a>
          <%
          }
          else if(i<=iTotalPages)
          {
          %>
           <a class='page gradient' href="${pageContext.request.contextPath}/klijent/DubaiHotels.jsp?iPageNo=<%=i%>"><%=i%></a>
          <% 
          }
        }
        if(iTotalPages>iTotalSearchRecords && i<iTotalPages)
        {
         %>
         <a class='page active' href="${pageContext.request.contextPath}/hotels/DubaiHotels.jsp?iPageNo=<%=i%>&cPageNo=<%=i%>"> >> Next</a> 
         <%
        }
        }
      %>
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
                                 <script type="text/javascript">
                                    $(".user-menu").click(function() {
  $(this).toggleClass("show");
});

                                </script>
</body>
</html>
