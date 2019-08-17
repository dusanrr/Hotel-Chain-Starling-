package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.text.SimpleDateFormat;
import java.util.Base64;

public class PrikaziSobe extends HttpServlet {

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
    public int daysBetween(java.util.Date d1, java.util.Date d2){
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
            String hotelID = request.getParameter("hotelID");
            String location = request.getParameter("lokacija");
            String rooms = request.getParameter("sobe");
            String checkinDate = request.getParameter("datumDolaska");
            String checkoutDate = request.getParameter("datumOdlaska");
            String adults = request.getParameter("odrasli");
            String kids = request.getParameter("deca");
            String rates = request.getParameter("paket");
            
            SimpleDateFormat myFormat = new SimpleDateFormat("MM.dd.yyyy");
            String dateBeforeString = checkinDate;
            String dateAfterString = checkoutDate;
            int dani = 0;
            
            try {
	       java.util.Date dateBefore = myFormat.parse(dateBeforeString);
	       java.util.Date dateAfter = myFormat.parse(dateAfterString);
	       long difference = dateAfter.getTime() - dateBefore.getTime();
	       int daysBetween = (int) (difference / (1000*60*60*24));
               dani = daysBetween;
               
               if(dani < 1)
               {
                   request.setAttribute("popupid", 2);
                    request.setAttribute("poruka", "Datumi dolaska i odlaska nisu validni.");
                    RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                    rd.forward(request, response);
               }
            } catch (Exception e) {
	       e.printStackTrace();
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
            String sqlPagination="SELECT SQL_CALC_FOUND_ROWS * FROM tipovisoba where hotelID='"+hotelID+"' and odrasli >= "+adults+" and deca >= "+kids+" limit "+iPageNo+","+iShowRows+"";
            
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
                    "<style>\n" +
                    "/* Style the Image Used to Trigger the Modal */\n" +
                    ".myImg {\n" +
                    "    border-radius: 5px;\n" +
                    "    cursor: pointer;\n" +
                    "    transition: 0.3s;\n" +
                    "}\n" +
                    "\n" +
                    ".myImg:hover {opacity: 0.7;}\n" +
                    "\n" +
                    "/* The Modal (background) */\n" +
                    ".modal {\n" +
                    "    display: none; /* Hidden by default */\n" +
                    "    position: fixed; /* Stay in place */\n" +
                    "    z-index: 1; /* Sit on top */\n" +
                    "    padding-top: 100px; /* Location of the box */\n" +
                    "    left: 0;\n" +
                    "    top: 0;\n" +
                    "    width: 100%; /* Full width */\n" +
                    "    height: 100%; /* Full height */\n" +
                    "    overflow: auto; /* Enable scroll if needed */\n" +
                    "    background-color: rgb(0,0,0); /* Fallback color */\n" +
                    "    background-color: rgba(0,0,0,0.9); /* Black w/ opacity */\n" +
                    "}\n" +
                    "\n" +
                    "/* Modal Content (Image) */\n" +
                    ".modal-content {\n" +
                    "    margin: auto;\n" +
                    "    display: block;\n" +
                    "    width: 80%;\n" +
                    "    max-width: 700px;\n" +
                    "}\n" +
                    "\n" +
                    "/* Caption of Modal Image (Image Text) - Same Width as the Image */\n" +
                    "#caption {\n" +
                    "    margin: auto;\n" +
                    "    display: block;\n" +
                    "    width: 80%;\n" +
                    "    max-width: 700px;\n" +
                    "    text-align: center;\n" +
                    "    color: #ccc;\n" +
                    "    padding: 10px 0;\n" +
                    "    height: 150px;\n" +
                    "}\n" +
                    "\n" +
                    "/* Add Animation - Zoom in the Modal */\n" +
                    ".modal-content, #caption {\n" +
                    "    -webkit-animation-name: zoom;\n" +
                    "    -webkit-animation-duration: 0.6s;\n" +
                    "    animation-name: zoom;\n" +
                    "    animation-duration: 0.6s;\n" +
                    "}\n" +
                    "\n" +
                    "@-webkit-keyframes zoom {\n" +
                    "    from {-webkit-transform:scale(0)}\n" +
                    "    to {-webkit-transform:scale(1)}\n" +
                    "}\n" +
                    "\n" +
                    "@keyframes zoom {\n" +
                    "    from {transform:scale(0)}\n" +
                    "    to {transform:scale(1)}\n" +
                    "}\n" +
                    "\n" +
                    "/* The Close Button */\n" +
                    ".close {\n" +
                    "    position: absolute;\n" +
                    "    top: 15px;\n" +
                    "    right: 35px;\n" +
                    "    color: #f1f1f1;\n" +
                    "    font-size: 40px;\n" +
                    "    font-weight: bold;\n" +
                    "    transition: 0.3s;\n" +
                    "}\n" +
                    "\n" +
                    ".close:hover,\n" +
                    ".close:focus {\n" +
                    "    color: #bbb;\n" +
                    "    text-decoration: none;\n" +
                    "    cursor: pointer;\n" +
                    "}\n" +
                    "\n" +
                    "/* 100% Image Width on Smaller Screens */\n" +
                    "@media only screen and (max-width: 700px){\n" +
                    "    .modal-content {\n" +
                    "        width: 100%;\n" +
                    "    }\n" +
                    "}"+
                    "</style>\n" +
                    "<script>\n" +
                    
                    "$('.button').bind('click', function() {\n" +
                    "	$('.modal').addClass('hide');\n" +
                    "});\n" +
                    "</script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<!-- The Modal -->\n" +
                    "<div id=\"myModal\" class=\"modal\">\n" +
                    "\n" +
                    "  <!-- The Close Button -->\n" +
                    "  <span class=\"close\" onclick=\"document.getElementById('myModal').style.display='none'\">&times;</span>\n" +
                    "\n" +
                    "  <!-- Modal Content (The Image) -->\n" +
                    "  <img class=\"modal-content\" id=\"img01\">\n" +
                    "\n" +
                    "  <!-- Modal Caption (Image Text) -->\n" +
                    "  <div id=\"caption\"></div>\n" +
                    "</div>\n"+
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
"				 <li><a href=\"klijent/rezervacije.jsp\">rezervacije</a></li> \n" +
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
"				<li><a href=\"klijent/rezervacije.jsp\">rezervacije</a></li> \n" +
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
                    "    <li><a href=\"#\"><a href=\"#\">View Details</a></li>\n" +
                    "    <li><a href=\"#\">View Confirmation</a></li>\n" +
                    "  </ul>\n" +
                    "</div>\n"+
                     "<!--start main -->\n" +
                    "<div class=\"main_bg\">\n" +
                    "    <div class=\"wrap\">\n" +
                    "        <div class=\"main\">\n" +
                    "		<div class=\"res_online\">\n");
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
                    "</div>	\n" +
                    "<div class=\"MainContainer\">\n" +
"            <!-- ******** Start LeftContent ********* -->\n" +
"            <div class=\"leftContent\">\n" +
"                <div class=\"Check\">\n" +
"                    <form name=\"form1\" method=\"POST\">\n" +
"                        <i class=\"fas fa-calendar\"></i><span>Datum dolaska</span><br>\n" +
"                        <div class=\"book_date\">\n" +
"				<input class=\"date\" id=\"datepicker\" name=\"arrival\" readonly disabled type=\"text\" placeholder=\""+checkinDate+"\"\">\n" +
"                        </div><br>\n" +
"                        <i class=\"fas fa-calendar\"></i><span>Datum odlaska</span><br>\n" +
"                        <div class=\"book_date\">\n" +
"				<input class=\"date\" id=\"datepicker1\" name=\"departure\" readonly disabled type=\"text\"\" placeholder=\""+checkoutDate+"\">\n" +
"			</div><br>\n" +
"                        <i class=\"fas fa-bed\"></i><span>Sobe</span><br>\n" +
"                        <input type=\"number\" name=\"roomss\" readonly placeholder=\""+rooms+"\">\n"+
"                        <div class=\"Adult\">\n" +
"                            <i class=\"fa fa-users\"></i><span>Odrasli</span><br>\n" +
"                        <input type=\"number\" name=\"adultss\" readonly placeholder=\""+adults+"\">\n"+
"                        </div>\n" +
"                        <div class=\"child\">\n" +
"                             <i class=\"fa fa-child\"></i><span>Deca</span><br>\n" +
"                        <input type=\"number\" name=\"kidss\" readonly placeholder=\""+kids+"\">\n"+
"                        </div>                      \n");
out.println("<div class=\"clearfix\"></div>\n" +
"                    </form>\n" +
"                </div>\n" +
"                <!-- ===============  Start Result ======================= -->\n" +
"                <div class=\"result\">\n" +
"                    <h2>Tipovi soba</h2>\n" +
"                    <ul>\n");
                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                        
                        ResultSet rzs = null;
                        PreparedStatement pst = null;
                        
