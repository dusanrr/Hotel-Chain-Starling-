package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;

public class IzmeniSobu extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesija = request.getSession();
        Klijent user = (Klijent)sesija.getAttribute("user");
        if(request.getSession().getAttribute("user") != null && user.getPower().equals("Admin"))
        {
        out.println("<!DOCTYPE html>\n" +
"<html>\n" +
"    <head>\n" +
"        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
"        <title>Kontrolna tabla</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <link rel=\"shortcut icon\" href=\"admin/images/favicon.png\" type=\"image/x-icon\"/>\n" +
"        <link href='admin/fonts/fonts/fonts.css' rel='stylesheet' type='text/css'>  "+
"        <link href=\"admin/css/admin.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
"        <link href=\"admin/fonts/css/all.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
"        <link rel=\"stylesheet\" href=\"admin/css/material.blue-red.min.css\">\n" +
"        <script defer src=\"css/js/material.min.js\"></script>\n" +
"        <script src=\"admin/js/jquery.min.js\" type=\"text/javascript\"></script>\n" +
"        <script src=\"admin/js/js.js\" type=\"text/javascript\"></script>\n" +
"        <script src=\"admin/js/jquery.min.js\" type=\"text/javascript\"></script>\n" +
"        <script src=\"admin/js/js.js\" type=\"text/javascript\"></script>\n" +
"        <script src=\"admin/js/sweetalert.min.js\"></script>\n" +
"        <link rel=\"stylesheet\" href=\"css/js/sweetalert.css\">\n" +
"        <script>\n" +
"        //EDIT ROOM VALIDACIJA\n" +
"        function formValidation()  \n" +
        "{  \n" +
        "    var number = document.editroom.number;\n" +
        "    \n" +
        "    if(validatenumber(number))\n" +
        "    {\n" +
        "        document.editroom.submit();\n" +
        "    }\n" +
        "    return false;  \n" +
        "} \n" +
        "function validatenumber(number)  \n" +
        "{   \n" +
        "    var numbers = /^[0-9]+$/;  \n" +
        "    if(number.value.length == 0)\n" +
        "    {\n" +
        "       swal (\"Warning\" ,  \"Polje broj mora biti popunjeno.\" ,  \"warning\" );\n" +
        "       number.focus();   \n" +
        "       return false;\n" +
        "    }\n" +
        "    else if(!number.value.match(numbers))  \n" +
        "    {  \n" +
        "       swal (\"Warning\" ,  \"Polje broj može sadržati samo brojeve.\" ,  \"warning\" );\n" +
        "       number.focus();  \n" +
        "       return false;  \n" +
        "    } \n"+
        "    else if(number.value < 1 || number.value > 10000)\n" +
        "    {\n" +
        "        swal (\"Warning\" ,  \"Polje broj ne moze biti manje od 1 i vise od 10000.\" ,  \"warning\" );\n" +
        "        number.focus();  \n" +
        "        return false;  \n" +
        "    }\n" +
        "        return true;\n" +
        "}\n"+
"        </script>\n"+
"    </head>\n");
 
out.println("<body>");
out.println("<section class=\"navigation\">\n" +
"    <p class=\"title\">\n" +
"      <img style=\"height: 70px; width: 170px;\" src=\"klijent/images/logo.png\">\n" +
"    <p><hr style=\"height: 1px; width:100%; max-width: 185px; margin: 0 auto;\">\n" +
"\n" +
"        <img class=\"profile-pic\" src=\"admin/images/img_avatar.png\" alt=\"\"/>\n");       
             
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
            
            while(rs.next())
            { 
                out.println("<p class=\"name\">"+rs.getString("ime")+"</p>");
                out.println("<p class=\"name\">"+rs.getString("vrsta")+"</p>");
            }
            rs.close();
            stmt.close();
            con.close();
        }
        catch(Exception e){}
                out.println(
                "<div class=\"function-wrapper\">\n" +
"              <a href=\"odjavljivanje\">\n" +
"              <button id=\"odjavljivanje\" class=\"function\"><i class=\"material-icons\">clear</i></button>\n" +
"              <div class=\"mdl-tooltip\" for=\"logout\">\n" +
"                Odjavi se\n" +
"              </div>\n" +
"              </a>\n" +
"            </div>\n" +
"\n" +
"            <div class=\"options-wrapper\">\n" +
"                \n" +
"              <div class=\"panel-option\">\n" +
"                <i class=\"material-icons\">computer</i>\n" +
"                <a href=\"admin/index.jsp\"><p>Statistika</p></a>\n" +
"              </div>\n" +
"\n" +
"              <div class=\"panel-option\">\n" +
"                <i class=\"material-icons\">account_box</i>\n" +
"                <a href=\"admin/klijenti.jsp\"><p>Klijenti</p></a>\n" +
"              </div>\n" +
"\n" +
"              <div class=\"panel-option\">\n" +
"                <i class=\"material-icons\">hotel</i>\n" +
"                <a href=\"admin/hoteli.jsp\"><p>Hoteli</p></a>\n" +
"              </div>\n" +
"\n" +
"              <div class=\"panel-option active\">\n" +
"                <i class=\"material-icons\">room</i>\n" +
"                <a href=\"admin/sobe.jsp\"><p>Sobe</p></a>\n" +
"              </div>\n" +
"              \n" +
"              <div class=\"panel-option\">\n" +
"                <i class=\"material-icons\">room</i>\n" +
"                <a href=\"admin/tipovisoba.jsp\"><p>Tipovi soba</p></a>\n" +
"              </div>\n" +
"              \n" +
"              <div class=\"panel-option\">\n" +
"                <i class=\"material-icons\">shopping_cart</i>\n" +
"                <a href=\"admin/rezervacije.jsp\"><p>Rezervacije</p></a>\n" +
"              </div>\n" +
"              \n" +
"              <div>\n"+  
"  </section>\n" +
"\n" +
"<section class=\"page-content\">\n" +
"    <div class=\"header\">\n" +
"      <p class=\"big\">Kontrolna tabla <span class=\"small\">all informations about Starling Hotel Chain</span></p>\n" +
"    </div><br><br>\n" +
"    <div class=\"content\">\n" +
"      <h3 style=\"width: 50%; float: none; margin: 5px auto; color: #AAA; text-align: center;\">Izmena sobe</h3>\n");
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            String roomID = (String)request.getParameter("id");
            String upit = "select * from sobe where ID='"+roomID+"'";
            rs = stmt.executeQuery(upit);
                       
            if(rs.first())
            { 
            out.println("<form action=\"SobaIzmenjena?id="+rs.getInt("ID")+"\" name=\"editroom\" method=\"POST\">\n" +
"           <div class=\"grid\">\n" +
"           <div class=\"col-6_sm-12 padded\">\n" +
"            <label> ID sobe </label>\n" +
"          <input type=\"text\" name=\"id\" value=\""+rs.getInt("ID")+"\" disabled/>\n" +
"          \n");
         
out.println("<label> Broj sobe </label>\n" +
"          <input type=\"text\" name=\"number\" value=\""+rs.getInt("broj")+"\"/> \n"+
"          <input type=\"text\" name=\"number1\" hidden=\"true\" value=\""+rs.getInt("broj")+"\"/> \n"+
"          <input type=\"text\" name=\"hotelID\" hidden=\"true\" value=\""+rs.getInt("hotelID")+"\"/> \n"); 

            out.println("<label> Status sobe </label>\n" +
"          <input type=\"text\" name=\"status\" disabled value=\"Ova soba je "+rs.getString("status")+"\"/> \n"); 
                try
                {
                    Connection con1 = null;
                    Statement stmt1 = null;
                    ResultSet rs1 = null;
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmt1 = con1.createStatement();
                    String upits = "select * from tipovisoba where ID='"+rs.getInt("tipsobeID")+"'";
                    rs1 = stmt1.executeQuery(upits);

                if(rs1.next())
                {
                out.println("<label> Tip sobe </label>\n" +
"          <input type=\"text\" name=\"type\" disabled value=\""+rs1.getString("naziv")+"\"/> \n"); 
                }
                    rs1.close();
                    stmt1.close();
                    con1.close();
                }
                catch(Exception e){}
                
                try
                {
                    Connection con2 = null;
                    Statement stmt2 = null;
                    ResultSet rs2 = null;
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmt2 = con2.createStatement();
                    String upits = "select * from hoteli where ID='"+rs.getInt("hotelID")+"'";
                    rs2 = stmt2.executeQuery(upits);

                if(rs2.first())
                {
                out.println("<label>Hotel</label><br><input type=\"text\" name=\"id\" value=\""+rs2.getString("naziv")+"\" disabled/>\n");
                }
                    rs2.close();
                    stmt2.close();
                    con2.close();
                }
                catch(Exception e){} 
                
                
                out.println("<label> Promeni tip sobe</label> <select name=\"rtype\">\n" +
                "<option selected=\"\" class=\"opt\"></option>");
                try
                {
                    Connection con3 = null;
                    Statement stmt3 = null;
                    ResultSet rs3 = null;
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmt3 = con3.createStatement();
                    String upits = "select * from tipovisoba where hotelID='"+rs.getInt("hotelID")+"'";
                    rs3 = stmt3.executeQuery(upits);

                    while(rs3.next())
                    {
                    out.println("<option class=\"opt\" value=\""+rs3.getInt("ID")+"\">"+rs3.getString("naziv")+"</option>");
                    }
                    rs3.close();
                    stmt3.close();
                    con3.close();
                }
                catch(Exception e){}
                
                out.println("</select>");
               
                if(rs.getString("status") != "Prazna")
                {
                    out.println("<br><label>Otključaj sobu</label> <select name=\"rstatus\">\n" +
                    "<option selected=\"\" value=\"\" class=\"opt\"></option>"
                     + "<option value=\"Prazna\" class=\"opt\">Prazna</option></select>");
                }
            }
            rs.close();
            stmt.close();
            con.close();
        }
        catch(Exception e){}
         
