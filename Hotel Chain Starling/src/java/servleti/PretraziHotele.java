package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class PretraziHotele extends HttpServlet {

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
            ResultSet rsRowCnt = null;

            PreparedStatement psPagination=null;
            PreparedStatement psRowCnt=null;

            int iShowRows=5;  //prikaza po strani
            int iTotalSearchRecords=10;  //broj strana koji se prikazuje

            int iTotalRows=nullIntconv(request.getParameter("iTotalRows"));
            int iTotalPages=nullIntconv(request.getParameter("iTotalPages"));
            int iPageNo=nullIntconv(request.getParameter("iPageNo"));
            int cPageNo=nullIntconv(request.getParameter("cPageNo"));
            
            String searchcity = (String)request.getParameter("lokacija");
            String nrooms = (String)request.getParameter("sobe");
            String cidate = (String)request.getParameter("datumDolaska");
            String codate = (String)request.getParameter("datumOdlaska");
            String adults = (String)request.getParameter("odrasli");
            String kids = (String)request.getParameter("deca");
            String rates = (String)request.getParameter("paket");
            int dan = LocalDateTime.now().getDayOfMonth();
            int mesec = LocalDateTime.now().getMonthValue();
            int godina = LocalDateTime.now().getYear();
            
            SimpleDateFormat myFormat = new SimpleDateFormat("MM.dd.yyyy");
            String dateBeforeString = cidate;
            String dateAfterString = codate;
            int dani = 0;
            
            try {
	       Date dateBefore = myFormat.parse(dateBeforeString);
	       Date dateAfter = myFormat.parse(dateAfterString);
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

            String danmesecgodinaa = ""+dan+"."+mesec+"."+godina+"";
            
            int iStartResultNo=0;
            int iEndResultNo=0;

            if(iPageNo==0)
            {
                iPageNo=0;
            }
            else{
                iPageNo=Math.abs((iPageNo-1)*iShowRows);
            }
            
            String sqlPagination="SELECT SQL_CALC_FOUND_ROWS DISTINCT H.ID,H.naziv,H.drzava,H.grad,H.adresa,H.opis,H.zvezdice,H.slika FROM `hoteli` H INNER JOIN `sobe` R ON H.ID=R.hotelID INNER JOIN `tipovisoba` RT ON RT.hotelID=H.ID where H.grad='"+searchcity+"' and RT.odrasli >= "+adults+" and RT.deca >= "+kids+" limit "+iPageNo+","+iShowRows+"";
            

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
                    "<link href='admin/fonts/fonts/fonts.css' rel='stylesheet' type='text/css'>  " +
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
                Statement stmt = null;
                ResultSet rs = null;
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmt = conn.createStatement();
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
                    "\n"
                    + "<!----start-images-slider---->\n" +
"		<div class=\"images-slider\">\n" +
"			<!-- start slider -->\n" +
"		    <div id=\"fwslider\">\n" +
"		        <div class=\"slider_container\">\n" +
"		            <div class=\"slide\"> \n" +
"		                <!-- Slide image -->\n" +
"                                <img class=\"rezslika\" src=\"klijent/images/slider-bg.jpg\" alt=\"\"/>\n" +
"		                <!-- /Slide image -->\n" +
"		                \n" +
"		            </div>\n" +
"		            <!-- /Duplicate to create more slides -->\n" +
"		            \n" +
"		        </div>\n" +
"		        <div class=\"timers\"> </div>\n" +
"		        <div class=\"slidePrev\"><span> </span></div>\n" +
"		        <div class=\"slideNext\"><span> </span></div>\n" +
"		    </div>\n" +
"		    <!--/slider -->\n" +
"		</div>"+
                    "<!--start main -->\n" +
                    "<div id=\"stepwizard\">\n" +
                    "  <!-- progressbar -->\n" +
                    "  <ul id=\"progressbar\">\n" +
                    "    <li class=\"active\"><a href=\"klijent/index.jsp\">Begin Search</a></li>\n" +
                    "    <li class=\"active\"><a href=\"#\">Search Hotel</a></li>\n" +
                    "    <li><a href=\"#\">Choose room</a></li>\n" +
                    "    <li><a href=\"#\"><a href=\"#\">View Details</a></li>\n" +
                    "    <li><a href=\"#\">View Confirmation</a></li>\n" +
                    "  </ul>\n" +
                    "</div>\n");
                         
            while(rsPagination.next())
            { 
                ResultSet rzs = null;
                PreparedStatement pst = null;

                String query1="SELECT COUNT(ID) as broj FROM sobe where hotelID='"+rsPagination.getInt("ID")+"' and status='Prazna'";

                pst = conn.prepareStatement(query1);
                rzs = pst.executeQuery();
                if(rzs.next())   
                {
                    int broj = rzs.getInt("broj");
                    if(broj == 0)
                    {
                        out.println("<div class=\"main_bg\">\n" +
                    "    <div class=\"wrap\">\n" +
                    "        <div class=\"main\">\n" +
                    "		<div class=\"res_online\">");
                out.println("<center><h1>Nema rezultataa.</h1></center>");
                out.println("		</div>			\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>	\n");
                    break;
                    }
                    else if(broj >= Integer.parseInt(nrooms))
                    {
                                      out.println("<div class=\"product\">\n" +
"            <div class=\"title\">\n"+
"             "+rsPagination.getString("naziv")+"");
            out.println("</div>");

            out.println("<div class=\"text\">\n" +
"              <div class=\"code\">Država: "+rsPagination.getString("drzava")+"</div>\n" +
"              <div class=\"code\">Grad: "+rsPagination.getString("grad")+"</div>\n" +
"              <div class=\"code\">Adresa: "+rsPagination.getString("adresa")+"</div>\n" +
"              <div class=\"description\">\n" +
"                "+rsPagination.getString("opis")+"\n" +
"              </div>\n" +
"              <div class=\"review\">");
            
                  if(rsPagination.getInt("zvezdice") == 1)
                  {
                    
                    out.println("<span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon star-disable\"></span>\n" +
"                    <span class=\"star-icon star-disable\"></span>\n" +
"                    <span class=\"star-icon star-disable\"></span>\n" +
"                    <span class=\"star-icon star-disable\"></span>");
                  }
                  else if(rsPagination.getInt("zvezdice") == 2)
                  {
                    out.println("<span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon star-disable\"></span>\n" +
"                    <span class=\"star-icon star-disable\"></span>\n" +
"                    <span class=\"star-icon star-disable\"></span>");
                   }
                  else if(rsPagination.getInt("zvezdice") == 3)
                  {
                    out.println("<span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon star-disable\"></span>\n" +
"                    <span class=\"star-icon star-disable\"></span>");
                   }
                  else if(rsPagination.getInt("zvezdice") == 4)
                  {
                    out.println("<span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon star-disable\"></span>");
                   }
                  else if(rsPagination.getInt("zvezdice") == 5)
                  {
                    out.println("<span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon\"></span>\n" +
"                    <span class=\"star-icon\"></span>");
                   }
                
                out.println("<span class=\"star-reviews\">"+rsPagination.getInt("zvezdice")+"</span>\n" +
"              </div>    ");
                    
                Connection cons = null;
                Statement stmts = null;
                ResultSet rss = null;
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    cons = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmts = cons.createStatement();
                    String query = "SELECT AVG("+rates+") AS prosek FROM tipovisoba WHERE hotelID='"+rsPagination.getInt("ID")+"'";
                    rss = stmts.executeQuery(query); 
                    if(rss.first())
                    { 
                      out.println("<div class=\"price\">\n" +
    "                      $"+rss.getInt("prosek")+"\n" +
    "                      </div>");
                    }
                    rss.close();
                    stmts.close();
                    cons.close();
                }
                catch(Exception e)
                {
                  e.printStackTrace();
                }
                
                out.println("<div class=\"code\">AVG/Night from</div>\n" +
"              <div class=\"shop-actions\">           \n" +
"                <a class=\"product text shop-actions hot\" href=\"PrikaziSobe?hotelID="+rsPagination.getInt("ID")+"&lokacija="+searchcity+"&sobe="+nrooms+"&datumDolaska="+cidate+"&datumOdlaska="+codate+"&odrasli="+adults+"&deca="+kids+"&paket="+rates+"\">POGLEDAJ SOBE</a>\n" +
"              </div>\n" +
"            </div>\n" +
"\n" +
"            <div class=\"preview\">\n" +
"              <svg class=\"svg\" viewBox=\"0 0 200 200\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
"                <circle class=\"circle\" cx=\"100\" cy=\"100\" r=\"125\"/>");

                      byte[] imgData = rsPagination.getBytes("slika"); // blob field 
                      String encode = Base64.getEncoder().encodeToString(imgData);
                      
                  out.println("<image class=\"image\" xlink:href=\"data:image/jpeg;base64,"+encode+"\" x=\"0\" y=\"0\" width=\"200px\" height=\"180px\"/>\n" +
"              </svg>\n" +
"            </div>\n" +
"\n" +
"          </div>");
                    }
                } 
            }
            try
            {
            if(!rsPagination.first())
            {
                out.println("<div class=\"main_bg\">\n" +
                    "    <div class=\"wrap\">\n" +
                    "        <div class=\"main\">\n" +
                    "		<div class=\"res_online\">");
                out.println("<center><h1>Nema rezultata.</h1></center>");
                out.println("		</div>			\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>	\n");
            }
            psPagination.close();
            rsRowCnt.close();
            psPagination.close();
            psRowCnt.close();
            conn.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
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
                    out.println("<a class='page gradient' href=\"PretraziHotele?lokacija="+searchcity+"&sobe="+nrooms+"&datumDolaska="+cidate+"&datumOdlaska="+codate+"&paket="+rates+"&odrasli="+adults+"&deca="+kids+"&iPageNo="+prePageNo+"&cPageNo="+prePageNo+"\"> << Previous</a>\n");
                }
                
                for(i=((cPage*iTotalSearchRecords)-(iTotalSearchRecords-1));i<=(cPage*iTotalSearchRecords);i++)
                {
                    if(i==((iPageNo/iShowRows)+1))
                    {
                        out.println("<a class='page gradient' href=\"PretraziHotele?lokacija="+searchcity+"&sobe="+nrooms+"&datumDolaska="+cidate+"&datumOdlaska="+codate+"&paket="+rates+"&odrasli="+adults+"&deca="+kids+"&iPageNo="+i+"\" style=\"color: #59d\"><b>"+i+"</b></a>\n");
                    }
                    else if(i<=iTotalPages)
                    {
                        out.println("<a class='page gradient' href=\"PretraziHotele?lokacija="+searchcity+"&sobe="+nrooms+"&datumDolaska="+cidate+"&datumOdlaska="+codate+"&paket="+rates+"&odrasli="+adults+"&deca="+kids+"&iPageNo="+i+"\">"+i+"</a>\n");
                    }
                }
                if(iTotalPages>iTotalSearchRecords && i<iTotalPages)
                {
                    out.println("<a class='page active' href=\"PretraziHotele?lokacija="+searchcity+"&sobe="+nrooms+"&datumDolaska="+cidate+"&datumOdlaska="+codate+"&paket="+rates+"&odrasli="+adults+"&deca="+kids+"&iPageNo="+i+"&cPageNo="+i+"\"> >> Next</a>\n");
                }
            }
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
"<script type=\"text/javascript\">\n" +
"  $(\".user-menu\").click(function() {\n" +
"  $(this).toggleClass(\"show\");\n" +
"});\n" +
"\n" +
" </script>\n"+
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
