package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;

public class IzmeniTipSobe extends HttpServlet {

   
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
"        //EDIT ROOM TYPE VALIDACIJA\n" +
"        function formValidation()  \n" +
"        {  \n" +
"            var rtname = document.ert.rtname;\n" +
"            var rtadults = document.ert.rtadults;\n" +
"            var rtkids = document.ert.rtkids;\n" +
"            var rtdesc = document.ert.rtdesc; \n" +
"            var rtmember = document.ert.rtmember;\n" +
"            var rtstandard = document.ert.rtstandard;\n" +
"            var rtbb = document.ert.rtbb;\n" +
"            var rtdpoints = document.ert.rtdpoints;\n" +
"            var rtepackage = document.ert.rtepackage;\n" +
"            var rtsp = document.ert.rtsp;\n" +
"            var photo = document.ert.photo;\n" +
"            \n" +
"            if(aphabet(rtname))\n" +
"            {\n" +
"            if(validatertadults(rtadults))\n" +
"            {\n" +
"            if(validatertkids(rtkids))\n" +
"            {\n" +
"            if(validatertdesc(rtdesc))\n" +
"            {\n" +
"            if(allnumeric1(rtmember))\n" +
"            {\n" +
"            if(allnumeric2(rtstandard))  \n" +
"            {  \n" +
"            if(allnumeric3(rtbb))  \n" +
"            {     \n" +
"            if(allnumeric4(rtdpoints))  \n" +
"            { \n" +
"            if(allnumeric5(rtepackage))  \n" +
"            {\n" +
"            if(allnumeric6(rtsp))  \n" +
"            {\n" +
"            if(validatephoto(photo))  \n" +
"            { \n" +
"                document.ert.submit();\n" +
"            }\n" +
"            }  \n" +
"            }   \n" +
"            }  \n" +
"            }   \n" +
"            } \n" +
"            }\n" +
"            }\n" +
"            }\n" +
"            }\n" +
"            }\n" +
"            return false;  \n" +
"        } \n" +
"        function aphabet(rtname)\n" +
"        {\n" +
"            var letters = /^[0-9a-zA-Z\\s]+$/;\n" +
"            if(rtname.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje naziv mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtname.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtname.value.match(letters))\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje naziv može da sadrži samo slova i brojeve.\" ,  \"warning\" );\n" +
"                rtname.focus();\n" +
"                return false;\n" +
"            }\n" +
"            return true;\n"+
"        }\n" +
"        function validatertadults(rtadults)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtadults.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje odrasli mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtadults.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"            else if(!rtadults.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje odrasli može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtadults.focus();  \n" +
"                return false;  \n" +
"            } \n"+  
"            else if(rtadults.value < 1 || rtadults.value > 5)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje odrasli ne moze biti manje od 1 i vise od 5.\" ,  \"warning\" );\n" +
"                rtadults.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"                return true;\n" +
"        }\n" +
"        function validatertkids(rtkids)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtkids.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje deca mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtkids.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"            else if(!rtkids.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje deca može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtkids.focus();  \n" +
"                return false;  \n" +
"            } \n"+
"            else if(rtkids.value < 0 || rtkids.value > 5)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje deca ne moze biti manje od 1 i vise od 5.\" ,  \"warning\" );\n" +
"                rtkids.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"                return true;\n" +
"        }\n" +
"        function validatertdesc(rtdesc)  \n" +
"        {   \n" +
"            if(rtdesc.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje opis mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtdesc.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true; \n"+
"        }\n" +
"        function allnumeric1(rtmember)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtmember.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje paket 1 mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtmember.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtmember.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje paket 1 može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtmember.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;\n"+
"        }\n" +
"        function allnumeric2(rtstandard)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtstandard.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje paket 2 mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtstandard.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtstandard.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje paket 2 može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtstandard.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;\n"+
"        }\n" +
"        function allnumeric3(rtbb)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtbb.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje paket 3 mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtbb.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtbb.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje paket 3 može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtbb.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;\n"+
"        }\n" +
"        function allnumeric4(rtdpoints)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtdpoints.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje paket 4 mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtdpoints.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtdpoints.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje paket 4 može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtdpoints.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;\n"+
"        }\n" +
"        function allnumeric5(rtepackage)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtepackage.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje paket 5 mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtepackage.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtepackage.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje paket 5 može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtepackage.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;\n"+
"        }\n" +
"        function allnumeric6(rtsp)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtsp.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje starling poeni polje mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtsp.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtsp.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje starling poeni može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtsp.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;\n"+
"        }\n" +
"        function validatephoto(photo)  \n" +
"        {   \n" +
"            var fileExtension = ['jpeg', 'jpg', 'png'];\n" +
"            if(photo.value.length != 0)  \n" +
"            { \n"+
"               if ($.inArray($(photo).val().split('.').pop().toLowerCase(), fileExtension) == -1)\n" +
"               {\n" +
"                   swal (\"Warning\" ,  \"Slika mora biti u .jpeg, .jpg ili .png formatu.\" ,  \"warning\" );\n" +
"                   photo.focus();  \n" +
"                   return false;\n" +
"               }\n" +
"            }\n" +
"            return true;\n" +
"        }\n"+
"        \n" +
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
"              <div class=\"panel-option\">\n" +
"                <i class=\"material-icons\">room</i>\n" +
"                <a href=\"admin/sobe.jsp\"><p>Sobe</p></a>\n" +
"              </div>\n" +
"              \n" +
"              <div class=\"panel-option active\">\n" +
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
"      <h3 style=\"width: 50%; float: none; margin: 5px auto; color: #AAA; text-align: center;\">Izmena tipa sobe</h3>\n");
        try
        {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            String roomtypeID = (String)request.getParameter("id");
            String upit = "select * from tipovisoba where ID='"+roomtypeID+"'";
            rs = stmt.executeQuery(upit);
                       
            while(rs.next())
            { 
            out.println("<form method=\"POST\" name=\"ert\" action=\"TipSobeIzmenjen?id="+rs.getInt("ID")+"\" enctype=\"multipart/form-data\">\n" +
"           <div class=\"grid\">\n" +
"           <div class=\"col-6_sm-12 padded\">\n" +
"            <label> Tip sobe ID </label>\n" +
"          <input type=\"text\" name=\"rtID\" value=\""+rs.getInt("ID")+"\" disabled/>\n" +
"          \n");
           
            out.println("<label> Naziv </label>\n" +
"          <input type=\"text\" name=\"rtname\" value=\""+rs.getString("naziv")+"\"/> \n" +
"          <input type=\"text\" name=\"rtname1\" hidden=\"true\" value=\""+rs.getString("naziv")+"\"/> \n" +
"          <input type=\"text\" name=\"IDhotel\" hidden=\"true\" value=\""+rs.getInt("hotelID")+"\"/> \n" +
"          \n" +
"          <label> Opis </label>\n" +
"          <textarea style=\"resize:none;\" name=\"rtdesc\">"+rs.getString("opis")+"</textarea>\n" +
"          \n" +
"          <label> Odrasli </label>\n" +
"          <input type=\"text\" name=\"rtadults\" value=\""+rs.getInt("odrasli")+"\"/>\n" +
"          \n" +
"          <label> Deca </label>\n" +
"          <input type=\"text\" name=\"rtkids\" value=\""+rs.getInt("deca")+"\"/>\n" +
"          \n" +
"          <label> Paket 1 </label>\n" +
"          <input type=\"text\" name=\"rtmember\" value=\""+rs.getInt("paket1")+"\"/>\n" +
"          \n"+
"          <label> Paket 2</label>\n" +
"          <input type=\"text\" name=\"rtstandard\" value=\""+rs.getInt("paket2")+"\"/>\n" +
"          \n"+
"          <label> Paket 3 </label>\n" +
"          <input type=\"text\" name=\"rtbb\" value=\""+rs.getInt("paket3")+"\"/>\n" +
"          \n"+
"          <label> Paket 4</label>\n" +
"          <input type=\"text\" name=\"rtdpoints\" value=\""+rs.getInt("paket4")+"\"/>\n" +
"          \n"+
"          <label> Paket 5 </label>\n" +
"          <input type=\"text\" name=\"rtepackage\" value=\""+rs.getInt("paket5")+"\"/>\n" +
"          \n"+
"          <label> Starling poeni </label>\n" +
"          <input type=\"text\" name=\"rtsp\" value=\""+rs.getInt("starling_poeni")+"\"/>\n" +
"          \n"+
"          <label> Slika </label>\n" +
"          <input type=\"file\" name=\"photo\" size=\"50\"/>");
            
        
        ResultSet ra = null;
        Statement sz = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            sz = con.createStatement();
            String upits = "select * from hoteli where ID='"+rs.getInt("hotelID")+"'";
            ra = sz.executeQuery(upits);
            
            out.println("<label> Hotel </label>");
            while(ra.next())
            { 
                out.println("<input type=\"text\" name=\"backup\" disabled placeholder=\"Hotel\" value=\""+ra.getString("naziv")+"\"/>\n");
            } 
            ra.close();
            sz.close();
            con.close();
        }
        catch(Exception e){}
        
         out.println("<label> Promeni hotel </label>\n"
                    + "<select name=\"hotelID\">");
             
             
        ResultSet res = null;
        Statement sta = null;
        try
        {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            sta = con.createStatement();
            String upits = "select * from hoteli";
            res = sta.executeQuery(upits);
            out.println("<option value=\"\" selected disabled></option>"); 
            while(res.next())
            { 
                out.println("<option value=\""+res.getInt("ID")+"\">"+res.getString("naziv")+"</option>"); 
            } 
            out.println("</select><br>\n");
            res.close();
            sta.close();
            con.close();
        }
        catch(Exception e){}
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
        else if(request.getSession().getAttribute("user") != null && user.getPower().equals("Menadzer hotela"))
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
"        //EDIT NEW ROOM TYPE VALIDACIJA\n" +
"        function formValidation()  \n" +
"        {  \n" +
"            var rtname = document.ert.rtname;\n" +
"            var rtadults = document.ert.rtadults;\n" +
"            var rtkids = document.ert.rtkids;\n" +
"            var rtdesc = document.ert.rtdesc; \n" +
"            var rtmember = document.ert.rtmember;\n" +
"            var rtstandard = document.ert.rtstandard;\n" +
"            var rtbb = document.ert.rtbb;\n" +
"            var rtdpoints = document.ert.rtdpoints;\n" +
"            var rtepackage = document.ert.rtepackage;\n" +
"            var rtsp = document.ert.rtsp;\n" +
"            var photo = document.ert.photo;\n" +
"            \n" +
"            if(aphabet(rtname))\n" +
"            {\n" +
"            if(validatertadults(rtadults))\n" +
"            {\n" +
"            if(validatertkids(rtkids))\n" +
"            {\n" +
"            if(validatertdesc(rtdesc))\n" +
"            {\n" +
"            if(allnumeric1(rtmember))\n" +
"            {\n" +
"            if(allnumeric2(rtstandard))  \n" +
"            {  \n" +
"            if(allnumeric3(rtbb))  \n" +
"            {     \n" +
"            if(allnumeric4(rtdpoints))  \n" +
"            { \n" +
"            if(allnumeric5(rtepackage))  \n" +
"            {\n" +
"            if(allnumeric6(rtsp))  \n" +
"            {\n" +
"            if(validatephoto(photo))  \n" +
"            { \n" +
"                document.ert.submit();\n" +
"            }\n" +
"            }  \n" +
"            }   \n" +
"            }  \n" +
"            }   \n" +
"            } \n" +
"            }\n" +
"            }\n" +
"            }\n" +
"            }\n" +
"            }\n" +
"            return false;  \n" +
"        } \n" +
"        function aphabet(rtname)\n" +
"        {\n" +
"            var letters = /^[0-9a-zA-Z\\s]+$/;\n" +
"            if(rtname.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje naziv mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtname.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtname.value.match(letters))\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje naziv može da sadrži samo slova i brojeve.\" ,  \"warning\" );\n" +
"                rtname.focus();\n" +
"                return false;\n" +
"            }\n" +
"            return true;\n"+
"        }\n" +
"        function validatertadults(rtadults)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtadults.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje odrasli mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtadults.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"            else if(!rtadults.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje odrasli može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtadults.focus();  \n" +
"                return false;  \n" +
"            } \n"+
"            else if(rtadults.value < 1 || rtadults.value > 5)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje odrasli ne moze biti manje od 1 i vise od 5.\" ,  \"warning\" );\n" +
"                rtadults.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"                return true;\n" +
"        }\n" +
"        function validatertkids(rtkids)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtkids.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje deca mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtkids.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"            else if(!rtkids.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje deca može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtkids.focus();  \n" +
"                return false;  \n" +
"            } \n"+
"            else if(rtkids.value < 0 || rtkids.value > 5)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje deca ne moze biti manje od 1 i vise od 5.\" ,  \"warning\" );\n" +
"                rtkids.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"               return true;\n" +
"        }\n" +
"        function validatertdesc(rtdesc)  \n" +
"        {   \n" +
"            if(rtdesc.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje opis mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtdesc.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"                return true;\n" +
"        }\n" +
"        function allnumeric1(rtmember)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtmember.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje paket 1 mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtmember.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtmember.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje paket 1 može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtmember.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;"+
"        }\n" +
"        function allnumeric2(rtstandard)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtstandard.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje paket 2 mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtstandard.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtstandard.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje paket 2 može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtstandard.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;"+
"        }\n" +
"        function allnumeric3(rtbb)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtbb.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje paket 3 mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtbb.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtbb.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje paket 3 može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtbb.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;"+
"        }\n" +
"        function allnumeric4(rtdpoints)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtdpoints.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje paket 4 mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtdpoints.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtdpoints.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje paket 4 može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtdpoints.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;"+
"        }\n" +
"        function allnumeric5(rtepackage)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtepackage.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje paket 5 mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtepackage.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtepackage.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje paket 5 može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtepackage.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;"+
"        }\n" +
"        function allnumeric6(rtsp)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(rtsp.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje starling poeni mora biti popunjeno.\" ,  \"warning\" );\n" +
"                rtsp.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!rtsp.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje starling poeni može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                rtsp.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;"+
"        }\n" +
"        function validatephoto(photo)  \n" +
"        {   \n" +
"            var fileExtension = ['jpeg', 'jpg', 'png'];\n" +
"            if(photo.value.length != 0)  \n" +
"            { \n"+
"               if ($.inArray($(photo).val().split('.').pop().toLowerCase(), fileExtension) == -1)\n" +
"               {\n" +
"                   swal (\"Warning\" ,  \"Slika mora biti u .jpeg, .jpg ili .png formatu.\" ,  \"warning\" );\n" +
"                   photo.focus();  \n" +
"                   return false;\n" +
"               }\n" +
"            }\n" +
"            return true;\n" +
"        }\n"+
"        \n" +
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
"              <div class=\"panel-option\">\n" +
"                <i class=\"material-icons\">room</i>\n" +
"                <a href=\"menadzer/sobe.jsp\"><p>Sobe</p></a>\n" +
"              </div>\n" +
"              \n" +
"              <div class=\"panel-option active\">\n" +
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
"      <h3 style=\"width: 50%; float: none; margin: 5px auto; color: #AAA; text-align: center;\">Izmena tipa sobe</h3>\n");
        try
        {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            String roomtypeID = (String)request.getParameter("id");
            String upit = "select * from tipovisoba where ID='"+roomtypeID+"'";
            rs = stmt.executeQuery(upit);
                       
            while(rs.next())
            { 
            out.println("<form method=\"POST\" name=\"ert\" action=\"TipSobeIzmenjen?id="+rs.getInt("ID")+"\" enctype=\"multipart/form-data\">\n" +
"           <div class=\"grid\">\n" +
"           <div class=\"col-6_sm-12 padded\">\n" +
"            <label> Tip sobe ID </label>\n" +
"          <input type=\"text\" name=\"rtID\" value=\""+rs.getInt("ID")+"\" disabled/>\n" +
"          \n");
            ResultSet ra = null;
            Statement sz = null;
            try
            {

                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                sz = con.createStatement();
                String upits = "select * from hoteli where ID='"+rs.getInt("hotelID")+"'";
                ra = sz.executeQuery(upits);

                out.println("<label> Hotel </label>");
                while(ra.next())
                { 
                    out.println("<input type=\"text\" name=\"backup\" disabled value=\""+ra.getString("naziv")+"\"/>\n");
                } 
                ra.close();
                sz.close();
                con.close();
            }
            catch(Exception e){}
            out.println("<label> Naziv </label>\n" +
"          <input type=\"text\" name=\"rtname\"  value=\""+rs.getString("naziv")+"\"/> \n" +
"          <input type=\"text\" name=\"rtname1\" hidden=\"true\" value=\""+rs.getString("naziv")+"\"/> \n" +
"          \n" +
"          <label> Opis </label>\n" +
"          <textarea style=\"resize:none;\" name=\"rtdesc\">"+rs.getString("opis")+"</textarea>\n" +
"          \n" +
"          <label> Odrasli </label>\n" +
"          <input type=\"text\" name=\"rtadults\" value=\""+rs.getInt("odrasli")+"\"/>\n" +
"          \n" +
"          <label> Deca </label>\n" +
"          <input type=\"text\" name=\"rtkids\" value=\""+rs.getInt("deca")+"\"/>\n" +
"          \n" +
"          <label> Paket 1 </label>\n" +
"          <input type=\"text\" name=\"rtmember\" value=\""+rs.getInt("paket1")+"\"/>\n" +
"          \n"+
"          <label> Paket 2 </label>\n" +
"          <input type=\"text\" name=\"rtstandard\" value=\""+rs.getInt("paket2")+"\"/>\n" +
"          \n"+
"          <label> Paket 3 </label>\n" +
"          <input type=\"text\" name=\"rtbb\" value=\""+rs.getInt("paket3")+"\"/>\n" +
"          \n"+
"          <label> Paket 4 </label>\n" +
"          <input type=\"text\" name=\"rtdpoints\" value=\""+rs.getInt("paket4")+"\"/>\n" +
"          \n"+
"          <label> Paket 5 </label>\n" +
"          <input type=\"text\" name=\"rtepackage\" value=\""+rs.getInt("paket5")+"\"/>\n" +
"          \n"+
"          <label> Starling poeni </label>\n" +
"          <input type=\"text\" name=\"rtsp\" value=\""+rs.getInt("starling_poeni")+"\"/>\n" +
"          \n"+
"          <label> Slika </label>\n" +
"          <input type=\"file\" name=\"photo\" size=\"50\"/>");
            } 
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
