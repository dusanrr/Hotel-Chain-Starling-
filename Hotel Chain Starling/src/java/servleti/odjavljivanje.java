package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.util.ArrayList;

public class odjavljivanje extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesija = request.getSession();  
        Klijent user = (Klijent)sesija.getAttribute("user");
        
        String poruka = "";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            String upit = "select * from klijenti where ID='"+user.getId()+"'";
            rs = stmt.executeQuery(upit);
            if(rs.next())
            {
                sesija.invalidate();  
                request.setAttribute("popupid", 3);
                request.setAttribute("poruka", "Uspešno ste se izlogovali!");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);
            }
            else
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Niste ulogovani!");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                sesija.invalidate();
                rd.forward(request, response); 
            }
            rs.close();
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
            sesija.invalidate();
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
            sesija.invalidate();
            npe.printStackTrace();
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Došlo je do greške! ("+npe.getMessage()+")");
            RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
            rd.forward(request, response);  
        }
        catch(Exception e)
        {
            sesija.invalidate();
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
