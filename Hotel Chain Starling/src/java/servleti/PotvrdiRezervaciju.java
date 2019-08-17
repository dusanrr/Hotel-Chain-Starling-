package servleti;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

public class PotvrdiRezervaciju extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesija = request.getSession();
        Connection conn = null;
        String poruka = "";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");  
            
            
            Klijent user = (Klijent)sesija.getAttribute("user");
            
            int clientID = user.getId();
            String hotelID = request.getParameter("hotelID");
            String rt = request.getParameter("tipsobe");
            String location = request.getParameter("lokacija");
            String rooms = request.getParameter("sobe");
            String checkinDate = request.getParameter("datumDolaska");
            String checkoutDate = request.getParameter("datumOdlaska");
            String adults = request.getParameter("odrasli");
            String kids = request.getParameter("deca");
            String rate = request.getParameter("paket");
            String money = request.getParameter("novac");
            String status = "Zauzeta";
            String points = request.getParameter("poeni");
            
            int sat = LocalDateTime.now().getHour();
            int minut = LocalDateTime.now().getMinute();
            String checkintime = ""+sat+":"+minut+"";
            String checkouttime = "09:00";


            ResultSet rzs = null;
            PreparedStatement pst = null;

            String query="SELECT COUNT(ID) as broj FROM sobe where hotelID='"+hotelID+"' and tipsobeID='"+rt+"' and status='Prazna'";

            pst = conn.prepareStatement(query);
            rzs = pst.executeQuery();
            if(rzs.first())   
            {
                int broj = rzs.getInt("broj");
                if(broj >= Integer.parseInt(rooms))
                {

                    if(Integer.parseInt(rooms) > 2)
                    {
                        request.setAttribute("popupid", 2);
                        request.setAttribute("poruka", "Ne možete rezervisati više od dve sobe odjednom.");
                        RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                        rd.forward(request, response);  
                    }
                    else if(Integer.parseInt(rooms) < 1)
                    {
                        request.setAttribute("popupid", 2);
                        request.setAttribute("poruka", "Ne možete rezervisati manje od jedne sobe.");
                        RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                        rd.forward(request, response);  
                    }
                    else if(Integer.parseInt(rooms) <= 2)
                    {
                        try
                        {
                            ResultSet rs = null;
                            PreparedStatement ps = null;
                            
                            PreparedStatement pstmt = null;
                            PreparedStatement pcc = null;

                            String query1="SELECT ID FROM sobe where hotelID='"+hotelID+"' and tipsobeID='"+rt+"' and status='Prazna'";

                            ps = conn.prepareStatement(query1);
                            rs = ps.executeQuery();

                            int lol=0;
                            int soba1=0;
                            int soba2=0;
                            while(rs.next())
                            {
                                lol++;
                                if(Integer.parseInt(rooms) == 1)
                                {
                                    if(rs.first())
                                    {
                                        soba1 = rs.getInt("ID");
                                        String sql = "update sobe set klijentID=IF(TRIM('"+clientID+"') != '', '"+clientID+"', `klijentID`), hotelID=IF(TRIM('"+hotelID+"') != '', '"+hotelID+"', `hotelID`), tipsobeID=IF(TRIM('"+rt+"') != '', '"+rt+"', `tipsobeID`), datum1=IF(TRIM('"+checkinDate+"') != '', '"+checkinDate+"', `datum1`), datum2=IF(TRIM('"+checkoutDate+"') != '', '"+checkoutDate+"', `datum2`), vreme1=IF(TRIM('"+checkintime+"') != '', '"+checkintime+"', `vreme1`), vreme2=IF(TRIM('"+checkouttime+"') != '', '"+checkouttime+"', `vreme2`), status=IF(TRIM('"+status+"') != '', '"+status+"', `status`)  where ID='"+soba1+"' and status='Prazna'";
                                        pstmt = conn.prepareStatement(sql); 
                                        pstmt.executeUpdate();
                                        request.setAttribute("popupid", 3);
                                        request.setAttribute("poruka", "Uspešno ste izvršili rezervaciju. Starling Hotel Chain.");
                                        RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                                        rd.forward(request, response);
                                    }
                                    if(money != null)
                                    {
                                        pstmt = conn.prepareStatement("insert into rezervacije(hotelID, tipsobeID, klijentID, datum1, datum2, vreme2, sobe, odrasli, deca, novac, poeni, soba1) values(?,?,?,?,?,?,?,?,?,?,?,?)");
                                        pstmt.setInt(1, Integer.parseInt(hotelID));
                                        pstmt.setInt(2, Integer.parseInt(rt));
                                        pstmt.setInt(3, user.getId());
                                        pstmt.setString(4, checkinDate);
                                        pstmt.setString(5, checkoutDate);
                                        pstmt.setString(6, checkouttime);
                                        pstmt.setInt(7, Integer.parseInt(rooms));
                                        pstmt.setInt(8, Integer.parseInt(adults));
                                        pstmt.setInt(9, Integer.parseInt(kids));
                                        pstmt.setDouble(10, Integer.parseInt(money));
                                        pstmt.setInt(11, -1);
                                        pstmt.setInt(12, rs.getInt("ID"));
                                        pstmt.executeUpdate();

                                        String clientt = "update klijenti set poeni=poeni+5 where ID='"+user.getId()+"'";
                                        pcc = conn.prepareStatement(clientt); 
                                        pcc.executeUpdate();
                                        
                                        if(rate.equals("paket4"))
                                        {
                                            String clients = "update klijenti set poeni='"+user.getPoints()*2+"' where ID='"+user.getId()+"'";
                                            pcc = conn.prepareStatement(clients); 
                                            pcc.executeUpdate();
                                        }
                                        pcc.close();
                                    }
                                    else if(points != null)
                                    {
                                        pstmt = conn.prepareStatement("insert into rezervacije(hotelID, tipsobeID, klijentID, datum1, datum2, vreme2, sobe, odrasli, deca, novac, poeni, soba1) values(?,?,?,?,?,?,?,?,?,?,?,?)");
                                        pstmt.setInt(1, Integer.parseInt(hotelID));
                                        pstmt.setInt(2, Integer.parseInt(rt));
                                        pstmt.setInt(3, user.getId());
                                        pstmt.setString(4, checkinDate);
                                        pstmt.setString(5, checkoutDate);
                                        pstmt.setString(6, checkouttime);
                                        pstmt.setInt(7, Integer.parseInt(rooms));
                                        pstmt.setInt(8, Integer.parseInt(adults));
                                        pstmt.setInt(9, Integer.parseInt(kids));
                                        pstmt.setDouble(10, -1);
                                        pstmt.setInt(11, Integer.parseInt(points));
                                        pstmt.setInt(12, rs.getInt("ID"));
                                        pstmt.executeUpdate();


                                        PreparedStatement pc = null;

                                        String client = "update klijenti set poeni=poeni-"+points+" where ID='"+user.getId()+"'";
                                        pc = conn.prepareStatement(client); 
                                        pc.executeUpdate();

                                        pc.close();
                                    }
                                }
                                else if(Integer.parseInt(rooms) == 2)
                                {
                                    if(lol == 1)
                                    {
                                        if(rs.first())
                                        {
                                            soba1 = rs.getInt("ID");
                                            String sql = "update sobe set klijentID=IF(TRIM('"+clientID+"') != '', '"+clientID+"', `klijentID`), hotelID=IF(TRIM('"+hotelID+"') != '', '"+hotelID+"', `hotelID`), tipsobeID=IF(TRIM('"+rt+"') != '', '"+rt+"', `tipsobeID`), datum1=IF(TRIM('"+checkinDate+"') != '', '"+checkinDate+"', `datum1`), datum2=IF(TRIM('"+checkoutDate+"') != '', '"+checkoutDate+"', `datum2`), vreme1=IF(TRIM('"+checkintime+"') != '', '"+checkintime+"', `vreme1`), vreme2=IF(TRIM('"+checkouttime+"') != '', '"+checkouttime+"', `vreme2`), status=IF(TRIM('"+status+"') != '', '"+status+"', `status`)  where ID='"+soba1+"' and status='Prazna'";
                                            pstmt = conn.prepareStatement(sql); 
                                            pstmt.executeUpdate();
                                            
                                            request.setAttribute("popupid", 3);
                                            request.setAttribute("poruka", "Uspešno ste izvršili rezervaciju. Starling Hotel Chain.");
                                            RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                                            rd.forward(request, response);
                                        }
                                    }
                                    else if(lol == 2)
                                    {
                                        if(rs.first())
                                        {
                                            soba1 = rs.getInt("ID");
                                            String sql = "update sobe set klijentID=IF(TRIM('"+clientID+"') != '', '"+clientID+"', `klijentID`), hotelID=IF(TRIM('"+hotelID+"') != '', '"+hotelID+"', `hotelID`), tipsobeID=IF(TRIM('"+rt+"') != '', '"+rt+"', `tipsobeID`), datum1=IF(TRIM('"+checkinDate+"') != '', '"+checkinDate+"', `datum1`), datum2=IF(TRIM('"+checkoutDate+"') != '', '"+checkoutDate+"', `datum2`), vreme1=IF(TRIM('"+checkintime+"') != '', '"+checkintime+"', `vreme1`), vreme2=IF(TRIM('"+checkouttime+"') != '', '"+checkouttime+"', `vreme2`), status=IF(TRIM('"+status+"') != '', '"+status+"', `status`)  where ID='"+soba1+"' and status='Prazna'";
                                            pstmt = conn.prepareStatement(sql); 
                                            pstmt.executeUpdate();

                                            if(rs.next())
                                            {
                                                soba2 = rs.getInt("ID");
                                                String sql1 = "update sobe set klijentID=IF(TRIM('"+clientID+"') != '', '"+clientID+"', `klijentID`), hotelID=IF(TRIM('"+hotelID+"') != '', '"+hotelID+"', `hotelID`), tipsobeID=IF(TRIM('"+rt+"') != '', '"+rt+"', `tipsobeID`), datum1=IF(TRIM('"+checkinDate+"') != '', '"+checkinDate+"', `datum1`), datum2=IF(TRIM('"+checkoutDate+"') != '', '"+checkoutDate+"', `datum2`), vreme1=IF(TRIM('"+checkintime+"') != '', '"+checkintime+"', `vreme1`), vreme2=IF(TRIM('"+checkouttime+"') != '', '"+checkouttime+"', `vreme2`), status=IF(TRIM('"+status+"') != '', '"+status+"', `status`)  where ID='"+soba2+"' and status='Prazna'";
                                                pstmt = conn.prepareStatement(sql1); 
                                                pstmt.executeUpdate();
                                            } 
                                            if(money != null)
                                            {
                                                pstmt = conn.prepareStatement("insert into rezervacije(hotelID, tipsobeID, klijentID, datum1, datum2, vreme2, sobe, odrasli, deca, novac, poeni, soba1, soba2) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                                pstmt.setInt(1, Integer.parseInt(hotelID));
                                                pstmt.setInt(2, Integer.parseInt(rt));
                                                pstmt.setInt(3, user.getId());
                                                pstmt.setString(4, checkinDate);
                                                pstmt.setString(5, checkoutDate);
                                                pstmt.setString(6, checkouttime);
                                                pstmt.setInt(7, Integer.parseInt(rooms));
                                                pstmt.setInt(8, Integer.parseInt(adults));
                                                pstmt.setInt(9, Integer.parseInt(kids));
                                                pstmt.setDouble(10, Integer.parseInt(money));
                                                pstmt.setInt(11, -1);
                                                pstmt.setInt(12, soba1);
                                                pstmt.setInt(13, soba2);
                                                pstmt.executeUpdate();
                                                
                                                String clientt = "update klijenti set poeni=poeni+10 where ID='"+user.getId()+"'";
                                                pcc = conn.prepareStatement(clientt); 
                                                pcc.executeUpdate();
                                        
                                                if(rate.equals("paket4"))
                                                {
                                                    String clients = "update klijenti set poeni='"+user.getPoints()*2+"' where ID='"+user.getId()+"'";
                                                    pcc = conn.prepareStatement(clients); 
                                                    pcc.executeUpdate();
                                                }
                                                pcc.close();
                                                request.setAttribute("popupid", 3);
                                                request.setAttribute("poruka", "Uspešno ste izvršili rezervaciju. Starling Hotel Chain.");
                                                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                                                rd.forward(request, response);
                                            }
                                            else if(points != null)
                                            {
                                                pstmt = conn.prepareStatement("insert into rezervacije(hotelID, tipsobeID, klijentID, datum1, datum2, vreme2, sobe, odrasli, deca, novac, poeni, soba1, soba2) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                                pstmt.setInt(1, Integer.parseInt(hotelID));
                                                pstmt.setInt(2, Integer.parseInt(rt));
                                                pstmt.setInt(3, user.getId());
                                                pstmt.setString(4, checkinDate);
                                                pstmt.setString(5, checkoutDate);
                                                pstmt.setString(6, checkouttime);
                                                pstmt.setInt(7, Integer.parseInt(rooms));
                                                pstmt.setInt(8, Integer.parseInt(adults));
                                                pstmt.setInt(9, Integer.parseInt(kids));
                                                pstmt.setDouble(10, -1);
                                                pstmt.setInt(11, Integer.parseInt(points));
                                                pstmt.setInt(12, soba1);
                                                pstmt.setInt(13, soba2);
                                                pstmt.executeUpdate();


                                                PreparedStatement pc = null;

                                                String client = "update klijenti set poeni=poeni-"+points+" where ID='"+user.getId()+"'";
                                                pc = conn.prepareStatement(client); 
                                                pc.executeUpdate();  
                                                
                                                request.setAttribute("popupid", 3);
                                                request.setAttribute("poruka", "Uspešno ste izvršili rezervaciju. Starling Hotel Chain.");
                                                RequestDispatcher rd = request.getRequestDispatcher("klijent/index.jsp");
                                                rd.forward(request, response);
                                            }
                                        }
                                    }
                                }
                            } 
                            rs.close();
                            ps.close();
                            pstmt.close();
                            conn.close();
                        }
                        catch(SQLException ex)
                        {
                            ex.printStackTrace();
                            sesija.invalidate();
                            if(conn!=null)
                                try
                                {
                                    conn.close();
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
                } 
            }
            rzs.close();
            pst.close();
            conn.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            sesija.invalidate();
            if(conn!=null)
                try
                {
                    conn.close();
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
