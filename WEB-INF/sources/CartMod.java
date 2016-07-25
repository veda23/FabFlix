//package New;

//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class Cart
 */

//Import required java libraries
import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


//Extend HttpServlet class
//@WebServlet("/Cart")
public class CartMod extends HttpServlet {

	public void init() throws ServletException
	{
		// Do required initialization
		//style= "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">";
	
	}

	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException
	{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();	
//try{
		String count = request.getParameter("count");
		String movieid = request.getParameter("id");
/*
		ResultSet rs = executeSQL.execute("SELECT * FROM movies WHERE id="+movieid);
		rs.next();
		String title=rs.getString("title");
		String trailer=rs.getString("trailer_url");
		Movie m = new Movie(title, trailer, movieid);
*/
		ArrayList<Movie>workingList = (ArrayList<Movie>)session.getAttribute("list");
		
		for(Movie mov : workingList){
			if(Integer.parseInt(mov.movieid) == Integer.parseInt(movieid)){
				if(Integer.parseInt(count) == 0){
					int i = workingList.indexOf(mov);
					workingList.remove(i);
					break;
				}
				else if(Integer.parseInt(count) == 1){
					mov.count=mov.count+1;	
				}
				else if(Integer.parseInt(count) == -1){
					mov.count = mov.count-1;
					if(mov.count <= 0)
						workingList.remove(mov);
				}
			}
		}

		session.setAttribute("list",workingList);
		response.sendRedirect("Cart");
		//request.getRequestDispatcher("Cart").include(request,response);
//}catch(SQLException e){
//}

	}


}
