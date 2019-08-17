package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import beans.*;

@MultipartConfig(maxFileSize = 16177215) 
public class HotelIzmenjen extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesija = request.getSession();
        Klijent user = (Klijent)sesija.getAttribute("user");
        if(request.getSession().getAttribute("user") != null && user.getPower().equals("Admin"))
        {
            String poruka = "";
            int popupid;

            String id = (String)request.getParameter("id");
            String name = (String)request.getParameter("name");
            String name1 = (String)request.getParameter("name1");
            String state = (String)request.getParameter("state");
            String city = (String)request.getParameter("city");
            String address = (String)request.getParameter("address");
            String description = (String)request.getParameter("description");
            String stars = (String)request.getParameter("stars");

            InputStream image = null; 

            Part filePart = request.getPart("photo");
            if (filePart != null) {
                System.out.println(filePart.getName());
                System.out.println(filePart.getSize());
                System.out.println(filePart.getContentType());

                image = filePart.getInputStream();
            }

            if(name.isEmpty() || state.isEmpty() || city.isEmpty() || address.isEmpty() || description.isEmpty() || stars.isEmpty())
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Niste popunili sva polja!");
                RequestDispatcher rd = request.getRequestDispatcher("admin/hoteli.jsp");
                rd.forward(request, response);
            }

            Connection con = null;
            PreparedStatement pstmt = null;
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                if(filePart.getSize() > 0)
                {
                    String kuku = "update hoteli set slika=null where ID='"+id+"'";
                    pstmt = con.prepareStatement(kuku);
                    pstmt.executeUpdate();

                    pstmt.close();

                    String novi = "update hoteli set slika=? where ID=?";
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
                
                if(request.getSession().getAttribute("user") != null && user.getPower().equals("Admin"))
                {
                    stmt = con.createStatement();
                    String upit = "select * from hoteli where naziv='"+name+"'";
                    rzlt = stmt.executeQuery(upit);
                    if(rzlt.first() && name.equals(name1))
                    {
                        String sql = "update hoteli set naziv=IF(TRIM('"+name+"') != '', '"+name+"', `naziv`), drzava=IF(TRIM('"+state+"') != '', '"+state+"', `drzava`), grad=IF(TRIM('"+city+"') != '', '"+city+"', `grad`), adresa=IF(TRIM('"+address+"') != '', '"+address+"', `adresa`), opis=IF(TRIM('"+description+"') != '', '"+description+"', `opis`), zvezdice=IF(TRIM('"+stars+"') != '', '"+stars+"', `zvezdice`) where ID='"+id+"'";
                        pstmt = con.prepareStatement(sql); 
                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste izmenili hotel.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/hoteli.jsp");
                        rd.forward(request, response);
                    }
                    else if(rzlt.first() && !name.equals(name1))
                    {
                        request.setAttribute("popupid", 2);
                        request.setAttribute("poruka", "Već postoji hotel sa tim nazivom.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/hoteli.jsp");
                        rd.forward(request, response);
                    } 
                    else if(!rzlt.first())
                    {
                        String sql = "update hoteli set naziv=IF(TRIM('"+name+"') != '', '"+name+"', `naziv`), drzava=IF(TRIM('"+state+"') != '', '"+state+"', `drzava`), grad=IF(TRIM('"+city+"') != '', '"+city+"', `grad`), adresa=IF(TRIM('"+address+"') != '', '"+address+"', `adresa`), opis=IF(TRIM('"+description+"') != '', '"+description+"', `opis`), zvezdice=IF(TRIM('"+stars+"') != '', '"+stars+"', `zvezdice`) where ID='"+id+"'";
                        pstmt = con.prepareStatement(sql); 
                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste izmenili hotel.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin/hoteli.jsp");
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

            String poruka = "";
            int popupid;

            String name = (String)request.getParameter("name");
            String name1 = (String)request.getParameter("name1");
            String state = (String)request.getParameter("state");
            String city = (String)request.getParameter("city");
            String address = (String)request.getParameter("address");
            String description = (String)request.getParameter("description");
            String stars = (String)request.getParameter("stars");

            InputStream image = null; 

            Part filePart = request.getPart("photo");
            if (filePart != null) {
                System.out.println(filePart.getName());
                System.out.println(filePart.getSize());
                System.out.println(filePart.getContentType());

                image = filePart.getInputStream();
            }

            if(name.isEmpty() || state.isEmpty() || city.isEmpty() || address.isEmpty() || description.isEmpty() || stars.isEmpty())
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Niste popunili sva polja!");
                RequestDispatcher rd = request.getRequestDispatcher("menadzer/hotel.jsp");
                rd.forward(request, response);
            }
            Connection con = null;
            PreparedStatement pstmt = null;
            
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                if(filePart.getSize() > 0)
                {
                    String kuku = "update hoteli set slika=null where ID='"+user.getHotelID()+"'";
                    pstmt = con.prepareStatement(kuku);
                    pstmt.executeUpdate();

                    pstmt.close();

                    String novi = "update hoteli set slika=? where ID=?";
                    pstmt = con.prepareStatement(novi);
                    pstmt.setBlob(1, image);
                    pstmt.setInt(2, user.getHotelID());
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

                if(request.getSession().getAttribute("user") != null && user.getPower().equals("Menadzer hotela"))
                {
                    stmt = con.createStatement();
                    String upit = "select * from hoteli where naziv='"+name+"'";
                    rzlt = stmt.executeQuery(upit);
                    if(rzlt.first() && name.equals(name1))
                    {
                        String sql = "update hoteli set naziv=IF(TRIM('"+name+"') != '', '"+name+"', `naziv`), drzava=IF(TRIM('"+state+"') != '', '"+state+"', `drzava`), grad=IF(TRIM('"+city+"') != '', '"+city+"', `grad`), adresa=IF(TRIM('"+address+"') != '', '"+address+"', `adresa`), opis=IF(TRIM('"+description+"') != '', '"+description+"', `opis`), zvezdice=IF(TRIM('"+stars+"') != '', '"+stars+"', `zvezdice`) where ID='"+user.getHotelID()+"'";
                        pstmt = con.prepareStatement(sql); 
                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste izmenili hotel.");
                        RequestDispatcher rd = request.getRequestDispatcher("menadzer/hotel.jsp");
                        rd.forward(request, response);      
                    }
                    else if(rzlt.first() && !name.equals(name1))
                    {
                        request.setAttribute("popupid", 2);
                        request.setAttribute("poruka", "Već postoji hotel sa tim nazivom.");
                        RequestDispatcher rd = request.getRequestDispatcher("menadzer/hotel.jsp");
                        rd.forward(request, response);
                    } 
                    else if(!rzlt.first())
                    {
                        String sql = "update hoteli set naziv=IF(TRIM('"+name+"') != '', '"+name+"', `naziv`), drzava=IF(TRIM('"+state+"') != '', '"+state+"', `drzava`), grad=IF(TRIM('"+city+"') != '', '"+city+"', `grad`), adresa=IF(TRIM('"+address+"') != '', '"+address+"', `adresa`), opis=IF(TRIM('"+description+"') != '', '"+description+"', `opis`), zvezdice=IF(TRIM('"+stars+"') != '', '"+stars+"', `zvezdice`) where ID='"+user.getHotelID()+"'";
                        pstmt = con.prepareStatement(sql); 
                        pstmt.executeUpdate();
                        request.setAttribute("popupid", 3);
                        request.setAttribute("poruka", "Uspešno ste izmenili hotel.");
                        RequestDispatcher rd = request.getRequestDispatcher("menadzer/hotel.jsp");
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
