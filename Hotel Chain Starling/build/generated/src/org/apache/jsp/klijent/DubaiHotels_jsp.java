package org.apache.jsp.klijent;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.Klijent;
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Timer;
import java.time.LocalDateTime;

public final class DubaiHotels_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


public int nullIntconv(String str)
{   
    int conv=0;
    if(str==null)
    {
        str="0";
    }
    else if((str.trim()).equals("null"))
    {
        str="0";
    }
    else if(str.equals(""))
    {
        str="0";
    }
    try{
        conv=Integer.parseInt(str);
    }
    catch(Exception e)
    {
    }
    return conv;
}

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write('\n');


    Connection conn = null;
    Class.forName("com.mysql.cj.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");

    ResultSet rsPagination = null;
    ResultSet rsRowCnt = null;
    
    PreparedStatement psPagination=null;
    PreparedStatement psRowCnt=null;
    
    int iShowRows=5;  //prikaza po strani
    int iTotalSearchRecords=10;  //broj strana koji se prikazuje
    
    int iTotalRows=nullIntconv(request.getParameter("iTotalRows"));
    int iTotalPages=nullIntconv(request.getParameter("iTotalPages"));
    int iPageNo=nullIntconv(request.getParameter("iPageNo"));
    int cPageNo=nullIntconv(request.getParameter("cPageNo"));
    
    String poruka = "";
    
    int iStartResultNo=0;
    int iEndResultNo=0;
    
    if(iPageNo==0)
    {
        iPageNo=0;
    }
    else{
        iPageNo=Math.abs((iPageNo-1)*iShowRows);
    }
    
    String sqlPagination="SELECT SQL_CALC_FOUND_ROWS * FROM hoteli where grad='Dubai' limit "+iPageNo+","+iShowRows+"";

    psPagination=conn.prepareStatement(sqlPagination);
    rsPagination=psPagination.executeQuery();
    
    //racuna ukupan broj redova
    String sqlRowCnt="SELECT FOUND_ROWS() as cnt";
    psRowCnt=conn.prepareStatement(sqlRowCnt);
    rsRowCnt=psRowCnt.executeQuery();
     
    if(rsRowCnt.next())
    {
       iTotalRows=rsRowCnt.getInt("cnt");
    }

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<title>Starling Hotels</title>\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n");
      out.write("<link rel=\"shortcut icon\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/images/favicon.png\" type=\"image/x-icon\"/>\n");
      out.write("<link href='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/admin/fonts/fonts/fonts.css' rel='stylesheet' type='text/css'>  \n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/css/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" />\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/admin/fonts/css/all.css\" rel=\"stylesheet\">\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/css/animate.min.css\" rel=\"stylesheet\">\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/js/jquery.min.js\"></script>\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/css/fwslider.css\" type=\"text/css\" media=\"all\">\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/js/jquery-ui.min.js\"></script>\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/js/css3-mediaqueries.js\"></script>\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/js/fwslider.js\"></script>\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/js/slick.js\"></script>\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/js/sweetalert.min.js\"></script>\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/css/sweetalert.css\">\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/css/jquery-ui.css\" />\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/js/jquery-ui.js\"></script>\n");
      out.write("\t\t  <script>\n");
      out.write("\t\t\t\t  $(function() {\n");
      out.write("\t\t\t\t    $( \"#datepicker,#datepicker1\" ).datepicker();\n");
      out.write("\t\t\t\t  });\n");
      out.write("\t\t  </script>\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/css/JFGrid.css\" />\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/css/JFFormStyle-1.css\" />\n");
      out.write("\t\t<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/js/JFCore.js\"></script>\n");
      out.write("\t\t<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/js/JFForms.js\"></script>\n");
      out.write("<script>\n");
      out.write("\t\t$(function() {\n");
      out.write("\t\t\tvar pull \t\t= $('#pull');\n");
      out.write("\t\t\t\tmenu \t\t= $('nav ul');\n");
      out.write("\t\t\t\tmenuHeight\t= menu.height();\n");
      out.write("\n");
      out.write("\t\t\t$(pull).on('click', function(e) {\n");
      out.write("\t\t\t\te.preventDefault();\n");
      out.write("\t\t\t\tmenu.slideToggle();\n");
      out.write("\t\t\t});\n");
      out.write("\n");
      out.write("\t\t\t$(window).resize(function(){\n");
      out.write("        \t\tvar w = $(window).width();\n");
      out.write("        \t\tif(w > 320 && menu.is(':hidden')) {\n");
      out.write("        \t\t\tmenu.removeAttr('style');\n");
      out.write("        \t\t}\n");
      out.write("    \t\t});\n");
      out.write("\t\t});\n");
      out.write("</script>\n");
      out.write("<script>  \n");
      out.write("//SEARCH VALIDACIJA\n");
      out.write("function formValidation()  \n");
      out.write("{  \n");
      out.write("    var city = document.search.lokacija; \n");
      out.write("    var cidate = document.search.datumDolaska; \n");
      out.write("    var codate = document.search.datumOdlaska; \n");
      out.write("    \n");
      out.write("    if(aphabet(city))\n");
      out.write("    {\n");
      out.write("    if(validatecidate(cidate))\n");
      out.write("    {\n");
      out.write("    if(validatecodate(codate))  \n");
      out.write("    {\n");
      out.write("        document.search.submit();\n");
      out.write("    }  \n");
      out.write("    }   \n");
      out.write("    }  \n");
      out.write("    return false;  \n");
      out.write("} \n");
      out.write("function aphabet(city)\n");
      out.write("{\n");
      out.write("    var letters = /^[a-zA-Z\\s]+$/;\n");
      out.write("    if(city.value.length == 0)\n");
      out.write("    {\n");
      out.write("        swal (\"Warning\" ,  \"Polje grad mora biti popunjeno.\" ,  \"warning\" );\n");
      out.write("        city.focus();   \n");
      out.write("        return false;\n");
      out.write("    }\n");
      out.write("    else if(!city.value.match(letters))\n");
      out.write("    {\n");
      out.write("        swal (\"Warning\" ,  \"Polje grad može sadržati samo slova.\" ,  \"warning\" );\n");
      out.write("        city.focus();\n");
      out.write("        return false;\n");
      out.write("    }\n");
      out.write("    return true;\n");
      out.write("}\n");
      out.write("function validatecidate(cidate)\n");
      out.write("{\n");
      out.write("    var allowBlank = true;\n");
      out.write("    var minYear = 2018;\n");
      out.write("    var maxYear = (new Date()).getFullYear();\n");
      out.write("\n");
      out.write("    var errorMsg = \"\";\n");
      out.write("\n");
      out.write("    //regular expression\n");
      out.write("    re = /^(\\d{1,2})\\.(\\d{1,2})\\.(\\d{4})$/;\n");
      out.write("\n");
      out.write("    if(cidate.value != '') \n");
      out.write("    {\n");
      out.write("      if(regs = cidate.value.match(re)) \n");
      out.write("      {\n");
      out.write("        if(regs[2] < 1 || regs[2] > 31) \n");
      out.write("        {\n");
      out.write("          errorMsg = \"Dan nije u validnom formatu: \" + regs[2];\n");
      out.write("        } else if(regs[1] < 1 || regs[1] > 12) \n");
      out.write("        {\n");
      out.write("          errorMsg = \"Mesec nije u validnom formatu: \" + regs[1];\n");
      out.write("        } else if(regs[3] < minYear || regs[3] > maxYear) \n");
      out.write("        {\n");
      out.write("          errorMsg = \"Godina nije u validnom formatu: \" + regs[3] + \" - mora biti između \" + minYear + \" i \" + maxYear;\n");
      out.write("        }\n");
      out.write("      } \n");
      out.write("      else \n");
      out.write("      {\n");
      out.write("        errorMsg = \"Polje datum dolaska nije u validnom formatu: \" + cidate.value;\n");
      out.write("      }\n");
      out.write("    } \n");
      out.write("    else\n");
      out.write("    {\n");
      out.write("      errorMsg = \"Polje datum dolaska mora biti popunjeno!\";\n");
      out.write("    }\n");
      out.write("\n");
      out.write("    if(errorMsg != \"\") \n");
      out.write("    {\n");
      out.write("      swal (\"Warning\" ,  \"\"+errorMsg ,  \"warning\" );\n");
      out.write("      cidate.focus();\n");
      out.write("      return false;\n");
      out.write("    }\n");
      out.write("    return true;\n");
      out.write("}\n");
      out.write("function validatecodate(codate)\n");
      out.write("{\n");
      out.write("    var allowBlank = true;\n");
      out.write("    var minYear = 1902;\n");
      out.write("    var maxYear = (new Date()).getFullYear();\n");
      out.write("\n");
      out.write("    var errorMsg = \"\";\n");
      out.write("\n");
      out.write("    //regular expression\n");
      out.write("    re = /^(\\d{1,2})\\.(\\d{1,2})\\.(\\d{4})$/;\n");
      out.write("\n");
      out.write("    if(codate.value != '') \n");
      out.write("    {\n");
      out.write("      if(regss = codate.value.match(re)) \n");
      out.write("      {\n");
      out.write("        if(regss[2] < 1 || regss[2] > 31) \n");
      out.write("        {\n");
      out.write("          errorMsg = \"Dan nije u validnom formatu: \" + regss[2];\n");
      out.write("        } else if(regss[1] < 1 || regss[1] > 12) \n");
      out.write("        {\n");
      out.write("          errorMsg = \"Mesec nije u validnom formatu: \" + regss[1];\n");
      out.write("        } else if(regss[3] < minYear || regss[3] > maxYear) \n");
      out.write("        {\n");
      out.write("          errorMsg = \"Godina nije u validnom formatu: \" + regss[3] + \" - mora biti između \" + minYear + \" i \" + maxYear;\n");
      out.write("        }\n");
      out.write("      } \n");
      out.write("      else \n");
      out.write("      {\n");
      out.write("        errorMsg = \"Polje datum odlaska nije u validnom formatu: \" + codate.value;\n");
      out.write("      }\n");
      out.write("    } \n");
      out.write("    else\n");
      out.write("    {\n");
      out.write("      errorMsg = \"Polje datum odlaska mora biti popunjeno!\";\n");
      out.write("    }\n");
      out.write("\n");
      out.write("    if(errorMsg != \"\") \n");
      out.write("    {\n");
      out.write("      swal (\"Warning\" ,  \"\"+errorMsg ,  \"warning\" );\n");
      out.write("      codate.focus();\n");
      out.write("      return false;\n");
      out.write("    }\n");
      out.write("    return true;\n");
      out.write("}\n");
      out.write("////////////////////////////////////////////////////////////////////////////////\n");
      out.write("</script>  \n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<!-- start header -->\n");
      out.write("<!--<div id=\"main-wrapper\">\n");
      out.write("\t<main class=\"main\" role=\"main\" id=\"main-content\">-->\n");
      out.write("\t\t\n");
      out.write("            ");

                Connection con = null;
                Statement stmt = null;
                ResultSet rs = null;
                HttpSession sesija = request.getSession();
                Klijent user = (Klijent)sesija.getAttribute("user");
                try
                {

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");
                    stmt = con.createStatement();
                    String upit = "select * from klijenti where kime='"+user.getUsername()+"'";
                    rs = stmt.executeQuery(upit);
                    
      out.write("\n");
      out.write("\n");
      out.write("                    ");
  if(rs.first())
                    { 
      out.write("\n");
      out.write("                    <div id=\"main-top-bar\">\n");
      out.write("\t\t\t<a href=\"https://rolecalljobs.com\" class=\"main-logo\"></a>\n");
      out.write("                <div class=\"user-menu\">\n");
      out.write("                  <div class=\"user-menu-item\">\n");
      out.write("                    <div class=\"user-image-wrapper\">\n");
      out.write("                      <span class=\"user-image\"><img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/admin/images/img_avatar.png\"></span>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"user-name-wrapper\">\n");
      out.write("                    ");
      out.print( rs.getString("ime"));
      out.write(" <i class=\"fa fa-caret-down\"></i>\n");
      out.write("                  </div>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"user-dropdown\">\n");
      out.write("                  <a href=\"#\" class=\"dropdown-item\"><span class=\"mr-3\"><i class=\"fa fa-underline\"></i></span>");
      out.print( rs.getString("kime"));
      out.write("</a>\n");
      out.write("                  <a href=\"#\" class=\"dropdown-item\"><span class=\"mr-3\"><i class=\"fab fa-superpowers\"></i></span>");
      out.print( rs.getString("vrsta"));
      out.write("</a>\n");
      out.write("                  <a href=\"#\" class=\"dropdown-item\"><span class=\"mr-3\"><i class=\"fa fa-envelope\"></i></span>");
      out.print( rs.getString("email"));
      out.write("</a>\n");
      out.write("                  <a href=\"#\" class=\"dropdown-item\"><span class=\"mr-3\"><i class=\"fab fa-pinterest-p\"></i></span>");
      out.print( rs.getInt("poeni"));
      out.write("</a>\n");
      out.write("                  <a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/odjavljivanje\" class=\"dropdown-item\"><span class=\"mr-3\"><i class=\"fa fa-power-off\"></i></span> Odjavi se</a>\n");
      out.write("                </div>\n");
      out.write("                 </div>\n");
      out.write("                <div class=\"shape\"></div>\n");
      out.write("\t\t</div>\n");
      out.write("                    ");
} 
                    rs.close();
                    stmt.close();
                    con.close();
                    
      out.write("        \n");
      out.write("            ");

                }
                catch(Exception e){}

            
      out.write("\n");
      out.write("<div class=\"header_bg\">\n");
      out.write("<div class=\"wrap\">\n");
      out.write("\t<div class=\"header\">\n");
      out.write("\t\t<div class=\"logo\">\n");
      out.write("\t\t\t<a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/index.jsp\"><img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/images/logo.png\" alt=\"\"></a>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<div class=\"h_right\">\n");
      out.write("\t\t\t<!--start menu -->\n");
      out.write("                        ");
