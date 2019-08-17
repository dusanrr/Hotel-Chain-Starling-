package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;

public class KreirajSobu extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>\n" +
"<html>\n" +
"    <head>\n" +
"        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
"        <title>Kontrolna tabla</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <link rel=\"shortcut icon\" href=\"admin/images/favicon.png\" type=\"image/x-icon\"/>\n" +
"        <link href='admin/fonts/fonts/fonts.css' rel='stylesheet' type='text/css'>\n" +
"        <link href=\"admin/css/admin.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
"        <link href=\"admin/fonts/css/all.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
"        <link rel=\"stylesheet\" href=\"admin/css/material.blue-red.min.css\">\n" +
"        <script defer src=\"css/js/material.min.js\"></script>\n" +
"        <script src=\"admin/js/jquery.min.js\" type=\"text/javascript\"></script>\n" +
"        <script src=\"admin/js/js.js\" type=\"text/javascript\"></script>\n" +
"        <script src=\"admin/js/jquery.min.js\" type=\"text/javascript\"></script>\n" +
"        <script src=\"admin/js/js.js\" type=\"text/javascript\"></script>\n" +
"        <script src=\"admin/js/sweetalert.min.js\"></script>\n" +
"        <link rel=\"stylesheet\" href=\"css/js/sweetalert.css\">\n"+
"        <script>\n"+
"        //ADD NEW ROOM VALIDACIJA\n" +
"        function formValidation()  \n" +
"        {  \n" +
"            var rnmbr = document.addnr.rnmbr;\n" +
"            var rtype = document.addnr.rtype;\n" +
"            if(validaternmbr(rnmbr))\n" +
"            {\n" +
"            if(validatert(rtype))\n" +
"            {\n" +
"                document.addnr.submit();\n" +
"            }\n"+
"            }\n"+
"            return false;  \n" +
"        } \n" +
"        function validaternmbr(rnmbr)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rnmbr.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje broj mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rnmbr.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"            else if(!rnmbr.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje broj može sadržati samo brojeve.\" ,  \"warning\");\n" +
"                rnmbr.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            else if(rnmbr.value < 1 || rnmbr.value > 10000)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje broj ne moze biti manje od 1 i vise od 10000.\" ,  \"warning\" );\n" +
"                rnmbr.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"                return true;\n" +
"        }\n"+

                
"        function validatert(rtype)  \n" +
"        {   \n" +
"            if(rtype.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje tip sobe mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtype.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"            return true;\n" +
"        }\n"+
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
        HttpSession sesija = request.getSession();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            Klijent user = (Klijent)sesija.getAttribute("user");
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
"                <a href=\"admin/reyervacije.jsp\"><p>Reyervacije</p></a>\n" +
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
"      <h3 style=\"width: 50%; float: none; margin: 5px auto; color: #AAA; text-align: center;\">Kreiranje sobe</h3>\n");
       
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            String hotelID = (String)request.getParameter("id");
            String upit = "select * from hoteli where ID='"+hotelID+"'";
            rs = stmt.executeQuery(upit);
                       
            while(rs.next())
            { 
            out.println("<form action=\"SobaKreirana?id="+rs.getInt("ID")+"\" name=\"addnr\" method=\"POST\">\n" +
"           <div class=\"grid\">\n" +
"           <div class=\"col-6_sm-12 padded\">\n" +
"            <label> Hotel </label>\n" +
"          <input type=\"text\" name=\"id\" value=\""+rs.getString("naziv")+"\" disabled/>\n" +
"          \n" +
"          <label> Broj sobe </label>\n" +
"          <input type=\"text\" name=\"rnmbr\"/> \n" +
"\n" +
"           <label>Tip sobe</label>\n" +
"           <select name=\"rtype\">\n");
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmt = con.createStatement();
                    String query = "select * from tipovisoba where hotelID="+hotelID+"";
                    rs = stmt.executeQuery(query);

                    while(rs.next())
                    { 
                        out.println("<option class=\"opt\" value=\""+rs.getInt("ID")+"\">"+rs.getString("naziv")+"</option>");
                    } 
                    rs.close();
                    stmt.close();
                    con.close();
                }
            catch(Exception e){}
                
out.println("</select><br>");
            
            } 
            rs.close();
            stmt.close();
            con.close();
        }
        catch(Exception e){}
out.println(
"        </div>\n"+
"       <div id=\"formbox\">\n" +
"          <input type=\"submit\" onclick=\"return formValidation()\" value=\"Kreiraj sobu\" id=\"btn_s\"  /></div>\n" +
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
