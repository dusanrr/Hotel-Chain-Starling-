package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

public class OtkaziRezervaciju extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession sesija = request.getSession();
        String poruka = "";
        String reservationID = (String)request.getParameter("id");
           
        Connection con = null;
        Statement stmt = null;
        PreparedStatement pstm = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            Klijent user = (Klijent)sesija.getAttribute("user");
            
            Statement stmts = null;
            ResultSet rss = null;
            try
            {
                stmts = con.createStatement();
                String upit = "select * from rezervacije where ID='"+reservationID+"'";
                rss = stmts.executeQuery(upit);

                if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
                {
                    if(rss.first())
                    {
                        if(rss.getInt("soba1") != -1 && rss.getInt("soba2") != -1)
                        {
                            String sql = "update sobe set klijentID='-1', datum1='Nema', datum2='Nema', vreme1='Nema', vreme2='Nema', status='Prazna' where ID='"+rss.getInt("soba1")+"' and hotelID='"+rss.getInt("hotelID")+"' and klijentID='"+user.getId()+"'";
                            pstm = con.prepareStatement(sql); 
                            pstm.executeUpdate();


                            String sql2 = "update sobe set klijentID='-1', datum1='Nema', datum2='Nema', vreme1='Nema', vreme2='Nema', status='Prazna' where ID='"+rss.getInt("soba2")+"' and hotelID='"+rss.getInt("hotelID")+"' and klijentID='"+user.getId()+"'";
                            pstm = con.prepareStatement(sql2); 
                            pstm.executeUpdate();
                            
                            if(rss.getInt("poeni") > 0)
                            {
                                String sql3 = "update klijenti set poeni=poeni+"+rss.getInt("poeni")+" where ID='"+user.getId()+"'";
                                pstm = con.prepareStatement(sql3); 
                                pstm.executeUpdate();
                            }
                        }
                        else if(rss.getInt("soba1") != -1 && rss.getInt("soba2") == -1)
                        {
                            String sql = "update sobe set klijentID='-1', datum1='Nema', datum2='Nema', vreme1='Nema', vreme2='Nema', status='Prazna' where  ID='"+rss.getInt("soba1")+"' and hotelID='"+rss.getInt("hotelID")+"' and klijentID='"+user.getId()+"'";
                            pstm = con.prepareStatement(sql); 
                            pstm.executeUpdate();
                            
                            if(rss.getInt("poeni") > 0)
                            {
                                String sql3 = "update klijenti set poeni=poeni+"+rss.getInt("poeni")+" where ID='"+user.getId()+"'";
                                pstm = con.prepareStatement(sql3); 
                                pstm.executeUpdate();
                            }
                        }
                        stmt = con.createStatement();
                        stmt.executeUpdate("delete from rezervacije where ID='"+reservationID+"'");

                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste otkazali rezervaciju.");
                        RequestDispatcher rd = request.getRequestDispatcher("klijent/rezervacije.jsp");
                        rd.forward(request, response);
                    }
                }
                else
                {
                    request.setAttribute("popupid", 2);
                    request.setAttribute("poruka", "Niste ulogovani.");
                    RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                    rd.forward(request, response);

                }
                rss.close();
                pstm.close();
                stmts.close();
                stmt.close();
                con.close();
            }
            catch(CommunicationsException ie)
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške! (connection lost)");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);
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
            catch(Exception e)
            {
                e.printStackTrace();
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Došlo je do greške. ("+e.getMessage()+"");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);  
            }
        }
        catch(CommunicationsException ie)
        {
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Došlo je do greške! (connection lost)");
            RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
            rd.forward(request, response);
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
        catch(ClassNotFoundException cnf) 
        {
            cnf.printStackTrace();
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Došlo je do greške. ("+cnf.getMessage()+"");
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
