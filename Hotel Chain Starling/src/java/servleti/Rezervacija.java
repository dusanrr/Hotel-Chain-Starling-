package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class Rezervacija extends HttpServlet {
     public int daysBetween(Date d1, Date d2){
             return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
     }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesija = request.getSession();
        Klijent user = (Klijent)sesija.getAttribute("user");
        try
        {
            Connection conn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            
            ResultSet rsPagination = null;
            
            PreparedStatement psPagination=null;
         
         
            String hotelID = request.getParameter("hotelID");
            String rt = request.getParameter("tipsobe");
            String location = request.getParameter("lokacija");
            String rooms = request.getParameter("sobe");
            String checkinDate = request.getParameter("datumDolaska");
            String checkoutDate = request.getParameter("datumOdlaska");
            String adults = request.getParameter("odrasli");
            String kids = request.getParameter("deca");
            int guests = Integer.parseInt(adults) + Integer.parseInt(kids);
            String rate = request.getParameter("paket");
            
            SimpleDateFormat myFormat = new SimpleDateFormat("MM.dd.yyyy");
            String dateBeforeString = checkinDate;
            String dateAfterString = checkoutDate;
            int dani = 0;
            int moneyz = 0;
            int paywithpoints=0;

            
            String rts = "";
            if(rate.equals("paket1"))
            {
                rts = "Member rate";
            }
            else if(rate.equals("paket2"))
            {
                rts = "Standard rate";
            }
            else if(rate.equals("paket3"))
            {
                rts = "Bed and breakfast";
            }
            else if(rate.equals("paket4"))
            {
                rts = "Double points";
            }
            else if(rate.equals("paket5"))
            {
                rts = "Explore package";
            }

            if(checkinDate == null)
            {
                checkinDate = "";
            }
            else if(checkoutDate == null)
            {
                checkoutDate = "";
            }
            else if(rooms == null)
            {
                rooms = "";
            }
            else if(adults == null)
            {
                adults = "";
            }
            else if(kids == null)
            {
                kids = "";
            }
            String sqlPagination="SELECT * FROM `tipovisoba` RT INNER JOIN `hoteli` HT where RT.ID='"+rt+"' and HT.ID='"+hotelID+"'";
            
            psPagination=conn.prepareStatement(sqlPagination);
            rsPagination=psPagination.executeQuery();
            if(rsPagination.first())
            {
            out.println("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<title>Starling Hotels</title>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n" +
                    "\n" +
                    "<link rel=\"shortcut icon\" href=\"klijent/images/favicon.png\" type=\"image/x-icon\"/>\n" +
                    "<link href='admin/fonts/fonts/fonts.css' rel='stylesheet' type='text/css'>  "+
                    "<link href=\"klijent/css/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" />\n" +
                    "<link href=\"admin/fonts/css/all.css\" rel=\"stylesheet\">\n" +
                    "<link href=\"klijent/css/animate.min.css\" rel=\"stylesheet\">\n" +
                    "<script src=\"klijent/js/jquery.min.js\"></script>\n" +
                    "<link rel=\"stylesheet\" href=\"klijent/css/fwslider.css\" type=\"text/css\" media=\"all\">\n" +
                    "<script type=\"text/javascript\" src=\"klijent/js/jquery-ui.min.js\"></script>\n" +
                    "<script type=\"text/javascript\" src=\"klijent/js/css3-mediaqueries.js\"></script>\n" +
                    "<script type=\"text/javascript\" src=\"klijent/js/fwslider.js\"></script>\n" +
                    "<script type=\"text/javascript\" src=\"klijent/js/slick.js\"></script>\n" +
                    "<script src=\"klijent/js/sweetalert.min.js\"></script>\n" +
                    "<link rel=\"stylesheet\" href=\"klijent/css/sweetalert.css\">\n" +
                    "<link rel=\"stylesheet\" href=\"klijent/css/jquery-ui.css\" />\n" +
                    "<script src=\"klijent/js/jquery-ui.js\"></script>\n" +
                    "		  <script>\n" +
                    "				  $(function() {\n" +
                    "				    $( \"#datepicker,#datepicker1\" ).datepicker();\n" +
                    "				  });\n" +
                    "		  </script>\n" +
                    "<link type=\"text/css\" rel=\"stylesheet\" href=\"klijent/css/JFGrid.css\" />\n" +
                    "<link type=\"text/css\" rel=\"stylesheet\" href=\"klijent/css/JFFormStyle-1.css\" />\n" +
                    "		<script type=\"text/javascript\" src=\"klijent/js/JFCore.js\"></script>\n" +
                    "		<script type=\"text/javascript\" src=\"klijent/js/JFForms.js\"></script>\n" +
                    "<script>\n" +
                    "		$(function() {\n" +
                    "			var pull 		= $('#pull');\n" +
                    "				menu 		= $('nav ul');\n" +
                    "				menuHeight	= menu.height();\n" +
                    "\n" +
                    "			$(pull).on('click', function(e) {\n" +
                    "				e.preventDefault();\n" +
                    "				menu.slideToggle();\n" +
                    "			});\n" +
                    "\n" +
                    "			$(window).resize(function(){\n" +
                    "        		var w = $(window).width();\n" +
                    "        		if(w > 320 && menu.is(':hidden')) {\n" +
                    "        			menu.removeAttr('style');\n" +
                    "        		}\n" +
                    "    		});\n" +
                    "		});\n" +
                    "</script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<!-- start header -->\n" +
                                        "<!--<div id=\"main-wrapper\">\n" +
"	<main class=\"main\" role=\"main\" id=\"main-content\">-->\n");
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
                    if(rs.first())
                    { 
out.println("                    <div id=\"main-top-bar\">\n" +
"			<a href=\"https://rolecalljobs.com\" class=\"main-logo\"></a>\n" +
"                <div class=\"user-menu\">\n" +
"                  <div class=\"user-menu-item\">\n" +
"                    <div class=\"user-image-wrapper\">\n" +
"                      <span class=\"user-image\"><img src=\"klijent/images/shc-avatar.png\"></span>\n" +
"                    </div>\n" +
"                    <div class=\"user-name-wrapper\">\n" +
"                    "+rs.getString("ime")+" <i class=\"fa fa-caret-down\"></i>\n" +
"                  </div>\n" +
"                </div>\n" +
"                <div class=\"user-dropdown\">\n" +
"                  <a href=\"#\" class=\"dropdown-item\"><span class=\"mr-3\"><i class=\"fa fa-underline\"></i></span>"+rs.getString("kime")+"</a>\n" +
"                  <a href=\"#\" class=\"dropdown-item\"><span class=\"mr-3\"><i class=\"fab fa-superpowers\"></i></span>"+rs.getString("vrsta")+"</a>\n" +
"                  <a href=\"#\" class=\"dropdown-item\"><span class=\"mr-3\"><i class=\"fa fa-envelope\"></i></span>"+rs.getString("email")+"</a>\n" +
"                  <a href=\"#\" class=\"dropdown-item\"><span class=\"mr-3\"><i class=\"fab fa-pinterest-p\"></i></span>"+rs.getInt("poeni")+"</a>\n" +
"                  <a href=\"odjavljivanje\" class=\"dropdown-item\"><span class=\"mr-3\"><i class=\"fa fa-power-off\"></i></span> Odjavi se</a>\n" +
"                </div>\n" +
"                 </div>\n" +
"                <div class=\"shape\"></div>\n" +
"		</div>\n");
                    }         
                    rs.close();
                    stmt.close();
                    con.close();
                }
                catch(Exception e){}
                    out.println("<div class=\"header_bg\">\n" +
                    "<div class=\"wrap\">\n" +
                    "	<div class=\"header\">\n" +
                    "		<div class=\"logo\">\n" +
                    "			<a href=\"index.jsp\"><img src=\"klijent/images/logo.png\" alt=\"\"></a>\n" +
                    "		</div>\n" +
                    "		<div class=\"h_right\">\n" +
                     "			<!--start menu -->\n");
                                            if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
                        {
out.println("			<ul class=\"menu\">\n" +
"				<li><a href=\"klijent/index.jsp\">Početna</a></li> \n" +
"				<li><a href=\"klijent/rezervacije.jsp\">rezervacije</a></li> \n" +
"				<li><a href=\"odjavljivanje\">Odjavi se</a></li>\n" +
"				<div class=\"clear\"></div>\n" +
"			</ul>\n");
                         }
                        else
                        {
out.println("                        <ul class=\"menu\">\n" +
"				 <li><a href=\"klijent/index.jsp\">Početna</a></li> \n" +
"				 <li><a href=\"klijent/rezervacije.jsp\">Rezervacije</a></li> \n" +
"                                <li><a href=\"klijent/login.jsp\">Login</a></li>\n" +
"                                <li><a href=\"klijent/registracija.jsp\">Registracija</a></li>\n" +
"				<div class=\"clear\"></div>\n" +
"			</ul>\n");
                         }
out.println("		</div>\n" +
"		<div class=\"clear\"></div>\n" +
"		<div class=\"top-nav\">\n" +
"		<nav class=\"clearfix\">\n");
                     if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
                     {
out.println("				<ul>\n" +
"				<li><a href=\"klijent/index.jsp\">Početna</a></li> \n" +
"				<li><a href=\"klijent/rezervacije.jsp\">Rezervacije</a></li> \n" +
"				<li><a href=\"odjavljivanje\">Odjavi se</a></li>\n" +
"				</ul>\n" +
"				<a href=\"#\" id=\"pull\">Menu</a>\n");

                      }
                      else
                      {
out.println("                                <ul>\n" +
"				 <li><a href=\"klijent/index.jsp\">Početna</a></li> \n" +
"				 <li><a href=\"klijent/rezervacije.jsp\">rezervacije</a></li> \n" +
"                                <li><a href=\"klijent/login.jsp\">Login</a></li>\n" +
"                                <li><a href=\"klijent/registracija.jsp\">Registracija</a></li>\n" +
"				</ul>\n" +
"				<a href=\"#\" id=\"pull\">Menu</a>\n");
                      }
                    out.println("			</nav>\n" +
                    "		</div>\n" +
                    "	</div>\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "\n" +
                    "<div class=\"Modern-Slider\">\n" +
                    "  <!-- Item -->\n" +
                    "  <div class=\"item\">\n" +
                    "    <div class=\"img-fill\">\n" +
                    "      <img src=\"klijent/images/slider-bg.jpg\" alt=\"\">\n" +
                    "      <div class=\"info\">\n" +
                    "        <div>\n" +
                    "            <h3>Starling Hotel Chain</h3>\n" +
                    "          <h5>Hotels in over 35 countries around the world</h5>\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "  <!-- // Item -->\n" +
                    "  <!-- Item -->\n" +
                    "  <div class=\"item\">\n" +
                    "    <div class=\"img-fill\">\n" +
                    "      <img src=\"klijent/images/slider3.jpg\" alt=\"\">\n" +
                    "      <div class=\"info\">\n" +
                    "        <div>\n" +
                    "          <h3>Starling Hotel Chain</h3>\n" +
                    "          <h5>Reservations are available in over 45 cities around the world</h5>\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "  <!-- // Item -->\n" +
                    "  <!-- Item -->\n" +
                    "  <div class=\"item\">\n" +
                    "    <div class=\"img-fill\">\n" +
                    "      <img src=\"klijent/images/slider4.jpg\" alt=\"\">\n" +
                    "      <div class=\"info\">\n" +
                    "        <div>\n" +
                    "          <h3>One of the best hotel chains in the world</h3>\n" +
                    "          <h5>We are one of the best in our business.</h5>\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "  <!-- // Item -->\n" +
                    "  <!-- Item -->\n" +
                    "  <div class=\"item\">\n" +
                    "    <div class=\"img-fill\">\n" +
                    "      <img src=\"klijent/images/slider5.jpg\" alt=\"\">\n" +
                    "      <div class=\"info\">\n" +
                    "        <div>\n" +
                    "          <h3>Starling Hotel Chain</h3>\n" +
                    "          <h5>Choose hotel and a room according to your wishes.</h5>\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "  <!-- // Item -->\n" +
                    "</div>\n" +
                     "<div id=\"stepwizard\">\n" +
                    "  <!-- progressbar -->\n" +
                    "  <ul id=\"progressbar\">\n" +
                    "    <li class=\"active\"><a href=\"klijent/index.jsp\">Begin Search</a></li>\n" +
                    "    <li class=\"active\"><a href=\"#\">Search Hotel</a></li>\n" +
                    "    <li class=\"active\"><a href=\"#\"><a href=\"#\">Choose room</a></li>\n" +
                    "    <li class=\"active\"><a href=\"#\"><a href=\"#\">View Details</a></li>\n" +
                    "    <li><a href=\"#\">View Confirmation</a></li>\n" +
                    "  </ul>\n" +
                    "</div>\n");
    
                     out.println("<!--start main -->\n" +
                    "<div class=\"main_bg\">\n" +
                    "    <div class=\"wrap\">\n" +
                    "        <div class=\"main\">\n");
                             if(request.getAttribute("poruka") != null)
    {
        out.println("<div class=\"frame\" id=\"error\">\n" +
        "  <div class=\"modal\">\n" +
        "    <img src=\"http://100dayscss.com/codepen/alert.png\" width=\"44\" height=\"38\" />\n" +
        "		<span class=\"title\">Info</span>\n" +
        "		<p>${poruka}</p>\n" +
        "                <div class=\"button\" onClick=\"Ajde()\">Close</div>\n" +
        "  </div>\n" +
        "</div>");
                }
                   
                    out.println("		<div class=\"res_online\">\n");
                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                        
                        ResultSet rzs = null;
                        PreparedStatement pst = null;
                        
                        String query="select * FROM hoteli where ID='"+hotelID+"'";

                        pst = con.prepareStatement(query);
                        rzs = pst.executeQuery();

                        if(rzs.first())
                        {
                            out.println("<h4>"+rzs.getString("naziv")+"</h4>\n" +
                                        "<center><p class=\"para\">"+rzs.getString("opis")+"</p></center>\n");
                        }
                        rzs.close();  
                        pst.close();
                        con.close();
                    }
                    catch(Exception e)
                    {
                      e.printStackTrace();
                    }
                    out.println("		</div>			\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>	\n");
                     
                   
                    out.println("  <div id=\"header\">\n");
      byte[] imgData = rsPagination.getBytes(21); // blob field 
      String encode = Base64.getEncoder().encodeToString(imgData);  
out.println("<div id=\"logo\"><img src=\"data:image/jpeg;base64,"+encode+"\"/>\n" +
"      <div id=\"address\">\n" +
"        <h1 style=\"margin-top:10px;\">Hotel sa "+rsPagination.getString(20)+" zvezdica</h1>\n" +
"        <h1 style=\"margin-top:10px;\">"+rsPagination.getString(15)+"</h1>\n" +
"        <h1 style=\"margin-top:10px;\">"+rsPagination.getString(16)+"</h1>\n" +
"        <h1 style=\"margin-top:10px;\">"+rsPagination.getString(17)+"</h1>\n" +
"        <h1 style=\"margin-top:10px;\">"+rsPagination.getString(18)+"</h1>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div id=\"page-details\">\n" +
"      <h1>Detalji</h1>\n" +
"      <h1 name=\"checkinDate\" value=\""+checkinDate+"\">Datum dolaska "+checkinDate+"</h1>\n" +
"      <h1>Datum odlaska "+checkoutDate+"</h1>\n" +
"      <h1>"+rooms+" Sobe - "+guests+" Osoba</h1>\n");
    try {
	       Date dateBefore = myFormat.parse(dateBeforeString);
	       Date dateAfter = myFormat.parse(dateAfterString);
	       long difference = dateAfter.getTime() - dateBefore.getTime();
	       int daysBetween = (int) (difference / (1000*60*60*24));
               dani = daysBetween;
               /* You can also convert the milliseconds to days using this method
                * float daysBetween = 
                *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
                */
            } catch (Exception e) {
	       e.printStackTrace();
            }
        out.println("<h1>Broj dana - "+dani+"</h1>\n");
        out.println("<h1>Paket - "+rts+"</h1>\n" +
"    </div>\n");
        if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
        {
            out.println("<div id=\"customer-details\">\n" +
"      <h1>"+user.getName()+"</h1>\n" +
"      <h1>"+user.getEmail()+"</h1>\n" +
"      <h1>"+user.getState()+"</h1>\n" +
"      <h1>"+user.getZip()+" "+user.getCity()+"</h1>\n" +
"      <h1>"+user.getAddress()+"</h1>\n" +
"      <h1>"+user.getPhone()+"</h1>\n" +
"    </div>\n");
        }
        else
        {
             out.println("<div id=\"customer-details\">\n" +
"      <h1>Detalji klijenta</h1>\n" +
"      <h1>Niste ulogovani. <a href=\"klijent/login.jsp\">LOGIN</a></h1>\n" +
"      <h1>Ako nemate profil registrujte se.<a href=\"klijent/registracija.jsp\">REGISTRACIJA</a></h1><hr>\n" +
"      <h1>Starling Hotel Chain</h1>\n" +
"    </div>\n");
        }

out.println("</div>\n" +
"\n" +
"\n" +
"<div id=\"body\">\n" +
"  <table>\n" +
"    <thead>\n" +
"      <tr>\n" +
"        <td class=\"center\">Hotel</td>\n");
           byte[] picData = rsPagination.getBytes(7); // blob field 
           String pic = Base64.getEncoder().encodeToString(picData);       
out.println(
"        <td class=\"center\">Tip sobe</td>\n" +
"        <td class=\"center\">Opis</td>\n" +
"        <td class=\"center\">Soba</td>\n" +
"        <td class=\"center\">Osoba</td>\n" +
"        <td class=\"center\">$ po sobi</td>\n" +
"        <td class=\"center\">Ukupno</td>\n" +
"      </tr>\n" +
"    </thead>\n" +
"    <tfoot>\n" +
"      <tr class=\"separator\">\n" +
"        <td colspan=\"6\"></td>\n" +
"      </tr>\n" +
"      <tr>\n");
             
            if(rate.equals("paket1"))
            {
                moneyz = (Integer.parseInt(rooms)*rsPagination.getInt(8))*dani;
            }
            else if(rate.equals("paket2"))
            {
                moneyz = (Integer.parseInt(rooms)*rsPagination.getInt(9))*dani;
            }
            else if(rate.equals("paket3"))
            {
                moneyz = (Integer.parseInt(rooms)*rsPagination.getInt(10))*dani;
            }
            else if(rate.equals("paket4"))
            {
                moneyz = (Integer.parseInt(rooms)*rsPagination.getInt(11))*dani;
            }
            else if(rate.equals("paket5"))
            {
                moneyz = (Integer.parseInt(rooms)*rsPagination.getInt(12))*dani;
            }
            //points
            paywithpoints = (int) ((Integer.parseInt(rooms)*rsPagination.getInt(13))*dani);

out.println("<td colspan=\"5\">Ukupno</td>\n" +
"        <td class=\"right\">"+moneyz+" $</td>\n" +
"      </tr>\n" +
"      <tr class=\"separator\">\n" +
"        <td colspan=\"6\"></td>\n" +
"      </tr>\n" +
"      <tbody>\n" +
"        <tr>\n" +
"          <td class=\"center\">"+rsPagination.getString(15)+"</td>\n" +
"          <td class=\"center\"><img style=\"max-width: 150px; max-height: 150px;\" src=\"data:image/jpeg;base64,"+pic+"\" alt=\"\"/></td>"+
"          <td class=\"center\">"+rsPagination.getString(15)+"</td>\n" +
"          <td class=\"center\">"+rooms+"</td>\n" +
"          <td class=\"center\">"+guests+"</td>\n");
            int money = 0;
            if(rate.equals("paket1"))
            {
                money = (Integer.parseInt(rooms)*rsPagination.getInt(8))*dani;
                out.println("<td class=\"center\">"+rsPagination.getInt(8)+" $</td>\n");
            }
            else if(rate.equals("paket2"))
            {
                money = (Integer.parseInt(rooms)*rsPagination.getInt(9))*dani;
                out.println("<td class=\"center\">"+rsPagination.getInt(9)+" $</td>\n");
            }
            else if(rate.equals("paket3"))
            {
                money = (Integer.parseInt(rooms)*rsPagination.getInt(10))*dani;
                out.println("<td class=\"center\">"+rsPagination.getInt(10)+" $</td>\n");
            }
            else if(rate.equals("paket4"))
            {
                money = (Integer.parseInt(rooms)*rsPagination.getInt(11))*dani;
                out.println("<td class=\"center\">"+rsPagination.getInt(11)+" $</td>\n");
            }
            else if(rate.equals("paket5"))
            {
                money = (Integer.parseInt(rooms)*rsPagination.getInt(12))*dani;
                out.println("<td class=\"center\">"+rsPagination.getInt(12)+" $</td>\n");
            }

        out.println("<td class=\"center\">"+money+" $</td>\n" +
"        </tr>\n" +
"      </tbody>\n" +
"\n" +
"  </table>\n" +
"</div>\n");
        if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
        {
out.println("<div class=\"main_bg\">\n" +
"<div class=\"wrap\">\n" +
"	<div class=\"main\">\n" +
"		<div class=\"res_online\">\n" +
"                    <h4>Plaćanje</h4>\n" +
"		</div>			\n" +
"			<div class=\"span_of_2\">\n" +
"                       <form method=\"POST\" name=\"payment\" action=\"PotvrdiRezervaciju?tipsobe="+rsPagination.getInt("ID")+"&hotelID="+hotelID+"&lokacija="+location+"&sobe="+rooms+"&datumDolaska="+checkinDate+"&datumOdlaska="+checkoutDate+"&odrasli="+adults+"&deca="+kids+"&paket="+rate+"&dani="+dani+"&novac="+moneyz+"\">\n" +
"					<h4>Kreditna kartica</h4>\n" +
"					<div class=\"book_date btm\">\n" +
"						<select name=\"ccard\" required class=\"frm-field required\">\n" +
"				            <option value=\"\" selected></option>         \n" +
"				            <option value=\"1\">American Express</option>         \n" +
"				            <option value=\"2\">Master card</option>   \n" +
"                                            <option value=\"3\">Visa</option>   \n" +
"                                            <option value=\"4\">Union pay</option>         \n" +
"		        		</select>\n" +
"					</div>	\n" +
"                                        <h4>Broj kreditne kartice</h4>\n" +
"					<div class=\"book_date btm\">\n" +
"                                            <input type=\"text\" required name=\"ccnmbr\" placeholder=\"XXXX-XXXX-XXXX-XXXX\">\n" +
"					</div>	\n" +
"                                        \n" +
"					<div class=\"sel_room\">\n" +
"						<h4>D. isteka(MM)</h4>\n" +
"						<select name=\"expm\" required class=\"frm-field required\">\n" +
"				            <option selected></option>         \n" +
"                                                    <option value=\"1\">1</option>   \n" +
"				            <option value=\"2\">2</option>         \n" +
"				            <option value=\"3\">3</option>   \n" +
"                                            <option value=\"4\">4</option>   \n" +
"                                            <option value=\"5\">5</option>\n" +
"                                            <option value=\"6\">6</option>   \n" +
"				            <option value=\"7\">7</option>         \n" +
"				            <option value=\"8\">8</option>   \n" +
"                                            <option value=\"9\">9</option>   \n" +
"                                            <option value=\"10\">10</option>  \n" +
"                                            <option value=\"11\">11</option>  \n" +
"                                            <option value=\"12\">12</option>  \n" +
"                                                </select>\n" +
"                                        </div>\n" +
"					\n" +
"					<div class=\"sel_room left\">\n" +
"						<h4>D. isteka(YYYY)</h4>\n" +
"						<select name=\"expy\" required class=\"frm-field required\">\n" +
"				            <option value=\"\" selected></option>         \n" +
"                                                    <option value=\"2018\">2018</option>   \n" +
"				            <option value=\"2019\">2019</option>         \n" +
"				            <option value=\"2020\">2020</option>   \n" +
"                                            <option value=\"2021\">2021</option>   \n" +
"                                            <option value=\"2022\">2022</option>\n" +
"                                            <option value=\"2023\">2023</option>   \n" +
"				            <option value=\"2024\">2024</option>         \n" +
"				            <option value=\"2025\">2025</option>   \n" +
"                                            <option value=\"2026\">2026</option>   \n" +
"                                            <option value=\"2027\">2027</option>  \n" +
"                                            <option value=\"2028\">2028</option>  \n" +
"                                                </select>\n" +
"					</div>	\n" +
"				<div class=\"clear\"></div>\n" +
"			</div>\n" +
"            <br>\n" +
"			<div class=\"res_btn\">\n" +
"					<input type=\"submit\" onclick=\"return formValidation()\" value=\"Rezervisi\" style=\"width: 280px;\">\n" +
"			</div>\n" +
"				</form>\n");
if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
{
                if(user.getPoints() >= paywithpoints)
                {
out.println("		<hr><div class=\"res_online\">\n" +
"                    <h4>Ili platite Starling Poenima</h4>\n" +
"			<div class=\"points_btn\">\n" +
"                       <form method=\"POST\" action=\"PotvrdiRezervaciju?tipsobe="+rsPagination.getInt("ID")+"&hotelID="+hotelID+"&lokacija="+location+"&sobe="+rooms+"&datumDolaska="+checkinDate+"&datumOdlaska="+checkoutDate+"&odrasli="+adults+"&deca="+kids+"&paket="+rate+"&poeni="+paywithpoints+"\">\n" +
"                       <input type=\"submit\" value=\"Plati  - "+paywithpoints+" poena\" style=\"width: 280px;\">\n" +
"			</div>\n" +
"		</div>			\n");
                }
}
out.println("	</div>\n" +
"</div>\n" +
"</div>		");
        }
                    }
                    rsPagination.close();
                    psPagination.close();
                    conn.close();   
                    }
                    catch(Exception e)
                    {
                      e.printStackTrace();
                    }
                    out.println("<!--start main -->\n" +
                    "<!--<div class=\"footer_bg\">-->\n" +
                    "<!--<div class=\"wrap\">-->\n" +
                    "<div class=\"footer\">\n" +
                    "                        <div class=\"footer-logo\">\n" +
                    "                            <a href=\"#\"><img src=\"https://i.imgur.com/zM3yItK.png\" alt=\"scanfcode\"></a>\n" +
                    "                        </div>\n" +
                    "			\n" +
                    "			<div class=\"f_nav\">\n");
					 if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
                        {
out.println("				<ul>\n" +
"					<li><a href=\"klijent/index.jsp\">Početna</a></li>\n" +
"					<li><a href=\"klijent/rezervacije.jsp\">rezervacije</a></li>\n" +
"				</ul>\n");
                                 
                        }
                        else
                        {
out.println("                            <li><a href=\"klijent/index.jsp\">Početna</a></li>\n" +
"					 <li><a href=\"klijent/rezervacije.jsp\">rezervacije</a></li>\n" +
"                                        <li><a href=\"klijent/login.jsp\">Login</a></li>\n" +
"                                        <li><a href=\"klijent/registracija.jsp\">Registracija</a></li>\n");
                         }
