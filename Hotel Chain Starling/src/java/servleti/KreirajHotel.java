package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import beans.*;

@MultipartConfig(maxFileSize = 16177215) 
public class KreirajHotel extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession sesija = request.getSession();
        String poruka = "";
        int popupid;
        String hname = (String)request.getParameter("hname");
        String hstate = (String)request.getParameter("hstate");
        String hcity = (String)request.getParameter("hcity");
        String haddress = (String)request.getParameter("haddress");
        String hdescription = (String)request.getParameter("hdescription");
        String hstars = (String)request.getParameter("hstars");
        
        InputStream inputStream = null;
         
        Part filePart = request.getPart("photo");
        if (filePart != null) {
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            inputStream = filePart.getInputStream();
        }
        if(hname.isEmpty() || hstate.isEmpty() || hcity.isEmpty() || haddress.isEmpty() || hdescription.isEmpty() || hstars.isEmpty())
        {
            request.setAttribute("popupid", 2);
            request.setAttribute("poruka", "Niste popunili sva polja!");
            RequestDispatcher rd = request.getRequestDispatcher("admin/hoteli.jsp");
            rd.forward(request, response);
        }
        Klijent user = (Klijent)sesija.getAttribute("user");
        Connection con = null;
        PreparedStatement pstmt = null;
        
        Statement stmt = null;
        ResultSet rzlt = null;
        if(user != null && user.getPower().equals("Admin"))
        {
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                try
                {   

                    stmt = con.createStatement();
                    String upit = "select * from hoteli where naziv='"+hname+"'";
                    rzlt = stmt.executeQuery(upit);

                    if(rzlt.first())
                    {
                        request.setAttribute("popupid", 2);
                        request.setAttribute("poruka", "Hotel sa tim nazivom vec postoji!");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/hoteli.jsp");
                        rd.forward(request, response);  
                    }
                    else
                    {
                        pstmt = con.prepareStatement("insert into hoteli(naziv, drzava, grad, adresa, opis, zvezdice, slika) values(?,?,?,?,?,?,?)");

                        pstmt.setString(1, hname);
                        pstmt.setString(2, hstate);
                        pstmt.setString(3, hcity);
                        pstmt.setString(4, haddress);
                        pstmt.setString(5, hdescription);
                        pstmt.setInt(6, Integer.parseInt(hstars));

                        if (inputStream != null) {
                            pstmt.setBlob(7, inputStream);
                        }

                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste kreirali hotel.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/hoteli.jsp");
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
            request.setAttribute("poruka", "Niste ulogovani ili niste administrator.");
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
