package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;

public class IzmeniHotel extends HttpServlet {

   
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
"        //EDIT HOTEL VALIDACIJA\n" +
"        function formValidation()  \n" +
"        {  \n" +
"            var name = document.edithotel.name;\n" +
"            var state = document.edithotel.state; \n" +
"            var city = document.edithotel.city;  \n" +
"            var address = document.edithotel.address;\n" +
"            var description = document.edithotel.description; \n" +
"            var stars = document.edithotel.stars; \n" +
"            var photo = document.edithotel.photo; \n" +
"            \n" +
"            if(aphabet(name))\n" +
"            {\n" +
"            if(aphabet1(state))\n" +
"            {\n" +
"            if(aphabet2(city))\n" +
"            {\n" +
"            if(alphanumeric1(address))  \n" +
"            {  \n" +
"            if(validatedescription(description))  \n" +
"            {     \n" +
"            if(validatestars(stars))  \n" +
"            { \n" +
"            if(validatephoto(photo))  \n" +
"            { \n" +
"                document.edithotel.submit();\n" +
"            }  \n" +
"            }   \n" +
"            }  \n" +
"            }   \n" +
"            }   \n" +
"            }   \n" +
"            }   \n" +
"            return false;  \n" +
"        } \n" +
"        function aphabet(name)\n" +
"        {\n" +
"            var letters = /^[a-zA-Z\\s]+$/;\n" +
"            if(name.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje naziv mora biti popunjeno.\" ,  \"warning\" );\n" +
"                name.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!name.value.match(letters))\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje naziv može da sadrži samo slova.\" ,  \"warning\" );\n" +
"                name.focus();\n" +
"                return false;\n" +
"            }\n" +
"            return true;\n"+
"        }\n" +
"        function alphanumeric1(address)  \n" +
"        {   \n" +
"            var letters = /^[0-9a-zA-Z\\s]+$/;  \n" +
"            if(address.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje adresa mora biti popunjeno.\" ,  \"warning\" );\n" +
"                address.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!address.value.match(letters))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje adresa može sadržati samo slova i brojeve.\" ,  \"warning\" );\n" +
"                address.focus();  \n" +
"                return false;  \n" +
"            }  \n" +
"            return true;\n"+
"        } \n" +
"        function aphabet1(state)\n" +
"        {\n" +
"            var letters = /^[a-zA-Z\\s]+$/;\n" +
"            if(state.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje država mora biti popunjeno.\" ,  \"warning\" );\n" +
"                state.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!state.value.match(letters))\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje država može sadržati samo slova.\" ,  \"warning\" );\n" +
"                state.focus();\n" +
"                return false;\n" +
"            }\n" +
"            return true;\n"+
"        }\n" +
"        function aphabet2(city)\n" +
"        {\n" +
"            var letters = /^[a-zA-Z\\s]+$/;\n" +
"            if(city.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje grad mora biti popunjeno.\" ,  \"warning\" );\n" +
"                city.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!city.value.match(letters))\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje grad može sadržati samo slova.\" ,  \"warning\" );\n" +
"                city.focus();\n" +
"                return false;\n" +
"            }\n" +
"            return true;\n"+
"        }\n" +
"        function allnumeric1(points)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"           if(points.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje poeni mora biti popunjeno.\" ,  \"warning\" );\n" +
"                points.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!points.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje poeni može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                points.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;\n"+
"        }\n" +
"        function validatedescription(description)  \n" +
"        {   \n" +
"            if(description.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje opis mora biti popunjeno.\" ,  \"warning\" );\n" +
"                description.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"            return true; \n"+
"        }\n" +
"        function validatestars(stars)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(stars.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje zvezdice mora biti popunjeno.\" ,  \"warning\" );\n" +
"                stars.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"            else if(!stars.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje zvezdice može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                stars.focus();  \n" +
"                return false;  \n" +
"            } \n"+             
"            else if(stars.value < 1 || stars.value > 5)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Hotel ne moze imati manje od 1 i vise od 5 zvezdica.\" ,  \"warning\" );\n" +
"                stars.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"                return true;\n" +
"        }" +
"        function validatephoto(photo)  \n" +
"        {   \n" +
"            if(photo.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Morate izabrati sliku.\" ,  \"warning\" );\n" +
"                photo.focus();  \n" +
"                return false;  \n" +
"            }\n" +
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
"              <div class=\"panel-option active\">\n" +
"                <i class=\"material-icons\">hotel</i>\n" +
"                <a href=\"admin/hoteli.jsp\"><p>Hoteli</p></a>\n" +
"              </div>\n" +
"\n" +
"              <div class=\"panel-option\">\n" +
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
"      <h3 style=\"width: 50%; float: none; margin: 5px auto; color: #AAA; text-align: center;\">Izmena hotela</h3>\n");
       
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
            out.println("<form action=\"HotelIzmenjen?id="+rs.getInt("ID")+"\" method=\"POST\" name=\"edithotel\" enctype=\"multipart/form-data\">\n" +
"           <div class=\"grid\">\n" +
"           <div class=\"col-6_sm-12 padded\">\n" +
"            <label> Hotel ID </label>\n" +
"          <input type=\"text\" name=\"id\" value=\""+rs.getInt("ID")+"\" disabled/>\n" +
"          \n" +
"          <label> Naziv </label>\n" +
"          <input type=\"text\" name=\"name\" value=\""+rs.getString("naziv")+"\"/> \n" +
"          <input type=\"text\" name=\"name1\" hidden=\"true\" value=\""+rs.getString("naziv")+"\"/> \n" +
"          \n" +
"          <label> Država </label>\n" +
"          <input type=\"text\" name=\"state\" value=\""+rs.getString("drzava")+"\"/> \n" +
"          \n" +
"          <label> Grad </label>\n" +
"          <input type=\"text\" name=\"city\" value=\""+rs.getString("grad")+"\"/> \n" +
"          \n" +
"          <label> Adresa </label>\n" +
"          <input type=\"text\" name=\"address\" value=\""+rs.getString("adresa")+"\"/> \n" +
"          \n" +
"          <label> Opis </label>\n" +
"          <textarea name=\"description\">"+rs.getString("opis")+"</textarea>\n" +
"          \n" +
"          <label> Zvezdice </label>\n" +
"          <input type=\"text\" name=\"stars\" value=\""+rs.getInt("zvezdice")+"\"/> \n"+
"           <label> Slika </label>\n" +
"          <input type=\"file\" name=\"photo\" size=\"50\"/>");
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
"        //EDIT HOTEL VALIDACIJA\n" +
"        function formValidation()  \n" +
"        {  \n" +
"            var name = document.edithotel.name;\n" +
"            var state = document.edithotel.state; \n" +
"            var city = document.edithotel.city;  \n" +
"            var address = document.edithotel.address;\n" +
"            var description = document.edithotel.description; \n" +
"            var stars = document.edithotel.stars; \n" +
"            var photo = document.edithotel.photo; \n" +
                    
"            \n" +
"            if(aphabet(name))\n" +
"            {\n" +
"            if(aphabet1(state))\n" +
"            {\n" +
"            if(aphabet2(city))\n" +
"            {\n" +
"            if(alphanumeric1(address))  \n" +
"            {  \n" +
"            if(validatedescription(description))  \n" +
"            {     \n" +
"            if(validatestars(stars))  \n" +
"            { \n" +
"            if(validatephoto(photo))  \n" +
"            { \n" +
"                document.edithotel.submit();\n" +
"            }  \n" +
"            }   \n" +
"            }  \n" +
"            }   \n" +
"            } \n" +
"            }\n" +
"            }\n" +
"            return false;  \n" +
"        } \n" +
"        function aphabet(name)\n" +
"        {\n" +
"            var letters = /^[a-zA-Z\\s]+$/;\n" +
"            if(name.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje naziv mora biti popunjeno.\" ,  \"warning\" );\n" +
"                name.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!name.value.match(letters))\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje naziv može da sadrži samo slova.\" ,  \"warning\" );\n" +
"                name.focus();\n" +
"                return false;\n" +
"            }\n" +
"            return true;\n" +
"        }\n" +
"        function alphanumeric1(address)  \n" +
"        {   \n" +
"            var letters = /^[0-9a-zA-Z\\s]+$/;\n" +
"            if(address.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje adresa mora biti popunjeno.\" ,  \"warning\" );\n" +
"                address.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!address.value.match(letters))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje adresa može sadržati samo slova i brojeve.\" ,  \"warning\" );\n" +
"                address.focus();  \n" +
"                return false;  \n" +
"            }  \n" +
"            return true;\n" +
"        } \n" +
"        function aphabet1(state)\n" +
"        {\n" +
"            var letters = /^[a-zA-Z\\s]+$/;\n" +
"            if(state.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje zvezdice mora biti popunjeno.\" ,  \"warning\" );\n" +
"                state.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!state.value.match(letters))\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje zvezdice može sadržati samo slova.\" ,  \"warning\" );\n" +
"                state.focus();\n" +
"                return false;\n" +
"            }\n" +
"            return true;\n" +
"        }\n" +
"        function aphabet2(city)\n" +
"        {\n" +
"            var letters = /^[a-zA-Z\\s]+$/;\n" +
"            if(city.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje grad mora biti popunjeno.\" ,  \"warning\" );\n" +
"                city.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!city.value.match(letters))\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje grad može sadržati samo slova.\" ,  \"warning\" );\n" +
"                city.focus();\n" +
"                return false;\n" +
"            }\n" +
"            return true;\n" +
"        }\n" +
"        function allnumeric1(points)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(points.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje poeni mora biti popunjeno.\" ,  \"warning\" );\n" +
"                points.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!points.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje poeni može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                points.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;\n" +
"        }\n" +
"        function validatedescription(description)  \n" +
"        {   \n" +
"            if(description.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje opis mora biti popunjeno.\" ,  \"warning\" );\n" +
"                description.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"            return true; \n" +
"        }\n" +
"        function validatestars(stars)  \n" +
"        {   \n" +
"           var numbers = /^[0-9]+$/;  \n" +
"            if(stars.value.length == 0)  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje zvezdice mora biti popunjeno.\" ,  \"warning\" );\n" +
"                stars.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"            else if(!stars.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje zvezdice može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                stars.focus();  \n" +
"                return false;  \n" +
"            } \n"+             
"            else if(stars.value < 1 || stars.value > 5)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Hotel ne moze imati manje od 1 i vise od 5 zvezdica.\" ,  \"warning\" );\n" +
"                stars.focus();  \n" +
"                return false;  \n" +
"            }\n" +
"                return true;\n" +
"        }"+
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
"              <div class=\"panel-option active\">\n" +
"                <i class=\"material-icons\">hotel</i>\n" +
"                <a href=\"menadzer/hotel.jsp\"><p>Hotel</p></a>\n" +
"              </div>\n" +
"\n" +
"              <div class=\"panel-option\">\n" +
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
"      <h3 style=\"width: 50%; float: none; margin: 5px auto; color: #AAA; text-align: center;\">Izmena hotela</h3>\n");
       
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
            out.println("<form action=\"HotelIzmenjen?id="+rs.getInt("ID")+"\" name=\"edithotel\" method=\"POST\" enctype=\"multipart/form-data\">\n" +
"           <div class=\"grid\">\n" +
"           <div class=\"col-6_sm-12 padded\">\n" +
"            <label> Hotel ID </label>\n" +
"          <input type=\"text\" name=\"id\" value=\""+rs.getInt("ID")+"\" disabled/>\n" +
"          \n" +
"          <label> Naziv </label>\n" +
"          <input type=\"text\" name=\"name\" value=\""+rs.getString("naziv")+"\"/> \n" +
"          <input type=\"text\" name=\"name1\" hidden=\"true\" value=\""+rs.getString("naziv")+"\"/> \n" +
"          \n" +
"          <label> Država </label>\n" +
"          <input type=\"text\" name=\"state\" value=\""+rs.getString("drzava")+"\"/> \n" +
"          \n" +
"          <label> Grad </label>\n" +
"          <input type=\"text\" name=\"city\" value=\""+rs.getString("grad")+"\"/> \n" +
"          \n" +
"          <label> Adresa </label>\n" +
"          <input type=\"text\" name=\"address\" value=\""+rs.getString("adresa")+"\"/> \n" +
"          \n" +
"          <label> Opis </label>\n" +
"          <textarea name=\"description\">"+rs.getString("opis")+"</textarea>\n" +
"          \n" +
"          <label> Zvezdice </label>\n" +
"          <input type=\"text\" name=\"stars\" value=\""+rs.getInt("zvezdice")+"\"/> \n"+
"           <label> Slika </label>\n" +
"          <input type=\"file\" name=\"photo\" size=\"50\"/>");
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
