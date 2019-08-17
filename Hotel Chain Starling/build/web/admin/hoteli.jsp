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
    if(request.getSession().getAttribute("user") != null && user.getPower().equals("Admin"))
    {
        
    }
    else
    {
        request.setAttribute("popupid", 2);
        request.setAttribute("poruka", "Niste ulogovani ili niste administrator.Pristup odbijen!");
        RequestDispatcher rd = request.getRequestDispatcher("../klijent/index.jsp");
        rd.forward(request, response);  
    }
%>
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

    Connection conn = null;
    Class.forName("com.mysql.cj.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");

    ResultSet rsPagination = null;
    ResultSet rsRowCnt = null;
    
    PreparedStatement psPagination=null;
    PreparedStatement psRowCnt=null;
    
    int iShowRows=20;  //prikaza po strani
    int iTotalSearchRecords=10;  //broj strana koji se prikazuje
    
    int iTotalRows=nullIntconv(request.getParameter("iTotalRows"));
    int iTotalPages=nullIntconv(request.getParameter("iTotalPages"));
    int iPageNo=nullIntconv(request.getParameter("iPageNo"));
    int cPageNo=nullIntconv(request.getParameter("cPageNo"));
    
    String poruka = "";
    
    int iStartResultNo=0;
    int iEndResultNo=0;
    
    if(iPageNo==0)
    {
        iPageNo=0;
    }
    else{
        iPageNo=Math.abs((iPageNo-1)*iShowRows);
    }
    
    String sqlPagination="SELECT SQL_CALC_FOUND_ROWS * FROM hoteli limit "+iPageNo+","+iShowRows+"";

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
        //ADD NEW HOTEL VALIDACIJA
        function formValidation1()  
        {  
            var name = document.addhotel.hname;
            var state = document.addhotel.hstate; 
            var city = document.addhotel.hcity;  
            var address = document.addhotel.haddress;
            var description = document.addhotel.hdescription; 
            var stars = document.addhotel.hstars; 
            var photo = document.addhotel.photo;
            
            if(aphabet(name))
            {
            if(aphabet1(state))
            {
            if(aphabet2(city))
            {
            if(alphanumeric1(address))  
            {  
            if(validatedescription(description))  
            {     
            if(validatestars(stars))  
            { 
            if(validatephoto(photo))
            {
            if(validatephoto1(photo))
            {
                document.addhotel.submit();
            }  
            }   
            }  
            }   
            } 
            }
            }
            }
            return false;  
        } 
        function aphabet(name)
        {
            var letters = /^[a-zA-Z\s]+$/;
            if(name.value.length == 0)
            {
                swal ("Warning" ,  "Polje naziv mora biti popunjeno." ,  "warning" );
                name.focus();   
                return false;
            }
            else if(!name.value.match(letters))
            {
                swal ("Warning" ,  "Polje naziv može da sadrži samo slova." ,  "warning" );
                name.focus();
                return false;
            }
            return true;
        }
        function alphanumeric1(address)  
        {   
            var letters = /^[0-9a-zA-Z\s]+$/;
            if(address.value.length == 0)
            {
                swal ("Warning" ,  "Polje adresa mora biti popunjeno." ,  "warning" );
                address.focus();   
                return false;
            }
            else if(!address.value.match(letters))  
            {  
                swal ("Warning" ,  "Polje adresa može sadržati samo slova i brojeve." ,  "warning" );
                address.focus();  
                return false;  
            }  
            return true;
        } 
        function aphabet1(state)
        {
            var letters = /^[a-zA-Z\s]+$/;
            if(state.value.length == 0)
            {
                swal ("Warning" ,  "Polje država mora biti popunjeno." ,  "warning" );
                state.focus();   
                return false;
            }
            else if(!state.value.match(letters))
            {
                swal ("Warning" ,  "Polje država može sadržati samo slova." ,  "warning" );
                state.focus();
                return false;
            }
            return true;
        }
        function aphabet2(city)
        {
            var letters = /^[a-zA-Z\s]+$/;
            if(city.value.length == 0)
            {
                swal ("Warning" ,  "Polje grad mora biti popunjeno." ,  "warning" );
                city.focus();   
                return false;
            }
            else if(!city.value.match(letters))
            {
                swal ("Warning" ,  "Polje grad može sadržati samo slova." ,  "warning" );
                city.focus();
                return false;
            }
            return true;
        }
        function allnumeric1(points)  
        {   
            var numbers = /^[0-9]+$/;  
            if(points.value.length == 0)
            {
                swal ("Warning" ,  "Polje poeni mora biti popunjeno." ,  "warning" );
                points.focus();   
                return false;
            }
            else if(!points.value.match(numbers))  
            {  
                swal ("Warning" ,  "Polje poeni može sadržati samo brojeve." ,  "warning" );
                points.focus();  
                return false;  
            } 
            return true;
        }
        function validatedescription(description)  
        {   
            if(description.value.length == 0)  
            {  
                swal ("Warning" ,  "Polje opis mora biti popunjeno." ,  "warning" );
                description.focus();  
                return false;  
            }
            return true; 
        }
        function validatestars(stars)  
        {   
            if(stars.value.length == 0)  
            {  
                swal ("Warning" ,  "Polje opis mora biti popunjeno." ,  "warning" );
                stars.focus();  
                return false;  
            }
            return true; 
        }
        function validatephoto(photo)  
        {   
            if(photo.value.length == 0)  
            {  
                swal ("Warning" ,  "Morate izabrati sliku." ,  "warning" );
                photo.focus();  
                return false;  
            }
            return true;
        }
        function validatephoto1(photo)  
        {   
            var fileExtension = ['jpeg', 'jpg', 'png'];
            if ($.inArray($(photo).val().split('.').pop().toLowerCase(), fileExtension) == -1)
            {
                swal ("Warning" ,  "Slika mora biti u .jpeg, .jpg ili .png formatu." ,  "warning" );
                photo.focus();  
                return false;
            }
            return true;
        }
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
            <p class="name"><%=rs.getString("ime")%></p>
            <p class="name"><%=rs.getString("vrsta")%></p>
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
              <div class="panel-option">
                <i class="material-icons">computer</i>
                <a href="${pageContext.request.contextPath}/admin/index.jsp"><p>Statistika</p></a>
                
              </div>

              <div class="panel-option">
                <i class="material-icons">account_box</i>
                <a href="${pageContext.request.contextPath}/admin/klijenti.jsp"><p>Klijenti</p></a>
              </div>

              <div class="panel-option active">
                <i class="material-icons">hotel</i>
                <a href="${pageContext.request.contextPath}/admin/hoteli.jsp"><p>Hoteli</p></a>
              </div>

              <div class="panel-option">
                <i class="material-icons">room</i>
                <a href="${pageContext.request.contextPath}/admin/sobe.jsp"><p>Sobe</p></a>
              </div>
              
              <div class="panel-option">
                <i class="material-icons">room</i>
                <a href="${pageContext.request.contextPath}/admin/tipovisoba.jsp"><p>Tipovi soba</p></a>
              </div>

              <div class="panel-option">
                <i class="material-icons">shopping_cart</i>
                <a href="${pageContext.request.contextPath}/admin/rezervacije.jsp"><p>Rezervacije</p></a>
              </div>
              <div>
  </section>

  <section class="page-content">
    <div class="header">
      <p class="big">Kontrolna tabla <span class="small">all informations about Starling Hotel Chain</span></p>
    </div>

    <div class="content">
      <div class='overlay'>
  <form class='slideUpForm' method="POST" name="addhotel" action="${pageContext.request.contextPath}/KreirajHotel" enctype="multipart/form-data">
    <span class='close'>X</span>
    <center><i class="fa fa-building" aria-hidden="true"></i></center><br>
    <label>Naziv hotela</label><input name="hname" placeholder='Naziv hotela' required type='text'>
    <label>Država</label><input name="hstate" placeholder='Država' required type='text'>
    <label>Grad</label><input name="hcity" placeholder='Grad' required type='text'>
    <label>Adresa</label><input name="haddress" placeholder='Adresa' required type='text'>
    <label>Opis</label><textarea name="hdescription" placeholder='Opis' rows='4'></textarea>
    <label>Zvezdice</label><select name="hstars">
                <option selected="" class="opt"></option>
                <option class="opt">1</option>
                <option class="opt">2</option>
                <option class="opt">3</option>  
                <option class="opt">4</option>   
                <option class="opt">5</option>   
    </select>
    <label>Slika</label><input type="file" name="photo" size="50"/>
    <center><input type='submit' onclick="return formValidation1()" value='Kreiraj hotel'><center>
  </form>
