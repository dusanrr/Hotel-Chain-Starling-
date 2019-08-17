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
    HttpSession sesija = request.getSession();
    Klijent user = (Klijent)sesija.getAttribute("user");
    String poruka = "";
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
                    <%} %>        
            <%
                    rs.close();
                    stmt.close();
                    con.close();
                }
                catch(SQLException ex)
                {
                    ex.printStackTrace();
                    sesija.invalidate();
                    if(con!=null)
                        try
                        {
                            con.close();
                        }
                        catch(Exception exc)
                        {
                            poruka = poruka+exc.getMessage();
                        }
                    request.setAttribute("popupid", 2);
                    request.setAttribute("poruka", "Došlo je do greške! ("+ex.getMessage()+")");
                    RequestDispatcher rd = request.getRequestDispatcher("../klijent/index.jsp");
                    rd.forward(request, response);
                }
                catch(NullPointerException npe)
                {
                    npe.printStackTrace();
                    request.setAttribute("popupid", 2);
                    request.setAttribute("poruka", "Niste ulogovani.");
                    RequestDispatcher rd = request.getRequestDispatcher("../klijent/index.jsp");
                    rd.forward(request, response);  
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    request.setAttribute("popupid", 2);
                    request.setAttribute("poruka", "Došlo je do greške! ("+e.getMessage()+")");
                    RequestDispatcher rd = request.getRequestDispatcher("../klijent/index.jsp");
                    rd.forward(request, response);  
                }

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
				<li><a href="${pageContext.request.contextPath}/klijent/index.jsp">Početna</a></li> 
				<li class="active"><a href="${pageContext.request.contextPath}/klijent/rezervacije.jsp">rezervacije</a></li> 
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
				<li><a href="${pageContext.request.contextPath}/klijent/index.jsp">Početna</a></li> 
				<li class="active"><a href="${pageContext.request.contextPath}/klijent/rezervacije.jsp">rezervacije</a></li> 
                                <li><a href="${pageContext.request.contextPath}/odjavljivanje">odjavi se</a></li>
				</ul>
				<a href="#" id="pull">Menu</a>
                                 <%
                         }
                        else
                        {
                        %>
                                <ul>
				<li><a href="${pageContext.request.contextPath}/klijent/index.jsp">Početna</a></li> 
				<li class="active"><a href="${pageContext.request.contextPath}/klijent/rezervacije.jsp">rezervacije</a></li> 
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

    <!----start-images-slider---->
		<div class="images-slider">
			<!-- start slider -->
		    <div id="fwslider">
		        <div class="slider_container">
		            <div class="slide"> 
		                <!-- Slide image -->
                                <img class="rezslika" src="${pageContext.request.contextPath}/klijent/images/sliderres.jpg" alt=""/>
		                <!-- /Slide image -->
		                
		            </div>
		        </div>
		        <div class="timers"> </div>
		        <div class="slidePrev"><span> </span></div>
		        <div class="slideNext"><span> </span></div>
		    </div>
		    <!--/slider -->
		</div>
<!--start main -->
<div class="main_bg">
    <div class="wrap">
        <div class="main">
		<div class="res_online">
                    <h4>Rezervacije</h4>
		</div>			
        </div>
    </div>
</div>	
<div class="wrapper">
    <div id="body">
                <table>
                  <thead>
                    <tr>
                      <td class="center">ID rezervacije</td>
                      <td class="center">Hotel</td>
                      <td class="center">Tip sobe</td>
                      <td class="center">Klijent</td>
                      <td class="center">Datum dolaska</td>
                      <td class="center">Datum odlaska</td>
                      <td class="center">Vreme odlaska</td>
                      <td class="center">Odrasli</td>
                      <td class="center">Deca</td>
                      <td class="center">Broj soba</td>
                      <td class="center">Novac</td>
                      <td class="center">Poeni</td>
                      <td class="center">Otkazi rezervaciju</td>
                    </tr>
                  </thead>
                  <tfoot>
                <tr class="separator">
                  <td colspan="6"></td>
                </tr>
                <!--<tr>
                    <td colspan="8">Subtotal</td>
                  <td class="right"></td>
                </tr>-->
                <tr class="separator">
                  <td colspan="6"></td>
                </tr>
                <tbody>
     <%    
        Connection conn = null;
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

        int iStartResultNo=0;
        int iEndResultNo=0;

        if(iPageNo==0)
        {
            iPageNo=0;
        }
        else{
            iPageNo=Math.abs((iPageNo-1)*iShowRows);
        }
        try
        {           
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");

            String sqlPagination="SELECT SQL_CALC_FOUND_ROWS * FROM `rezervacije` REZ INNER JOIN `hoteli` HT ON REZ.hotelID=HT.ID INNER JOIN `tipovisoba` RT ON REZ.tipsobeID=RT.ID INNER JOIN `klijenti` CT ON REZ.klijentID=CT.ID where REZ.klijentID='"+user.getId()+"' limit "+iPageNo+","+iShowRows+"";
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
               
            <%  int i=1;
                while(rsPagination.next())
                { 
                    
      %>
        <tr>
          <td class="center"><%= rsPagination.getInt(1)%></td>
          <!--<td class="center"><img style="max-width: 150px; max-height: 150px;" src="data:image/jpeg;base64," alt=""/></td>-->
          <td class="center"><%= rsPagination.getString(16) %></td>
          <td class="center"><%= rsPagination.getString(25) %></td>
          <td class="center"><%= rsPagination.getString(37) %></td>
          <td class="center"><%= rsPagination.getString(5) %></td>
          <td class="center"><%= rsPagination.getString(6) %></td>
          <td class="center"><%= rsPagination.getString(7) %></td>
          <td class="center"><%= rsPagination.getInt(9) %></td>
          <td class="center"><%= rsPagination.getInt(10) %></td>
          <td class="center"><%= rsPagination.getInt(8) %></td>
          <%
              if(rsPagination.getInt(11) == -1)
              {%>
                  <td class="center">Plaćeno poenima</td>
              <%}
              else
              {%>
                  <td class="center"><%= rsPagination.getInt(11) %>$</td>
              <%}
              %>
          <%
              if(rsPagination.getInt(12) == -1)
              {%>
                  <td class="center">Plaćeno novcem</td>
              <%}
              else
              {%>
                  <td class="center"><%= rsPagination.getInt(12) %></td>
              <%}
              %>
          <td class="center"><a class='page gradient' href="${pageContext.request.contextPath}/OtkaziRezervaciju?id=<%=rsPagination.getInt(1)%>">OTKAZI</a></td>
        </tr>
             <%}
                if(!rsPagination.first())
                {%>
                       <td class="center">Jos uvek nemate rezervacije.</td>
                       <td class="center"></td>
                       <td class="center"></td>
                       <td class="center"></td>
                       <td class="center"></td>
                       <td class="center"></td>
                       <td class="center"></td>
                       <td class="center"></td>
                       <td class="center"></td>
                       <td class="center"></td>
                       <td class="center"></td>
                       <td class="center"></td>
                <%} 
            rsPagination.close();
            rsRowCnt.close();
            psPagination.close();
            psRowCnt.close();
            conn.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            sesija.invalidate();
            if(conn!=null)
                try
                {
                    conn.close();
                }
                catch(Exception exc)
                {
                    poruka = poruka+exc.getMessage();
                }
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Došlo je do greške! ("+ex.getMessage()+")");
            RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
            rd.forward(request, response);
        }
        catch(NullPointerException npe)
        {
            npe.printStackTrace();
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Niste ulogovani.");
            RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
            rd.forward(request, response);  
        }
        catch(Exception e)
        {
            e.printStackTrace();
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Došlo je do greške! ("+e.getMessage()+")");
            RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
            rd.forward(request, response);  
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
        </tbody>
          </table>
        </div>
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
          <a class='page gradient' href="${pageContext.request.contextPath}/klijent/rezervacije.jsp?iPageNo=<%=prePageNo%>&cPageNo=<%=prePageNo%>"> << Previous</a>
         <%
        }
        
        for(i=((cPage*iTotalSearchRecords)-(iTotalSearchRecords-1));i<=(cPage*iTotalSearchRecords);i++)
        {
          if(i==((iPageNo/iShowRows)+1))
          {
          %>
           <a class='page gradient' href="${pageContext.request.contextPath}/klijent/rezervacije.jsp?iPageNo=<%=i%>" style="color: #59d"><b><%=i%></b></a>
          <%
          }
          else if(i<=iTotalPages)
          {
          %>
           <a class='page gradient' href="${pageContext.request.contextPath}/klijent/rezervacije.jsp?iPageNo=<%=i%>"><%=i%></a>
          <% 
          }
        }
        if(iTotalPages>iTotalSearchRecords && i<iTotalPages)
        {
         %>
         <a class='page active' href="${pageContext.request.contextPath}/klijent/rezervacije.jsp?iPageNo=<%=i%>&cPageNo=<%=i%>"> >> Next</a> 
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