out.println("			</div>\n" +
                    "			<div class=\"soc_icons\">\n" +
                    "				<ul>\n" +
                    "					<li><a class=\"icon1\" href=\"#\"></a></li>\n" +
                    "					<li><a class=\"icon2\" href=\"#\"></a></li>\n" +
                    "					<li><a class=\"icon3\" href=\"#\"></a></li>\n" +
                    "					<li><a class=\"icon4\" href=\"#\"></a></li>\n" +
                    "					<li><a class=\"icon5\" href=\"#\"></a></li>\n" +
                    "					<div class=\"clear\"></div>\n" +
                    "				</ul>	\n" +
                    "			</div>\n" +
                    "                        <div class=\"copy\">\n" +
                    "				<p class=\"link\"><span>Copyright © 2018 - All rights reserved | <a href=\"#\"> STARLING HOTEL CHAIN</a></span></p>\n" +
                    "			</div>\n" +
                    "			<div class=\"clear\"></div>\n" +
                    "<!--</div>-->\n" +
                    "<!--</div>-->\n" +
                    "</div>		\n" +
                    "<script>"+
                    "function formValidation()  \n" +
"{  \n" +
"    var ccard = document.payment.ccard;\n" +
"    var ccnmbr = document.payment.ccnmbr; \n" +
"    var expm = document.payment.expm;\n" +
"    var expy = document.payment.expy;\n" +
"    \n" +
"    if(validateccard(ccard))  \n" +
"    {\n" +
"    if(validateccnmbr(ccnmbr))  \n" +
"    {\n" +
"    if(validateexpm(expm))\n" +
"    {\n" +
"    if(validateexpy(expy))\n" +
"    {\n" +
"        document.payment.submit();\n" +
"    }\n" +
"    }\n" +
"    }\n" +
"    }\n" +
"    return false;  \n" +
"} \n" +
"function validateccard(ccard)  \n" +
"{   \n" +
"    if(ccard.value.length == 0)  \n" +
"    {  \n" +
"        swal (\"Warning\" ,  \"Polje kreditna kartica mora biti popunjeno.\" ,  \"warning\" );\n" +
"        ccard.focus();  \n" +
"        return false;  \n" +
"    }\n" +
"    return true;\n" +
"}\n" +
"function validateccnmbr(ccnmbr)  \n" +
"{   \n" +
"    var numbers = /^(\\d{4})\\-(\\d{4})\\-(\\d{4})\\-(\\d{4})$/;  \n" +
"    if(ccnmbr.value.length == 0)\n" +
"    {\n" +
"       swal (\"Warning\" ,  \"Polje broj kreditne kartice mora biti popunjeno.\" ,  \"warning\" );\n" +
"       ccnmbr.focus();   \n" +
"       return false;\n" +
"    }\n"+
"    else if(!ccnmbr.value.match(numbers))  \n" +
"    {  \n" +
"        swal (\"Warning\" ,  \"Broj kreditne kartice nije u validnom formatu.\" ,  \"warning\" );\n" +
"        ccnmbr.focus();  \n" +
"        return false;  \n" +
"    } \n" +
"    return true;\n" +
"}\n" +
"function validateexpm(expm)  \n" +
"{   \n" +
"    if(expm.value.length == 0)  \n" +
"    {  \n" +
"        swal (\"Warning\" ,  \"Polje datum isteka(MM) mora biti popunjeno.\" ,  \"warning\" );\n" +
"        expm.focus();  \n" +
"        return false;  \n" +
"    }\n" +
"    return true;\n" +
"}\n" +
"function validateexpy(expy)  \n" +
"{   \n" +
"    if(expy.value.length == 0)  \n" +
"    {  \n" +
"        swal (\"Warning\" ,  \"Polje datum isteka(YYYY) mora biti popunjeno.\" ,  \"warning\" );\n" +
"        expy.focus();  \n" +
"        return false;  \n" +
"    }\n" +
"    return true;\n" +
"}\n"+
                    "</script>\n"+
"                   <script type=\"text/javascript\">\n" +
"                                    $(\".user-menu\").click(function() {\n" +
"  $(this).toggleClass(\"show\");\n" +
"});\n" +
"\n" +
"                                </script>\n"+
                    "</body>\n" +
                    "</html>\n");
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