                        String query="select * FROM tipovisoba where hotelID='"+hotelID+"'";

                        pst = con.prepareStatement(query);
                        rzs = pst.executeQuery();

                        while(rzs.next())
                        {
                            out.println("<li><i class=\"fa fa-minus-square-o\"></i>"+rzs.getString("naziv")+"</li>");
                        }
                        rzs.close();  
                        pst.close();
                        con.close();
                    }
                    catch(Exception e)
                    {
                      e.printStackTrace();
                    }
                        out.println("</ul>\n" +
"                </div>\n" +
"            </div>\n" +
"            <!-- ******** Start RightContent ********* -->\n" +
"             <div class=\"rightContent\">\n" +
"                 <h2>Dostupne sobe</h2> \n" +
/*"                 <div class=\"View\">\n" +
"                     <span>View By Room</span>\n" +
"                     <span>View By Rate</span>\n" +
"                 </div>\n" +*/
"                 <div class=\"clearfix\"></div>");
            try
            {
                while(rsPagination.next())
                {
                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                        
                        ResultSet rzs = null;
                        PreparedStatement pst = null;
                        
                        String query="SELECT COUNT(ID) as broj FROM sobe where hotelID='"+hotelID+"' and tipsobeID='"+rsPagination.getInt("ID")+"' and status='Prazna'";

                        pst = con.prepareStatement(query);
                        rzs = pst.executeQuery();
                        if(rzs.first())   
                        {
                        int broj = rzs.getInt("broj");
                        if(broj >= Integer.parseInt(rooms))
                        {
                            out.println(" <div class=\"boxes\">\n" +
                            "                     <div class=\"imgHotel\">\n");
                                                        byte[] imgData = rsPagination.getBytes("slika"); // blob field 
                                                        String encode = Base64.getEncoder().encodeToString(imgData);

                            out.println("<a href=\"#\"><img class=\"myImg\"  alt=\""+rsPagination.getString("naziv")+"\" src=\"data:image/jpeg;base64,"+encode+"\"></a>\n" +
                            "                     </div>\n" +
                            "                     \n" +
                            "                     <div class=\"boxContent\">\n" +
                            "                         <h3>"+rsPagination.getString("naziv")+"</h3>\n" +
                            "                         <p>VAT Included. City Tax Excluded</p>\n" +
                            "                         <ul>\n" +
                            "                             <li><i class=\"fa fa-square\"></i>"+rsPagination.getString("opis")+"</li>\n" +
                            "                         </ul>\n" +
                            /*"                         <i class=\"fa fa-long-arrow-right\"><span>Read More</span></i>\n" +*/
                            "                     </div>\n" +
                            "                     <div class=\"Price\">\n" +
                            "                         <h3><span>From</span> "+rsPagination.getInt(""+rates+"")+"$</h3>\n" +
                            "                         <p>AVG/Night(USD)</p>\n" +
                            "                         <button><a href=\"Rezervacija?tipsobe="+rsPagination.getInt("ID")+"&hotelID="+hotelID+"&lokacija="+location+"&sobe="+rooms+"&datumDolaska="+checkinDate+"&datumOdlaska="+checkoutDate+"&odrasli="+adults+"&deca="+kids+"&paket="+rates+"\">ODABERI</a></button>\n" +
                            "                     </div>\n" +
                            "                     <div class=\"clearfix\"></div>\n" +
                            "                 </div>");
                        }
                        
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    
                }
                if(!rsPagination.first())
                {
                    out.println("<center><h3>Nema rezultata.</h3></center>");
                }
                rsPagination.close();
                rsRowCnt.close();
                psPagination.close();
                psRowCnt.close();
                conn.close();
            }
            catch(CommunicationsException ie)
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! (connection lost)");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            out.println("</div>\n" +
                    "        </div>\n");
            
            
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
            
            out.println("<div id=\"pagicontainer\">\n" +
                    "            <div class=\"pagination\">\n");
            
            //brojevi stranice
            int i=0;
            int cPage=0;
            if(iTotalRows!=0)
            {
                cPage=((int)(Math.ceil((double)iEndResultNo/(iTotalSearchRecords*iShowRows))));
                
                int prePageNo=(cPage*iTotalSearchRecords)-((iTotalSearchRecords-1)+iTotalSearchRecords);
                if((cPage*iTotalSearchRecords)-(iTotalSearchRecords)>0)
                {
                    out.println("<a class='page gradient' href=\"PrikaziSobe?hotelID="+hotelID+"&lokacija="+location+"&sobe="+rooms+"&datumDolaska="+checkinDate+"&datumOdlaska="+checkoutDate+"&odrasli="+adults+"&deca="+kids+"&paket="+rates+"&iPageNo="+prePageNo+"&cPageNo="+prePageNo+"\"> << Previous</a>\n");
                }
                
                for(i=((cPage*iTotalSearchRecords)-(iTotalSearchRecords-1));i<=(cPage*iTotalSearchRecords);i++)
                {
                    if(i==((iPageNo/iShowRows)+1))
                    {
                        out.println("<a class='page gradient' href=\"PrikaziSobe?hotelID="+hotelID+"&lokacija="+location+"&sobe="+rooms+"&datumDolaska="+checkinDate+"&datumOdlaska="+checkoutDate+"&odrasli="+adults+"&deca="+kids+"&paket="+rates+"&iPageNo="+i+"\" style=\"color: #59d\"><b>"+i+"</b></a>\n");
                    }
                    else if(i<=iTotalPages)
                    {
                        out.println("<a class='page gradient' href=\"PrikaziSobe?hotelID="+hotelID+"&lokacija="+location+"&sobe="+rooms+"&datumDolaska="+checkinDate+"&datumOdlaska="+checkoutDate+"&odrasli="+adults+"&deca="+kids+"&paket="+rates+"&iPageNo="+i+"\">"+i+"</a>\n");
                    }
                }
                if(iTotalPages>iTotalSearchRecords && i<iTotalPages)
                {
                    out.println("<a class='page active' href=\"PrikaziSobe?hotelID="+hotelID+"&lokacija="+location+"&sobe="+rooms+"&datumDolaska="+checkinDate+"&datumOdlaska="+checkoutDate+"&odrasli="+adults+"&deca="+kids+"&paket="+rates+"&iPageNo="+i+"&cPageNo="+i+"\"> >> Next</a>\n");
                }
            }
        }
        catch(CommunicationsException ie)
        {
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Došlo je do greške! (connection lost)");
            RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
            rd.forward(request, response);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        } 
        catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        }
        out.println("	</div>\n" +
                    "        </div>\n" +
                    "<!--start main -->\n" +
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
                    "<script>\n" +
                    "// Get the modal\n" +
                    "var modal = document.getElementById('myModal');\n" +
                    "\n" +
                    "// Get the image and insert it inside the modal - use its \"alt\" text as a caption\n" +
                    "var img = $('.myImg');\n" +
                    "var modalImg = $(\"#img01\");\n" +
                    "var captionText = document.getElementById(\"caption\");\n" +
                    "$('.myImg').click(function(){\n" +
                    "    modal.style.display = \"block\";\n" +
                    "    var newSrc = this.src;\n" +
                    "    modalImg.attr('src', newSrc);\n" +
                    "    captionText.innerHTML = this.alt;\n" +
                    "});\n" +
                    "\n" +
                    "// Get the <span> element that closes the modal\n" +
                    "var span = document.getElementsByClassName(\"close\")[0];\n" +
                    "\n" +
                    "// When the user clicks on <span> (x), close the modal\n" +
                    "span.onclick = function() {\n" +
                    "  modal.style.display = \"none\";\n" +
                    "}"+
                    "</script>\n"+
                    " <script type=\"text/javascript\">\n" +
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
