package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import beans.*;

@MultipartConfig(maxFileSize = 16177215) 
public class SobaKreirana extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession sesija = request.getSession();
        Klijent user = (Klijent)sesija.getAttribute("user");
        String poruka = "";
        int popupid;
        String rnmbr = (String)request.getParameter("rnmbr");
        String hotelID = (String)request.getParameter("id");
        String rtype = (String)request.getParameter("rtype");
        if(request.getSession().getAttribute("user") != null && user.getPower().equals("Admin"))
        {
            if(rtype.isEmpty() || rnmbr.isEmpty())
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Niste popunili sva polja!");
                RequestDispatcher rd = request.getRequestDispatcher("admin/sobe.jsp");
                rd.forward(request, response);
            }
            Connection con = null;
            PreparedStatement pstmt = null;

            Statement stmt = null;
            ResultSet rzlt = null;
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                try
                {   

                    stmt = con.createStatement();
                    String upit = "select * from sobe where broj='"+rnmbr+"' and hotelID='"+hotelID+"'";
                    rzlt = stmt.executeQuery(upit);

                    if(rzlt.first())
                    {
                        request.setAttribute("popupid", 2);
                        request.setAttribute("poruka", "Hotelska soba sa tim brojem vec postoji u ovom hotelu!");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/sobe.jsp");
                        rd.forward(request, response);  
                    }
                    else
                    {
                        pstmt = con.prepareStatement("insert into sobe(hotelID, tipsobeID, broj) values(?,?,?)");

                        pstmt.setInt(1, Integer.parseInt(hotelID));
                        pstmt.setInt(2, Integer.parseInt(rtype));
                        pstmt.setInt(3, Integer.parseInt(rnmbr));

                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste kreirali hotelsku sobu.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/sobe.jsp");
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
                if(rtype.isEmpty() || rnmbr.isEmpty())
                {
                    request.setAttribute("popupid", 2);
                    request.setAttribute("poruka", "Niste popunili sva polja!");
                    RequestDispatcher rd = request.getRequestDispatcher("menadzer/sobe.jsp");
                    rd.forward(request, response);
                }

                if(request.getSession().getAttribute("user") != null && user.getPower().equals("Menadzer hotela"))
                {
                    Connection con = null;
                    PreparedStatement pstmt = null;

                    Statement stmt = null;
                    ResultSet rzlt = null;
                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                        try
                        {   

                            stmt = con.createStatement();
                            String upit = "select * from sobe where broj='"+rnmbr+"' and hotelID='"+user.getHotelID()+"'";
                            rzlt = stmt.executeQuery(upit);

                            if(rzlt.first())
                            {
                                request.setAttribute("popupid", 2);
                                request.setAttribute("poruka", "Hotelska soba sa tim brojem vec postoji u ovom hotelu!");
                                RequestDispatcher rd = request.getRequestDispatcher("menadzer/sobe.jsp");
                                rd.forward(request, response);  
                            }
                            else
                            {
                                pstmt = con.prepareStatement("insert into sobe(hotelID, tipsobeID, broj, status) values(?,?,?,?)");

                                pstmt.setInt(1, user.getHotelID());
                                pstmt.setInt(2, Integer.parseInt(rtype));
                                pstmt.setInt(3, Integer.parseInt(rnmbr));
                                pstmt.setString(4, "Prazna");

                                pstmt.executeUpdate();
                                request.setAttribute("popupid", 3);
                                request.setAttribute("poruka", "Uspešno ste kreirali novu hotelsku sobu.");
                                RequestDispatcher rd = request.getRequestDispatcher("menadzer/sobe.jsp");
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
                    request.setAttribute("poruka", "Niste ulogovani ili niste menadzer hotela.");
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
