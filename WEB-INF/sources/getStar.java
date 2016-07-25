import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class getStar extends HttpServlet
{

    // Use http GET

	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
	{
		response.setContentType("text/html");    // Response mime type

		PrintWriter out = response.getWriter();

		try{
			String id = request.getParameter("star");
			if(id == null)
				return;
			else{
				String query = "SELECT * FROM stars WHERE id="+id;
				ResultSet rs = executeSQL.execute(query);
				if(!rs.next())
					return;
				String fn = rs.getString("first_name");
				String ln = rs.getString("last_name");
				String dob = rs.getString("dob");
				String photo = rs.getString("photo_url");
				String fullN = fn + " " + ln;
				out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"reset.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><link href=\"//fonts.googleapis.com/css?family=Crete+Round\" rel=\"stylesheet\" type=\"text/css\"><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <title>FabFlix</title></head><body><header><div class=\"wrapper\"><h1>FabFlix<span class=\"color\">.</span></h1> <nav><ul><li><a href=\"login.html\"><i class=\"material-icons\">person</i>Login</a></li><li><a href=\"Cart\"><i class=\"material-icons\">shopping_cart</i>Cart</a></li><li><a href=\"Search.jsp\"><i class=\"material-icons\">search</i>Search</a></li><li><a href=\"BrowseMain\"><i class=\"material-icons\">folder</i>Browse</a></li></ul></nav></div></header>");

                out.println("<div id=\"image\">"); 
				out.println("<center>");
				out.println("<h1>"+fullN+"</h1>");
				//out.println("<img src='"+photo+"'>");
				out.println("<table style=\"width:65%\">\n"+
					"<tr>\n"+
					"<td>ID:</td>"+
					"<td>"+id+"</td>"+
					"</tr>"+
					"<tr>"+
					"<td>Name:</td>"+
					"<td>"+fullN+"</td>"+
					"</tr>"+
					"<tr>"+
					"<td>DOB:</td>"+
					"<td>"+dob+"</td>"+
					"</tr>"+
					"<tr>"+
					"<td>Photo URL:</td>"+
					"<td><img src="+photo+"></td>"+
					"</tr>"+
					"<tr>"+
					"<td>Movies:</td>"+
					"<td>");
				printMovies(id, out,request);
				out.println("</td>"+
					"</tr>"+
					"</table>");
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
	public void printMovies(String id, PrintWriter out, HttpServletRequest request) throws Exception
	{
		String subquery = "SELECT movie_id FROM stars_in_movies WHERE star_id="+id;
		String query = "SELECT * FROM movies WHERE id IN (" + subquery + ")";
		ResultSet rs = executeSQL.execute(query);
		if(rs.next())
			out.print("<a href=\""+request.getContextPath()+"/getMovie?id=" + rs.getInt("id") + "\">"+rs.getString("title")+"</a>");
		while(rs.next()){
			out.print(", ");
			out.print("<a href=\""+request.getContextPath()+"/getMovie?id=" + rs.getInt("id") + "\">"+rs.getString("title")+"</a>");
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
	{
		doGet(request, response);
	}
}