if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
                        {
      out.write("\n");
      out.write("\t\t\t<ul class=\"menu\">\n");
      out.write("\t\t\t\t<li class=\"active\"><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/index.jsp\">Početna</a></li> \n");
      out.write("\t\t\t\t<li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/rezervacije.jsp\">rezervacije</a></li> \n");
      out.write("                                <li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/odjavljivanje\">odjavi se</a></li>\n");
      out.write("\t\t\t\t<div class=\"clear\"></div>\n");
      out.write("\t\t\t</ul>\n");
      out.write("                        ");

                         }
                        else
                        {
                        
      out.write("\n");
      out.write("                        <ul class=\"menu\">\n");
      out.write("\t\t\t\t<li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/index.jsp\">Početna</a></li> \n");
      out.write("\t\t\t\t<li class=\"active\"><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/rezervacije.jsp\">rezervacije</a></li> \n");
      out.write("                                <li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/login.jsp\">Login</a></li>\n");
      out.write("                                <li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/registracija.jsp\">Registracija</a></li>\n");
      out.write("\t\t\t\t<div class=\"clear\"></div>\n");
      out.write("\t\t\t</ul>\n");
      out.write("                                ");
}
      out.write("\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<div class=\"clear\"></div>\n");
      out.write("\t\t<div class=\"top-nav\">\n");
      out.write("\t\t<nav class=\"clearfix\">\n");
      out.write("                     ");