</div>
    <br><br>
  <table class="tables">
    <thead>
      <tr>
        <th>ID</th>
        <th>Naziv</th>
        <th>Država</th>
        <th>Grad</th>
        <th>Adresa</th>
        <th>Opis</th>
        <th>Zvezdice</th>
        <th>Slika</th>
        <th><center><i class="fa fa-edit" aria-hidden="true"></i></center></th>
        <th><center><i class="fa fa-trash" aria-hidden="true"></i></center></th>
        <th><center><i class="fa fa-plus" aria-hidden="true"></i></center></th>

      </tr>
    </thead>
    <tbody>
      
        
        <%    
        try
        {
            %>
            
            <%  int i=1;
                while(rsPagination.next())
            { %>
            <tr>
                <td><%= rsPagination.getInt("ID")%></td>
                <td><%= rsPagination.getString("naziv")%></td>
                <td><%= rsPagination.getString("drzava")%></td>
                <td><%= rsPagination.getString("grad")%></td>
                <td><%= rsPagination.getString("adresa")%></td>
                <td><textarea style="width: 100%; height: 100%; resize: none; background-color: transparent; border: none; overflow: none; " disabled><%= rsPagination.getString("opis")%></textarea></td>
                <td><%= rsPagination.getInt("zvezdice")%></td>
                <%
                            if(rsPagination.getBytes("slika") != null)
                            {
                            byte[] imgData = rsPagination.getBytes("slika");
                            String encode = Base64.getEncoder().encodeToString(imgData);
                            
                %>
                <td><img style="max-width: 200px; max-height: 200px;" src="data:image/jpeg;base64,<%=encode%>" alt=""/></td>
                <% }
                else
                {   
                %>
                <td>Nema slike</td>
                <%}%>
                <td><a class="button green" href="${pageContext.request.contextPath}/IzmeniHotel?id=<%=rsPagination.getInt("ID")%>">IZMENI</a></td>
                <td><a class="button red" href="${pageContext.request.contextPath}/ObrisiHotel?id=<%=rsPagination.getInt("ID")%>">OBRISI</a></td>
                <td><a class="showForm button blue" href="#">KREIRAJ</a></td>
            </tr>
            
              <%}
            rsPagination.close();
            rsRowCnt.close();
            psPagination.close();
            psRowCnt.close();
            con.close();
        }
        catch(SQLException ex)
        {
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
          <a class='page gradient' href="${pageContext.request.contextPath}/admin/hoteli.jsp?iPageNo=<%=prePageNo%>&cPageNo=<%=prePageNo%>"> << Previous</a>
         <%
        }
        
        for(i=((cPage*iTotalSearchRecords)-(iTotalSearchRecords-1));i<=(cPage*iTotalSearchRecords);i++)
        {
          if(i==((iPageNo/iShowRows)+1))
          {
          %>
           <a class='page gradient' href="${pageContext.request.contextPath}/admin/hoteli.jsp?iPageNo=<%=i%>" style="color: #59d"><b><%=i%></b></a>
          <%
          }
          else if(i<=iTotalPages)
          {
          %>
           <a class='page gradient' href="${pageContext.request.contextPath}/admin/hoteli.jsp?iPageNo=<%=i%>"><%=i%></a>
          <% 
          }
        }
        if(iTotalPages>iTotalSearchRecords && i<iTotalPages)
        {
         %>
         <a class='page active' href="${pageContext.request.contextPath}/admin/hoteli.jsp?iPageNo=<%=i%>&cPageNo=<%=i%>"> >> Next</a> 
         <%
        }
        }
      %>
	</div>
    </div>
  </section>
    <script src="${pageContext.request.contextPath}/admin/js/js.js" type="text/javascript"></script>
</body>
</html>
