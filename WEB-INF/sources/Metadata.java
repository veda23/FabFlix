
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Metadata extends HttpServlet
{
    // Use http GET
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

        String loginUser = "root";
        String loginPasswd = "root";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
	
        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

out.println("<!DOCTYPE HTML><html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"reset.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><link href='//fonts.googleapis.com/css?family=Crete+Round' rel='stylesheet' type='text/css'><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>FabFlix</title></head><body><header><div class=\"wrapper\"><h1>FabFlix<span class=\"color\">.</span></h1><nav><ul><li><a href=\"dashboard.html\"><i class=\"material-icons\">person</i>Logout</a></li><li><a href=\"dashboardmain.html\"><i class=\"material-icons\">settings</i>Options</a></li></ul></nav></div></header><div id=\"image\">");

        try
           {
		Class.forName("org.gjt.mm.mysql.Driver");
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);           
		Statement stmt = connection.createStatement();	      
		DatabaseMetaData meta = connection.getMetaData();
		ResultSet tables = meta.getTables(null,null,"%",null);
		out.println("<center>");
		out.println("<h1>Database Metadata</h1>");
		while(tables.next()){
			ResultSet rs = stmt.executeQuery("select * from "+tables.getString(3));
			   out.println("Table: "+tables.getString(3)+"<br>");
			ResultSetMetaData rsm = rs.getMetaData();
			int colCt = rsm.getColumnCount();
			for(int i = 1; i<=colCt; i++){
				out.println("Name and type: "+rsm.getColumnName(i)+", "+rsm.getColumnTypeName(i)+"<br>");
			}
			out.println("<br>");
		}
		out.println("</center>");
		out.println("<script src='//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script></div></body></html>");
		stmt.close();
		connection.close();
	}
        catch (SQLException ex) {
              while (ex != null) {
                    out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
        // out.close();
  }
 
/* 
 public void print_result_set_metadata(ResultSet rs) throws Exception{
         ResultSetMetaData rsdb = rs.getMetaData();
         int colNo = rsdb.getColumnCount();
         for(int i = 1; i<= colNo; i++){
                 System.out.println("Name and type: "+rsdb.getColumnName(i) + ", " + rsdb.getColumnTypeName(i));
         }
         System.out.print("\n");
 }
 */
 

public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
    }
}
