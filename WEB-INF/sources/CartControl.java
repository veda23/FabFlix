//package New;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


// Servlet implementation class CartControl

//@WebServlet("/CartControl")
public class CartControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LinkedList<Movie> list=new LinkedList<Movie>();
	
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		  PrintWriter out=response.getWriter();
		  String strAction = request.getParameter("action");
		  HttpSession session=request.getSession();
		  if(strAction.equals("Empty Cart")){
			  

			  
			  session.setAttribute("list", new ArrayList<Movie>());
			 			  
			  out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"reset.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><link href=\"//fonts.googleapis.com/css?family=Crete+Round\" rel=\"stylesheet\" type=\"text/css\"><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <title>FabFlix</title></head><body><header><div class=\"wrapper\"><h1>FabFlix<span class=\"color\">.</span></h1> <nav><ul><li><a href=\"login.html\"><i class=\"material-icons\">person</i>Login</a></li><li><a href=\"Cart\"><i class=\"material-icons\">shopping_cart</i>Cart</a></li><li><a href=\"Search.jsp\"><i class=\"material-icons\">search</i>Search</a></li><li><a href=\"BrowseMain\"><i class=\"material-icons\">folder</i>Browse</a></li></ul></nav></div></header>");

                out.println("<div id=\"image\">");
			response.sendRedirect("Cart");
			 // out.println("<p>Your cart is empty now</p><p>Continue shopping to add movies!");
		  }
		  if(strAction.equals("Update")){
			  
		  }
		  
		  
	}

}
