package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;

public class IzmeniKlijenta extends HttpServlet {

   
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
"        //EDIT CLIENT VALIDACIJA\n" +
"        function formValidation()  \n" +
"        {  \n" +
"            var username = document.editclient.username; \n" +
"            var password = document.editclient.password;  \n" +
"            var name = document.editclient.name;\n" +
"            var email = document.editclient.email;  \n" +
"            var phone = document.editclient.phone;  \n" +
"            var address = document.editclient.address;\n" +
"            var state = document.editclient.state; \n" +
"            var city = document.editclient.city; \n" +
"            var zip = document.editclient.zip; \n" +
"            var points = document.editclient.points; \n" +
"            \n" +
"            if(aphabet(name))\n" +
"            {\n" +
"            if(alphanumeric(username))  \n" +
"            { \n" +
"            if(pass_validation(password,7,12))  \n" +
"            { \n" +
"            if(ValidateEmail(email))  \n" +
"            { \n" +
"            if(allnumeric(phone))  \n" +
"            {  \n" +
"            if(alphanumeric1(address))  \n" +
"            {  \n" +
"            if(aphabet2(city))\n" +
"            {\n" +
"            if(aphabet1(state))\n" +
"            {\n" +
"            if(numbers(zip))  \n" +
"            {\n" +
"            if(allnumeric1(points))  \n" +
"            { \n" +
"                document.registration.submit();\n" +
"            }  \n" +
"            }   \n" +
"            }  \n" +
"            }   \n" +
"            }  \n" +
"            }  \n" +
"            }  \n" +
"            }  \n" +
"            }\n" +
"            }\n" +
"            return false;  \n" +
"        } \n" +
"        function pass_validation(password,mx,my)  \n" +
"        {  \n" +
"            var password_len = password.value.length;  \n" +
"            if(password_len == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje lozinka mora biti popunjeno.\" ,  \"warning\" );\n" +
"                password.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if (password_len == 0 ||password_len >= my || password_len < mx)  \n" +
"            {  \n" +
"                swal(\"Warning\" ,  \"Duzina lozinke mora biti\\n izmedju 7 i 12 karaktera.\" ,  \"warning\" );\n" +
"                password.focus();  \n" +
"                return false;  \n" +
"            }  \n" +
"            return true;\n"+
"            return true;  \n" +
"        }\n" +
"        function alphanumeric(username)  \n" +
"        {   \n" +
"            var letters = /^[0-9a-zA-Z]+$/;  \n" +
"            if(username.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje korisničko ime mora biti popunjeno.\" ,  \"warning\" );\n" +
"                username.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!username.value.match(letters))  \n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje korisničko ime može da sadrži samo slova i brojeve.\" ,  \"warning\" );\n" +
"                username.focus();  \n" +
"                return false;  \n" +
"            }  \n" +
"            return true;\n"+
"        } \n" +
"        function aphabet(name)\n" +
"        {\n" +
"            var letters = /^[a-zA-Z\\s]+$/;\n" +
"            if(name.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje ime i prezime mora biti popunjeno.\" ,  \"warning\" );\n" +
"                name.focus();  \n" +
"                return false;\n" +
"            }\n" +
"            else if(!name.value.match(letters))\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje ime i prezime može da sadrži samo slova.\" ,  \"warning\" );\n" +
"                name.focus();\n" +
"                return false;\n" +
"            }\n" +
"            return true;\n"+
"        }\n" +
"        function ValidateEmail(email)  \n" +
"        {  \n" +
"            var mailformat = /^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$/;  \n" +
"            if(email.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje email mora biti popunjeno.\" ,  \"warning\" );\n" +
"                email.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!email.value.match(mailformat))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Vaša email adresa nije u validnom formatu.\" ,  \"warning\" );\n" +
"                email.focus();  \n" +
"                return false;  \n" +
"            }  \n" +
"            return true;\n"+
"        } \n" +
"        function allnumeric(phone)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;  \n" +
"            if(phone.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje telefon mora biti popunjeno.\" ,  \"warning\" );\n" +
"                phone.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!phone.value.match(numbers))  \n" +
"            { \n" +
"                swal (\"Warning\" ,  \"Polje telefon može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                phone.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            else if(phone.value.length > 10)  \n" +
"            { \n" +
"                swal (\"Warning\" ,  \"Polje telefon ne može sadržati više od 10 cifara.\" ,  \"warning\" );\n" +
"                phone.focus();  \n" +
"                return false;  \n" +
"            } \n" +
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
"        function numbers(zip)  \n" +
"        {   \n" +
"            var numbers = /^[0-9]+$/;\n" +
"            if(zip.value.length == 0)\n" +
"            {\n" +
"                swal (\"Warning\" ,  \"Polje poštanski broj mora biti popunjeno.\" ,  \"warning\" );\n" +
"                zip.focus();   \n" +
"                return false;\n" +
"            }\n" +
"            else if(!zip.value.match(numbers))  \n" +
"            {  \n" +
"                swal (\"Warning\" ,  \"Polje poštanski broj može sadržati samo brojeve.\" ,  \"warning\" );\n" +
"                zip.focus();  \n" +
"                return false;  \n" +
"            } \n" +
"            return true;\n"+
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
"            return true;\n"+
"        }\n" +
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
"              <div class=\"panel-option active\">\n" +
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
"      <h3 style=\"width: 50%; float: none; margin: 5px auto; color: #AAA; text-align: center;\">Izmena klijenta</h3>\n");
       
        try
        {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            String clientID = (String)request.getParameter("id");
            String upit = "select * from klijenti where ID='"+clientID+"'";
            rs = stmt.executeQuery(upit);
                       
            while(rs.next())
            { 
            out.println("<form action=\"KlijentIzmenjen?id="+rs.getInt("ID")+"\" name=\"editclient\" method=\"POST\">\n" +
"           <div class=\"grid\">\n" +
"           <div class=\"col-6_sm-12 padded\">\n" +
"            <label> Klijent ID </label>\n" +
"          <input type=\"text\" name=\"id\" value=\""+rs.getInt("ID")+"\" disabled/>\n" +
"          \n" +
"          <label> Ime i prezime </label>\n" +
"          <input type=\"text\" name=\"name\" value=\""+rs.getString("ime")+"\"/> \n" +
"          \n" +
"          <label> Korisničko ime </label>\n" +
"          <input type=\"text\" name=\"username\" value=\""+rs.getString("kime")+"\"/> \n" +
"          <input type=\"text\" name=\"username1\" hidden=\"true\" value=\""+rs.getString("kime")+"\"/> \n" +
"          \n" +
"          <label> Lozinka </label>\n" +
"          <input type=\"text\" name=\"password\" value=\""+rs.getString("sifra")+"\"/> \n" +
"          \n" +
"          <label> Email </label>\n" +
"          <input type=\"text\" name=\"email\" value=\""+rs.getString("email")+"\"/>\n" +
"          \n" +
"          <label> Telefon </label>\n" +
"          <input type=\"text\" name=\"phone\" value=\""+rs.getInt("telefon")+"\"/>\n" +
"          \n" +
"          <label> Adresa </label>\n" +
"          <input type=\"text\" name=\"address\" value=\""+rs.getString("adresa")+"\"/> \n" +
"          \n" +
"          <label> Grad </label>\n" +
"          <input type=\"text\" name=\"city\" value=\""+rs.getString("grad")+"\"/> \n" +
"          \n" +
"          <label> Država </label>\n" +
"          <input type=\"text\" name=\"state\" value=\""+rs.getString("drzava")+"\"/> \n" +
"          \n" +
"          <label> Poštanski broj </label>\n" +
"          <input type=\"text\" name=\"zip\" value=\""+rs.getInt("pbroj")+"\"/> \n" +
"          \n" +
"          <label> Poeni </label>\n" +
"          <input type=\"text\" name=\"points\" value=\""+rs.getInt("poeni")+"\"/> \n" +
"\n" +
"           <label>Menadzer hotela</label>\n" +
"           <select name=\"hotelID\" placeholder=\"Hotel\">\n" +
"                <option selected=\"\" class=\"opt\"></option>");
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmt = con.createStatement();
                    String query = "select * from hoteli";
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
                
out.println("</select><br>"+
"          <label> Vrsta </label>\n" +
"          <select name=\"power\">\n" +
"              <option selected=\"\"></option>\n" +
"              <option>Klijent</option>\n" +
"              <option>Menadzer hotela</option>\n" +
"              <option>Admin</option>\n" +
"          </select>");           
            } 
            rs.close();
            stmt.close();
            con.close();
        }
        catch(Exception e){}
out.println(
"        </div>\n"+
"       <div id=\"formbox\">\n" +
"          <input type=\"submit\" onclick=\"return formValidation()\" value=\"Izmeni\" id=\"btn_s\"  /></div>\n" +
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
