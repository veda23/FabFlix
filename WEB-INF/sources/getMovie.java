import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class getMovie extends HttpServlet
{

    // Use http GET

	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
	{
		response.setContentType("text/html");    // Response mime type

		PrintWriter out = response.getWriter();

		try{
			String id = request.getParameter("id");
			if(id == null)
				return;
			else{
				String query = "SELECT * FROM movies WHERE id="+id;
				ResultSet rs = executeSQL.execute(query);
				if(!rs.next())
					return;
				String title = rs.getString("title");
				String year = rs.getString("year");
				String director = rs.getString("director");
				String banner = rs.getString("banner_url");
				String trailer = rs.getString("trailer_url");
		                out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"reset.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><link href=\"//fonts.googleapis.com/css?family=Crete+Round\" rel=\"stylesheet\" type=\"text/css\"><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <title>FabFlix</title></head><body><header><div class=\"wrapper\"><h1>FabFlix<span class=\"color\">.</span></h1><nav><ul><li><a href=\"login.html\"><i class=\"material-icons\">person</i>Login</a></li><li><a href=\"Cart\"><i class=\"material-icons\">shopping_cart</i>Cart</a></li><li><a href=\"Search.jsp\"><i class=\"material-icons\">search</i>Search</a></li><li><a href=\"BrowseMain\"><i class=\"material-icons\">folder</i>Browse</a></li></ul></nav></div></header>");

                		out.println("<div id=\"image\">");
				out.println("<center>");
				out.println("<h1>"+title+"</h1>");
				out.println("<table style=\"width:65%\">\n"+
					"<tr>\n"+
					"<td>ID:</td>"+
					"<td>"+id+"</td>"+
					"</tr>"+
					"<tr>"+
					"<td>Title:</td>"+
					"<td>"+title+"</td>"+
					"</tr>"+
					"<tr>"+
					"<td>Year:</td>"+
					"<td>"+year+"</td>"+
					"</tr>"+
					"<tr>"+
					"<td>Director:</td>"+
					"<td>"+director+"</td>"+
					"</tr>"+
					"<tr>"+
					"<td>Genres:</td>"+
					"<td>");
				MovieDisplay.printGenres(id, out,"t_asc",request.getContextPath());
				out.println("</td>"+
					"</tr>"+
					"<tr>"+
					"<td>Stars:</td>"+
					"<td>");
				out.println(MovieDisplay.printStars(id, out,request.getContextPath()));
				out.println("</td>"+
					"</tr>"+
					"<tr>"+
					"<td>Banner URL:</td>"+
					"<td>"+"<img src="+banner+"></td>"+
					"</tr>"+
					"<tr>"+
					"<td>Trailer URL:</td>"+
					"<td><a href=\""+trailer+"\">"+trailer+"</a></td>"+
					"</tr>"+
					"</table>");
				out.println("<a href=\""+request.getContextPath()+"/Cart?id="+id+"\"><img src=\"Cart-Icon.png\" height=\"20\" width=\"20\"> Add to Cart - $10.00</a>");
				out.println("</center>");
			}
		}
        	catch (SQLException ex) {
			while (ex != null){
				out.println ("SQL Exception:  " + ex.getMessage ());
				ex = ex.getNextException ();
			}  // end while
		}  // end catch SQLException*/
		catch (Exception e){
			
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
	{
		doGet(request, response);
	}
}
