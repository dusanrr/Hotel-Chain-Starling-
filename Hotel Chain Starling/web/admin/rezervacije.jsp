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
    
    String sqlPagination="select SQL_CALC_FOUND_ROWS * from `rezervacije` R inner join `tipovisoba` RT inner join `hoteli` H inner join `klijenti` C where C.ID=R.klijentID and H.ID=R.hotelID and RT.ID=R.tipsobeID limit "+iPageNo+","+iShowRows+"";

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
            //User user = (User)sesija.getAttribute("user");
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
              
              <div class="panel-option">
                <i class="material-icons">room</i>
                <a href="${pageContext.request.contextPath}/admin/tipovisoba.jsp"><p>Tipovi soba</p></a>
              </div>

              <div class="panel-option active">
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
      <br><br>
  <table class="tables">
    <thead>
      <tr>
        <th>ID</th>
        <th>Hotel - ID</th>
        <th>Tip sobe - ID</th>
        <th>Ime i prezime klijenta - ID</th>
        <th>Datum dolaska</th>
        <th>Datum odlaska</th>
        <th>Vreme odlaska</th>
        <th>Sobe</th>
        <th>Odrasli</th>
        <th>Deca</th>
        <th>Novac</th>
        <th>Poeni</th>
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
                <td><%=rsPagination.getInt(1)%></td>
                <td><%=rsPagination.getString(29)%> - (ID:<%=rsPagination.getInt(28)%>)</td>
                <td><%=rsPagination.getString(17)%> - (ID:<%=rsPagination.getInt(15)%>)</td>
                <td><%=rsPagination.getString(37)%> - (ID:<%=rsPagination.getInt(36)%>)</td>
                <td><%=rsPagination.getString(5)%></td>
                <td><%=rsPagination.getString(6)%></td>
                <td><%=rsPagination.getString(7)%></td>
                <td><%=rsPagination.getInt(8)%></td>
                <td><%=rsPagination.getInt(9)%></td>
                <td><%=rsPagination.getInt(10)%></td>
                 <%
              if(rsPagination.getInt(11) == -1)
              {%>
                  <td class="center">Plaćeno poenima</td>
              <%}
              else
              {%>
                  <td class="center"><%= rsPagination.getInt(11) %></td>
              <%}
              %>
          <%
              if(rsPagination.getInt(12) == -1)
              {%>
                  <td class="center">Plaćeno novcem</td>
              <%}
              else
              {%>
                  <td class="center"><%= rsPagination.getInt(12) %></td>
              <%}
              %>
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
          <a class='page gradient' href="${pageContext.request.contextPath}/admin/rezervacije.jsp?iPageNo=<%=prePageNo%>&cPageNo=<%=prePageNo%>"> << Previous</a>
         <%
        }
        
        for(i=((cPage*iTotalSearchRecords)-(iTotalSearchRecords-1));i<=(cPage*iTotalSearchRecords);i++)
        {
          if(i==((iPageNo/iShowRows)+1))
          {
          %>
           <a class='page gradient' href="${pageContext.request.contextPath}/admin/rezervacije.jsp?iPageNo=<%=i%>" style="color: #59d"><b><%=i%></b></a>
          <%
          }
          else if(i<=iTotalPages)
          {
          %>
           <a class='page gradient' href="${pageContext.request.contextPath}/admin/rezervacije.jsp?iPageNo=<%=i%>"><%=i%></a>
          <% 
          }
        }
        if(iTotalPages>iTotalSearchRecords && i<iTotalPages)
        {
         %>
         <a class='page active' href="${pageContext.request.contextPath}/admin/rezervacije.jsp?iPageNo=<%=i%>&cPageNo=<%=i%>"> >> Next</a> 
         <%
        }
        }
      %>
	</div>
    </div>
  </section>          
</body>
</html>
