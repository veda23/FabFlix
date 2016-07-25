
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CheckOut extends HttpServlet
{

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	//get list from session
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	HttpSession session = request.getSession(false);
	ArrayList<Movie> cart = (ArrayList)session.getAttribute("list");
	String fn = request.getParameter("first_name");
	String ln = request.getParameter("last_name");
	String cc = request.getParameter("cc");
	String ex = request.getParameter("exp");
	String q = "SELECT * FROM creditcards WHERE first_name='"+fn+"' AND last_name='"+ln+"' AND id='"+cc+"' AND expiration='"+ex+"'";
	java.util.Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	out.println("<p>"+query+"</p>");
	try{

		ResultSet rs = executeSQL.execute(q);


		out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"reset.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><link href=\"//fonts.googleapis.com/css?family=Crete+Round\" rel=\"stylesheet\" type=\"text/css\"><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <title>FabFlix</title></head><body><header><div class=\"wrapper\"><h1>FabFlix<span class=\"color\">.</span></h1> <nav><ul><li><a href=\"login.html\"><i class=\"material-icons\">person</i>Login</a></li><li><a href=\"login.html\"><i class=\"material-icons\">shopping_cart</i>Cart</a></li><li><a href=\"Search.jsp\"><i class=\"material-icons\">search</i>Search</a></li><li><a href=\"BrowseMain\"><i class=\"material-icons\">folder</i>Browse</a></li></ul></nav></div></header>");

                out.println("<div id=\"image\">");
		if(!rs.next()){
			//out.println("<center>Error: Payment information does not exist</center>");
			session.setAttribute("error","cc_error");
			//getServletContext().getRequestDispatcher("/Checkout.jsp").forward(request,response);
			out.println("<meta http-equiv=\"refresh\" content=\"0;"+request.getContextPath()+"/Checkout.jsp\"/>");
		}
		else{

			ResultSet rs2 = executeSQL.execute("SELECT * FROM customers where cc_id="+rs.getString("id"));

			rs2.next();
			out.println("<center><h1>Purchase Summary<h1><br>");
			for(Movie mov : cart){
				for(int i=0;i<mov.count;i++){
					String query = "INSERT INTO sales (customer_id,movie_id,sale_date) VALUES ('"+ rs2.getString("id") + "','"+mov.movieid+"','"+sdf.format(date)+"')";
					//out.println(query);
			//		out.println(mov.title+"<br>");
					
					executeSQL.update(query);
					

					session.setAttribute("list",new ArrayList<Movie>());	
				}
				out.println("<p style=\"font-size:20px; font-family: Crete Round; color:#444\">" + mov.title+" X "+mov.count+ "</p><br>");
				
			}
			out.println("</center>");
			//for loop to add to sales table
		}
		out.println("<center><p style=\"font-size:16px; font-family: Crete Round; color:#444\">Your payment was successful!</p></center>");
	}catch(SQLException e){
		e.getNextException();	
	}	
    }
}