if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
                        {
      out.write("\n");
      out.write("\t\t\t\t<ul>\n");
      out.write("\t\t\t\t<li class=\"active\"><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/index.jsp\">Početna</a></li> \n");
      out.write("\t\t\t\t<li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/rezervacije.jsp\">rezervacije</a></li> \n");
      out.write("\t\t\t\t</ul>\n");
      out.write("\t\t\t\t<a href=\"#\" id=\"pull\">Menu</a>\n");
      out.write("                                 ");

                         }
                        else
                        {
                        
      out.write("\n");
      out.write("                                <ul>\n");
      out.write("\t\t\t\t<li class=\"active\"><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/index.jsp\">Početna</a></li> \n");
      out.write("\t\t\t\t<li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/rezervacije.jsp\">rezervacije</a></li> \n");
      out.write("                                <li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/login.jsp\">Login</a></li>\n");
      out.write("                                <li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/registracija.jsp\">Registracija</a></li>\n");
      out.write("\t\t\t\t</ul>\n");
      out.write("\t\t\t\t<a href=\"#\" id=\"pull\">Menu</a>\n");
      out.write("                        \n");
      out.write("                         ");
}
      out.write("\n");
      out.write("\t\t\t</nav>\n");
      out.write("\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("</div>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("       <img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/images/dubaicity.jpg\" alt=\"Dubai\" style=\"height: 550px; width: 100%;\"/>\n");
      out.write("<!--start main -->\n");
      out.write("<div class=\"main_bg\">\n");
      out.write("    <div class=\"wrap\">\n");
      out.write("        <div class=\"main\">\n");
      out.write("                        <div class=\"online_reservation\">\n");
      out.write("\t<div class=\"b_room\">\n");
      out.write("\t\t<div class=\"booking_room\">\n");
      out.write("\t\t\t<h4>book a room online</h4>\n");
      out.write("\t\t\t<p>Find hotel rooms</p>\n");
      out.write("\t\t</div>\n");
      out.write("\n");
      out.write("\t\t<div class=\"reservation\">\n");
      out.write("                    <form method=\"GET\" action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/PrikaziSobe\" name=\"search\">\n");
      out.write("\t\t\t<ul>\n");
      out.write("                                <li  class=\"span1_of_1 left\">\n");
      out.write("\t\t\t\t\t<h5>Grad</h5>\n");
      out.write("\t\t\t\t\t<div class=\"book_date\">\n");
      out.write("                                            \n");
      out.write("\t\t\t\t<input type=\"text\" name=\"lokacija\" required readonly=\"readonly\" value=\"Dubai\" oninvalid=\"this.setCustomValidity('Enter city here')\" oninput=\"this.setCustomValidity('')\">\n");
      out.write("                               \n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\n");
      out.write("\t\t\t\t</li>\n");
      out.write("                                <li class=\"span1_of_1\">\n");
      out.write("\t\t\t\t\t<h5>Sobe:</h5>\n");
      out.write("\t\t\t\t\t<div class=\"section_room\">\n");
      out.write("                                            <select name=\"sobe\" required oninvalid=\"this.setCustomValidity('Select number of rooms here.')\" oninput=\"this.setCustomValidity('')\">\n");
      out.write("                                                <option value=\"1\">1</option>         \n");
      out.write("                                                <option value=\"2\">2</option>\n");
      out.write("                                            </select>\n");
      out.write("\t\t\t\t\t</div>\t\n");
      out.write("\t\t\t\t</li>\n");
      out.write("\t\t\t\t<li  class=\"span1_of_1 left\">\n");
      out.write("\t\t\t\t\t<h5>Datum dolaska:</h5>\n");
      out.write("\t\t\t\t\t<div class=\"book_date\">\n");
      out.write("                                            <input class=\"date\" name=\"datumDolaska\" id=\"datepicker\" type=\"text\" required <!--oninvalid=\"this.setCustomValidity('Choose check in date here.')\" oninput=\"this.setCustomValidity('')\"-->\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\n");
      out.write("\t\t\t\t</li>\n");
      out.write("\t\t\t\t<li  class=\"span1_of_1 left\">\n");
      out.write("\t\t\t\t\t<h5>Datum odlaska:</h5>\n");
      out.write("\t\t\t\t\t<div class=\"book_date\">\n");
      out.write("                                            <input class=\"date\" name=\"datumOdlaska\" id=\"datepicker1\" type=\"text\" required <!--oninvalid=\"this.setCustomValidity('Choose check out date here.')\" oninput=\"this.setCustomValidity('')\"-->\n");
      out.write("\t\t\t\t\t</div>\t\t\n");
      out.write("\t\t\t\t</li>\n");
      out.write("\t\t\t\t<li class=\"span1_of_3\">\n");
      out.write("\t\t\t\t\t<h5>Paketi</h5>\n");
      out.write("\t\t\t\t\t<div class=\"section_room\">\n");
      out.write("                                            <select name=\"paket\" required>\n");
      out.write("                                                <option value=\"paket1\">Member rate</option>\n");
      out.write("                                                <option value=\"paket2\">Standard rate</option>\n");
      out.write("                                                <option value=\"paket3\">Bed and breakfast</option>\n");
      out.write("                                                <option value=\"paket4\">Double your points</option>\n");
      out.write("                                                <option value=\"paket5\">Explore package</option>\n");
      out.write("                                            </select>\n");
      out.write("\t\t\t\t\t</div>\t\n");
      out.write("\t\t\t\t</li>\n");
      out.write("\t\t\t\t<li class=\"span1_of_2 left\">\n");
      out.write("\t\t\t\t\t<h5>Odrasli:</h5>\n");
      out.write("\t\t\t\t\t<!----------start section_room----------->\n");
      out.write("\t\t\t\t\t<div class=\"section_room\">\n");
      out.write("\t\t\t\t\t\t<select name=\"odrasli\" required class=\"frm-field required\" oninvalid=\"this.setCustomValidity('Select number of adults here.')\" oninput=\"this.setCustomValidity('')\">\n");
      out.write("\t\t\t\t\t\t\t<option value=\"1\">1</option>\n");
      out.write("                                                        <option value=\"2\">2</option>         \n");
      out.write("                                                        <option value=\"3\">3</option>\n");
      out.write("\t\t\t\t\t\t\t<option value=\"4\">4</option>\n");
      out.write("                                                        <option value=\"5\">5</option>\n");
      out.write("\t\t        \t\t</select>\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\n");
      out.write("\t\t\t\t</li>\n");
      out.write("                                <li class=\"span1_of_2 left\">\n");
      out.write("\t\t\t\t\t<h5>Deca:</h5>\n");
      out.write("\t\t\t\t\t<!----------start section_room----------->\n");
      out.write("\t\t\t\t\t<div class=\"section_room\">\n");
      out.write("\t\t\t\t\t\t<select name=\"deca\" required class=\"frm-field required\" oninvalid=\"this.setCustomValidity('Select number of kids here.')\" oninput=\"this.setCustomValidity('')\">\n");
      out.write("\t\t\t\t\t\t\t<option value=\"0\">0</option>\n");
      out.write("                                                        <option value=\"1\">1</option>\n");
      out.write("                                                        <option value=\"2\">2</option>         \n");
      out.write("                                                        <option value=\"3\">3</option>\n");
      out.write("\t\t\t\t\t\t\t<option value=\"4\">4</option>\n");
      out.write("                                                        <option value=\"5\">5</option>\n");
      out.write("\t\t        \t\t</select>\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\n");
      out.write("\t\t\t\t</li>\n");
      out.write("\t\t\t\t<li class=\"span1_of_3\">\n");
      out.write("\t\t\t\t\t<!--<div class=\"date_btn\">\n");
      out.write("                                             <input type=\"submit\" value=\"FIND HOTELS\" onclick=\"return formValidation()\" />\n");
      out.write("\t\t\t\t\t</div>-->\n");
      out.write("\t\t\t\t</li>\n");
      out.write("\t\t\t\t<div class=\"clear\"></div>\n");
      out.write("\t\t\t</ul>\n");
      out.write("                    \n");
      out.write("\t\t</div>\n");
      out.write("\t\t<div class=\"clear\"></div>\n");
      out.write("\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("\t\t<div class=\"res_online\">\n");
      out.write("\t\t\t<h4>Hotels in Dubai</h4>\n");
      out.write("\t\t\t<p class=\"para\">With a name that's equated with luxury, Dubai promises an ultrachic ambience on the shores of the Arabian Gulf. Enjoy the view from Burj Khalifa—the tallest skyscraper in the world—or explore Palm Jumeirah, a sprawling man-made island. Eclectic shopping, dining, and nightlife scenes all make Dubai a coveted United Arab Emirates destination; explore it all from your Starling hotel in Dubai.</p>\n");
      out.write("\t\t</div>\t\t\t\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("</div>\t\n");
      out.write("<div class=\"wrapper\">\n");
      out.write("     ");
    
        try
        {
           
            
      out.write("\n");
      out.write("\n");
      out.write("            ");
  int i=1;
                while(rsPagination.next())
            { 
      out.write("\n");
      out.write("              <div class=\"product\">\n");
      out.write("            <div class=\"title\">\n");
      out.write("              ");
      out.print( rsPagination.getString("naziv"));
      out.write("\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <div class=\"text\">\n");
      out.write("              <div class=\"code\">Država: ");
      out.print( rsPagination.getString("drzava"));
      out.write("</div>\n");
      out.write("              <div class=\"code\">Grad: ");
      out.print( rsPagination.getString("grad"));
      out.write("</div>\n");
      out.write("              <div class=\"code\">Adresa: ");
      out.print( rsPagination.getString("adresa"));
      out.write("</div>\n");
      out.write("              <div class=\"description\">\n");
      out.write("                ");
      out.print( rsPagination.getString("opis"));
      out.write("\n");
      out.write("              </div>\n");
      out.write("              <div class=\"review\">\n");
      out.write("                  ");

                  if(rsPagination.getInt("zvezdice") == 1)
                  {
                  
      out.write("\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon star-disable\"></span>\n");
      out.write("                    <span class=\"star-icon star-disable\"></span>\n");
      out.write("                    <span class=\"star-icon star-disable\"></span>\n");
      out.write("                    <span class=\"star-icon star-disable\"></span>\n");
      out.write("                ");
}
                  else if(rsPagination.getInt("zvezdice") == 2)
                  {
                
      out.write("\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon star-disable\"></span>\n");
      out.write("                    <span class=\"star-icon star-disable\"></span>\n");
      out.write("                    <span class=\"star-icon star-disable\"></span>\n");
      out.write("                    \n");
      out.write("                   ");
}
                  else if(rsPagination.getInt("zvezdice") == 3)
                  {
                
      out.write("\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon star-disable\"></span>\n");
      out.write("                    <span class=\"star-icon star-disable\"></span>\n");
      out.write("                    \n");
      out.write("                   ");
}
                  else if(rsPagination.getInt("zvezdice") == 4)
                  {
                
      out.write("\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon star-disable\"></span>\n");
      out.write("                    \n");
      out.write("                   ");
}
                  else if(rsPagination.getInt("zvezdice") == 5)
                  {
                
      out.write("\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    <span class=\"star-icon\"></span>\n");
      out.write("                    \n");
      out.write("                    ");
}
      out.write("\n");
      out.write("                \n");
      out.write("                <span class=\"star-reviews\">");
      out.print( rsPagination.getInt("zvezdice"));
      out.write("</span>\n");
      out.write("              </div>    \n");
      out.write("              ");

                  Connection cons = null;
                  Statement stmts = null;
                  ResultSet rss = null;
                  try
                  {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    cons = DriverManager.getConnection("jdbc:mysql://localhost:3306/starling", "root", "");


                    stmts = cons.createStatement();
                    String query = "SELECT AVG(paket1) AS prosek FROM `tipovisoba` WHERE hotelID='"+rsPagination.getInt("ID")+"'";
                    rss = stmts.executeQuery(query); 
                    if(rss.first())
                    {
                        
      out.write("\n");
      out.write("                        <div class=\"price\">\n");
      out.write("                        $");
      out.print(rss.getInt("prosek"));
      out.write("\n");
      out.write("                        </div>\n");
      out.write("                        ");

                    }
                    rss.close();
                    stmts.close();
                    cons.close();
                  }
                  catch(Exception e)
                  {
                    e.printStackTrace();
                  }
              
      out.write("\n");
      out.write("              \n");
      out.write("              <div class=\"code\">AVG/Night from</div>\n");
      out.write("              <div class=\"shop-actions\">    \n");
      out.write("                  <input type=\"text\" name=\"hotelID\" required value=\"");
      out.print(rsPagination.getInt("ID"));
      out.write("\" hidden=\"true\">\n");
      out.write("                  <a class=\"product text shop-actions hot\" onclick=\"return formValidation()\">POGLEDAJ SOBE</a>\n");
      out.write("              </div>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <div class=\"preview\">\n");
      out.write("              <svg class=\"svg\" viewBox=\"0 0 200 200\" xmlns=\"http://www.w3.org/2000/svg\">\n");
      out.write("                <circle class=\"circle\" cx=\"100\" cy=\"100\" r=\"125\"/>\n");
      out.write("                ");

                      byte[] imgData = rsPagination.getBytes("slika"); // blob field 
                      String encode = Base64.getEncoder().encodeToString(imgData);
                  
      out.write("\n");
      out.write("                  <image class=\"image\" xlink:href=\"data:image/jpeg;base64,");
      out.print(encode);
      out.write("\" x=\"0\" y=\"0\" width=\"200px\" height=\"180px\"/>\n");
      out.write("              </svg>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("          </div>\n");
      out.write("              </form>\n");
      out.write("            \n");
      out.write("             ");
}
            rsPagination.close();
            rsRowCnt.close();
            psPagination.close();
            psRowCnt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    
      out.write("\n");
      out.write("            ");

        //racuna next start i end
        try{
            if(iTotalRows<(iPageNo+iShowRows))
            {
                iEndResultNo=iTotalRows;
            }
            else
            {
                iEndResultNo=(iPageNo+iShowRows);
            }
           
            iStartResultNo=(iPageNo+1);
            iTotalPages=((int)(Math.ceil((double)iTotalRows/iShowRows)));
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


      out.write("                     \n");
      out.write("        <div id=\"pagicontainer\">\n");
      out.write("            <div class=\"pagination\">\n");
      out.write("                                  ");

        //brojevi stranice
        int i=0;
        int cPage=0;
        if(iTotalRows!=0)
        {
        cPage=((int)(Math.ceil((double)iEndResultNo/(iTotalSearchRecords*iShowRows))));
        
        int prePageNo=(cPage*iTotalSearchRecords)-((iTotalSearchRecords-1)+iTotalSearchRecords);
        if((cPage*iTotalSearchRecords)-(iTotalSearchRecords)>0)
        {
         
      out.write("\n");
      out.write("          <a class='page gradient' href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/DubaiHotels.jsp?iPageNo=");
      out.print(prePageNo);
      out.write("&cPageNo=");
      out.print(prePageNo);
      out.write("\"> << Previous</a>\n");
      out.write("         ");

        }
        
        for(i=((cPage*iTotalSearchRecords)-(iTotalSearchRecords-1));i<=(cPage*iTotalSearchRecords);i++)
        {
          if(i==((iPageNo/iShowRows)+1))
          {
          
      out.write("\n");
      out.write("           <a class='page gradient' href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/DubaiHotels.jsp?iPageNo=");
      out.print(i);
      out.write("\" style=\"color: #59d\"><b>");
      out.print(i);
      out.write("</b></a>\n");
      out.write("          ");

          }
          else if(i<=iTotalPages)
          {
          
      out.write("\n");
      out.write("           <a class='page gradient' href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/DubaiHotels.jsp?iPageNo=");
      out.print(i);
      out.write('"');
      out.write('>');
      out.print(i);
      out.write("</a>\n");
      out.write("          ");
 
          }
        }
        if(iTotalPages>iTotalSearchRecords && i<iTotalPages)
        {
         
      out.write("\n");
      out.write("         <a class='page active' href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/hotels/DubaiHotels.jsp?iPageNo=");
      out.print(i);
      out.write("&cPageNo=");
      out.print(i);
      out.write("\"> >> Next</a> \n");
      out.write("         ");

        }
        }
      
      out.write("\n");
      out.write("\t</div>\n");
      out.write("        </div>\n");
      out.write("</div>\n");
      out.write("<!--start main -->\n");
      out.write("<!--<div class=\"footer_bg\">-->\n");
      out.write("<!--<div class=\"wrap\">-->\n");
      out.write("<div class=\"footer\">\n");
      out.write("                        <div class=\"footer-logo\">\n");
      out.write("                            <a href=\"#\"><img src=\"https://i.imgur.com/zM3yItK.png\" alt=\"scanfcode\"></a>\n");
      out.write("                        </div>\n");
      out.write("\t\t\t\n");
      out.write("\t\t\t<div class=\"f_nav\">\n");
      out.write("                            ");
if(request.getSession().getAttribute("user") != null && user.getPower().equals("Klijent"))
                        {
      out.write("\n");
      out.write("\t\t\t\t<ul>\n");
      out.write("\t\t\t\t\t<li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/index.jsp\">Početna</a></li>\n");
      out.write("\t\t\t\t\t<li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/rezervacije.jsp\">rezervacije</a></li>\n");
      out.write("\t\t\t\t</ul>\n");
      out.write("                                 ");

                         }
                        else
                        {
                        
      out.write("\n");
      out.write("                        \n");
      out.write("                                        <li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/index.jsp\">Početna</a></li>\n");
      out.write("\t\t\t\t\t<li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/rezervacije.jsp\">rezervacije</a></li>\n");
      out.write("                                        <li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/login.jsp\">Login</a></li>\n");
      out.write("                                        <li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/klijent/registracija.jsp\">Registracija</a></li>\n");
      out.write("                        \n");
      out.write("                         ");
}
      out.write("\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"soc_icons\">\n");
      out.write("\t\t\t\t<ul>\n");
      out.write("\t\t\t\t\t<li><a class=\"icon1\" href=\"#\"></a></li>\n");
      out.write("\t\t\t\t\t<li><a class=\"icon2\" href=\"#\"></a></li>\n");
      out.write("\t\t\t\t\t<li><a class=\"icon3\" href=\"#\"></a></li>\n");
      out.write("\t\t\t\t\t<li><a class=\"icon4\" href=\"#\"></a></li>\n");
      out.write("\t\t\t\t\t<li><a class=\"icon5\" href=\"#\"></a></li>\n");
      out.write("\t\t\t\t\t<div class=\"clear\"></div>\n");
      out.write("\t\t\t\t</ul>\t\n");
      out.write("\t\t\t</div>\n");
      out.write("                        <div class=\"copy\">\n");
      out.write("\t\t\t\t<p class=\"link\"><span>Copyright © 2018 - All rights reserved | <a href=\"#\"> STARLING HOTEL CHAIN</a></span></p>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"clear\"></div>\n");
      out.write("<!--</div>-->\n");
      out.write("<!--</div>-->\n");
      out.write("</div>\t\n");
      out.write("                                 <script type=\"text/javascript\">\n");
      out.write("                                    $(\".user-menu\").click(function() {\n");
      out.write("  $(this).toggleClass(\"show\");\n");
      out.write("});\n");
      out.write("\n");
      out.write("                                </script>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
