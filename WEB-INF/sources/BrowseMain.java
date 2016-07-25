
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import javax.naming.*;
public class BrowseMain extends HttpServlet
{

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html");    // Response mime type

        PrintWriter out = response.getWriter();

		String query = "SELECT * FROM genres ORDER BY name";
	
		ResultSet rs = executeSQL.execute(query);
	
		try{
			        out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"reset.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><link href=\"//fonts.googleapis.com/css?family=Crete+Round\" rel=\"stylesheet\" type=\"text/css\"><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <title>FabFlix</title></head><body><header><div class=\"wrapper\"><h1>FabFlix<span class=\"color\">.</span></h1> <nav><ul><li><a href=\"login.html\"><i class=\"material-icons\">person</i>Login</a></li><li><a href=\"Cart\"><i class=\"material-icons\">shopping_cart</i>Cart</a></li><li><a href=\"Search.jsp\"><i class=\"material-icons\">search</i>Search</a></li><li><a href=\"BrowseMain\"><i class=\"material-icons\">folder</i>Browse</a></li></ul></nav></div></header>");

                out.println("<div id=\"image\">");
			out.println("<center>");
			out.println("<h1 align=\"center\">Browse by Genre</h1>");
			while(rs!=null && rs.next()){
				String genre = rs.getString("name");
			out.println("<label style=\"font-size:18px; font-family:'Crete Round'; color:#444\">&nbsp&nbsp&nbsp<a href=\""+request.getContextPath()+"/Browse?order=t_asc&by=genre&query="+genre+"\">"+genre+"</a></label>");
			}
			out.println("<br><br><br><h1 align=\"center\">Browse by Title</h1>");
			for(char c = '0'; c <= '9'; c++){
				out.println("<label style=\"font-size:18px; font-family:'Crete Round'; color:#444\"><a href=\""+request.getContextPath()+"/Browse?order=t_asc&by=title&query="+c+"\">"+c+"</a></label>");
			}
			out.println("<br><br>");
			for(char c = 'A'; c <= 'Z'; c++){
				out.println("<label style=\"font-size:18px; font-family:'Crete Round'; color:#444\"><a href=\""+request.getContextPath()+"/Browse?order=t_asc&by=title&query="+c+"\">"+c+"</a></label>");
			}
/*
//
//
out.println("<br><br>Test-Test-Test<br>");
out.println("creating context");
Context initContext = new InitialContext();
if(initContext == null)
	out.println("initcontext null");
out.println("lookup env");
            Context envContext = (Context) initContext.lookup("java:comp/env");
if(envContext==null)
	out.println("envContext null");
out.println("lookup datasource");
            DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
if(ds == null)
	out.println("datasource null");
out.println("getting connection");
            Connection conn = ds.getConnection();
if(conn == null)
	out.println("connection null");
Statement statement = conn.createStatement();
out.println("execute statement");
String sql="SELECT title from movies limit 10";
ResultSet rstemp=statement.executeQuery(sql);
out.println("print results");
while(rs.next())
	out.println(rs.getString("title"));

//
//
*/
			out.println("</center>");
		}
        	catch (SQLException ex) {
			while (ex != null){
				System.out.println ("SQL Exception:  " + ex.getMessage ());
				ex = ex.getNextException ();
			}  // end while
		}  // end catch SQLException
		//
/*		//
		catch(NamingException ne){
			while(ne!=null){
			out.println("Naming Exception: "+ne.getMessage());
			ne=ne.getNextException();
			}
		}
*/

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
    }
}