out.println(
"        </div>\n"+
"        <div id=\"formbox\">\n" +
"          <input type=\"submit\" onclick=\"return formValidation()\" value=\"Izmeni\" id=\"btn_s\"  /></div>\n" +
"        </div>\n" +
"        </div>\n" +
"\n" +
"  </div></form>\n"+
"    </div>\n" +
"  </section>\n" +
"    <script src=\"admin/js/js.js\" type=\"text/javascript\"></script>\n" +
"</body>\n" +
"</html>\n" +
"");
        } 
        else if(request.getSession().getAttribute("user") != null && user.getPower().equals("Menadzer hotela"))
        {
            try
            {
                if(!user.getPower().equals("Menadzer hotela"))
                {
                    response.sendRedirect("../klijent/index.jsp");
                    request.setAttribute("poruka", "Pristup odbijen.");
                    return;
                }
            }
            catch(NullPointerException npe)
            {
                npe.printStackTrace();
                response.sendRedirect("../klijent/index.jsp");
                request.setAttribute("poruka", "Pristup odbijen.");
            }
        out.println("<!DOCTYPE html>\n" +
"<html>\n" +
"    <head>\n" +
"        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
"        <title>Kontrolna tabla</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <link rel=\"shortcut icon\" href=\"admin/images/favicon.png\" type=\"image/x-icon\"/>\n" +
"        <link href='admin/fonts/fonts/fonts.css' rel='stylesheet' type='text/css'>  "+
"        <link href=\"admin/css/admin.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
"        <link href=\"admin/fonts/css/all.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
"        <link rel=\"stylesheet\" href=\"admin/css/material.blue-red.min.css\">\n" +
"        <script defer src=\"css/js/material.min.js\"></script>\n" +
"        <script src=\"admin/js/jquery.min.js\" type=\"text/javascript\"></script>\n" +
"        <script src=\"admin/js/js.js\" type=\"text/javascript\"></script>\n" +
"        <script src=\"admin/js/jquery.min.js\" type=\"text/javascript\"></script>\n" +
"        <script src=\"admin/js/js.js\" type=\"text/javascript\"></script>\n" +
"        <script src=\"admin/js/sweetalert.min.js\"></script>\n" +
"        <link rel=\"stylesheet\" href=\"css/js/sweetalert.css\">\n" +
"        <script>\n" +
"        //EDIT ROOM VALIDACIJA\n" +
"        function formValidation()  \n" +
        "{  \n" +
        "    var number = document.editroom.number;\n" +
        "    \n" +
        "    if(validatenumber(number))\n" +
        "    {\n" +
        "        document.editroom.submit();\n" +
        "    }\n" +
        "    return false;  \n" +
        "} \n" +
        "function validatenumber(number)  \n" +
        "{   \n" +
        "    var numbers = /^[0-9]+$/;  \n" +
        "    if(number.value.length == 0)\n" +
        "    {\n" +
        "       swal (\"Warning\" ,  \"Polje broj mora biti popunjeno.\" ,  \"warning\" );\n" +
        "       number.focus();   \n" +
        "       return false;\n" +
        "    }\n" +
        "    else if(number.value < 1 || number.value > 10000)\n" +
        "    {\n" +
        "        swal (\"Warning\" ,  \"Polje broj ne moze biti manje od 1 i vise od 10000.\" ,  \"warning\" );\n" +
        "        number.focus();  \n" +
        "        return false;  \n" +
        "    }\n" +
        "    else if(!number.value.match(numbers))  \n" +
        "    {  \n" +
        "        swal (\"Warning\" ,  \"Polje broj može sadržati samo brojeve.\" ,  \"warning\" );\n" +
        "        number.focus();  \n" +
        "        return false;  \n" +
        "    } \n" +
        "    return true;\n" +
        "}\n"+
"        </script>\n"+
"    </head>\n");
 
out.println("<body>");
out.println("<section class=\"navigation\">\n" +
"    <p class=\"title\">\n" +
"      <img style=\"height: 70px; width: 170px;\" src=\"klijent/images/logo.png\">\n" +
"    <p><hr style=\"height: 1px; width:100%; max-width: 185px; margin: 0 auto;\">\n" +
"\n" +
"        <img class=\"profile-pic\" src=\"admin/images/img_avatar.png\" alt=\"\"/>\n");       
             
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
            
            while(rs.next())
            { 
                out.println("<p class=\"name\">"+rs.getString("ime")+"</p>");
                out.println("<p class=\"name\">"+rs.getString("vrsta")+"</p>");
            }
            rs.close();
            stmt.close();
            con.close();
        }
        catch(Exception e){}
                out.println(
                "<div class=\"function-wrapper\">\n" +
"              <a href=\"odjavljivanje\">\n" +
"              <button id=\"logout\" class=\"function\"><i class=\"material-icons\">clear</i></button>\n" +
"              <div class=\"mdl-tooltip\" for=\"odjavljivanje\">\n" +
"                Odjavi se\n" +
"              </div>\n" +
"              </a>\n" +
"            </div>\n" +
"\n" +
"               <div class=\"options-wrapper\">\n" +
"              <div class=\"panel-option\">\n" +
"                <i class=\"material-icons\">computer</i>\n" +
"                <a href=\"menadzer/index.jsp\"><p>Statistika</p></a>\n" +
"              </div>\n" +
"\n" +
"              <div class=\"panel-option\">\n" +
"                <i class=\"material-icons\">hotel</i>\n" +
"                <a href=\"menadzer/hotel.jsp\"><p>Hotel</p></a>\n" +
"              </div>\n" +
"\n" +
"              <div class=\"panel-option active\">\n" +
"                <i class=\"material-icons\">room</i>\n" +
"                <a href=\"menadzer/sobe.jsp\"><p>Sobe</p></a>\n" +
"              </div>\n" +
"              \n" +
"              <div class=\"panel-option\">\n" +
"                <i class=\"material-icons\">room</i>\n" +
"                <a href=\"menadzer/tipovisoba.jsp\"><p>Tipovi soba</p></a>\n" +
"              </div>\n" +
"\n" +
"              <div class=\"panel-option\">\n" +
"                <i class=\"material-icons\">shopping_cart</i>\n" +
"                <a href=\"menadzer/rezervacije.jsp\"><p>Rezervacije</p></a>\n" +
"              </div>\n" +
"              <div>"+
"  </section>\n" +
"\n" +
"<section class=\"page-content\">\n" +
"    <div class=\"header\">\n" +
"      <p class=\"big\">Kontrolna tabla <span class=\"small\">all informations about Starling Hotel Chain</span></p>\n" +
"    </div><br><br>\n" +
"    <div class=\"content\">\n" +
"      <h3 style=\"width: 50%; float: none; margin: 5px auto; color: #AAA; text-align: center;\">Izmena sobe</h3>\n");
       
        try
        {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            String roomID = (String)request.getParameter("id");
            String upit = "select * from sobe where ID='"+roomID+"'";
            rs = stmt.executeQuery(upit);
                       
            if(rs.first())
            { 
            out.println("<form action=\"SobaIzmenjena?id="+rs.getInt("ID")+"\" name=\"editroom\" method=\"POST\">\n" +
"           <div class=\"grid\">\n" +
"           <div class=\"col-6_sm-12 padded\">\n" +
"            <label> ID sobe </label>\n" +
"          <input type=\"text\" name=\"id\" value=\""+rs.getInt("ID")+"\" disabled/>\n" +
"          \n");
         
out.println("<label> Broj sobe </label>\n" +
"          <input type=\"text\" name=\"number\" value=\""+rs.getInt("broj")+"\"/> \n"+
"          <input type=\"text\" name=\"number1\" hidden=\"true\" value=\""+rs.getInt("broj")+"\"/> \n"); 
            out.println("<label> Status sobe </label>\n" +
"          <input type=\"text\" name=\"status\" disabled value=\""+rs.getString("status")+"\"/> \n"); 
                try
                {
                    Connection con1 = null;
                    Statement stmt1 = null;
                    ResultSet rs1 = null;
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmt1 = con1.createStatement();
                    String upits = "select * from tipovisoba where ID='"+rs.getInt("tipsobeID")+"'";
                    rs1 = stmt1.executeQuery(upits);

                if(rs1.next())
                {
                    out.println("<label> Tip sobe </label>\n" +
"          <input type=\"text\" name=\"type\" disabled value=\""+rs1.getString("naziv")+"\"/> \n"); 
                }
                    rs1.close();
                    stmt1.close();
                    con1.close();
                }
                catch(Exception e){}
                try
                {
                    Connection con2 = null;
                    Statement stmt2 = null;
                    ResultSet rs2 = null;
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmt2 = con2.createStatement();
                    String upits = "select * from hoteli where ID='"+rs.getInt("hotelID")+"'";
                    rs2 = stmt2.executeQuery(upits);

                if(rs2.first())
                {
                    out.println("<label>Hotel</label><br><input type=\"text\" name=\"id\" value=\""+rs2.getString("naziv")+"\" disabled/>\n");
                }
                    rs2.close();
                    stmt2.close();
                    con2.close();
                }
                catch(Exception e){} 
                
                out.println("<label> Promeni tip sobe </label> <select name=\"rtype\">\n" +
                "<option selected=\"\" class=\"opt\"></option>");
                try
                {
                    Connection con3 = null;
                    Statement stmt3 = null;
                    ResultSet rs3 = null;
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmt3 = con3.createStatement();
                    String upits = "select * from tipovisoba where hotelID='"+user.getHotelID()+"'";
                    rs3 = stmt3.executeQuery(upits);

                    while(rs3.next())
                    {
                        out.println("<option class=\"opt\" value=\""+rs3.getInt("ID")+"\">"+rs3.getString("naziv")+"</option>");
                    }
                    rs3.close();
                    stmt3.close();
                    con3.close();
                }
                catch(Exception e){}
                out.println("</select>");
               
                if(rs.getString("status") != "Prazna")
                {
                out.println("<br><label>Otključaj sobu</label> <select name=\"rstatus\">\n" +
                "<option selected=\"\" value=\"\" class=\"opt\"></option>"
                 + "<option value=\"Prazna\" class=\"opt\">Prazna</option></select>");
                }
            }
            rs.close();
            stmt.close();
            con.close();
        }
        catch(Exception e){}
         
out.println(
"        </div>\n"+
"        <div id=\"formbox\">\n" +
"          <input type=\"submit\" onclick=\"return formValidation()\" value=\"Izmeni\" id=\"btn_s\"  /></div>\n" +
"        </div>\n" +
"        </div>\n" +
"  </div></form>\n"+
"    </div>\n" +
"  </section>\n" +
"    <script src=\"admin/js/js.js\" type=\"text/javascript\"></script>\n" +
"</body>\n" +
"</html>\n" +
"");
        }
        else
        {
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Niste ulogovani ili niste administrator/menadzer hotela.");
            RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
            rd.forward(request, response);
        }
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
