
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddStar extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

        String loginUser = "root";
        String loginPasswd = "root";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();


        try{
		Class.forName("org.gjt.mm.mysql.Driver");
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
                Statement stmt = dbcon.createStatement();

		String firstname = request.getParameter("first_name");
		String lastname = request.getParameter("last_name");
		

                out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"reset.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><link href='//fonts.googleapis.com/css?family=Crete+Round' rel='stylesheet' type='text/css'><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>FabFlix</title></head><body><header><div class=\"wrapper\"><h1>FabFlix<span class=\"color\">.</span></h1><nav><ul><li><a href=\"dashboard.html\"><i class=\"material-icons\">person</i>Logout</a></li><li><a href=\"dashboardmain.html\"><i class=\"material-icons\">settings</i>Options</a></li></ul></nav></div></header><div id=\"image\"><br><br><br><br><br><h4 align=\"center\">");
		
		if(lastname == null || lastname == ""){
			RequestDispatcher rd = request.getRequestDispatcher("starincorrect.html");
			rd.forward(request, response);
		}
		else{
			String columns = "(first_name,last_name)";
			String[] arr = new String[2];
			arr[0] = firstname;
			arr[1] = lastname;
			String values;
			if(arr.length > 1)
				values = "(\""+arr[0]+"\",";
			else
				values="(\"\",";
			values = values+"\""+arr[1]+"\")";

//			ResultSet rs = stmt.executeQuery("select name from mysql.proc where name='insert_star'");
//			if(!rs.next())
//				stmt.execute("CREATE PROCEDURE insert_star (IN fn VARCHAR(100), ln VARCHAR(100)) BEGIN INSERT INTO stars (first_name,last_name) VALUES(fn,ln) END");

			CallableStatement cs = dbcon.prepareCall("{CALL insert_star(?,?)}");
			cs.setString("fn",firstname);
			cs.setString("ln",lastname);			
		
			//out.println(cs.toString()+"<br>");

			cs.execute();
	
			out.println("Record successfully inserted into database");
			out.println("<br><br><br><br>");
			out.println("Go to");
			out.println("<a href=\"dashboardmain.html\" style=\"color:rgba(255,255,255,255)\">Options</a>");
		}
		out.println("</h1><script src='//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script></div></body></html>");
	
	        //RequestDispatcher rd = request.getRequestDispatcher("dashboardwelcome.html");
                //rd.forward(request,response);
		
	//	if(firstname.equals(""))
	//		firstname = "noooo";
	//	out.println(firstname);
	//	out.println(lastname);
			

		//ps.close();
		//statement.close();
		dbcon.close();
	}
        catch (SQLException ex) {
                while (ex != null) {
                	out.println ("SQL Exception:  " + ex.getMessage ());
                	ex = ex.getNextException ();
                }  // end while
        }  // end catch SQLException

        catch(java.lang.Exception ex){
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
        }
        
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
    }
}
