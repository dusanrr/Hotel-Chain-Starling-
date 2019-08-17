package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import beans.*;

@MultipartConfig(maxFileSize = 16177215) 
public class TipSobeIzmenjen extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession sesija = request.getSession();
        String poruka = "";
        int popupid;
        
        String id = (String)request.getParameter("id");
        String IDhotel = (String)request.getParameter("IDhotel");
        String hotelID = (String)request.getParameter("hotelID");
        String name = (String)request.getParameter("rtname");   
        String name1 = (String)request.getParameter("rtname1");
        String rtadults = (String)request.getParameter("rtadults");
        String rtkids = (String)request.getParameter("rtkids");
        String description = (String)request.getParameter("rtdesc");
        String rtmember = (String)request.getParameter("rtmember");
        String rtstandard = (String)request.getParameter("rtstandard");
        String rtbb = (String)request.getParameter("rtbb");
        String rtdpoints = (String)request.getParameter("rtdpoints");
        String rtepackage = (String)request.getParameter("rtepackage");
        String rtsp = (String)request.getParameter("rtsp");
        
        InputStream image = null;
         
        Part filePart = request.getPart("photo");
        if (filePart != null) {
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            image = filePart.getInputStream();
        }
        Connection con = null;
        PreparedStatement pstmt = null;
        Klijent user = (Klijent)sesija.getAttribute("user");
        if(request.getSession().getAttribute("user") != null && user.getPower().equals("Admin"))
        {
            if(name.isEmpty() || rtadults.isEmpty() || rtkids.isEmpty() || description.isEmpty() || rtmember.isEmpty() || rtstandard.isEmpty() || rtbb.isEmpty() || rtdpoints.isEmpty() || rtepackage.isEmpty() || rtsp.isEmpty())
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Niste popunili sva polja!");
                RequestDispatcher rd = request.getRequestDispatcher("admin/tipovisoba.jsp");
                rd.forward(request, response);
            }
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                if(filePart.getSize() > 0)
                {
                    String kuku = "update tipovisoba set slika=null where ID='"+id+"'";
                    pstmt = con.prepareStatement(kuku);
                    pstmt.executeUpdate();

                    pstmt.close();

                    String novi = "update tipovisoba set slika=? where ID=?";
                    pstmt = con.prepareStatement(novi);
                    pstmt.setBlob(1, image);
                    pstmt.setString(2, id);
                    pstmt.executeUpdate();

                    pstmt.close();
                    con.close();
                }
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                sesija.invalidate();
                if(con!=null)
                    try
                    {
                        con.close();
                    }
                    catch(Exception exc)
                    {
                        poruka = poruka+exc.getMessage();
                    }
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! ("+ex.getMessage()+")");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);
            }
            catch(NullPointerException npe)
            {
                npe.printStackTrace();
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! ("+npe.getMessage()+")");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);  
            }
            catch(Exception e)
            {
                e.printStackTrace();
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! ("+e.getMessage()+")");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);  
            }  
            Statement stmt = null;
            ResultSet rzlt = null;
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");

                    stmt = con.createStatement();
                    String upit = "select * from tipovisoba where naziv='"+name+"' and hotelID='"+IDhotel+"'";
                    rzlt = stmt.executeQuery(upit);

                    if(rzlt.first() && name.equals(name1))
                    {
                        String sql = "update tipovisoba set naziv=IF(TRIM('"+name+"') != '', '"+name+"', `naziv`), opis=IF(TRIM('"+description+"') != '', '"+description+"', `opis`), odrasli=IF(TRIM('"+rtadults+"') != '', '"+rtadults+"', `odrasli`), deca=IF(TRIM('"+rtkids+"') != '', '"+rtkids+"', `deca`), paket1=IF(TRIM('"+rtmember+"') != '', '"+rtmember+"', `paket1`), paket2=IF(TRIM('"+rtstandard+"') != '', '"+rtstandard+"', `paket2`), paket3=IF(TRIM('"+rtbb+"') != '', '"+rtbb+"', `paket3`), paket4=IF(TRIM('"+rtdpoints+"') != '', '"+rtdpoints+"', `paket4`), paket5=IF(TRIM('"+rtepackage+"') != '', '"+rtepackage+"', `paket5`), starling_poeni=IF(TRIM('"+rtsp+"') != '', '"+rtsp+"', `starling_poeni`) where ID='"+id+"' and hotelID='"+IDhotel+"'";
                        pstmt = con.prepareStatement(sql); 
                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste izmenili tip hotelske sobe.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/tipovisoba.jsp");
                        rd.forward(request, response);
                    }
                    else if(rzlt.first() && !name.equals(name1))
                    {
                        request.setAttribute("popupid", 2);
                        request.setAttribute("poruka", "U ovom hotelu već postoji tip hotelske sobe sa tim nazivom.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/tipovisoba.jsp");
                        rd.forward(request, response);
                    } 
                    else if(!rzlt.first())
                    {
                        String sql = "update tipovisoba set naziv=IF(TRIM('"+name+"') != '', '"+name+"', `naziv`), opis=IF(TRIM('"+description+"') != '', '"+description+"', `opis`), odrasli=IF(TRIM('"+rtadults+"') != '', '"+rtadults+"', `odrasli`), deca=IF(TRIM('"+rtkids+"') != '', '"+rtkids+"', `deca`), paket1=IF(TRIM('"+rtmember+"') != '', '"+rtmember+"', `paket1`), paket2=IF(TRIM('"+rtstandard+"') != '', '"+rtstandard+"', `paket2`), paket3=IF(TRIM('"+rtbb+"') != '', '"+rtbb+"', `paket3`), paket4=IF(TRIM('"+rtdpoints+"') != '', '"+rtdpoints+"', `paket4`), paket5=IF(TRIM('"+rtepackage+"') != '', '"+rtepackage+"', `paket5`), starling_poeni=IF(TRIM('"+rtsp+"') != '', '"+rtsp+"', `starling_poeni`) where ID='"+id+"' and hotelID='"+IDhotel+"'";
                        pstmt = con.prepareStatement(sql); 
                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste izmenili tip hotelske sobe.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/tipovisoba.jsp");
                        rd.forward(request, response);
                    }
                rzlt.close();
                stmt.close();
                pstmt.close();
                con.close();
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                sesija.invalidate();
                if(con!=null)
                    try
                    {
                        con.close();
                    }
                    catch(Exception exc)
                    {
                        poruka = poruka+exc.getMessage();
                    }
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! ("+ex.getMessage()+")");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);
            }
            catch(NullPointerException npe)
            {
                npe.printStackTrace();
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! ("+npe.getMessage()+")");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);  
            }
            catch(Exception e)
            {
                e.printStackTrace();
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! ("+e.getMessage()+")");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);  
            } 
        }
        else if(request.getSession().getAttribute("user") != null && user.getPower().equals("Menadzer hotela"))
        {
            if(name.isEmpty() || rtadults.isEmpty() || rtkids.isEmpty() || description.isEmpty() || rtmember.isEmpty() || rtstandard.isEmpty() || rtbb.isEmpty() || rtdpoints.isEmpty() || rtepackage.isEmpty() || rtsp.isEmpty())
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Niste popunili sva polja!");
                RequestDispatcher rd = request.getRequestDispatcher("menadzer/tipovisoba.jsp");
                rd.forward(request, response);
            }
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                if(filePart.getSize() > 0)
                {
                    String kuku = "update tipovisoba set slika=null where ID='"+id+"'";
                    pstmt = con.prepareStatement(kuku);
                    pstmt.executeUpdate();

                    pstmt.close();

                    String novi = "update tipovisoba set slika=? where ID=?";
                    pstmt = con.prepareStatement(novi);
                    pstmt.setBlob(1, image);
                    pstmt.setString(2, id);
                    pstmt.executeUpdate();

                    pstmt.close();
                    con.close();
                }
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                sesija.invalidate();
                if(con!=null)
                    try
                    {
                        con.close();
                    }
                    catch(Exception exc)
                    {
                        poruka = poruka+exc.getMessage();
                    }
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! ("+ex.getMessage()+")");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);
            }
            catch(NullPointerException npe)
            {
                npe.printStackTrace();
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! ("+npe.getMessage()+")");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);  
            }
            catch(Exception e)
            {
                e.printStackTrace();
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! ("+e.getMessage()+")");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);  
            }  
            Statement stmt = null;
            ResultSet rzlt = null;
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");

                    stmt = con.createStatement();
                    String upit = "select * from tipovisoba where naziv='"+name+"' and hotelID='"+user.getHotelID()+"'";
                    rzlt = stmt.executeQuery(upit);

                    if(rzlt.first() && name.equals(name1))
                    {
                        String sql = "update tipovisoba set naziv=IF(TRIM('"+name+"') != '', '"+name+"', `naziv`), opis=IF(TRIM('"+description+"') != '', '"+description+"', `opis`), odrasli=IF(TRIM('"+rtadults+"') != '', '"+rtadults+"', `odrasli`), deca=IF(TRIM('"+rtkids+"') != '', '"+rtkids+"', `deca`), paket1=IF(TRIM('"+rtmember+"') != '', '"+rtmember+"', `paket1`), paket2=IF(TRIM('"+rtstandard+"') != '', '"+rtstandard+"', `paket2`), paket3=IF(TRIM('"+rtbb+"') != '', '"+rtbb+"', `paket3`), paket4=IF(TRIM('"+rtdpoints+"') != '', '"+rtdpoints+"', `paket4`), paket5=IF(TRIM('"+rtepackage+"') != '', '"+rtepackage+"', `paket5`), starling_poeni=IF(TRIM('"+rtsp+"') != '', '"+rtsp+"', `starling_poeni`) where ID='"+id+"' and hotelID='"+user.getHotelID()+"'";
                        pstmt = con.prepareStatement(sql); 
                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste izmenili tip hotelske sobe.");
                        RequestDispatcher rd = request.getRequestDispatcher("menadzer/tipovisoba.jsp");
                        rd.forward(request, response);
                    }
                    else if(rzlt.first() && !name.equals(name1))
                    {
                        request.setAttribute("popupid", 2);
                        request.setAttribute("poruka", "U ovom hotelu već postoji tip hotelske sobe sa tim nazivom.");
                        RequestDispatcher rd = request.getRequestDispatcher("menadzer/tipovisoba.jsp");
                        rd.forward(request, response);
                    } 
                    else if(!rzlt.first())
                    {
                        String sql = "update tipovisoba set naziv=IF(TRIM('"+name+"') != '', '"+name+"', `naziv`), opis=IF(TRIM('"+description+"') != '', '"+description+"', `opis`), odrasli=IF(TRIM('"+rtadults+"') != '', '"+rtadults+"', `odrasli`), deca=IF(TRIM('"+rtkids+"') != '', '"+rtkids+"', `deca`), paket1=IF(TRIM('"+rtmember+"') != '', '"+rtmember+"', `paket1`), paket2=IF(TRIM('"+rtstandard+"') != '', '"+rtstandard+"', `paket2`), paket3=IF(TRIM('"+rtbb+"') != '', '"+rtbb+"', `paket3`), paket4=IF(TRIM('"+rtdpoints+"') != '', '"+rtdpoints+"', `paket4`), paket5=IF(TRIM('"+rtepackage+"') != '', '"+rtepackage+"', `paket5`), starling_poeni=IF(TRIM('"+rtsp+"') != '', '"+rtsp+"', `starling_poeni`) where ID='"+id+"' and hotelID='"+user.getHotelID()+"'";
                        pstmt = con.prepareStatement(sql); 
                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste izmenili tip hotelske sobe.");
                        RequestDispatcher rd = request.getRequestDispatcher("menadzer/tipovisoba.jsp");
                        rd.forward(request, response);
                    }
                rzlt.close();
                stmt.close();
                pstmt.close();
                con.close();
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                sesija.invalidate();
                if(con!=null)
                    try
                    {
                        con.close();
                    }
                    catch(Exception exc)
                    {
                        poruka = poruka+exc.getMessage();
                    }
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! ("+ex.getMessage()+")");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);
            }
            catch(NullPointerException npe)
            {
                npe.printStackTrace();
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! ("+npe.getMessage()+")");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);  
            }
            catch(Exception e)
            {
                e.printStackTrace();
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! ("+e.getMessage()+")");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);  
            } 
        }
        else
        {
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Niste ulogovani ili niste administrator/menadžer hotela.");
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
