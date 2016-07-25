//package New;

//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class Dashboard
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
//@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
	
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException
	{
		String loginUser = "root";
		String loginPasswd = "root";
		String loginUrl = "jdbc:mysql:///moviedbtemp";
	

		response.setContentType("text/html");    // Response mime type

		// Output stream to STDOUT
		PrintWriter out = response.getWriter();
			

		try
		{
			Class.forName("org.gjt.mm.mysql.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			//Statement statement = dbcon.createStatement();
			
			String option = request.getParameter("options");
			
			if(option.equals("Add Star")){
				RequestDispatcher rd = request.getRequestDispatcher("addstar.html");
			        rd.forward(request,response);
			}
			
			else if(option.equals("Add Movie")){
				RequestDispatcher rd = request.getRequestDispatcher("addmovie.html");
				rd.forward(request,response);
			}
			else if(option.equals("Get Metadata")){
				RequestDispatcher rd = request.getRequestDispatcher("Metadata");
				rd.forward(request,response);
				//out.println("Get Metadata");
			}
			else
				out.println("");
			dbcon.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	
	}



}
