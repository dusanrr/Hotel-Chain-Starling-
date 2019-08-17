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

    String sqlPagination="SELECT SQL_CALC_FOUND_ROWS * FROM tipovisoba limit "+iPageNo+","+iShowRows+"";

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
        //ADD NEW ROOM TYPE VALIDACIJA
        function formValidation1()  
        {  
            var rthotel = document.newrt.rthotel;
            var rtname = document.newrt.rtname;
            var rtdescription = document.newrt.rtdescription; 
            var rtmember = document.newrt.rtmember;
            var rtstandard = document.newrt.rtstandard;
            var rtbb = document.newrt.rtbb;
            var rtdpoints = document.newrt.rtdpoints;
            var rtepackage = document.newrt.rtepackage;
            var rtsp = document.newrt.rtsp;
            var photo = document.newrt.photo;
            
            if(validaterthotel(rthotel))
            {
            if(aphabet(rtname))
            {
            if(validatertdescription(rtdescription))
            {
            if(allnumeric1(rtmember))
            {
            if(allnumeric2(rtstandard))  
            {  
            if(allnumeric3(rtbb))  
            {     
            if(allnumeric4(rtdpoints))  
            { 
            if(allnumeric5(rtepackage))  
            {
            if(allnumeric6(rtsp))  
            {
            if(validatephoto(photo))  
            {
            if(validatephoto1(photo))  
            {
                document.newrt.submit();
            }
            }  
            }   
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
        function validaterthotel(rthotel)  
        {   
            if(rthotel.value.length == 0)  
            {  
                swal ("Warning" ,  "Polje hotel mora biti popunjeno." ,  "warning" );
                rthotel.focus();  
                return false;  
            }
            return true;
        }
        function aphabet(rtname)
        {
            var letters = /^[0-9a-zA-Z\s]+$/;  
            if(rtname.value.length == 0)
            {
                swal ("Warning" ,  "Polje naziv mora biti popunjeno." ,  "warning" );
                rtname.focus();   
                return false;
            }
            else if(!rtname.value.match(letters))
            {
                swal ("Warning" ,  "Polje naziv može da sadrži samo slova i brojeve." ,  "warning" );
                rtname.focus();
                return false;
            }
            return true;
        }
        function validatertdescription(rtdescription)  
        {   
            if(rtdescription.value.length == 0)  
            {  
                swal ("Warning" ,  "Polje opis mora biti popunjeno." ,  "warning" );
                rtdescription.focus();  
                return false;  
            } 
            return true; 
        }
        function allnumeric1(rtmember)  
        {   
            var numbers = /^[0-9]+$/;  
            if(rtmember.value.length == 0)
            {
                swal ("Warning" ,  "Polje paket 1 mora biti popunjeno." ,  "warning" );
                rtmember.focus();   
                return false;
            }
            else if(!rtmember.value.match(numbers))  
            {  
                swal ("Warning" ,  "Polje paket 1 može sadržati samo brojeve." ,  "warning" );
                rtmember.focus();  
                return false;  
            } 
            return true;
        }
        function allnumeric2(rtstandard)  
        {   
            var numbers = /^[0-9]+$/;  
            if(rtstandard.value.length == 0)
            {
                swal ("Warning" ,  "Polje paket 2 mora biti popunjeno." ,  "warning" );
                rtstandard.focus();   
                return false;
            }
            else if(!rtstandard.value.match(numbers))  
            {  
                swal ("Warning" ,  "Polje paket 2 može sadržati samo brojeve." ,  "warning" );
                rtstandard.focus();  
                return false;  
            } 
            return true;
        }
        function allnumeric3(rtbb)  
        {   
            var numbers = /^[0-9]+$/;  
            if(rtbb.value.length == 0)
            {
                swal ("Warning" ,  "Polje paket 3 mora biti popunjeno." ,  "warning" );
                rtbb.focus();   
                return false;
            }
            else if(!rtbb.value.match(numbers))  
            {  
                swal ("Warning" ,  "Polje paket 3 može sadržati samo brojeve." ,  "warning" );
                rtbb.focus();  
                return false;  
            } 
            return true;
        }
        function allnumeric4(rtdpoints)  
        {   
            var numbers = /^[0-9]+$/;  
            if(rtdpoints.value.length == 0)
            {
                swal ("Warning" ,  "Polje paket 4 mora biti popunjeno." ,  "warning" );
                rtdpoints.focus();   
                return false;
            }
            else if(!rtdpoints.value.match(numbers))  
            {  
                swal ("Warning" ,  "Polje paket 4 može sadržati samo brojeve." ,  "warning" );
                rtdpoints.focus();  
                return false;  
            } 
            return true;
        }
        function allnumeric5(rtepackage)  
        {   
            var numbers = /^[0-9]+$/;  
            if(rtepackage.value.length == 0)
            {
                swal ("Warning" ,  "Polje paket 5 mora biti popunjeno." ,  "warning" );
                rtepackage.focus();   
                return false;
            }
            else if(!rtepackage.value.match(numbers))  
            {  
                swal ("Warning" ,  "Polje paket 6 može sadržati samo brojeve." ,  "warning" );
                rtepackage.focus();  
                return false;  
            } 
            return true;
        }
        function allnumeric6(rtsp)  
        {   
            var numbers = /^[0-9]+$/;  
            if(rtsp.value.length == 0)
            {
                swal ("Warning" ,  "Polje starling poeni mora biti popunjeno." ,  "warning" );
                rtsp.focus();   
                return false;
            }
            else if(!rtsp.value.match(numbers))  
            {  
                swal ("Warning" ,  "Polje starling poeni može sadržati samo brojeve." ,  "warning" );
                rtsp.focus();  
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

              <div class="panel-option">
                <i class="material-icons">hotel</i>
                <a href="${pageContext.request.contextPath}/admin/hoteli.jsp"><p>Hoteli</p></a>
              </div>

              <div class="panel-option">
                <i class="material-icons">room</i>
                <a href="${pageContext.request.contextPath}/admin/sobe.jsp"><p>Sobe</p></a>
              </div>
              
              <div class="panel-option active">
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
  <form class='slideUpForm' method="POST" name="newrt" action="${pageContext.request.contextPath}/KreirajTipSobe" enctype="multipart/form-data">
    <span class='close'>X</span>
    <center><i class="fa fa-bed" aria-hidden="true"></i></center>
    <label>Hotel</label><select name="rthotel">
                <option selected="" class="opt"></option>
                <%
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmt = con.createStatement();
                    String upit = "select * from hoteli";
                    rs = stmt.executeQuery(upit);
                %>

                    <%  while(rs.next())
                    { %>
                    <option class="opt" value="<%=rs.getInt("ID")%>"><%=rs.getString("naziv")%></option>
                    <%} %>

                    <%
                     rs.close();
                     stmt.close();
                     con.close();
                 }
            catch(Exception e){}

        %>
    </select>
    <label>Tip sobe</label><input name="rtname" required type='text'>
    <label>Odrasli</label><select name="rtadults">
                <option class="opt" value="1">1</option>
                <option class="opt" value="2">2</option>
                <option class="opt" value="3">3</option>
                <option class="opt" value="4">4</option>
                <option class="opt" value="5">5</option>
    </select>
    <label>Deca</label><select name="rtkids">
                <option class="opt" value="1">1</option>
                <option class="opt" value="2">2</option>
                <option class="opt" value="3">3</option>
                <option class="opt" value="4">4</option>
    </select>
    <label>Opis</label><textarea name="rtdescription" style="resize:none;"></textarea>
    <label>Paket 1</label><input name="rtmember" required type='text'>
    <label>Paket 2</label><input name="rtstandard" required type='text'>
    <label>Paket 3</label><input name="rtbb" required type='text'>
    <label>Paket 4</label><input name="rtdpoints" required type='text'>
    <label>Paket 5</label><input name="rtepackage" required type='text'>
    <label>Starling poeni</label><input name="rtsp" required type='text'>
    <label>Slika</label><input type="file" name="photo" size="50"/>
    <center><input type='submit' onclick="return formValidation1()" value='Kreiraj tip sobe'><center>
  </form>
</div>
    <br><br>
  <table class="tables">
    <thead>
      <tr>
        <th>ID</th>
        <th>Naziv</th>
        <th>Hotel - ID</th>
        <th>Opis</th>
        <th>Odrasli</th>
        <th>Deca</th>
        <th>Slika</th>
        <th>Paket 1</th>
        <th>Paket 2</th>
        <th>Paket 3</th>
        <th>Paket 4</th>
        <th>Paket 5</th>
        <th>Starling poeni</th>
        <th><i class="fa fa-edit" aria-hidden="true"></i></center</th>
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
                <%
                    Connection conne = null;
                    Statement stmts = null;
                    ResultSet rss = null;
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conne = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmts = conne.createStatement();
                    String upits = "select * from hoteli where ID='"+rsPagination.getInt("hotelID")+"'";
                    rss = stmts.executeQuery(upits);
                %>
                
                <%
                    if(rss.next())
                    {
                %>
                <td><%= rss.getString("naziv")%> - (ID:<%= rss.getInt("ID")%>)</td>
                <%
                    }
                %>
                <%
                    rss.close();
                    stmts.close();
                    conne.close();
                }
                    catch(Exception e){}

                %>
                <td><textarea style="width: 100%; height: 100%; resize: none; background-color: transparent; border: none; overflow: none; " disabled><%= rsPagination.getString("opis")%></textarea></td >
                <td><%= rsPagination.getInt("odrasli")%></td>
                <td><%= rsPagination.getInt("deca")%></td>
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
                <td><%= rsPagination.getInt("paket1")%>$</td>
                <td><%= rsPagination.getInt("paket2")%>$</td>
                <td><%= rsPagination.getInt("paket3")%>$</td>
                <td><%= rsPagination.getInt("paket4")%>$</td>
                <td><%= rsPagination.getInt("paket5")%>$</td>
                <td style="color:green;"><%= rsPagination.getInt("starling_poeni")%></td>
                <td><a class="button green" href="${pageContext.request.contextPath}/IzmeniTipSobe?id=<%=rsPagination.getInt("ID")%>">IZMENI</a></td>
                <td><a class="button red" href="${pageContext.request.contextPath}/ObrisiTipSobe?id=<%=rsPagination.getInt("ID")%>">OBRISI</a></td>
                <td><a class="showForm button blue" href="#">KREIRAJ</a></td>
            </tr>
            
             <%}
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    %>
            <%
  //// calculate next record start record  and end record 
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
        //// index of pages 
        
        int i=0;
        int cPage=0;
        if(iTotalRows!=0)
        {
        cPage=((int)(Math.ceil((double)iEndResultNo/(iTotalSearchRecords*iShowRows))));
        
        int prePageNo=(cPage*iTotalSearchRecords)-((iTotalSearchRecords-1)+iTotalSearchRecords);
        if((cPage*iTotalSearchRecords)-(iTotalSearchRecords)>0)
        {
         %>
          <a class='page gradient' href="${pageContext.request.contextPath}/admin/tipovisoba.jsp?iPageNo=<%=prePageNo%>&cPageNo=<%=prePageNo%>"> << Previous</a>
         <%
        }
        
        for(i=((cPage*iTotalSearchRecords)-(iTotalSearchRecords-1));i<=(cPage*iTotalSearchRecords);i++)
        {
          if(i==((iPageNo/iShowRows)+1))
          {
          %>
           <a class='page gradient' href="${pageContext.request.contextPath}/admin/tipovisoba.jsp?iPageNo=<%=i%>" style="color: #59d"><b><%=i%></b></a>
          <%
          }
          else if(i<=iTotalPages)
          {
          %>
           <a class='page gradient' href="${pageContext.request.contextPath}/admin/tipovisoba.jsp?iPageNo=<%=i%>"><%=i%></a>
          <% 
          }
        }
        if(iTotalPages>iTotalSearchRecords && i<iTotalPages)
        {
         %>
         <a class='page active' href="${pageContext.request.contextPath}/admin/tipovisoba.jsp?iPageNo=<%=i%>&cPageNo=<%=i%>"> >> Next</a> 
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
