package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

public class ObrisiHotel extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession sesija = request.getSession();
        String poruka = "";
        int popupid;
        String hotelID = (String)request.getParameter("id");
           
        Connection con = null;
        Statement stmt = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            Klijent user = (Klijent)sesija.getAttribute("user");
            if(request.getSession().getAttribute("user") != null && user.getPower().equals("Admin"))
            {
                stmt = con.createStatement();
                stmt.executeUpdate("delete from hoteli where ID='"+hotelID+"'");
                request.setAttribute("popupid", 3);
                request.setAttribute("poruka", "Uspešno ste obrisali hotel.");
                RequestDispatcher rd = request.getRequestDispatcher("admin/hoteli.jsp");
                rd.forward(request, response);
            }
            else
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Niste ulogovani ili niste administrator.");
                RequestDispatcher rd = request.getRequestDispatcher("admin/hoteli.jsp");
                rd.forward(request, response);               
            }
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
