package servleti;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import beans.*;

@MultipartConfig(maxFileSize = 16177215) 
public class KreirajTipSobe extends HttpServlet {

   
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
            String rthotel = (String)request.getParameter("rthotel");
            String rtname = (String)request.getParameter("rtname");
            String rtadults = (String)request.getParameter("rtadults");
            String rtkids = (String)request.getParameter("rtkids");
            String rtdescription = (String)request.getParameter("rtdescription");
            String rtmember = (String)request.getParameter("rtmember");
            String rtstandard = (String)request.getParameter("rtstandard");
            String rtbb = (String)request.getParameter("rtbb");
            String rtdpoints = (String)request.getParameter("rtdpoints");
            String rtepackage = (String)request.getParameter("rtepackage");
            String rtsp = (String)request.getParameter("rtsp");

            InputStream inputStream = null; 

            Part filePart = request.getPart("photo");
            if (filePart != null) {
                System.out.println(filePart.getName());
                System.out.println(filePart.getSize());
                System.out.println(filePart.getContentType());

                inputStream = filePart.getInputStream();
            }
            if(rtname.isEmpty() || rtadults.isEmpty() || rtkids.isEmpty() || rtdescription.isEmpty() || rtmember.isEmpty() || rtstandard.isEmpty() || rtbb.isEmpty() || rtdpoints.isEmpty() || rtepackage.isEmpty() || rtsp.isEmpty() || inputStream == null)
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Niste popunili sva polja!");
                RequestDispatcher rd = request.getRequestDispatcher("admin/roomtype.jsp");
                rd.forward(request, response);
            }
            Connection con = null;
            PreparedStatement pstmt = null;

            Statement stmt = null;
            ResultSet rzlt = null;
            if(request.getSession().getAttribute("user") != null && user.getPower().equals("Admin"))
            {
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    try
                    {   

                        stmt = con.createStatement();
                        String upit = "select * from tipovisoba where naziv='"+rtname+"' and hotelID='"+rthotel+"'";
                        rzlt = stmt.executeQuery(upit);

                        if(rzlt.first())
                        {
                            request.setAttribute("popupid", 2);
                            request.setAttribute("poruka", "Tip hotelske sobe sa tim nazivom vec postoji u ovom hotelu!");
                            RequestDispatcher rd = request.getRequestDispatcher("admin/tipovisoba.jsp");
                            rd.forward(request, response);  
                        }
                        else
                        {
                            pstmt = con.prepareStatement("insert into tipovisoba(hotelID, naziv, opis, odrasli, deca, slika, paket1, paket2, paket3, paket4, paket5, starling_poeni) values(?,?,?,?,?,?,?,?,?,?,?,?)");

                            pstmt.setInt(1, Integer.parseInt(rthotel));
                            pstmt.setString(2, rtname);
                            pstmt.setString(3, rtdescription);
                            pstmt.setInt(4, Integer.parseInt(rtadults));
                            pstmt.setInt(5, Integer.parseInt(rtkids));
                            if (inputStream != null) {
                                pstmt.setBlob(6, inputStream);
                            }
                            pstmt.setInt(7, Integer.parseInt(rtmember));
                            pstmt.setInt(8, Integer.parseInt(rtstandard));
                            pstmt.setInt(9, Integer.parseInt(rtbb));
                            pstmt.setInt(10, Integer.parseInt(rtdpoints));
                            pstmt.setInt(11, Integer.parseInt(rtepackage));
                            pstmt.setInt(12, Integer.parseInt(rtsp));
                            pstmt.executeUpdate();
                            request.setAttribute("popupid", 3);
                            request.setAttribute("poruka", "Uspešno ste kreirali tip hotelske sobe.");
                            RequestDispatcher rd = request.getRequestDispatcher("admin/tipovisoba.jsp");
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
                RequestDispatcher rd = request.getRequestDispatcher("admin/roomtype.jsp");
                rd.forward(request, response);  
            }
        }
        else if(request.getSession().getAttribute("user") != null && user.getPower().equals("Menadzer hotela"))
        {
            String poruka = "";
            int popupid;

            String rtname = (String)request.getParameter("rtname");
            String rtadults = (String)request.getParameter("rtadults");
            String rtkids = (String)request.getParameter("rtkids");
            String rtdescription = (String)request.getParameter("rtdescription");
            String rtmember = (String)request.getParameter("rtmember");
            String rtstandard = (String)request.getParameter("rtstandard");
            String rtbb = (String)request.getParameter("rtbb");
            String rtdpoints = (String)request.getParameter("rtdpoints");
            String rtepackage = (String)request.getParameter("rtepackage");
            String rtsp = (String)request.getParameter("rtsp");

            InputStream inputStream = null;

            Part filePart = request.getPart("photo");
            if (filePart != null) {
                System.out.println(filePart.getName());
                System.out.println(filePart.getSize());
                System.out.println(filePart.getContentType());

                inputStream = filePart.getInputStream();
            }

            if(rtname.isEmpty() || rtadults.isEmpty() || rtkids.isEmpty() || rtdescription.isEmpty() || rtmember.isEmpty() || rtstandard.isEmpty() || rtbb.isEmpty() || rtdpoints.isEmpty() || rtepackage.isEmpty() || rtsp.isEmpty() || inputStream == null)
            {
                request.setAttribute("popupid", 2);
                request.setAttribute("poruka", "Niste popunili sva polja!");
                RequestDispatcher rd = request.getRequestDispatcher("manager/roomtype.jsp");
                rd.forward(request, response);
            }

            Connection con = null;
            PreparedStatement pstmt = null;

            Statement stmt = null;
            ResultSet rzlt = null;
            if(request.getSession().getAttribute("user") != null && user.getPower().equals("Menadzer hotela"))
            {
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    try
                    {   

                        stmt = con.createStatement();
                        String upit = "select * from tipovisoba where naziv='"+rtname+"' and hotelID='"+user.getHotelID()+"'";
                        rzlt = stmt.executeQuery(upit);

                        if(rzlt.first())
                        {
                            request.setAttribute("popupid", 2);
                            request.setAttribute("poruka", "Tip hotelske sobe sa tim nazivom vec postoji u ovom hotelu!");
                            RequestDispatcher rd = request.getRequestDispatcher("menadzer/tipovisoba.jsp");
                            rd.forward(request, response);  
                        }
                        else
                        {
                            pstmt = con.prepareStatement("insert into tipovisoba(hotelID, naziv, opis, odrasli, deca, slika, paket1, paket2, paket3, paket4, paket5, starling_poeni) values(?,?,?,?,?,?,?,?,?,?,?,?)");

                            pstmt.setInt(1, user.getHotelID());
                            pstmt.setString(2, rtname);
                            pstmt.setString(3, rtdescription);
                            pstmt.setInt(4, Integer.parseInt(rtadults));
                            pstmt.setInt(5, Integer.parseInt(rtkids));
                            if (inputStream != null) {

                                pstmt.setBlob(6, inputStream);
                            }
                            pstmt.setInt(7, Integer.parseInt(rtmember));
                            pstmt.setInt(8, Integer.parseInt(rtstandard));
                            pstmt.setInt(9, Integer.parseInt(rtbb));
                            pstmt.setInt(10, Integer.parseInt(rtdpoints));
                            pstmt.setInt(11, Integer.parseInt(rtepackage));
                            pstmt.setInt(12, Integer.parseInt(rtsp));
                            pstmt.executeUpdate();
                            request.setAttribute("popupid", 3);
                            request.setAttribute("poruka", "Uspešno ste kreirali novi tip hotelske sobe.");
                            RequestDispatcher rd = request.getRequestDispatcher("menadzer/tipovisoba.jsp");
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
