import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

 
public class AndroidLogin extends HttpServlet {
 
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  	
	        String loginUser = "root";
        	String loginPasswd = "root";
        	String loginUrl = "jdbc:mysql://localhost:3306/moviedb";


        // Output stream to STDOUT
	 	PrintWriter out = response.getWriter();
 		String n=request.getParameter("email");
        	String p=request.getParameter("password");
	//	out.println(n);   
//	Statement stmt = null;
		int count=0;
        	try
           	{
              		//Class.forName("org.gjt.mm.mysql.Driver");
              		Class.forName("com.mysql.jdbc.Driver").newInstance();

              		Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
//	        stmt = dbcon.createStatement();
//	      	String sql = "select * from movies where title=\"Shrek\"";
//		ResultSet rs = stmt.executeQuery(sql);
                 
              		String sql = "select * from customers where email=? and password=?";
              		PreparedStatement ps = dbcon.prepareStatement(sql);
             	 	ps.setString(1, n);
		        ps.setString(2, p);
              		ResultSet rs = ps.executeQuery();
            		while(rs.next()) {
          			count++;      		

            		}
	   
	    		if(count > 0)
				out.println("Login Successful");
	    		else
				out.println("Login not successful");
        	} catch (Exception e) {
            		e.printStackTrace();
        	}
        //System.out.println(json);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		doGet(request, response);
    }

}
