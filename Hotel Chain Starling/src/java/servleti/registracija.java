package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;

public class registracija extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession sesija = request.getSession();
        String poruka = "";
        Klijent user = new Klijent();
        //Klijent user = (Klijent)sesija.getAttribute("user");
        
        String name = (String)request.getParameter("name");
        String username = (String)request.getParameter("username");
        String password = (String)request.getParameter("password");
        String email = (String)request.getParameter("email");
        String phone = (String)request.getParameter("phone");
        String address = (String)request.getParameter("address");
        String state = (String)request.getParameter("state");
        String city = (String)request.getParameter("city");
        String zip = (String)request.getParameter("zip");
      
        if(username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || state.isEmpty() || city.isEmpty() || zip.isEmpty())
        {
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Niste popunili sva potrebna polja.");
            RequestDispatcher rd = request.getRequestDispatcher("klijent/registracija.jsp");
            rd.forward(request, response);
        }
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        Statement stmt = null;
        ResultSet rzlt = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            
            stmt = con.createStatement();
            String upit = "select * from klijenti where kime='"+username+"'";
            rzlt = stmt.executeQuery(upit);
            
            if(rzlt.first())
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Korisničko ime koje ste uneli je zauzeto.");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/registracija.jsp");
                rd.forward(request, response);  
            }
            else
            {
                pstmt = con.prepareStatement("insert into klijenti(ime, kime, sifra, email, telefon, adresa, grad, drzava, pbroj, vrsta, poeni, hotelID) values(?,?,?,?,?,?,?,?,?,?,?,?)");

                pstmt.setString(1, name);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, email);
                pstmt.setInt(5, Integer.parseInt(phone));
                pstmt.setString(6, address);
                pstmt.setString(7, city);
                pstmt.setString(8, state);
                pstmt.setInt(9, Integer.parseInt(zip));
                pstmt.setString(10, "Klijent");
                pstmt.setInt(11, 0);
                pstmt.setInt(12, -1);

                pstmt.executeUpdate();
                
                request.setAttribute("popupid", 3);
                request.setAttribute("poruka", "Uspešno ste se registrovali. Starling Hotel Chain.");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);
            }
            rzlt.close();
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
        catch(ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Došlo je do greške! ("+cnfe.getMessage()+")");
            RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
            rd.forward(request, response);  
        }
        /*catch(Exception e)
        {
            e.printStackTrace();
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Došlo je do greške! ("+e.getMessage()+")");
            RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
            rd.forward(request, response);  
        }*/
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
