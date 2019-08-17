 package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;
import javax.swing.JOptionPane;
public class SobaIzmenjena extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession sesija = request.getSession();
        String poruka = "";
        int popupid;
        
        String id = (String)request.getParameter("id");
        String hotelID = (String)request.getParameter("hotelID");
        String number = (String)request.getParameter("number");
        String number1 = (String)request.getParameter("number1");
        String type = (String)request.getParameter("rtype");
        String status = (String)request.getParameter("rstatus");
        
        Connection con = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rzlt = null;
        Klijent user = (Klijent)sesija.getAttribute("user");
        if(request.getSession().getAttribute("user") != null && user.getPower().equals("Admin"))
        {
            if(number.isEmpty())
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Niste popunili sva polja!");
                RequestDispatcher rd = request.getRequestDispatcher("admin/sobe.jsp");
                rd.forward(request, response);
            }
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");

                    stmt = con.createStatement();
                    String upit = "select * from sobe where broj='"+number+"' and hotelID='"+hotelID+"'";
                    rzlt = stmt.executeQuery(upit);
                    if(rzlt.first() && number.equals(number1))
                    {
                        if(status.equals("Zauzeta"))
                        {
                            String sql = "update sobe set broj=IF(TRIM('"+number+"') != '', '"+number+"', `broj`), tipsobeID=IF(TRIM('"+type+"') != '', '"+type+"', `tipsobeID`), status=IF(TRIM('"+status+"') != '', '"+status+"', `status`) where ID='"+id+"' and hotelID='"+hotelID+"'";
                            pstmt = con.prepareStatement(sql); 
                            pstmt.executeUpdate();
                            request.setAttribute("popupid", 3);
                            request.setAttribute("poruka", "Uspešno ste izmenili hotelsku sobu.");
                            RequestDispatcher rd = request.getRequestDispatcher("admin/sobe.jsp");
                            rd.forward(request, response);
                        }
                        else
                        {
                            String sql = "update sobe set klijentID='-1', broj=IF(TRIM('"+number+"') != '', '"+number+"', `broj`), tipsobeID=IF(TRIM('"+type+"') != '', '"+type+"', `tipsobeID`),datum1='Nema', datum2='Nema', vreme1='Nema', vreme2='Nema', status=IF(TRIM('"+status+"') != '', '"+status+"', `status`) where ID='"+id+"' and hotelID='"+hotelID+"'";
                            pstmt = con.prepareStatement(sql); 
                            pstmt.executeUpdate();
                            request.setAttribute("popupid", 3);
                            request.setAttribute("poruka", "Uspešno ste izmenili hotelsku sobu.");
                            RequestDispatcher rd = request.getRequestDispatcher("admin/sobe.jsp");
                            rd.forward(request, response);
                        }
                    }
                    else if(rzlt.first() && !number.equals(number1))
                    {
                        request.setAttribute("popupid", 2);
                        request.setAttribute("poruka", "U ovom hotelu već postoji hotelska soba sa tim brojem.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/sobe.jsp");
                        rd.forward(request, response);
                    } 
                    else if(!rzlt.first())
                    {
                        if(status.equals("Zauzeta"))
                        {
                            String sql = "update sobe set broj=IF(TRIM('"+number+"') != '', '"+number+"', `broj`), tipsobeID=IF(TRIM('"+type+"') != '', '"+type+"', `tipsobeID`), status=IF(TRIM('"+status+"') != '', '"+status+"', `status`) where ID='"+id+"' and hotelID='"+hotelID+"'";
                            pstmt = con.prepareStatement(sql); 
                            pstmt.executeUpdate();
                            request.setAttribute("popupid", 3);
                            request.setAttribute("poruka", "Uspešno ste izmenili hotelsku sobu.");
                            RequestDispatcher rd = request.getRequestDispatcher("admin/sobe.jsp");
                            rd.forward(request, response);
                        }
                        else
                        {
                            String sql = "update sobe set klijentID='-1', broj=IF(TRIM('"+number+"') != '', '"+number+"', `broj`), tipsobeID=IF(TRIM('"+type+"') != '', '"+type+"', `tipsobeID`),datum1='Nema', datum2='Nema', vreme1='Nema', vreme2='Nema', status=IF(TRIM('"+status+"') != '', '"+status+"', `status`) where ID='"+id+"' and hotelID='"+hotelID+"'";
                            pstmt = con.prepareStatement(sql); 
                            pstmt.executeUpdate();
                            request.setAttribute("popupid", 3);
                            request.setAttribute("poruka", "Uspešno ste izmenili hotelsku sobu.");
                            RequestDispatcher rd = request.getRequestDispatcher("admin/sobe.jsp");
                            rd.forward(request, response);
                        }
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
                if(number.isEmpty())
                {
                    request.setAttribute("popupid", 2);
                    request.setAttribute("poruka", "Niste popunili sva polja!");
                    RequestDispatcher rd = request.getRequestDispatcher("menadzer/sobe.jsp");
                    rd.forward(request, response);
                }
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");

                    stmt = con.createStatement();
                    String upit = "select * from sobe where broj='"+number+"' and hotelID='"+user.getHotelID()+"'";
                    rzlt = stmt.executeQuery(upit);
                    if(rzlt.first() && number.equals(number1))
                    {
                        if(status.equals("Zauzeta"))
                        {
                            String sql = "update sobe set broj=IF(TRIM('"+number+"') != '', '"+number+"', `broj`), tipsobeID=IF(TRIM('"+type+"') != '', '"+type+"', `tipsobeID`), status=IF(TRIM('"+status+"') != '', '"+status+"', `status`) where ID='"+id+"' and hotelID='"+user.getHotelID()+"'";
                            pstmt = con.prepareStatement(sql); 
                            pstmt.executeUpdate();
                            request.setAttribute("popupid", 3);
                            request.setAttribute("poruka", "Uspešno ste izmenili hotelsku sobu.");
                            RequestDispatcher rd = request.getRequestDispatcher("menadzer/sobe.jsp");
                            rd.forward(request, response);
                        }
                        else
                        {
                            String sql = "update sobe set klijentID='-1', broj=IF(TRIM('"+number+"') != '', '"+number+"', `broj`), tipsobeID=IF(TRIM('"+type+"') != '', '"+type+"', `tipsobeID`),datum1='Nema', datum2='Nema', vreme1='Nema', vreme2='Nema', status=IF(TRIM('"+status+"') != '', '"+status+"', `status`) where ID='"+id+"' and hotelID='"+user.getHotelID()+"'";
                            pstmt = con.prepareStatement(sql); 
                            pstmt.executeUpdate();
                            request.setAttribute("popupid", 3);
                            request.setAttribute("poruka", "Uspešno ste izmenili hotelsku sobu.");
                            RequestDispatcher rd = request.getRequestDispatcher("menadzer/sobe.jsp");
                            rd.forward(request, response);
                        }
                    }
                    else if(rzlt.first() && !number.equals(number1))
                    {
                        request.setAttribute("popupid", 2);
                        request.setAttribute("poruka", "U ovom hotelu već postoji hotelska soba sa tim brojem.");
                        RequestDispatcher rd = request.getRequestDispatcher("menadzer/sobe.jsp");
                        rd.forward(request, response);
                    } 
                    else if(!rzlt.first())
                    {
                        if(status.equals("Zauzeta"))
                        {
                            String sql = "update sobe set broj=IF(TRIM('"+number+"') != '', '"+number+"', `broj`), tipsobeID=IF(TRIM('"+type+"') != '', '"+type+"', `tipsobeID`), status=IF(TRIM('"+status+"') != '', '"+status+"', `status`) where ID='"+id+"' and hotelID='"+user.getHotelID()+"'";
                            pstmt = con.prepareStatement(sql); 
                            pstmt.executeUpdate();
                            request.setAttribute("popupid", 3);
                            request.setAttribute("poruka", "Uspešno ste izmenili hotelsku sobu.");
                            RequestDispatcher rd = request.getRequestDispatcher("menadzer/sobe.jsp");
                            rd.forward(request, response);
                        }
                        else
                        {
                            String sql = "update sobe set klijentID='-1', broj=IF(TRIM('"+number+"') != '', '"+number+"', `broj`), tipsobeID=IF(TRIM('"+type+"') != '', '"+type+"', `tipsobeID`),datum1='Nema', datum2='Nema', vreme1='Nema', vreme2='Nema', status=IF(TRIM('"+status+"') != '', '"+status+"', `status`) where ID='"+id+"' and hotelID='"+user.getHotelID()+"'";
                            pstmt = con.prepareStatement(sql); 
                            pstmt.executeUpdate();
                            request.setAttribute("popupid", 3);
                            request.setAttribute("poruka", "Uspešno ste izmenili hotelsku sobu.");
                            RequestDispatcher rd = request.getRequestDispatcher("menadzer/sobe.jsp");
                            rd.forward(request, response);
                        }
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
                request.setAttribute("poruka", "Niste ulogovani ili niste administrator/menažer hotela.");
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
