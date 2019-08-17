package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;

public class prijavljivanje extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesija = request.getSession();
        String poruka = "";
        String username = (String)request.getParameter("username");
        String password = (String)request.getParameter("password");
        Klijent user = new Klijent();
        user.setUsername(username);
        user.setPassword(password);
        sesija.setAttribute("user", user);
        if(username.isEmpty() || password.isEmpty())
        {
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Niste popunili sva potrebna polja!");
            RequestDispatcher rd = request.getRequestDispatcher("klijent/login.jsp");
            rd.forward(request, response);
        }
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            stmt = con.createStatement();
            String upit = "select * from klijenti where kime='"+username+"' and sifra='"+password+"'";
            rs = stmt.executeQuery(upit);
            if(rs.next())
            {
                int id = rs.getInt("ID");
                String name = rs.getString("ime");
                String usernames = rs.getString("kime");
                String passwords = rs.getString("sifra");
                String email = rs.getString("email");
                String telefon = rs.getString("telefon");
                String adresa = rs.getString("adresa");
                String city = rs.getString("grad");
                String state = rs.getString("drzava");
                int zip = rs.getInt("pbroj");
                String power = rs.getString("vrsta");
                int points = rs.getInt("poeni");
                int hotelID = rs.getInt("hotelID");
                user.setId(id);
                user.setName(name);
                user.setUsername(usernames);
                user.setPassword(passwords);
                user.setEmail(email);
                user.setPhone(telefon);
                user.setAddress(adresa);
                user.setCity(city);
                user.setState(state);
                user.setZip(zip);
                user.setPower(power);
                user.setPoints(points);
                user.setHotelID(hotelID);
            }
            else
            {
                poruka = "Neispravno korisničko ime i lozinka! Pokušajte ponovo.";
                request.setAttribute("poruka", poruka);
                request.setAttribute("popupid", 2);
                user.setPassword("");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/login.jsp");
                sesija.invalidate();
                rd.forward(request, response); 
            }
            rs.close();
            stmt.close();
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
        
        if(user.getPower().equals("Klijent"))
        {
            request.setAttribute("popupid", 3);
            request.setAttribute("poruka", "Uspešno ste se ulogovali kao klijent.");
            RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
            rd.forward(request, response);
        }
        else if(user.getPower().equals("Admin"))
        {
            request.setAttribute("popupid", 3);
            request.setAttribute("poruka", "Uspešno ste se ulogovali kao administrator.");
            RequestDispatcher rd = request.getRequestDispatcher("admin/index.jsp");
            rd.forward(request, response);
        }
        else if(user.getPower().equals("Menadzer hotela"))
        {
            request.setAttribute("popupid", 3);
            request.setAttribute("poruka", "Uspešno ste se ulogovali kao hotel menadžer.");
            RequestDispatcher rd = request.getRequestDispatcher("menadzer/index.jsp");
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
    }// </editor-fold>

}
