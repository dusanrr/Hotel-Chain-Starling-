 package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;
import javax.swing.JOptionPane;
public class KlijentIzmenjen extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession sesija = request.getSession();
        String poruka = "";
        int popupid;
        
        String id = (String)request.getParameter("id");
        String name = (String)request.getParameter("name");
        String username = (String)request.getParameter("username");
        String username1 = (String)request.getParameter("username1");
        String password = (String)request.getParameter("password");
        String email = (String)request.getParameter("email");
        String phone = (String)request.getParameter("phone");
        String address = (String)request.getParameter("address");
        String state = (String)request.getParameter("state");
        String city = (String)request.getParameter("city");
        String zip = (String)request.getParameter("zip");
        String power = (String)request.getParameter("power");
        String points = (String)request.getParameter("points");
        String hotelID = (String)request.getParameter("hotelID");

        if(username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || state.isEmpty() || city.isEmpty() || zip.isEmpty())
        {
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Niste popunili sva potrebna polja.");
            RequestDispatcher rd = request.getRequestDispatcher("administration/index.jsp");
            rd.forward(request, response);
        }
        Connection con = null;
        PreparedStatement pstmt = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
            Klijent user = (Klijent)sesija.getAttribute("user");
            Statement stmt = null;
            ResultSet rzlt = null;
            if(request.getSession().getAttribute("user") != null && user.getPower().equals("Admin"))
            {
                stmt = con.createStatement();
                String upit = "select * from klijenti where kime='"+username+"'";
                rzlt = stmt.executeQuery(upit);

                if(rzlt.first() && username.equals(username1))
                {
                    if(power.equals("Klijent") || power.equals("Admin"))
                    {
                        String sql = "update klijenti set ime=IF(TRIM('"+name+"') != '', '"+name+"', `ime`), kime=IF(TRIM('"+username+"') != '', '"+username+"', `kime`), sifra=IF(TRIM('"+password+"') != '', '"+password+"', `sifra`), email=IF(TRIM('"+email+"') != '', '"+email+"', `email`), telefon=IF(TRIM('"+phone+"') != '', '"+phone+"', `telefon`), adresa=IF(TRIM('"+address+"') != '', '"+address+"', `adresa`), grad=IF(TRIM('"+city+"') != '', '"+city+"', `grad`), drzava=IF(TRIM('"+state+"') != '', '"+state+"', `drzava`), pbroj=IF(TRIM('"+zip+"') != '', '"+zip+"', `pbroj`), vrsta=IF(TRIM('"+power+"') != '', '"+power+"', `vrsta`), poeni=IF(TRIM('"+points+"') != '', '"+points+"', `poeni`), hotelID=-1 where ID='"+id+"'";
                        pstmt = con.prepareStatement(sql); 
                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste izmenili klijenta.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/klijenti.jsp");
                        rd.forward(request, response);
                    }
                    else
                    {
                        String sql = "update klijenti set ime=IF(TRIM('"+name+"') != '', '"+name+"', `ime`), kime=IF(TRIM('"+username+"') != '', '"+username+"', `kime`), sifra=IF(TRIM('"+password+"') != '', '"+password+"', `sifra`), email=IF(TRIM('"+email+"') != '', '"+email+"', `email`), telefon=IF(TRIM('"+phone+"') != '', '"+phone+"', `telefon`), adresa=IF(TRIM('"+address+"') != '', '"+address+"', `adresa`), grad=IF(TRIM('"+city+"') != '', '"+city+"', `grad`), drzava=IF(TRIM('"+state+"') != '', '"+state+"', `drzava`), pbroj=IF(TRIM('"+zip+"') != '', '"+zip+"', `pbroj`), vrsta=IF(TRIM('"+power+"') != '', '"+power+"', `vrsta`), poeni=IF(TRIM('"+points+"') != '', '"+points+"', `poeni`), hotelID=IF(TRIM('"+hotelID+"') != '', '"+hotelID+"', `hotelID`) where ID='"+id+"'";
                        pstmt = con.prepareStatement(sql); 
                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste izmenili klijenta.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/klijenti.jsp");
                        rd.forward(request, response);
                    }
                }
                else if(rzlt.first() && !username.equals(username1))
                {
                    request.setAttribute("popupid", 2);
                    request.setAttribute("poruka", "Korisnik sa tim korisničkim imenom već postoji u bazi.");
                    RequestDispatcher rd = request.getRequestDispatcher("admin/klijenti.jsp");
                    rd.forward(request, response);
                } 
                else if(!rzlt.first())
                {
                    if(power.equals("Klijent") || power.equals("Admin"))
                    {
                        String sql = "update klijenti set ime=IF(TRIM('"+name+"') != '', '"+name+"', `ime`), kime=IF(TRIM('"+username+"') != '', '"+username+"', `kime`), sifra=IF(TRIM('"+password+"') != '', '"+password+"', `sifra`), email=IF(TRIM('"+email+"') != '', '"+email+"', `email`), telefon=IF(TRIM('"+phone+"') != '', '"+phone+"', `telefon`), adresa=IF(TRIM('"+address+"') != '', '"+address+"', `adresa`), grad=IF(TRIM('"+city+"') != '', '"+city+"', `grad`), drzava=IF(TRIM('"+state+"') != '', '"+state+"', `drzava`), pbroj=IF(TRIM('"+zip+"') != '', '"+zip+"', `pbroj`), vrsta=IF(TRIM('"+power+"') != '', '"+power+"', `vrsta`), poeni=IF(TRIM('"+points+"') != '', '"+points+"', `poeni`), hotelID=-1 where ID='"+id+"'";
                        pstmt = con.prepareStatement(sql); 
                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste izmenili klijenta.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/klijenti.jsp");
                        rd.forward(request, response);
                    }
                    else
                    {
                        String sql = "update klijenti set ime=IF(TRIM('"+name+"') != '', '"+name+"', `ime`), kime=IF(TRIM('"+username+"') != '', '"+username+"', `kime`), sifra=IF(TRIM('"+password+"') != '', '"+password+"', `sifra`), email=IF(TRIM('"+email+"') != '', '"+email+"', `email`), telefon=IF(TRIM('"+phone+"') != '', '"+phone+"', `telefon`), adresa=IF(TRIM('"+address+"') != '', '"+address+"', `adresa`), grad=IF(TRIM('"+city+"') != '', '"+city+"', `grad`), drzava=IF(TRIM('"+state+"') != '', '"+state+"', `drzava`), pbroj=IF(TRIM('"+zip+"') != '', '"+zip+"', `pbroj`), vrsta=IF(TRIM('"+power+"') != '', '"+power+"', `vrsta`), poeni=IF(TRIM('"+points+"') != '', '"+points+"', `poeni`), hotelID=IF(TRIM('"+hotelID+"') != '', '"+hotelID+"', `hotelID`) where ID='"+id+"'";
                        pstmt = con.prepareStatement(sql); 
                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste izmenili klijenta.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/klijenti.jsp");
                        rd.forward(request, response);
                    }
                }
            }
            else
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Niste ulogovani ili niste administrator.");
                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                rd.forward(request, response);
            }
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
