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
    if(request.getSession().getAttribute("user") != null && user.getPower().equals("Menadzer hotela"))
    {
        
    }
    else
    {
        request.setAttribute("popupid", 2);
        request.setAttribute("poruka", "Niste ulogovani ili niste menadzer hotela.Pristup odbijen!");
        RequestDispatcher rd = request.getRequestDispatcher("../klijent/index.jsp");
        rd.forward(request, response);  
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Kontrolna tabla</title>
        <meta charset="UTF-8">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/admin/images/favicon.png" type="image/x-icon"/>
        <link href='${pageContext.request.contextPath}/admin/fonts/fonts/fonts.css' rel='stylesheet' type='text/css'>
        <link href="${pageContext.request.contextPath}/admin/css/admin.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/admin/fonts/css/all.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/material.blue-red.min.css">
        <script defer src="${pageContext.request.contextPath}/css/js/material.min.js"></script>
        <script src="${pageContext.request.contextPath}/admin/js/jquery.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/admin/js/js.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/admin/js/jquery.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/admin/js/js.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/admin/js/sweetalert.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/js/sweetalert.css">
        <script>
      function error()  
        {    
            swal ("Error" ,  "${poruka}",  "error" );    
        }
        function success()  
        {   
            swal ("Success" ,  "${poruka}",  "success" );  
        }
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
  <section class="navigation">
    <p class="title">
      <img style="height: 70px; width: 170px;" src="${pageContext.request.contextPath}/klijent/images/logo.png">
    <p><hr style="height: 1px; width:100%; max-width: 185px; margin: 0 auto;">

        <img class="profile-pic" src="${pageContext.request.contextPath}/admin/images/img_avatar.png" alt=""/>
        
         <%
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String poruka = "";
        try
        {           
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            String upit = "select * from klijenti where kime='"+user.getUsername()+"'";
            rs = stmt.executeQuery(upit);
            %>
            
            <%  while(rs.next())
            { %>
            <p class="name"><%=rs.getString("ime")%><p>
            <p class="name"><%=rs.getString("vrsta")%><p>
            <%} %>
           
            <%
            rs.close();
            stmt.close();
            con.close();
        }
        catch(Exception e){}

    %>
       
            <div class="function-wrapper">
              <a href="${pageContext.request.contextPath}/odjavljivanje">
              <button id="logout" class="function"><i class="material-icons">clear</i></button>
              <div class="mdl-tooltip" for="odjavljivanje">
                Odjavi se
              </div>
              </a>
            </div>

            <div class="options-wrapper">
              <div class="panel-option active">
                <i class="material-icons">computer</i>
                <a href="${pageContext.request.contextPath}/menadzer/index.jsp"><p>Statistika</p></a>
              </div>

              <div class="panel-option">
                <i class="material-icons">hotel</i>
                <a href="${pageContext.request.contextPath}/menadzer/hotel.jsp"><p>Hotel</p></a>
              </div>

              <div class="panel-option">
                <i class="material-icons">room</i>
                <a href="${pageContext.request.contextPath}/menadzer/sobe.jsp"><p>Sobe</p></a>
              </div>
              
              <div class="panel-option">
                <i class="material-icons">room</i>
                <a href="${pageContext.request.contextPath}/menadzer/tipovisoba.jsp"><p>Tipovi soba</p></a>
              </div>

              <div class="panel-option">
                <i class="material-icons">shopping_cart</i>
                <a href="${pageContext.request.contextPath}/menadzer/rezervacije.jsp"><p>Rezervacije</p></a>
              </div>
              <div>
  </section>

  <section class="page-content">
    <div class="header">
      <p class="big">Kontrolna tabla <span class="small">all informations about Starling Hotel Chain</span></p>
      <!--<div id="wrapper">
        <input class="search" type="search" placeholder="search...">
        <button class="search-btn">
            <i class="material-icons">search</i>
          </button>

        <button id="help" class="help">
            <i class="material-icons">help</i>
            </button>

        <div class="mdl-tooltip" for="help">
          Help
        </div>
      </div>-->
    </div>

    <div class="content">
      <h2 style="width: 50%; float: none; margin: 50px auto; color: #AAA; text-align: center;">Statistika</h2>
      <div class="wrapperic">
        
     <div class="counter col_fourth">
      <center><i class="fa fa-bed"></i></center>
      <%
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                stmt = con.createStatement();
                String upit = "select * from tipovisoba where hotelID="+user.getHotelID()+"";
                rs = stmt.executeQuery(upit);
                int i=0;
                %>
                <% while(rs.next())
                {
                i++;
                %>

                <%} 
                %>

                <h2 class="timer count-title count-number" data-to="<%=i%>" data-speed="3000"><%=i%></h2>
                <%
                rs.close();
                stmt.close();
                con.close();
            }
            catch(SQLException ex)
            {
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
                request.setAttribute("poruka", "Došlo je do greške! ("+npe.getMessage()+")");
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
      <p class="count-text ">Hotel - Tipovi soba</p>
    </div>
    <div class="counter col_fourth">
      <center><i class="fa fa-bed"></i></center>
      <%
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                stmt = con.createStatement();
                String upit = "select * from sobe where hotelID="+user.getHotelID()+"";
                rs = stmt.executeQuery(upit);
                int i=0;
                %>
                <%  while(rs.next())
                {
                i++;
                %>

                <%} 
                %>

                <h2 class="timer count-title count-number" data-to="<%=i%>" data-speed="3000"><%=i%></h2>
                <%
                rs.close();
                stmt.close();
                con.close();
            }
            catch(SQLException ex)
            {
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
                request.setAttribute("poruka", "Došlo je do greške! ("+npe.getMessage()+")");
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
      <p class="count-text ">Hotel - Sobe</p>
    </div>

    <div class="counter col_fourth end">
      <center><i class="fa fa-shopping-cart"></i></center>
      <%
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                stmt = con.createStatement();
                String upit = "select * from rezervacije where hotelID="+user.getHotelID()+"";
                rs = stmt.executeQuery(upit);
                int i=0;
                %>
                <%  while(rs.next())
                {
                i++;
                %>

                <%} 
                %>

                <h2 class="timer count-title count-number" data-to="<%=i%>" data-speed="3000"><%=i%></h2>
                <%
                rs.close();
                stmt.close();
                con.close();
            }
            catch(SQLException ex)
            {
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
                request.setAttribute("poruka", "Došlo je do greške! ("+npe.getMessage()+")");
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
      <p class="count-text ">Hotel - Rezervacije</p>
    </div>
</div>
    </div>
  </section>
              <script src="js/js.js" type="text/javascript"></script>
</body>
</html>
