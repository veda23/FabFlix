//package New;

//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class Cart
 */

//Import required java libraries
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.*;

import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


//Extend HttpServlet class
//@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String message,init,style;
	LinkedList<Movie> list=new LinkedList<Movie>();
	LinkedList<Movie> sessionlist=new LinkedList<Movie>();
	String cost="$10";
	int price=10;
	int total=0;
	int items=0;

	public void init() throws ServletException
	{
		// Do required initialization
		//style= "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">";
	
		message = "Your Cart";
		/*init="<form action=\"Checkout.jsp\" method=\"get\">"+
		"<br><center><input type=\"submit\" name=\"action\" value=\"Checkout\" align=\"bottom\"></center>"+
		"</form>"+
		"<form action=\"CartControl\" method=\"get\">"+
		"<br><center><input type=\"submit\" name=\"action\" value=\"Empty Cart\" align=\"bottom\" > ";*/
		items=0;
		total=0;
	}

	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException
	{
		String loginUser = "root";
		String loginPasswd = "root";
		String loginUrl = "jdbc:mysql:///moviedb";
		String title="";
		String director="";
		int year=0000;
		String banner="";
		String trailer="";

		response.setContentType("text/html");    // Response mime type

		// Output stream to STDOUT
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);	

		try
		{
			Class.forName("org.gjt.mm.mysql.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			//Statement statement = dbcon.createStatement();

			String movieid = request.getParameter("id");
			out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"reset.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><link href=\"//fonts.googleapis.com/css?family=Crete+Round\" rel=\"stylesheet\" type=\"text/css\"><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <title>FabFlix</title></head><body><header><div class=\"wrapper\"><h1>FabFlix<span class=\"color\">.</span></h1> <nav><ul><li><a href=\"login.html\"><i class=\"material-icons\">person</i>Login</a></li><li><a href=\"Cart\"><i class=\"material-icons\">shopping_cart</i>Cart</a></li><li><a href=\"Search.jsp\"><i class=\"material-icons\">search</i>Search</a></li><li><a href=\"BrowseMain\"><i class=\"material-icons\">folder</i>Browse</a></li></ul></nav></div></header>");

			out.println("<div id=\"image\">");
			out.println("<body><center><h1>" + message + "</h1></center>");//<p style=\"font-size:16px; font-family:Crete Round; color:#444; align:center\">Your Cart contains:</p>");
			ArrayList<Movie> workingList;
			if(movieid != null){
				//String movieid= "908";
				String query="select * from movies where id=?";
				PreparedStatement ps= dbcon.prepareStatement(query);
				ps.setString(1, movieid);
				//ps.setInt(1, 905);
				ResultSet result=ps.executeQuery();
			out.println("<div id=\"image\">");
				out.println("<table class=\"center\">");//<tr><td>Your Cart Contains:</td></tr>");
							
				workingList = (ArrayList<Movie>)session.getAttribute("list");
				if(workingList == null)
				workingList = new ArrayList<Movie>();
				if(!result.next())
					return;
				title=result.getString("title");
				trailer=result.getString("trailer_url");
				Movie m = new Movie(title, trailer, movieid);
				workingList.add(m);
				session.setAttribute("list",workingList);
				//for(Movie mov : workingList) {
					out.println("<tr><td>Added "+ m.title+" to your cart</td></tr></table>");
				//}
				
			}
			else{
				workingList = (ArrayList<Movie>)session.getAttribute("list");
				if(workingList == null || workingList.size() == 0)
					out.println("<table class=\"center\"><tr><td>Your cart is empty</td></tr>");
				else{
					out.println("<table class=\"center\">");
					for(Movie mov : workingList) {
						out.println("<tr><td>" + mov.title+ "</td>" + 
							"<td><a href=\""+request.getContextPath()+"/CartMod?id="+mov.movieid+"&count=1\">&nbsp&nbsp+&nbsp&nbsp</a></td>"+
							"<td>"+Integer.toString(mov.count)+"</td>"+
							"<td><a href=\""+request.getContextPath()+"/CartMod?id="+mov.movieid+"&count=-1\">&nbsp&nbsp-&nbsp&nbsp</a></td>"+
							"<td><a href=\""+request.getContextPath()+"/CartMod?id="+mov.movieid+"&count=0\">&nbsp&nbsp&nbspRemove</a></td>" + 
							"</tr>");
					}
					out.println("</table>");
				}
				//items = workingList.size();
				/*if(workingList != null){
					Iterator<Movie>i = workingList.iterator();
					while(i.hasNext()){
						out.println(i.next().title+"<br>");
					}
				}*/
			}
				items = 0;
				for(Movie mov:workingList){
					items +=mov.count;
				}
				out.println("<br><br><br><table class=\"center\"><tr><td>Total no. of items in cart="+ items +"</td></tr><tr><td>Total price="+items * price+"</td></tr></table>");
				out.println("<form action=\"Checkout.jsp\" method=\"get\">"+"<center><input type=\"submit\" name=\"action\" value=\"Checkout\"></center>"+"</form>");
				out.println("<form action=\"CartControl\" method=\"get\">"+"<center><input type=\"submit\" name=\"action\" value=\"Empty Cart\"></center></form>");
				/*out.println("<p style=\"font-size:16px; font-family: Crete Round; color:#444; align:center\">"+init+"</p>");*/
/*
			if(session.getAttribute("list")==null){
				if(movieid==null){
					out.println("<p>Your Cart is empty!</p>");
				}
				else{
					LinkedList<Movie> newlist=new LinkedList<Movie>();
					
					session.setAttribute("list", newlist);
					while(result.next()){

						title=result.getString("title");
						out.println("<p>"+title+"</p>");
						
						banner=result.getString("banner_url");
						out.println("<p><img src ="+"banner"+"/></p>");
					}	
					Movie m=new Movie(title,trailer,movieid);

					AddToList(m,session,out);
					out.println("<div><p><table><tr><td>Movie</td><td>Price</td><td>Quantity</td></tr></table></div>");
					print(list,out);
					//String feedback= request.getParameter("Quantity");
					//System.out.println(feedback);
				}
				
			}
			else
			{
				list=(LinkedList<Movie>)session.getAttribute("list");
				

				//out.println("<table></table>");

				while(result.next()){

					title=result.getString("title");
					out.println("<p>"+title+"</p>");
					
					banner=result.getString("banner_url");
					out.println("<p><img src ="+"banner"+"/></p>");
					

					Movie m=new Movie(title,banner,movieid);

					AddToList(m,session,out);
					out.println("<div><p><table><tr><td>Movie</td><td>Price</td><td>Quantity</td></tr></table></div>");
					print(list,out);
					//String feedback= request.getParameter("Quantity");
					//System.out.println(feedback);
				}
			}

		//out.println("<p>Total no. of items in cart="+ items +"</p><p>Total price="+total+"</p>");
			out.println("<p>"+init+"</p>");
			//String action=request.getParameter("action");
			System.out.println(action);
			if(action.equals("Checkout")){
				RequestDispatcher rd=request.getRequestDispatcher("CheckOut");
				rd.forward(request, response);
			}
			if(action.equals("Empty Cart")){
				items=0;
				total=0;
				RequestDispatcher rd1=request.getRequestDispatcher("CartControl");
				rd1.forward(request, response);
			}
			if(session!=null){
				session=null;
				session.invalidate();
				items=0;
				total=0;
			}
*/
			
			//out.println("<p>"+init+"</p>");
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}

	public void AddToList(Movie movie,HttpSession session,PrintWriter out){
		if(session.getAttribute("list")!=null){
			sessionlist=(LinkedList)session.getAttribute("list");
			Iterator t=sessionlist.iterator();
			while(t.hasNext()){
				if(((Movie)t.next()).movieid==movie.movieid){
				(((Movie)t.next()).count)++;
				session.setAttribute("list",sessionlist);
				}
				else{
					list.add(movie);
					session.setAttribute("list",list);
					
				}
			}
		}
		
		out.println("<p style=\"font-size:16px; font-family:Crete Round; color:#444\"><"+movie.title+" has been added to your cart!</p></html>");
		++items;
		total=total+price;
	}

	public void print(LinkedList<Movie> list,PrintWriter out){
		Iterator t=list.iterator();
		
		/*out.println("<br><right><select name=\"Quantity\">"+
				"<option value=\"1\">1</option>"+
				"<option value=\"2\">2</option>"+
				"<option value=\"3\">3</option>"+
				"<option value=\"4\">4</option>"+
				"<option value=\"5\">5</option>"+
				"<option value=\"6\">6</option>"+
				"<option value=\"7\">7</option>"+
				"<option value=\"8\">8</option>"+
				"<option value=\"9\">9</option>"+
				"<option value=\"10\">10</option>"+
				"</right></select>");
		out.println("<input= \"hidden\" name=\"Quantity\">");
		*/
		while(t.hasNext()){

			out.println("<table style:\"font-size:16px\"><tr><td>"+ ((Movie)t.next()).title+"</td><td>"+price+"</td><td>"+((Movie)t.next()).count+"</tr></table></p>");
			//total+=price;

		}

	}

}
